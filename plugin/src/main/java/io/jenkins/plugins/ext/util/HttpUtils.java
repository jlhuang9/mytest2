package io.jenkins.plugins.ext.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @description:
 * @author: huangcq
 * @create: 2019-01-07 14:23
 **/
public class HttpUtils {

    public static String httpPostWithJSON(String url) throws IOException {
        return httpPostWithJSON(url, "");
    }

    public static String httpGet(String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse execute = client.execute(httpGet);
        HttpEntity he = execute.getEntity();
        return EntityUtils.toString(he, "UTF-8");
    }

    public static String httpPostWithJSON(String url, String jsonStr) throws IOException {

        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient client = HttpClients.createDefault();
        String respContent = null;

        StringEntity entity = new StringEntity(jsonStr,"utf-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        HttpResponse resp = client.execute(httpPost);
        if(resp.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("请求url报错 : "+url);
        }
        HttpEntity he = resp.getEntity();
        respContent = EntityUtils.toString(he,"UTF-8");
        return respContent;
    }

}
