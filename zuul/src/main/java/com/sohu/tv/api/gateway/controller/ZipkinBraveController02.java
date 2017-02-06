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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api("zipkin brave api")
@RestController
@RequestMapping("/zipkin/brave/service2")
public class ZipkinBraveController02 {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private OkHttpClient okHttpClient;

    @ApiOperation("trace第二步")
    @RequestMapping(value = "/test2", method = RequestMethod.GET)
    public String myboot() throws Exception {
        Thread.sleep(100);
        Request request3 = new Request.Builder().url("http://localhost:8032/zipkin/brave/service3/test3").build();
        Response response3 = okHttpClient.newCall(request3).execute();
        String response3str = response3.body().string();

        Request request4 = new Request.Builder().url("http://localhost:8032/zipkin/brave/service4/test4").build();
        Response response4 = okHttpClient.newCall(request4).execute();
        String response4str = response4.body().string();
        return response3str + "_" + response4str;
    }

}
