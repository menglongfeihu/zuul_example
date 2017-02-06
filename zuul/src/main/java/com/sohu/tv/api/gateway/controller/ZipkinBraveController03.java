/**
 * Copyright (c) 2012 Sohu. All Rights Reserved
 */
package com.sohu.tv.api.gateway.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import okhttp3.OkHttpClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Api("zipkin brave api")
@RestController
@RequestMapping("/zipkin/brave/service4")
public class ZipkinBraveController03 {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private OkHttpClient okHttpClient;


    @ApiOperation("trace第4步")
    @RequestMapping(value = "/test4", method = RequestMethod.GET)
    public String myboot() throws Exception{
        Thread.sleep(100);

        return "service4";
    }


}
