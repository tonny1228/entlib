package org.llama.library.utils;

import javassist.*;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by tonny on 2015/11/2.
 */
public class POJOUtil {
    public static Object copyToDynamicObject(String classname, Object object, String... field) {
        if (field == null || field.length == 0 || object == null) {
            return object;
        }


        Class<?> clazz = null;
        try {
            clazz = Class.forName(classname);
        } catch (Exception e) {
            try {
                clazz = makeClass(classname, object, field, null, null, null);
            } catch (CannotCompileException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }

        Object instance = null;
        try {
            instance = clazz.newInstance();
            for (String s : field) {
                FieldUtils.writeField(instance, s, PropertyUtils.getProperty(object, s));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

    public static Object copyToDynamicObject(String classname, Object object, Map<String, String> replaceMapping) {
        //        List<Field> fields = ClassUtils.getFields(object.getClass());
        //        StringBuffer buffer = new StringBuffer();
        //        for (String s : replaceMapping.keySet()) {
        //            buffer.append(s).append(",");
        //        }
        //
        //        List<Field> includes = new ArrayList<Field>();
        //
        //        for (Field field : fields) {
        //            if (replaceMapping.containsKey(field.getName()) || buffer.indexOf(field.getName() + ".") >= 0) {
        //                continue;
        //            }
        //            includes.add(field);
        //        }

        Class<?> clazz = null;
        try {
            clazz = Class.forName(classname);
        } catch (Exception e) {
            try {
                clazz = makeClass(classname, object, null, null, replaceMapping, null);
            } catch (CannotCompileException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }

        Object instance = null;

        try {
            instance = clazz.newInstance();
            for (String s : replaceMapping.keySet()) {
                FieldUtils.writeField(instance, replaceMapping.get(s), PropertyUtils.getProperty(object, s));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

    public static Object copyToDynamicObject(String classname, Object object, Map<String, String> replaceMapping, Map<String, Object> adding, String... excludes) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(classname);
        } catch (ClassNotFoundException e) {
            try {
                clazz = makeClass(classname, object, null, excludes, replaceMapping, adding);
            } catch (CannotCompileException e1) {
                e1.printStackTrace();
            }
        }

        Map<String, String> reverse = new LinkedHashMap<String, String>();
        for (String s : replaceMapping.keySet()) {
            reverse.put(replaceMapping.get(s), s);
        }


        Object instance = null;
        try {
            instance = clazz.newInstance();
            Field[] all = clazz.getDeclaredFields();
            for (Field field : all) {
                String name = field.getName();
                if (adding != null && adding.containsKey(name)) {
                    FieldUtils.writeField(instance, name, adding.get(name));
                } else if (replaceMapping.containsValue(name)) {
                    FieldUtils.writeField(instance, name, PropertyUtils.getProperty(object, reverse.get(name)));
                } else {
                    FieldUtils.writeField(instance, name, PropertyUtils.getProperty(object, name));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }


    public static Object copyToDynamicObject(String classname, Object object, Map<String, String> replaceMapping, String... excludes) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(classname);
        } catch (ClassNotFoundException e) {
            try {
                clazz = makeClass(classname, object, null, excludes, replaceMapping, null);
            } catch (CannotCompileException e1) {
                e1.printStackTrace();
            }
        }

        Map<String, String> reverse = new LinkedHashMap<String, String>();
        for (String s : replaceMapping.keySet()) {
            reverse.put(replaceMapping.get(s), s);
        }


        Object instance = null;
        try {
            instance = clazz.newInstance();
            Field[] all = clazz.getDeclaredFields();
            for (Field field : all) {
                String name = field.getName();
                if (replaceMapping.containsValue(name)) {
                    FieldUtils.writeField(instance, name, PropertyUtils.getProperty(object, reverse.get(name)));
                } else {
                    FieldUtils.writeField(instance, name, PropertyUtils.getProperty(object, name));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

    private static Class<?> makeClass(String classname, Object object, String[] includes, String[] excludes, Map<String, String> replaceMapping, Map<String, Object> adding) throws CannotCompileException {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = null;
        try {
            cc = pool.get(classname);
            return Class.forName(classname);
        } catch (Exception e) {
            cc = pool.makeClass(classname);
        }

        List<Field> fields = ClassUtils.getFields(object.getClass());
        StringBuffer buffer = new StringBuffer();
        if (replaceMapping == null) {
            replaceMapping = new HashMap<String, String>();
        }
        for (String s : replaceMapping.keySet()) {
            buffer.append(s).append(",");
        }


        List<Field> in = new ArrayList<Field>();

        List<String> ex = new ArrayList<String>();
        if (excludes != null)
            for (String exclude : excludes) {
                ex.add(exclude);
            }

        for (Field field : fields) {
            String name = field.getName();
            if (ex.contains(name) || replaceMapping.containsKey(name) || buffer.indexOf(name + ".") >= 0) {
                continue;
            }

            if (includes != null && Arrays.binarySearch(includes, name) < 0) {
                continue;
            }
            try {
                PropertyUtils.getProperty(object, field.getName());
            } catch (Exception e) {
                continue;
            }
            in.add(field);
        }


        buffer = new StringBuffer();
        if (replaceMapping == null) {
            replaceMapping = new HashMap<String, String>();
        }
        for (String s : replaceMapping.keySet()) {
            buffer.append(s).append(",");
        }


        for (Field includeField : in) {
            String name = includeField.getName();
            if (replaceMapping.containsKey(name) || buffer.indexOf(includeField + ".") >= 0) {
                continue;
            }
            try {
                CtField field = cc.getField(name);
                continue;
            } catch (NotFoundException e) {

            }

            String src = "public " + includeField.getType().getName() + " " + name + ";";
            CtField f = CtField.make(src, cc);
            f.setModifiers(Modifier.PUBLIC);
            cc.addField(f);
        }


        if (adding != null) {
            for (String s : adding.keySet()) {
                String src = "public " + adding.get(s).getClass().getName() + " " + s + ";";
                CtField f = CtField.make(src, cc);
                f.setModifiers(Modifier.PUBLIC);
                cc.addField(f);
            }
        }

        for (String s : replaceMapping.keySet()) {
            Field declaredField = getField(object.getClass(), s);
            String src = "public " + declaredField.getType().getName() + " " + replaceMapping.get(s) + ";";
            CtField f = CtField.make(src, cc);
            f.setModifiers(Modifier.PUBLIC);
            cc.addField(f);
        }

        return cc.toClass();
    }

    //
    //    private static Class<?> makeClass(String classname, ClassPool pool, Class clazz, Map<String, String> om) throws CannotCompileException {
    //        CtClass cc = pool.makeClass(classname);
    //        for (String s : om.keySet()) {
    //            Field declaredField = getField(clazz, s);
    //            String src = "public " + declaredField.getType().getName() + " " + om.get(s) + ";";
    //            CtField f = CtField.make(src, cc);
    //            f.setModifiers(Modifier.PUBLIC);
    //            cc.addField(f);
    //        }
    //        return cc.toClass();
    //    }
    //
    //
    private static Field getField(Class clazz, String f) {
        if (f.contains(".")) {
            Field field = FieldUtils.getField(clazz, StringUtils.substringBefore(f, "."), true);
            return getField(field.getType(), StringUtils.substringAfter(f, "."));
        } else {
            return FieldUtils.getField(clazz, f, true);
        }
    }
    //
    //    private static Class<?> makeClass(String classname, ClassPool pool, Class clazz, String[] field) throws CannotCompileException {
    //        CtClass cc = pool.makeClass(classname);
    //        for (String s : field) {
    ////            FieldUtils.
    //            Field declaredField = FieldUtils.getField(clazz, s, true);
    //            String src = "public " + declaredField.getType().getName() + " " + s + ";";
    //            CtField f = CtField.make(src, cc);
    //            f.setModifiers(Modifier.PUBLIC);
    //            cc.addField(f);
    //        }
    //        return cc.toClass();
    //    }
}
