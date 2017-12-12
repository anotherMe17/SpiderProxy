package io.github.anotherme17.spiderproxy.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author lirenhao
 * date: 2017/10/23 下午6:20
 */
@Service
public class SpringBeanService implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanService.applicationContext = applicationContext;
    }

    public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return applicationContext.getBean(name, requiredType);
    }

    public static <T> Map<String, T> getBeans(Class<T> requiredType) throws BeansException {
        return applicationContext.getBeansOfType(requiredType);
    }
}
