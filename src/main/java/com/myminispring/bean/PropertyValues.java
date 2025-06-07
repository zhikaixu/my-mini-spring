package com.myminispring.bean;

import java.util.ArrayList;
import java.util.List;

public class PropertyValues {

    private final List<PropertyValue> propertyValueList = new ArrayList<>();

    /**
     * 添加属性值
     * @param propertyValue
     */
    public void addPropertyValue(PropertyValue propertyValue) {
        if (propertyValue == null) {
            throw new IllegalArgumentException("PropertyValue不能为null");
        }
        // 移除已存在的同名属性
        this.propertyValueList.removeIf(pv -> pv.getName().equals(propertyValue.getName()));
        this.propertyValueList.add(propertyValue);
    }

    /**
     * 获取所有属性值
     * @return 属性值数组
     */
    public PropertyValue[] getPropertyValues() {
        return this.propertyValueList.toArray(new PropertyValue[0]);
    }

    /**
     * 根据属性名获取属性值
     * @param propertyName
     * @return 属性值对象，如果不存在返回null
     */
    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue propertyValue : this.propertyValueList) {
            if (propertyValue.getName().equals(propertyName)) {
                return propertyValue;
            }
        }
        return null;
    }

    public boolean isEmpty() {
        return this.propertyValueList.isEmpty();
    }

    public boolean contains(String propertyName) {
        return getPropertyValue(propertyName) != null;
    }

    public int size() {
        return this.propertyValueList.size();
    }

}
