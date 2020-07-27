package cn.jnx.util;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import cn.jnx.bean.HttpResp;

public class HttpClient {
    List<NameValuePair> valuePairs2 = null;

    @Test
    public void t() throws ClientProtocolException, IOException {
        List<NameValuePair> valuePairs = new LinkedList<NameValuePair>();
        valuePairs.add(new BasicNameValuePair("username", "admin"));
        valuePairs.add(new BasicNameValuePair("password", "123456"));

        HttpResp hrp = post("http://127.0.0.1:8080/login", valuePairs);
        post("http://127.0.0.1:8080/search", null);

    }

    RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
    // 创建cookie store的本地实例
    CookieStore cookieStore = new BasicCookieStore();
    // 创建HttpClient上下文
    HttpClientContext context = HttpClientContext.create();

    // 创建一个HttpClient
    CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig)
            .setDefaultCookieStore(cookieStore).build();

    public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException {
        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
        // 创建cookie store的本地实例
        CookieStore cookieStore = new BasicCookieStore();
        // 创建HttpClient上下文
        HttpClientContext context = HttpClientContext.create();

        // 创建一个HttpClient
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig)
                .setDefaultCookieStore(cookieStore).build();
        context.setCookieStore(cookieStore);
        CloseableHttpResponse res = null;

        HttpGet get = new HttpGet(url);
        res = httpClient.execute(get, context);
        res.close();
        return res;
    }

    /**
     * 简单post接口调用
     * 
     * @param url        访问接口地址
     * @param valuePairs 键值对参数
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public HttpResp post(String url, List<NameValuePair> valuePairs) {
        CloseableHttpResponse res = null;
        HttpResp resp = null;
        try {
            HttpPost post = new HttpPost(url);
            if (null != valuePairs) {
                // 注入post数据
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(valuePairs, Consts.UTF_8);
                entity.setContentType("application/x-www-form-urlencoded");
                post.setEntity(entity);
            }

            res = httpClient.execute(post, context);
            resp = HttpResp.convertResp(res);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                res.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resp;
    }


}
