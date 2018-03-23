package com.lmtech.admin.common.web;

import com.lmtech.util.SpringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SpringContextLoaderListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent event) {
		SpringUtil.destroyContext();
	}

	public void contextInitialized(ServletContextEvent event) {
		ServletContext servletContext = event.getServletContext();
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		SpringUtil.initContext(context);
	}

}
