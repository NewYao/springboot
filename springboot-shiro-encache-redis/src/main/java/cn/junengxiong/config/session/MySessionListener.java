package cn.junengxiong.config.session;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
/**
 * 
 * @ClassName:  MySessionListener   
 * @Description 统计session数量
 * @version 
 * @author JH
 * @date 2019年9月2日 上午11:15:38
 */
public class MySessionListener implements SessionListener {

    private final AtomicInteger sessionCount = new AtomicInteger(0);

    /**
     * 登录
     */
    @Override
    public void onStart(Session session) {
        sessionCount.incrementAndGet();
        System.out.println("登录，在线人数："+sessionCount.get());
    }

    /**
     * 登出
     */
    @Override
    public void onStop(Session session) {
        sessionCount.decrementAndGet();
        System.out.println("登出，在线人数："+sessionCount.get());
    }

    /**
     * session过期
     */
    @Override
    public void onExpiration(Session session) {
        sessionCount.decrementAndGet();
        System.out.println("session过期，在线人数："+sessionCount.get());
    }

}
