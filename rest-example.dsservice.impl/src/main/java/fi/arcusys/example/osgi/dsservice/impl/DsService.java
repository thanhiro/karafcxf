package fi.arcusys.example.osgi.dsservice.impl;

import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Reference;
import fi.arcusys.example.osgi.twitterservice.TwitterService;

@Component(name = "TheDsService")
public class DsService {

    private TwitterService twitterService;

    public String getFromDs() {
        return twitterService.getOneStuff();
    }

    @Activate
    public void activate() {
        System.out.println("Activating the ds service");
        System.out.println(twitterService.getOneStuff());
    }

    @Reference
    public void setTwitterService(final TwitterService twitterService) {
        this.twitterService = twitterService;
    }
}
