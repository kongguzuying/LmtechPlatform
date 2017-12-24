package com.lmtech.util;

import java.util.ArrayList;
import java.util.List;

import com.lmtech.service.SpringContextAware;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lmtech.service.ApplicationInitService;
import com.lmtech.service.SpringLoadedListener;
import org.springframework.stereotype.Component;

/**
 * Spring util
 *
 * @author hjb
 */
@Component
public class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext context = null;
    private static String contextConfigPath = "applicationContext*.xml";

    private static List<SpringLoadedListener> loadedListeners = new ArrayList<SpringLoadedListener>();

    static {
        SpringLoadedListener listener = new SpringLoadedListener() {
            @Override
            public void handle() {
                try {
                    ApplicationInitService initService = (ApplicationInitService) SpringUtil.getObject(ApplicationInitService.class);
                    initService.initApplication();
                } catch (Exception e) {
                    LoggerManager.warn(e.getMessage());
                }
            }
        };
        loadedListeners.add(listener);
    }

    /**
     * init spring context
     */
    public static void initContext() {
        initContext(contextConfigPath);
        initFinished();
    }

    /**
     * init spring context
     *
     * @param contextPath
     */
    public static void initContext(String contextPath) {
        if (context == null) {
            context = new ClassPathXmlApplicationContext(contextPath);
        }
        initFinished();
    }

    /**
     * init spring context
     *
     * @param contextPaths
     */
    public static void initContext(String[] contextPaths) {
        if (context == null) {
            context = new ClassPathXmlApplicationContext(contextPaths);
        }
        initFinished();
    }

    /**
     * init spring context
     *
     * @param ctx
     */
    public static void initContext(ApplicationContext ctx) {
        context = ctx;
        initFinished();
    }

    /**
     * init spring context
     */
    public static void initContextByContextAware() {
        context = SpringContextAware.getApplicationContext();
        initFinished();
    }

    /**
     * destroy spring context
     */
    public static void destroyContext() {
        context = null;
    }

    /**
     * 是否存在上下文
     * @return
     */
    public static boolean hasContext() {
        return context != null;
    }

    /**
     * get bean
     *
     * @param beanId
     * @return
     */
    public static Object getObject(String beanId) {
        return context.getBean(beanId);
    }

    /**
     * get bean by class
     * @param clazz
     * @return
     */
    public static Object getObjectByClass(Class<?> clazz) {
        return context.getBean(clazz);
    }

    /**
     * get bean
     *
     * @param beanId
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getObjectT(String beanId) {
        Object object = getObject(beanId);
        if (object != null) {
            return (T) object;
        } else {
            return null;
        }
    }

    /**
     * get bean by class
     *
     * @param cls
     * @return
     */
    public static Object getObject(Class<?> cls) {
        if (cls.isInterface()) {
            //接口Spring注入默认规则，去除头部I，将开头字母改为小写，例如IAccountDao注入为accountDao
            String interfaceName = cls.getSimpleName();
            String defaultBeanId = StringUtil.setFirstLower(interfaceName);

            return getObject(defaultBeanId);
        } else {
            //当作类处理
            String className = cls.getSimpleName().replace("Impl", "");
            String defaultBeanId = StringUtil.setFirstLower(className);

            return getObject(defaultBeanId);
        }
    }

    /**
     * get bean by class
     *
     * @param cls
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getObjectT(Class<?> cls) {
        Object object = getObject(cls);
        if (object != null) {
            return (T) object;
        } else {
            return null;
        }
    }

    private static void initFinished() {
        if (loadedListeners != null && loadedListeners.size() > 0) {
            for (SpringLoadedListener listener : loadedListeners) {
                listener.handle();
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
