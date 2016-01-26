package fi.arcusys.example.osgi.websocket;

import fi.arcusys.example.osgi.twitterservice.TwitterService;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;

@WebSocket
public class TwitterSocket {
    private TwitterService twitterService;

    private static final Logger LOG = Log.getLogger(TwitterSocket.class);
    private Session session;
    private org.eclipse.jetty.websocket.api.RemoteEndpoint remote;

    public TwitterSocket(TwitterService twitterService) {
        System.out.println("Creating the TwitterSocket");
        this.twitterService = twitterService;
    }

    @OnWebSocketClose
    public void onClose(Session session, int status, String reason) {
        this.session = null;
        this.remote = null;
    }

    @OnWebSocketConnect
    public void onWebSocketOpen(Session session) {
        System.out.println("going to open");
        this.session = session;
        this.remote = this.session.getRemote();
        LOG.info("WebSocket Connect: {}", session);
        try {
            this.remote.sendString("{\"text\": \"You are now connected to " +
                    this.getClass().getName() + "\"}");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // start to emit twitter messages
        while (twitterService.isEmitting()) {
            try {
                String json = twitterService.getNext();
                this.remote.sendString(json);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @OnWebSocketMessage
    public void onWebSocketText(Session session, String message) {
        LOG.info("Echoing back text message [{}]", message);
        try {
            remote.sendString(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}