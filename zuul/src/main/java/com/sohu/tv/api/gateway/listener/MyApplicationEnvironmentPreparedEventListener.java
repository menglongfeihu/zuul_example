/**
 * Copyright (c) 2012 Sohu. All Rights Reserved
 */
package com.sohu.tv.api.gateway.listener;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;


public class MyApplicationEnvironmentPreparedEventListener implements
        ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    private final Logger logger = LoggerFactory.getLogger(MyApplicationEnvironmentPreparedEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment eviConfigurableEnvironment = event.getEnvironment();
        MutablePropertySources mapMutablePropertySources = eviConfigurableEnvironment.getPropertySources();
        if (mapMutablePropertySources != null) {
            Iterator<PropertySource<?>> iter = mapMutablePropertySources.iterator();

            while (iter.hasNext()) {
                PropertySource<?> ps = iter.next();
                logger.info("ps.getName:{};ps.getSource:{};ps.getClass:{}", ps.getName(), ps.getSource(),ps.getClass());
            }
        }
    }
}
