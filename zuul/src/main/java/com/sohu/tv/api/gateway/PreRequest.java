/**
 * Copyright (c) 2012 Sohu. All Rights Reserved
 */
package com.sohu.tv.api.gateway;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

class PreRequest extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1000;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        HttpServletRequest req = RequestContext.getCurrentContext().getRequest();
        Enumeration<String> headerIt = req.getHeaderNames();
        while (headerIt.hasMoreElements()) {
            String name = headerIt.nextElement();
            String value = req.getHeader(name);
            System.out.println("header is : " + name + ":" + value);
        }
        return null;
    }
}