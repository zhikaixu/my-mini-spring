package com.myminispring.bean.factory.support;

import com.myminispring.bean.BeansException;
import com.myminispring.bean.factory.BeanFactory;
import com.myminispring.bean.factory.config.BeanDefinition;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {


    @Override
    public Object getBean(String beanName) throws BeansException {
        return doGetBean(beanName, null);
    }

    @Override
    public <T> T getBean(String beanName, Class<T> requiredType) throws BeansException {
        return (T) doGetBean(beanName, requiredType);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return getBean(requiredType.getName(), requiredType);
    }

    @Override
    public boolean containsBean(String name) {
        return containsSingleton(name) || containsBeanDefinition(name);
    }

    protected <T> T doGetBean(String name, Class<T> requiredType) throws BeansException {
        Object bean = getSingleton(name);
        if (bean != null) {
            return (T) bean;
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);
        return (T) createBean(name, beanDefinition, null);
    }

    protected abstract BeanDefinition getBeanDefinition(String name) throws BeansException;

    protected abstract Object createBean(String name, BeanDefinition beanDefinition, Object[] args) throws BeansException;

    protected abstract boolean containsBeanDefinition(String beanName);

}
