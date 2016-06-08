package org.llama.library.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 类、包工具
 *
 * @author tonny
 */
public class ClassUtils extends org.apache.commons.lang.ClassUtils {

    /**
     * 查找参数类的参数类型
     *
     * @param clazz
     * @return
     */
    public static Class getParameterizedType(Class clazz) {
        Type genericSuperclass = clazz.getGenericSuperclass();
        return find(genericSuperclass);
    }

    /**
     * @param genericSuperclass
     */
    protected static Class find(Type genericSuperclass) {
        if (genericSuperclass instanceof ParameterizedType) {
            Type type = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
            return find(type);
        }
        return (Class) genericSuperclass;
    }

    /**
     * 判断类是否存在
     *
     * @param className 类名称
     * @return 类是否存在
     */
    public static boolean isClassExist(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static <T> T newInstance(String className, Object... arguments) {
        try {
            Class[] parameterTypes = new Class[arguments.length];
            for (int i = 0; i < arguments.length; i++) {
                Object object = arguments[i];
                parameterTypes[i] = object.getClass();
            }
            return (T) newInstance(className, parameterTypes, arguments);
        } catch (Exception e) {
            throw new IllegalArgumentException("无法创建实例", e);
        }
    }

    public static <T> T newInstance(Class clazz) {
        try {
            return (T) clazz.newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("无法创建实例", e);
        }
    }

    public static <T> T newInstance(String className, Class[] types, Object[] arguments) {
        try {
            Class clz = Class.forName(className);
            return (T) clz.getConstructor(types).newInstance(arguments);
        } catch (Exception e) {
            throw new IllegalArgumentException("无法创建实例", e);
        }
    }

    /**
     * 查询类的接口中是superInterface或子类的接口
     *
     * @param clazz
     * @param superInterface
     * @return
     */
    public static Class getInterface(Class clazz, Class superInterface) {
        final Class<?>[] interfaces = clazz.getInterfaces();
        for (Class<?> class1 : interfaces) {
            if (ClassUtils.isAssignable(class1, superInterface)) {
                return class1;
            }
        }
        return null;
    }


    public static List<Field> getFields(Class clazz) {
        List<Field> list = new ArrayList<Field>();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            list.add(declaredField);
        }
        Class superclass = clazz.getSuperclass();
        if (superclass.equals(Object.class)) {
            return list;
        }
        list.addAll(0, getFields(superclass));
        return list;
    }
}
