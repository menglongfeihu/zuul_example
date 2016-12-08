/**
 * Copyright (c) 2012 Sohu. All Rights Reserved
 */
package com.sohu.tv.api.gateway;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.spy;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GroovyCompiler {

    private static final Logger LOG = LoggerFactory.getLogger(GroovyCompiler.class);

    /**
     * Compiles Groovy code and returns the Class of the compiles code.
     *
     * @param sCode
     * @param sName
     * @return
     */
    public Class compile(String sCode, String sName) {
        GroovyClassLoader loader = getGroovyClassLoader();
        LOG.warn("Compiling filter: " + sName);
        Class groovyClass = loader.parseClass(sCode, sName);
        return groovyClass;
    }

    /**
     * @return a new GroovyClassLoader
     */
    GroovyClassLoader getGroovyClassLoader() {
        return new GroovyClassLoader();
    }

    /**
     * Compiles groovy class from a file
     *
     * @param file
     * @return
     * @throws java.io.IOException
     */
    public Class compile(File file) throws IOException {
        GroovyClassLoader loader = getGroovyClassLoader();
        Class groovyClass = loader.parseClass(file);
        return groovyClass;
    }

    @RunWith(MockitoJUnitRunner.class)
    public static class UnitTest {
        @Test
        public void testLoadGroovyFromString() {

            GroovyCompiler compiler = spy(new GroovyCompiler());

            try {

                String code = "class test { public String hello(){return \"hello\" } } ";
                Class clazz = compiler.compile(code, "test");
                assertNotNull(clazz);
                assertEquals(clazz.getName(), "test");
                GroovyObject groovyObject = (GroovyObject) clazz.newInstance();
                Object[] args = {};
                String s = (String) groovyObject.invokeMethod("hello", args);
                assertEquals(s, "hello");


            } catch (Exception e) {
                assertFalse(true);
            }

        }
    }
}