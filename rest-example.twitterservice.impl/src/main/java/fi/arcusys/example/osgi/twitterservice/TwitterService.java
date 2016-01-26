package fi.arcusys.example.osgi.twitterservice;

public interface TwitterService {
    String[] getCurrentTerms();
    String getOneStuff();
    String getNext() throws InterruptedException;
    boolean isEmitting();
}
