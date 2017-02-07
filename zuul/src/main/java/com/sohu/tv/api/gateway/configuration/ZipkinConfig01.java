/**
 * Copyright (c) 2012 Sohu. All Rights Reserved
 */
package com.sohu.tv.api.gateway.configuration;


//@Configuration
public class ZipkinConfig01 {

    /*
    @Bean
    public SpanCollector spanCollector() {
        HttpSpanCollector.Config spanConfig = HttpSpanCollector.Config.builder()
                .compressionEnabled(false)// 默认false，span在transport之前是否会被gzipped。
                .connectTimeout(5000)// 5s，默认10s
                .flushInterval(1)
                .readTimeout(6000)// 5s，默认60s
                .build();
        return HttpSpanCollector.create("http://localhost:9411", spanConfig, new EmptySpanCollectorMetricsHandler());
    }

    @Bean
    public Brave brave(SpanCollector spanCollector) {
        Brave.Builder builder = new Brave.Builder("service1");
        builder.spanCollector(spanCollector);
        builder.traceSampler(Sampler.create(1));// 采集率
        return builder.build();
    }

    @Bean
    public BraveServletFilter braveServletFilter(Brave brave) {
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
    */
}
