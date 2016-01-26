package fi.arcusys.example.osgi.twitterservice;

public interface TwitterService {
    String getOneStuff();
    String getNext() throws InterruptedException;
    boolean isEmitting();
}
