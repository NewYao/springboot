package cn.junengxiong.config.shiro_config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.cache.ehcache.EhCacheManager;
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
import org.springframework.util.ObjectUtils;

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
		// <!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，注意
		// <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
		filterChainDefinitionMap.put("/**", "authc");// 设置/** 为user后，记住我才会生效
		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面,前后端分离设置此为controller返回的未登录的接口
		// --------------------------------------------------
		// 前后端分离使用下面设置
		shiroFilterFactoryBean.setLoginUrl("/login.html");
		// shiroFilterFactoryBean.setLoginUrl("/unauthorized");
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
		// 启用身份验证缓存，即缓存AuthenticationInfo信息，默认false
		myShiroRealm.setAuthenticationCachingEnabled(true);
		// 缓存AuthenticationInfo信息的缓存名称 在ehcache-shiro.xml中有对应缓存的配置
		myShiroRealm.setAuthenticationCacheName("authenticationCache");
		// 启用授权缓存，即缓存AuthorizationInfo信息，默认false
		myShiroRealm.setAuthorizationCachingEnabled(true);
		// 缓存AuthorizationInfo信息的缓存名称 在ehcache-shiro.xml中有对应缓存的配置
		myShiroRealm.setAuthorizationCacheName("authorizationCache");
		return myShiroRealm;
	}

	/**
	 * 注入 securityManager
	 */
	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(myShiroRealm());
		securityManager.setRememberMeManager(rememberMeManager());
		securityManager.setCacheManager(ehCacheManager());// 将缓存管理交给ehCache
		return securityManager;
	}


	@Bean
	public EhCacheManager ehCacheManager() {
		net.sf.ehcache.CacheManager cacheManager = net.sf.ehcache.CacheManager.getCacheManager("ehcache");
		EhCacheManager em = new EhCacheManager();
		//因为配合springboot热启动，所以注入bean时加上此判断，不然会报错
		if (ObjectUtils.isEmpty(cacheManager)) {
			em.setCacheManagerConfigFile("classpath:ehcache.xml");
		} else {
			em.setCacheManager(cacheManager);
		}
		return em;
	}

	/**
	 * Cookie 对象 用户免登陆操作，但是需要配置filter /** 权限为user生效
	 * 
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
