package com.irongroup.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.irongroup.unit.DataInitUtil;

public class InitListener implements ServletContextListener {
	private Logger logger =Logger.getLogger(InitListener.class);
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.info("contextDestroyed.");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("contextInitialized.");
//		DataInitUtil.initCache();
	}

}
