package fi.arcusys.example.osgi.twitterservice.impl;

import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Deactivate;
import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import fi.arcusys.example.osgi.twitterservice.TwitterService;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component(name = "TwitterService")
public class TwitterServiceImpl implements TwitterService {
    BlockingQueue<String> msgQueue = new LinkedBlockingQueue<>(100000);
    Client hosebirdClient;

    @Activate
    public void activate() {
        System.out.println("Activating the twitter service");

        Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();
        List<String> terms = Lists.newArrayList("angularjs", "reactjs", "es6",
                "javascript", "es2015", "typescript", "vuejs", "cyclejs");
        hosebirdEndpoint.trackTerms(terms);
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

}
