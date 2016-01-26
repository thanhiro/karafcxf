package fi.arcusys.example.osgi.twitterservice;

import aQute.bnd.annotation.metatype.Meta;

@Meta.OCD
public interface MyConfig {
    String[] terms();
}
