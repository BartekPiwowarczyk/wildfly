package com.example.wildfly;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/hello")
public class HelloEndpoint {


    Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    ConfigBean configBean;
    @GET
    @Path("/say")
    public String sayHello() {
        logger.info("Ktoś się dobija !!");
        return configBean.getHello();
    }
}
