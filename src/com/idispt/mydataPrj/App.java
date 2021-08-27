package com.idispt.mydataPrj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import  com.idispt.mydataPrj.server.MydataServer;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main( String[] args ){
        logger.info("======= Mydata info Server =======");
        try {
            new MydataServer().run();
        }catch (Exception e) {

        }
    }
}