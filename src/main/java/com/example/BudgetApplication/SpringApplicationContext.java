package com.example.BudgetApplication;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Class to get the ApplicationContext object which contains Beans managed by Spring.
 * 
 * @author Benjamin Chang
 *
 */
public class SpringApplicationContext implements ApplicationContextAware {

	private static ApplicationContext CONTEXT;

	/**
	 * Setter for the ApplicationContext
	 *
	 * @param applicationContext - ApplicationContext to be input
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		CONTEXT = applicationContext;

	}

	/**
	 * Getter for Bean
	 *
	 * @param beanName - String name of the bean
	 * @return CONTEXT.getBean(beanName)
	 */
	public static Object getBean(String beanName) {
		return CONTEXT.getBean(beanName);
	}
}