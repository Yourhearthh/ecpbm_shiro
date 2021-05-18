package com.example.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @ClassName:
 * @Description: 获取类的反射信息
 * @author: baoguangyu
 * @date: 2021-04-21 13:38
 * @version: 1.0
 */
public class ReflectUtil {
    /**
     * 日志类
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectUtil.class);

    /**
     * 获取属性名数组
     *
     * @param o 对象
     * @return
     */
    public static <T> String[] getFiledName(T o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
//            System.out.println(fields[i].gettype());
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }


    /**
     * 根据属性名获取对象的属性值
     *
     * @param fieldName 字段名
     * @param clz        当前对象
     * @return 对象的字节码
     */
    public static Class<?> getFieldTypeByName(String fieldName, Class clz) {
        try {
            //得到成员变量的字节码
            Field field = clz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.getType();
        } catch (Exception e) {
            System.out.println("没有此字段");
            return null;
        }
    }

    /**
     * 获取类的泛型字节码（子类）
     *
     * @param clazz 类的字节码对象
     * @param idx   字节码索引
     * @return 泛型的实际类的字节码对象
     */
    public static Class getGenericClass(Class clazz, int idx) {
        Type type = clazz.getGenericSuperclass();
        Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
        String className = actualTypeArguments[idx].getTypeName();
        Class<?> aClass = null;
        try {
            aClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return aClass;
    }

    /**
     * 根据属性名获取对象的类型，由get，set方法的字段当作bean中的属性
     *
     * @param fieldName 属性名
     * @param o         对象
     * @return
     */
    public static <T> Object getFieldValueByName(String fieldName, T o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(o, new Object[]{});
            return value;
        } catch (Exception e) {
            //没有此get方法（不是bean的属性）,忽略
            return null;
        }
    }
}
