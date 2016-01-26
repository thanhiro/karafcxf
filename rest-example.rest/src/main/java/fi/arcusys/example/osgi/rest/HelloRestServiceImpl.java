package fi.arcusys.example.osgi.rest;

import fi.arcusys.example.osgi.service.impl.HelloService;

public class HelloRestServiceImpl implements HelloRestService {
    private HelloService helloService;

    public String getName(String name) {
        return "{\"greeting\": \"" + helloService.sayHello(name) + "\"}";
    }

    public HelloRestServiceImpl() {
    }

    public HelloService getHelloService() {
        return helloService;
    }

    public void setHelloService(HelloService helloService) {
        this.helloService = helloService;
    }
}
