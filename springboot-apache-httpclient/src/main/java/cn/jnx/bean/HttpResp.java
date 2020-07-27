package cn.jnx.bean;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @ClassName: ResponseBean
 * @Description 自定义请求返回类，可根据具体需求进行增改
 * @version
 * @author JH
 * @date 2020年7月16日 下午3:10:16
 */
public class HttpResp {

    private static final Logger logger = LoggerFactory.getLogger(HttpResp.class);

    private int state;
    private String entity;

    public int getState() {
        return state;
    }

    private void setState(int state) {
        this.state = state;
    }

    public String getEntity() {
        return entity;
    }

    private void setEntity(String entity) {
        this.entity = entity;
    }

    public static Logger getLogger() {
        return logger;
    }

    private HttpResp() {
    }

    /**
     * 
     * @Description 返还类自定义编码格式
     * @param res
     * @param encoding
     * @return
     */
    public static HttpResp convertResp(final CloseableHttpResponse res, String encoding) {
        HttpResp rb = new HttpResp();
        rb.setState(res.getStatusLine().getStatusCode());

        HttpEntity entity = res.getEntity();
        if (entity != null) {
            try {
                rb.setEntity(EntityUtils.toString(entity, encoding));
                EntityUtils.consume(entity);
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.info("当前请求返回状态码：{}", rb.getState());
        logger.info("当前请求返回结果：\\n{}", rb.getEntity());
        return rb;
    }

    /**
     * 
     * @Description 返还类默认使用UTF-8
     * @param res
     * @return
     */
    public static HttpResp convertResp(final CloseableHttpResponse res) {
        HttpResp rb = new HttpResp();
        rb.setState(res.getStatusLine().getStatusCode());

        HttpEntity entity = res.getEntity();
        if (entity != null) {
            try {
                rb.setEntity(EntityUtils.toString(entity, "utf-8"));
                EntityUtils.consume(entity);
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.info("当前请求返回状态码：{}", rb.getState());
        logger.info("当前请求返回结果：{}", rb.getEntity());
        return rb;
    }
}
