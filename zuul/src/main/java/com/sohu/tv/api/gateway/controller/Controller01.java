/**
 * Copyright (c) 2012 Sohu. All Rights Reserved
 */
package com.sohu.tv.api.gateway.controller;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sohu.tv.api.gateway.beans.Bean01;
import com.sohu.tv.api.gateway.beans.Bean02;
import com.sohu.tv.api.gateway.beans.MysqlBean;
import com.sohu.tv.api.gateway.beans.RedisBean;

@RestController
@RequestMapping("/example01")
public class Controller01 {
    protected final Log logger = LogFactory.getLog(getClass());
    @Resource
    Bean01 bean01;

    @Resource
    Bean02 bean02;

    @Resource
    MysqlBean mysqlBean;

    @Resource
    RedisBean redisBean;

    @RequestMapping(value = "/test1", method = {RequestMethod.POST, RequestMethod.GET})
    Bean01 home() {
        //logger.info(bean02.toString());
        //mysqlBean.test();
        //redisBean.Set("test", "test hhh ", 1000);
        return bean01;
    }

}
