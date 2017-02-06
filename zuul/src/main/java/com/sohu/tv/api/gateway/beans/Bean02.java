/**
 * Copyright (c) 2012 Sohu. All Rights Reserved
 */
package com.sohu.tv.api.gateway.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "my")
public class Bean02 {
    protected final Log logger = LogFactory.getLog(getClass());
    private List<String> servers = new ArrayList<String>();

    public List<String> getServers() {
        return servers;
    }

    public void setServers(List<String> servers) {
        this.servers = servers;
    }

    @Override
    public String toString() {
        if (servers != null && servers.size() > 0) {
            for (String server : servers) {
               logger.info("server:" + server);
            }
        }
        return "Bean02 [servers.size=" + servers.size() + "]";
    }

}
