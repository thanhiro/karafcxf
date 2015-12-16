package fi.arcusys.example.osgi.dsservice.impl;

import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;

@Component(name = "TheDsService")
public class DsService {

    public String getFromDs() {
        return "and I come from Declarative Services";
    }

    @Activate
    public void activate() {
        System.out.println("Activating the ds service");
    }
}
