package cn.junengxiong.config.shiro_config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ShiroConfig {
    // remeberMe cookie 加密的密钥 各个项目不一样 默认AES算法 密钥长度（128 256 512）
    private static final String ENCRYPTION_KEY = "3AvVhmFLUs0KTA3Kprsdag==";

    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        // 配置退出 过滤器，其中具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");
        //因为目前演示页面依附在此项目下，特为演示页面新增可无权限访问，前后端分离后无需此设置
        filterChainDefinitionMap.put("/login.html", "anon");
        // <!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->因为保存在LinkedHashMap中，顺序很重要
        // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/**", "authc");//设置/**  为user后，记住我才会生效
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面,前后端分离设置此为controller返回的未登录的接口
        // --------------------------------------------------
        // 前后端分离使用下面设置
        //shiroFilterFactoryBean.setLoginUrl("/login.html");
        shiroFilterFactoryBean.setLoginUrl("/unauthorized");//前后端分离只需要把需要登录返回告诉前端页面即可
        // ---------------------------------------------------
        // 登录成功后跳转的链接,前后端分离不用设置
        // shiroFilterFactoryBean.setSuccessUrl("/index");

        // 未授权的界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    /**
     * 自定义身份认证 realm;
     * <p>
     * 必须写这个类，并加上 @Bean 注解，目的是注入 MyShiroRealm， 否则会影响 MyShiroRealm类 中其他类的依赖注入
     */
    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        //设置密码比较器
        //myShiroRealm.setCredentialsMatcher(myCredentialsMatcher());
        return myShiroRealm;
    }
    @Bean
    public MyCredentialsMatcher myCredentialsMatcher() {
        MyCredentialsMatcher mtm = new MyCredentialsMatcher();
        return mtm;
    }
    /**
     * 注入 securityManager
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }
    
    /**
     * Cookie 对象
     * 用户免登陆操作，但是需要配置filter /** 权限为user生效
     * @return
     */
    public SimpleCookie rememMeCookie() {
        // 初始化设置cookie的名称
        SimpleCookie simpleCookie = new SimpleCookie("boot-shiro");
        simpleCookie.setMaxAge(2592000);// 设置cookie的生效时间
        simpleCookie.setHttpOnly(true);
        return simpleCookie;
    }

    /**
     * cookie 管理对象，记住我功能
     * 
     * @return
     */
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememMeCookie());
        // remeberMe cookie 加密的密钥 各个项目不一样 默认AES算法 密钥长度（128 256 512）
        cookieRememberMeManager.setCipherKey(Base64.decode(ENCRYPTION_KEY));
        return cookieRememberMeManager;
    }

    /**
     * 配置Shiro生命周期处理器,在此说明，如果配合spring食用时，是无需配置此项的，可以查看
     * shiro-spring-config.ShiroBeanConfiguration文件，此配置已经帮我们配置好了
     * 
     * @return
     */
//    @Bean(name = "lifecycleBeanPostProcessor")
//    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
//        return new LifecycleBeanPostProcessor();
//    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
     * 
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 开启aop注解支持
     * 
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
