/**
 * Copyright (c) 2012 Sohu. All Rights Reserved
 */
package com.sohu.tv.api.gateway;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.netflix.zuul.FilterFileManager;
import com.netflix.zuul.FilterLoader;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.ContextLifecycleFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.filters.FilterRegistry;
import com.netflix.zuul.filters.StaticResponseFilter;
import com.netflix.zuul.filters.SurgicalDebugFilter;
import com.netflix.zuul.groovy.GroovyCompiler;
import com.netflix.zuul.groovy.GroovyFileFilter;
import com.netflix.zuul.http.ZuulServlet;
import com.netflix.zuul.monitoring.MonitoringHelper;

@SpringBootApplication
public class Application {

    private  final static Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        LOGGER.info("=======开始启动Spring boot=======");
        new SpringApplicationBuilder(Application.class).web(true).run(args);
        /*.listeners(new MyApplicationEnvironmentPreparedEventListener())
        .listeners(new MyApplicationPreparedEventListener())
        .listeners(new MyApplicationStartedEventListener()).run(args);*/
        LOGGER.info("=======成功启动Spring boot=======");
    }

    @Component
    public static class MyCommandLineRunner implements CommandLineRunner {
        @Override
        public void run(String... args) throws Exception {
            MonitoringHelper.initMocks();
            initJavaFilters();
            FilterLoader.getInstance().setCompiler(new GroovyCompiler());
            try {
                FilterFileManager.setFilenameFilter(new GroovyFileFilter());
                String bootPath = "F:\\workspace-learn\\zuul\\src\\main\\groovy\\filters\\";
                FilterFileManager.init(10,bootPath + "pre", bootPath + "post", bootPath + "route");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        private void initJavaFilters() {
            LOGGER.info("=======注册过滤器=======");
            final FilterRegistry registry = FilterRegistry.instance();

            registry.put("staticFilter", new StaticResponseFilter() {

                @Override
                public Object uri() {
                    LOGGER.info("=======staticFilter=======");
                    String path = RequestContext.getCurrentContext().getRequest().getRequestURI();
                    LOGGER.info("path" + path);
                    return "/static";
                }

                @Override
                public String responseBody() {
                   return "static Filter response......";
                }

                @Override
                public String toString() {
                    return "staticFilter";
                }

            });

            registry.put("surgicalFilter", new SurgicalDebugFilter() {

                @Override
                public boolean patternMatches() {

                    return true;
                }

                @Override
                public String toString() {
                    return "surgicalFilter";
                }
            });
            registry.put("javaPreFilter", new ZuulFilter() {
                @Override
                public int filterOrder() {
                    return 50000;
                }

                @Override
                public String filterType() {
                    return "pre";
                }

                @Override
                public boolean shouldFilter() {
                    return true;
                }

                @Override
                public Object run() {
                    LOGGER.info("running javaPreFilter");

                    RequestContext.getCurrentContext().set("name", "liaokailin");
                    return null;
                }
                @Override
                public String toString() {
                    return "javaPreFilter";
                }
            });

            registry.put("javaRoutingFilter", new ZuulFilter() {
                @Override
                public int filterOrder() {
                    return 50000;
                }

                @Override
                public String filterType() {
                    return "route";
                }

                @Override
                public boolean shouldFilter() {
                    return true;
                }

                @Override
                public Object run() {
                    LOGGER.info("running javaRoutingFilter");
                    try {
                        RequestContext.getCurrentContext().getResponse().sendRedirect("http://blog.csdn.net/liaokailin/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
                @Override
                public String toString() {
                    return "javaRoutingFilter";
                }
            });

            registry.put("javaPostFilter", new ZuulFilter() {
                @Override
                public int filterOrder() {
                    return 50000;
                }

                @Override
                public String filterType() {
                    return "post";
                }

                @Override
                public boolean shouldFilter() {
                    return true;
                }

                @Override
                public Object run() {
                    LOGGER.info("running javaPostFilter");
                    LOGGER.info(RequestContext.getCurrentContext().get("name").toString());
                    return null;
                }
                @Override
                public String toString() {
                    return "javaPostFilter";
                }

            });
        }

    }

    @Bean
    public ServletRegistrationBean zuulServlet() {
        ServletRegistrationBean servlet = new ServletRegistrationBean(new ZuulServlet());
        servlet.addUrlMappings("/test");
        servlet.addUrlMappings("/static");
        return servlet;
    }

    @Bean
    public FilterRegistrationBean contextLifecycleFilter() {
        FilterRegistrationBean filter = new FilterRegistrationBean(new ContextLifecycleFilter());
        filter.addUrlPatterns("/*");
        return filter;
    }
}