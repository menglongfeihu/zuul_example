/**
 * Copyright (c) 2012 Sohu. All Rights Reserved
 */
package com.sohu.tv.api.gateway.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpUtil {
    private static Logger logger = Logger.getLogger(HttpUtil.class);

    private static final String userAgent = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)";

    public static final int DEFAULT_TIMEOUT = 2000;

    public static String doGet(String url) {
        return doGet(url, HTTP.UTF_8, DEFAULT_TIMEOUT);
    }

    public static String doGet(String url, int timeout) {
        return doGet(url, HTTP.UTF_8, timeout);
    }

    public static String doGet(String url, String encode) {
        return doGet(url, encode, DEFAULT_TIMEOUT);
    }

    public static String doGet(String url, String encode, int timeout) {
        HttpURLConnection conn = null;
        ByteArrayOutputStream output;
        URL u;
        try {
            u = new URL(url);
            conn = (HttpURLConnection) u.openConnection();
            conn.setRequestProperty("User-Agent", userAgent);
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(timeout);
            InputStream in = conn.getInputStream();
            output = new ByteArrayOutputStream();
            byte buffer[] = new byte[4096];
            for (int bytesRead = 0; (bytesRead = in.read(buffer)) != -1;)
                output.write(buffer, 0, bytesRead);
            return output.toString(encode);
        } catch (Exception e) {
            logger.info("call http get error:", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }

    public static String doPost(String url, Map<String, String> params) {
        return doPost(url, params, HTTP.UTF_8, DEFAULT_TIMEOUT);
    }

    public static String doPost(String url, Map<String, String> params, int timeout) {
        return doPost(url, params, HTTP.UTF_8, timeout);
    }

    public static String doPost(String url, Map<String, String> params, Map<String, String> headers, int timeout) {
        return doPost(url, params, HTTP.UTF_8, headers, timeout);
    }

    private static String doPost(String url, Map<String, String> params, String encode, Map<String, String> headers,
            int timeout) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout);
        HttpPost httpPost = new HttpPost(url);
        // httpPost.setHeader("User-Agent", userAgent);
        if (headers != null) {
            Set<String> keys = headers.keySet();
            for (Iterator<String> i = keys.iterator(); i.hasNext();) {
                String key = i.next();
                httpPost.addHeader(key, headers.get(key));
            }
        }

        List<NameValuePair> postParams = new ArrayList<NameValuePair>();
        Set<String> keySet = params.keySet();
        String result = "";
        for (String key : keySet) {
            postParams.add(new BasicNameValuePair(key, params.get(key)));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(postParams, encode));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            result = EntityUtils.toString(entity);
        } catch (Exception e) {
            logger.error("call http post error:", e);
        } finally {
            if (httpClient != null) {
                httpClient.getConnectionManager().shutdown();
            }
        }
        return result;
    }

    public static String doPost(String url, Map<String, String> params, String encode) {
        return doPost(url, params, encode, DEFAULT_TIMEOUT);
    }

    public static String doPost(String url, Map<String, String> params, String encode, int timeout) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("User-Agent", userAgent);
        List<NameValuePair> postParams = new ArrayList<NameValuePair>();
        Set<String> keySet = params.keySet();
        String result = "";
        for (String key : keySet) {
            postParams.add(new BasicNameValuePair(key, params.get(key)));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(postParams, encode));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            result = EntityUtils.toString(entity);
        } catch (Exception e) {
            logger.error("call http post error:", e);
        } finally {
            if (httpClient != null) {
                httpClient.getConnectionManager().shutdown();
            }
        }
        return result;
    }

    /**
     * 获取二进制流
     */
    public static byte[] getBinary(String url) {
        HttpURLConnection conn = null;
        ByteArrayOutputStream output;
        URL u;
        try {
            u = new URL(url);
            conn = (HttpURLConnection) u.openConnection();
            conn.setRequestProperty("User-Agent", userAgent);
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(8000);
            conn.setReadTimeout(8000);
            InputStream in = conn.getInputStream();
            output = new ByteArrayOutputStream();
            byte buffer[] = new byte[4096];
            for (int bytesRead = 0; (bytesRead = in.read(buffer)) != -1;)
                output.write(buffer, 0, bytesRead);
            conn.disconnect();
            return output.toByteArray();
        } catch (Exception e) {
            logger.info("getBinary error", e);
        }
        return null;
    }


}
