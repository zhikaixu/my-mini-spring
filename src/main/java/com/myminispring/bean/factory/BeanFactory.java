package com.myminispring.bean.factory;

import com.myminispring.bean.BeansException;

public interface BeanFactory {

    /**
     * 根据Bean名称获取Bean实例
     * @param beanName
     * @return Bean实例
     * @throws BeansException
     */
    Object getBean(String beanName) throws BeansException;

    /**
     * 根据Bean名称和类型获取Bean实例
     * @param beanName
     * @param requiredType
     * @return Bean实例
     * @param <T> Bean类型
     * @throws BeansException
     */
    <T> T getBean(String beanName, Class<T> requiredType) throws BeansException;

    /**
     * 根据Bean类型获取Bean实例
     * @param requiredType
     * @return Bean实例
     * @param <T> Bean类型
     * @throws BeansException
     */
    <T> T getBean(Class<T> requiredType) throws BeansException;

    /**
     * 判断是否包含指定名称的Bean
     * @param name
     * @return 是否包含
     */
    boolean containsBean(String name);
}
