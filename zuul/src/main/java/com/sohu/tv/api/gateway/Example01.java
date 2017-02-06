/**
 * Copyright (c) 2012 Sohu. All Rights Reserved
 */
package com.sohu.tv.api.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/*
 * @Configuration
 * @ComponentScan
 * @EnableAutoConfiguration
 * 以上三个注解经常同时使用，为了方便可以使用@SpringBootApplication注解，该注解等同于以上三个注解
 */
@SpringBootApplication
//@ServletComponentScan
public class Example01 {


    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Example01.class);
        application.setShowBanner(false);
        //application.addListeners(new MyApplicationStartedEventListener());
        application.setAddCommandLineProperties(false);
        application.run(args);
        // SpringApplication.run(Example01.class, args);
    }

}
