/**
 * Copyright (c) 2012 Sohu. All Rights Reserved
 */
package com.sohu.tv.api.gateway.configuration;

import okhttp3.OkHttpClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.kristofa.brave.Brave;
import com.github.kristofa.brave.EmptySpanCollectorMetricsHandler;
import com.github.kristofa.brave.Sampler;
import com.github.kristofa.brave.SpanCollector;
import com.github.kristofa.brave.http.DefaultSpanNameProvider;
import com.github.kristofa.brave.http.HttpSpanCollector;
import com.github.kristofa.brave.mysql.MySQLStatementInterceptorManagementBean;
import com.github.kristofa.brave.okhttp.BraveOkHttpRequestResponseInterceptor;
import com.github.kristofa.brave.servlet.BraveServletFilter;

@Configuration
public class ZipkinConfig {

    @Autowired
    ZipkinProperties properties;


    @Bean
    public SpanCollector spanCollector() {
        HttpSpanCollector.Config spanConfig = HttpSpanCollector.Config.builder()
                .compressionEnabled(properties.isCompressionEnabled())// 默认false，span在transport之前是否会被gzipped。
                .connectTimeout(properties.getConnectTimeout())// 5s，默认10s
                .flushInterval(properties.getFlushInterval())
                .readTimeout(properties.getReadTimeout())// 5s，默认60s
                .build();
        return HttpSpanCollector.create(properties.getUrl(), spanConfig, new EmptySpanCollectorMetricsHandler());
    }

    @Bean
    public Brave brave(SpanCollector spanCollector) {
        Brave.Builder builder = new Brave.Builder(properties.getServiceName());
        builder.spanCollector(spanCollector);
        builder.traceSampler(Sampler.create(properties.getTraceSample()));// 采集率
        return builder.build();
    }

    @Bean
    public BraveServletFilter braveServletFilter(Brave brave) {
        /**
         * 设置sr、ss拦截器
         */
        return new BraveServletFilter(brave.serverRequestInterceptor(), brave.serverResponseInterceptor(),
                new DefaultSpanNameProvider());
    }

    @Bean
    public MySQLStatementInterceptorManagementBean mySQLStatementInterceptorManagementBean(Brave brave) {
        return new MySQLStatementInterceptorManagementBean(brave.clientTracer());
    }

    @Bean
    public OkHttpClient okHttpClient(Brave brave) {
        return new OkHttpClient.Builder()
                .addInterceptor(
                        new BraveOkHttpRequestResponseInterceptor(brave.clientRequestInterceptor(), brave
                                .clientResponseInterceptor(), new DefaultSpanNameProvider()))
                .build();
    }
}
