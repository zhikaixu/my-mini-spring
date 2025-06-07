package com.myminispring;

import com.myminispring.bean.PropertyValue;
import com.myminispring.bean.PropertyValues;
import com.myminispring.bean.UserDaoImpl;
import com.myminispring.bean.UserService;
import com.myminispring.bean.factory.config.BeanDefinition;
import com.myminispring.bean.factory.support.DefaultListableBeanFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ApiTest {
    @Test
    public void testBeanFactory() {
        // 1. 创建Bean工厂
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2. 注册UserDao
        BeanDefinition userDaoBeanDefinition = new BeanDefinition(UserDaoImpl.class);
        beanFactory.registerBeanDefinition("userDao", userDaoBeanDefinition);

        // 3. 注册UserService
        BeanDefinition userServiceBeanDefinition = new BeanDefinition(UserService.class);
        // 设置初始化方法
        userServiceBeanDefinition.setInitMethodName("init");

        // 设置属性
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("name", "zhangsan"));
        propertyValues.addPropertyValue(new PropertyValue("userDao", beanFactory.getBean("userDao")));
        userServiceBeanDefinition.setPropertyValues(propertyValues);

        beanFactory.registerBeanDefinition("userService", userServiceBeanDefinition);

        // 4. 获取Bean
        UserService userService = (UserService) beanFactory.getBean("userService");

        // 5. 使用Bean
        String result = userService.queryUserInfo();
        System.out.println("测试结果: " + result);

        // 6. 验证结果
        Assertions.assertEquals("beijing", result);
    }

    @Test
    public void testPropertyValues() {
        // 测试PropertyValues的功能
        PropertyValues propertyValues = new PropertyValues();

        // 测试添加属性
        propertyValues.addPropertyValue(new PropertyValue("name", "zhangsan"));
        propertyValues.addPropertyValue(new PropertyValue("age", 18));

        // 测试获取属性
        Assertions.assertTrue(propertyValues.contains("name"));
        Assertions.assertTrue(propertyValues.contains("age"));
        Assertions.assertFalse(propertyValues.contains("address"));

        // 测试属性数量
        Assertions.assertEquals(2, propertyValues.size());

        // 测试获取属性值
        Assertions.assertEquals("zhangsan", propertyValues.getPropertyValue("name").getValue());
        Assertions.assertEquals(18, propertyValues.getPropertyValue("age").getValue());

        // 测试替换属性
        propertyValues.addPropertyValue(new PropertyValue("name", "lisi"));
        Assertions.assertEquals("lisi", propertyValues.getPropertyValue("name").getValue());
        Assertions.assertEquals(2, propertyValues.size());

        // 测试转换值
        PropertyValue namePropertyValue = propertyValues.getPropertyValue("name");
        namePropertyValue.setConvertedValue("wangwu");
        Assertions.assertEquals("wangwu", namePropertyValue.getConvertedValue());
        Assertions.assertEquals("lisi", namePropertyValue.getValue());
    }
}
