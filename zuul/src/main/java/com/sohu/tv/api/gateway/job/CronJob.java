/**
 * Copyright (c) 2012 Sohu. All Rights Reserved
 */
package com.sohu.tv.api.gateway.job;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.sohu.tv.api.gateway.beans.MysqlBean;

@Configuration
@EnableScheduling // 启用定时任务
public class CronJob {
    protected final Log logger = LogFactory.getLog(getClass());
    @Resource
    MysqlBean mysqlBean;

    @Scheduled(cron = "0/5 * * * * ?") // 每20秒执行一次
    public void scheduler() {
        logger.info(">>>>>>>>>>>>> scheduled ... ");
        mysqlBean.test();
    }

}