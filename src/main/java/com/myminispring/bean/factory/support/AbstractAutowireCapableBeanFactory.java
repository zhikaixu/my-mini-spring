package com.myminispring.bean.factory.support;

import com.myminispring.bean.BeansException;
import com.myminispring.bean.PropertyValue;
import com.myminispring.bean.PropertyValues;
import com.myminispring.bean.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {


    /**
     * 创建Bean实例
     * @param beanName
     * @param beanDefinition
     * @param args 构造参数
     * @return Bean实例
     * @throws BeansException
     */
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException {
        Object bean = null;
        try {
            // 创建Bean实例
            bean = createBeanInstance(beanDefinition, beanName, args);

            // 填充Bean属性
            applyPropertyValues(beanName, bean, beanDefinition);

            // 执行Bean的初始化方法
            bean = initializeBean(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("创建Bean失败: " + beanName, e);
        }

        // 注册单例Bean
        if (beanDefinition.isSingleton()) {
            registerSingleton(beanName, bean);
        }
        return bean;
    }

    /**
     * 创建Bean实例
     * @param beanDefinition
     * @param beanName
     * @param args 构造参数
     * @return Bean实例
     * @throws BeansException
     */
    protected Object createBeanInstance(BeanDefinition beanDefinition, String beanName, Object[] args) throws BeansException {
        Constructor<?> constructorToUse = null;
        Class<?> beanClass = beanDefinition.getBeanClass();
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();

        // 如果有构造参数，则查找匹配的构造函数
        if (args != null && args.length > 0) {
            for (Constructor<?> constructor : declaredConstructors) {
                if (constructor.getParameterTypes().length == args.length) {
                    constructorToUse = constructor;
                    break;
                }
            }
        } else {
            try {
                // 如果没有构造参数，则使用默认构造函数
                return beanClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new BeansException("创建Bean实例失败: " + beanName, e);
            }
        }

        // 使用找到的构造函数创建实例
        try {
            return constructorToUse.newInstance(args);
        } catch (Exception e) {
            throw new BeansException("创建Bean实例失败: " + beanName, e);
        }
    }

    /**
     * 填充Bean属性
     * @param beanName
     * @param bean
     * @param beanDefinition
     */
    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            if (propertyValues.isEmpty()) {
                return;
            }

            for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                String name = propertyValue.getName();
                Object value = propertyValue.getValue();

                // 优先使用转换后的值
                Object valueToUse = propertyValue.getConvertedValue();
                if (valueToUse == null) {
                    valueToUse = value;
                }

                // 通过反射设置属性值
                Field field = bean.getClass().getDeclaredField(name);
                field.setAccessible(true);
                field.set(bean, valueToUse);
            }
        } catch (Exception e) {
            throw new BeansException("填充Bean属性失败: " + beanName, e);
        }
    }

    /**
     * 初始化Bean
     * @param beanName
     * @param bean
     * @param beanDefinition
     * @return
     */
    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        // 执行初始化方法
        invokeInitMethods(beanName, bean, beanDefinition);
        return bean;
    }

    /**
     * 执行Bean的初始化方法
     * @param beanName
     * @param bean
     * @param beanDefinition
     */
    private void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) {
        // 如果Bean定义了初始化方法，则执行
        String initMethodName = beanDefinition.getInitMethodName();
        if (initMethodName != null && !initMethodName.isEmpty()) {
            try {
                bean.getClass().getMethod(initMethodName).invoke(bean);
            } catch (Exception e) {
                throw new BeansException("执行Bean初始化方法失败: " + beanName, e);
            }
        }
    }
}
