package cn.junengxiong.config.shiro_config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
/**
 * 
 * @ClassName:  SpringBeanFactoryUtil   
 * @Description 手动bean注入工具类，用于解决filter中注入service为null问题
 * @version 
 * @author jh
 * @date 2019年8月28日 上午10:29:37
 */
@Component
public class SpringBeanFactoryUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringBeanFactoryUtil.applicationContext == null) {
            SpringBeanFactoryUtil.applicationContext = applicationContext;
        }
    }

    private SpringBeanFactoryUtil() {
        // TODO Auto-generated constructor stub
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    // 根据名称-------@Resource 注解
    public static Object getBeanByName(String name) {
        return getApplicationContext().getBean(name);
    }

    // 根据类型-------@Autowired 注解
    public static <T> T getBeanByType(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    // 根据名称和类型--------@Autowired+@Qualifier
    public static <T> T getBeanByNameAndType(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }
}
