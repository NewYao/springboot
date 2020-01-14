package cn.junengxiong.config.shiro_config;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.authc.pam.AllSuccessfulStrategy;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import cn.junengxiong.config.shiro_config.realms.ParentRealm;
import cn.junengxiong.config.shiro_config.realms.RealmEmail;
import cn.junengxiong.config.shiro_config.realms.RealmPhone;
import cn.junengxiong.config.shiro_config.realms.RealmUsername;

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
        filterChainDefinitionMap.put("/user", "user");
        // 因为目前演示页面依附在此项目下，特为演示页面新增可无权限访问，前后端分离后无需此设置
        filterChainDefinitionMap.put("/login.html", "anon");
        // <!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->因为保存在LinkedHashMap中，顺序很重要
        // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/**", "authc");// 设置/** 为user后，记住我才会生效
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面,前后端分离设置此为controller返回的未登录的接口
        // --------------------------------------------------
        // 前后端分离使用下面设置
        //shiroFilterFactoryBean.setLoginUrl("/login.html");
        shiroFilterFactoryBean.setLoginUrl("/unauthorized");// 前后端分离只需要把需要登录返回告诉前端页面即可
        // ---------------------------------------------------
        // 登录成功后跳转的链接,前后端分离不用设置
        // shiroFilterFactoryBean.setSuccessUrl("/index");

        // 未授权的界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    /**
     * 自定义身份认证 realmEmail;
     * <p>
     * 必须写这个类，并加上 @Bean 注解，目的是注入 MyShiroRealm， 否则会影响 MyShiroRealm类 中其他类的依赖注入
     */
    @Bean
    public ParentRealm realmEmail() {
        RealmEmail myShiroRealm = new RealmEmail();
        // 设置密码比较器
        myShiroRealm.setCredentialsMatcher(CredentialsMatcher());
        // 启用身份验证缓存，即缓存AuthenticationInfo信息，默认false
        myShiroRealm.setAuthenticationCachingEnabled(true);
        // 启用授权缓存，即缓存AuthorizationInfo信息，默认false,一旦配置了缓存管理器，授权缓存默认开启
        myShiroRealm.setAuthorizationCachingEnabled(true);
        
        return myShiroRealm;
    }
    /**
     * 自定义身份认证 realmPhone;
     * <p>
     * 必须写这个类，并加上 @Bean 注解，目的是注入 MyShiroRealm， 否则会影响 MyShiroRealm类 中其他类的依赖注入
     */
    @Bean
    public ParentRealm realmPhone() {
        RealmPhone realmPhone = new RealmPhone();
        // 设置密码比较器
        realmPhone.setCredentialsMatcher(CredentialsMatcher());
        // 启用身份验证缓存，即缓存AuthenticationInfo信息，默认false
        realmPhone.setAuthenticationCachingEnabled(true);
        // 启用授权缓存，即缓存AuthorizationInfo信息，默认false,一旦配置了缓存管理器，授权缓存默认开启
        realmPhone.setAuthorizationCachingEnabled(true);
        
        return realmPhone;
    }
    /**
     * 自定义身份认证 realmUsername;
     * <p>
     * 必须写这个类，并加上 @Bean 注解，目的是注入 MyShiroRealm， 否则会影响 MyShiroRealm类 中其他类的依赖注入
     */
    @Bean
    public ParentRealm realmUsername() {
        RealmUsername realmUsername = new RealmUsername();
        // 设置密码比较器
        realmUsername.setCredentialsMatcher(CredentialsMatcher());
        // 启用身份验证缓存，即缓存AuthenticationInfo信息，默认false
        realmUsername.setAuthenticationCachingEnabled(true);
        // 启用授权缓存，即缓存AuthorizationInfo信息，默认false,一旦配置了缓存管理器，授权缓存默认开启
        realmUsername.setAuthorizationCachingEnabled(true);
        return realmUsername;
    }
    /**
     * 自定义身份认证realm控制器
     * @return
     */
    @Bean
    public ModularRealmAuthenticator myModularRealmAuthenticator(){
        //自定义身份认证realm控制器
        MyModularRealmAuthenticator modularRealmAuthenticator = new MyModularRealmAuthenticator();
        //配置多realm认证策略
        //默认使用AtLeastOneSuccessFulAtrategy(至少一个成功的策略)
        //AllSuccessFulStrategy ()全部成功策略没问题
        //FirstSuccessFulStrategy 第一个成功策略
        modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return modularRealmAuthenticator;
    }

    @Bean
    public SimpleCredentialsMatcher CredentialsMatcher() {
        MyCredentialsMatcher hct = new MyCredentialsMatcher();//自定义凭证比较器
        // 加密算法的名称
        hct.setHashAlgorithmName("MD5");
        // 配置加密的次数
        hct.setHashIterations(1024);
        // 是否存储为16进制
        hct.setStoredCredentialsHexEncoded(true);
        return hct;
    }

    /**
     * 注入 securityManager
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setAuthenticator(myModularRealmAuthenticator());//配置自定义realm过滤器
        Set<Realm> realms  = new HashSet<>();
        realms.add(realmEmail());
        realms.add(realmPhone());
        realms.add(realmUsername());
        securityManager.setRealms(realms);;//配置自定义权限认证器
        securityManager.setRememberMeManager(rememberMeManager());//配置记住我管理器
        securityManager.setCacheManager(cacheManager());//配置缓存管理器
        return securityManager;
    }
    
    /**
     * 缓存管理器
     * @return
     */
    @Bean
    public CacheManager cacheManager() {
        MemoryConstrainedCacheManager mccm = new MemoryConstrainedCacheManager();
        return mccm;
    }
    
    /**
     * Cookie 对象 用户免登陆操作，但是需要配置filter /** 权限为user生效
     * 
     * @return
     */
    public SimpleCookie rememMeCookie() {
        // 初始化设置cookie的名称
        SimpleCookie simpleCookie = new SimpleCookie("shiro-remember");
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
