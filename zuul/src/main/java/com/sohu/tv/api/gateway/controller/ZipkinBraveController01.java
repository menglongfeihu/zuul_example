/**
 * Copyright (c) 2012 Sohu. All Rights Reserved
 */
package com.sohu.tv.api.gateway.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("zipkin brave api")
@RestController
@RequestMapping("/zipkin/brave/service1")
public class ZipkinBraveController01 {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private OkHttpClient okHttpClient;

    @ApiOperation("trace第一步")
    @RequestMapping("/test1")
    public String myboot() throws Exception {
        Thread.sleep(100);
        Request request = new Request.Builder().url("http://localhost:8033/zipkin/brave/service2/test2").build();
        /*
         * 1、执行execute()的前后，会执行相应的拦截器（cs,cr） 2、请求在被调用方执行的前后，也会执行相应的拦截器（sr,ss）
         */
        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
    }

}
