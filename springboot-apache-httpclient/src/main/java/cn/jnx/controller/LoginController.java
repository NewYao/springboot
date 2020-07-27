package cn.jnx.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jnx.bean.ReturnMap;

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private static int count = 0;
    @ResponseBody
    @RequestMapping("/login")
    public HashMap<String, Object> login(HttpServletRequest request, HttpSession session, String username,
            String password) throws InterruptedException {
        Thread.sleep(100);
            ++count;
        System.out.println(count);
        String ip = getIpAddress(request);
        System.out.println("获取ip：" + ip);
        System.out.println("login,sessionId:" + session.getId());
        if ("admin".equals(username) && "123456".equals(password)) {
            session.setAttribute("userid", 1);
            return new ReturnMap().ok().data("sessionId", session.getId()).data("总访问次数:" + count).data("ip:", ip).message("登录成功！");
        }
        return new ReturnMap().fail().data("sessionId", session.getId()).data("总访问次数:" + count).data("ip:", ip).message("登录失败！");
    }

    @ResponseBody
    @PostMapping("/search")
    public HashMap<String, Object> search(HttpSession session) {
        System.out.println("search,sessionId:" + session.getId());
        String userid = session.getAttribute("userid") == null ? "" : String.valueOf(session.getAttribute("userid"));
        if ("".equals(userid)) {
            return new ReturnMap().fail().data("sessionId", session.getId()).message("未登录！");
        }
        return new ReturnMap().ok().data("sessionId", session.getId()).data("userid", userid);
    }

    public String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for"); 
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {  
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if( ip.indexOf(",")!=-1 ){
                ip = ip.split(",")[0];
            }
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("X-Real-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        } 
        return ip;  
    }

}
