#Bunch of OSGi example modules

##What's included?

* Blueprint
    *`rest-example.rest` Apache CXF JAX-RS app using Blueprint dependency
    *`rest-example.service.impl` Blueprint service component 

* Declarative Services (DS)
    *`rest-example.dsservice` DS service component depending on other DS service (twitterservice)
    *`rest-example.twitterservice.impl` DS service component streaming from Twitter
    *`rest-example.websocket` Jetty WebSocket application streaming from DS twitterservice 

* `socket-client` Static file client app for websocket app above   


##How to set up the env

* Download and run Karaf
* Install required features in Karaf shell:
    * Declarative services
        * feature:install scr
    * Webconsole (installs HTTP services as well)
        * feature:install webconsole
    * Apache CXF
        * feature:repo-add cxf 3.1.4
        * feature:install cfx

##Installing bundles
    
CD to project dir and...

    $ mvn clean package

Use Karaf webconsole to install wanted bundles (jars) from Maven target directories.

