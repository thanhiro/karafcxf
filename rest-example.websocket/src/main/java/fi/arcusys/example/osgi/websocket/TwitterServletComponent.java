package fi.arcusys.example.osgi.websocket;

import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Deactivate;
import aQute.bnd.annotation.component.Reference;
import fi.arcusys.example.osgi.twitterservice.TwitterService;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

import javax.servlet.ServletException;

@Component
public class TwitterServletComponent {
    private static final String SERVLET_PATH = "/twiit";
    private static final String OTHER_SERVLET_PATH = "/plain-from-ds";

    private TwitterService twitterService;
    private HttpService httpService;

    @Reference
    public void setHttpService(HttpService httpService) {
        System.out.println("setting the http service!");
        this.httpService = httpService;
    }

    @Reference
    public void setTwitterService(final TwitterService twitterService) {
        System.out.println("setting the twittah service!");
        this.twitterService = twitterService;
    }

    @Activate
    protected void startup() {
        try {
            httpService.registerServlet(SERVLET_PATH, new WebSocketServlet() {
                @Override
                public void configure(WebSocketServletFactory factory) {
                    factory.getPolicy().setIdleTimeout(10000);
                    factory.setCreator((req, resp) -> new TwitterSocket(twitterService));
                }
            }, null, null);
            httpService.registerServlet(OTHER_SERVLET_PATH, new PlainServlet(), null, null);
        } catch (ServletException | NamespaceException e) {
            e.printStackTrace();
        }
    }

    @Deactivate
    protected void shutdown() {
        httpService.unregister(SERVLET_PATH);
        httpService.unregister(OTHER_SERVLET_PATH);
    }
}
