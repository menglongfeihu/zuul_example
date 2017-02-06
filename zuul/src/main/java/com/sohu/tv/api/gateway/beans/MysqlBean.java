/**
 * Copyright (c) 2012 Sohu. All Rights Reserved
 */
package com.sohu.tv.api.gateway.beans;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MysqlBean {

    protected final Log logger = LogFactory.getLog(getClass());


    @Autowired
    JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void Init() {
        jdbcTemplate.setQueryTimeout(1000);
    }
    @SuppressWarnings("deprecation")
    public void test() {
        int count = jdbcTemplate.queryForInt("select count(*) from festival_user");

        logger.info("count = " + count);
        String sql = "select * from festival_user";
        List<FestivalUser> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<FestivalUser>(FestivalUser.class));
        if (users != null && users.size() > 0) {
            for (FestivalUser festivalUser : users) {
                logger.info("user info = " + festivalUser);
            }
        }
    }
}
