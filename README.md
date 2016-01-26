##How to set up the env

* Download karaf
* Run karaf
* Install features:
    $ feature:install http
    $ feature:install scr
    $ feature:install webconsole
    $ feature:install cfx

##Installing bundles
    
CD to project dir and...

    $ mvn clean package

Use Karaf webconsole to install wanted bundles (jars) from Maven target directories.

