package cn.jnx.util;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import cn.jnx.bean.HttpResp;

public class HttpUtil {

    @Test
    public void doTest() throws IOException {
        httpWithProxy();
    }

    /**
     * 模拟登陆SVN地址等需要凭证的地址
     * 
     * @return
     */
    public HttpResp httpWithUsernameAndPassword() {
        CloseableHttpResponse response = null;
        HttpResp hrp = null;
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        // 账号
        String username = "";
        // 密码
        String password = "";
        // 设置凭证的域名信息和端口，用户名、密码
        credsProvider.setCredentials(new AuthScope("192.168.0.125", 80),
                new UsernamePasswordCredentials(username, password));
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
        try {
            HttpGet httpget = new HttpGet("http://192.168.0.125");
            response = httpclient.execute(httpget);
            hrp = HttpResp.convertResp(response);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return hrp;
    }

    /**
     * 代理
     * 
     * @param http://www.66ip.cn/areaindex_2/1.html此网站找代理站点
     * @return
     */
    public HttpResp httpWithProxy() {
        CloseableHttpResponse response = null;
        HttpResp hrp = null;
        // 创建默认的客户端
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 配置目标网点
        HttpHost target = new HttpHost("www.njpingpang.com", 80, "http");
        // 配置代理的网点
        HttpHost proxy = new HttpHost("47.94.89.87", 3128, "http");

        RequestConfig config = RequestConfig
                .custom()
                .setProxy(proxy)
                .setConnectTimeout(10000)
                .setSocketTimeout(10000)
                .setConnectionRequestTimeout(10000)
                .build();
        try {
            HttpGet request = new HttpGet("/");
            request.setConfig(config);
            request.setHeader("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
            response = httpclient.execute(target, request);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return hrp;
    }

}
