package fi.arcusys.example.osgi.twitterservice.impl;

import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Deactivate;
import aQute.configurable.Configurable;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import fi.arcusys.example.osgi.twitterservice.MyConfig;
import fi.arcusys.example.osgi.twitterservice.TwitterService;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

@Component(name = "TwitterService",
        properties = "service.pid=fi.arcusys.example.osgi.twitterservice.MyConfig")
public class TwitterServiceImpl implements TwitterService, ManagedService {

    private MyConfig config = () -> DEFAULT_TERMS;
    public static final  String[] DEFAULT_TERMS = new String[]{"angularjs", "reactjs", "es6",
            "javascript", "es2015", "typescript", "vuejs", "cyclejs"};

    BlockingQueue<String> msgQueue = new LinkedBlockingQueue<>(100000);
    Client hosebirdClient;

    @Activate
    public void activate() {
        System.out.println("Activating the twitter service");
    }

    @Override
    public String[] getCurrentTerms() {
        return config.terms();
    }

    public String getOneStuff() {
        String msg = null;
        try {
            msg = msgQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return msg;
    }

    public String getNext() throws InterruptedException {
        return msgQueue.take();
    }

    public boolean isEmitting() {
        return !hosebirdClient.isDone();
    }

    public Client getClient() {
        return hosebirdClient;
    }

    @Deactivate
    public void deactivate() {
        hosebirdClient.stop();
    }

    @Override
    public void updated(Dictionary<String, ?> dictionary) throws ConfigurationException {
        System.out.println("Update configuration in Twitter Service");
        if (dictionary != null) {
            MyConfig c = Configurable.createConfigurable(
                    MyConfig.class, dictionary);
            if (c.terms() == null || c.terms().length == 0) {
                config = () -> DEFAULT_TERMS;
            } else {
                config = c;
            }
        }
        initTwitterStream();
    }

    private void initTwitterStream() {
        Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();

        System.out.println("initializing twitter strean with terms: " + Arrays.asList(config.terms())
                .stream().collect(Collectors.joining(", ")));

        hosebirdEndpoint.trackTerms(Arrays.asList(config.terms()));
        Authentication hosebirdAuth = new OAuth1("",
                "",
                "",
                "");

        ClientBuilder builder = new ClientBuilder()
                .hosts(hosebirdHosts)
                .authentication(hosebirdAuth)
                .endpoint(hosebirdEndpoint)
                .processor(new StringDelimitedProcessor(msgQueue));

        hosebirdClient = builder.build();
        hosebirdClient.connect();
    }
}
