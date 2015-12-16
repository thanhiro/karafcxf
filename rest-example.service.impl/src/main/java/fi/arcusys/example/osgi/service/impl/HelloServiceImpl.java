package fi.arcusys.example.osgi.service.impl;

public class HelloServiceImpl implements HelloService {

    public String sayHello(String name) {
        return "How do you do, " + name;
    }

}
