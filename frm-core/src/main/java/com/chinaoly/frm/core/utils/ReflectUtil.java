package com.chinaoly.frm.core.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.util.Assert;

/**
 * 对象反射工具类
 * @author jiangyi
 * @Date 2018.5.11
 */

public class ReflectUtil {

    public ReflectUtil() {
    }

    public static Class<?> getGenericInterfaces(Class<?> clazz, int index) {
        Type[] type = ((ParameterizedType)clazz.getGenericSuperclass()).getActualTypeArguments();
        return (Class)type[index];
    }

    public static Method getReadMethod(Class<?> clazz, String fieldName) {
        try {
            Assert.notNull(fieldName);
            char cha = Character.toUpperCase(fieldName.charAt(0));
            String methodName = ReflectUtil.MethodPrefix.get + String.valueOf(cha) + fieldName.substring(1);
            return findMethod(clazz, methodName);
        } catch (Exception var4) {
            return null;
        }
    }

    public static List<Method> getAllReadMethod(Class<?> clazz, String... ignoreFieldNames) {
        ArrayList methodList = new ArrayList();

        try {
            Field[] fields = findFields(clazz);
            Set<String> set = null;
            if (ignoreFieldNames != null && ignoreFieldNames.length > 0) {
                set = new HashSet();
                Collections.addAll(set, ignoreFieldNames);
            }

            Field[] var5 = fields;
            int var6 = fields.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                Field field = var5[var7];
                if (!"serialVersionUID".equals(field.getName()) && (set == null || set.contains(field.getName()))) {
                    Method readMethod = getReadMethod(clazz, field.getName());
                    if (readMethod != null) {
                        methodList.add(readMethod);
                    }
                }
            }
        } catch (Exception var10) {
        }

        return methodList;
    }

    public static Method getWriteMethod(Class<?> clazz, String fieldName) {
        try {
            Assert.notNull(fieldName);
            Field f = findField(clazz, fieldName);
            char cha = Character.toUpperCase(fieldName.charAt(0));
            String methodName = ReflectUtil.MethodPrefix.set + String.valueOf(cha) + fieldName.substring(1);
            return findMethod(clazz, methodName, f.getType());
        } catch (Exception var5) {
            return null;
        }
    }

    public static Method getWriteMethodByRead(Class<?> clazz, Method method) {
        try {
            Assert.notNull(clazz);
            Assert.notNull(method);
            String methodName = method.getName();
            if (methodName.startsWith(ReflectUtil.MethodPrefix.get.toString())) {
                String fieldName = methodName.substring(3);
                char cha = Character.toLowerCase(fieldName.charAt(0));
                return getWriteMethod(clazz, cha + fieldName.substring(1));
            }
        } catch (Exception var5) {
        }

        return null;
    }

    public static List<Method> getAllWriteMethod(Class<?> clazz, String... ignoreFieldNames) {
        ArrayList methodList = new ArrayList();

        try {
            Field[] fields = findFields(clazz);
            Set<String> set = null;
            if (ignoreFieldNames != null && ignoreFieldNames.length > 0) {
                set = new HashSet();
                Collections.addAll(set, ignoreFieldNames);
            }

            Field[] var5 = fields;
            int var6 = fields.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                Field field = var5[var7];
                if (!"serialVersionUID".equals(field.getName()) && (set == null || set.contains(field.getName()))) {
                    Method readMethod = getWriteMethod(clazz, field.getName());
                    if (readMethod != null) {
                        methodList.add(readMethod);
                    }
                }
            }
        } catch (Exception var10) {
        }

        return methodList;
    }

    public static <T> T newInstance(Class<T> clazz) {
        if (clazz == null) {
            String msg = "Class method parameter cannot be null.";
            throw new IllegalArgumentException(msg);
        } else {
            try {
                return clazz.newInstance();
            } catch (Exception var2) {
                throw new RuntimeException("Unable to instantiate class [" + clazz.getName() + "]", var2);
            }
        }
    }

    public static Field findField(Class<?> type, String fieldName) {
        Assert.notNull(type, "type must not be null!");
        Assert.notNull(fieldName, "fieldName must not be null!");
        Class<?> targetClass = type;

        Field foundField;
        for(foundField = null; targetClass != Object.class; targetClass = targetClass.getSuperclass()) {
            try {
                foundField = targetClass.getDeclaredField(fieldName);
            } catch (Exception var5) {
                ;
            }

            if (foundField != null) {
                break;
            }
        }

        return foundField;
    }

    public static Method findMethod(Class<?> type, String methodName, Class... classes) {
        Assert.notNull(type, "type must not be null!");
        Assert.notNull(methodName, "fieldName must not be null!");
        Class<?> targetClass = type;

        Method foundMethod;
        for(foundMethod = null; targetClass != Object.class; targetClass = targetClass.getSuperclass()) {
            try {
                foundMethod = targetClass.getDeclaredMethod(methodName, classes);
            } catch (Exception var6) {
                ;
            }

            if (foundMethod != null) {
                break;
            }
        }

        return foundMethod;
    }

    public static Field[] findFields(Class<?> type) {
        Assert.notNull(type, "type must not be null!");
        Class<?> targetClass = type;

        ArrayList fieldList;
        for(fieldList = new ArrayList(); targetClass != Object.class; targetClass = targetClass.getSuperclass()) {
            fieldList.addAll(Arrays.asList(targetClass.getDeclaredFields()));
        }

        List<Field> result = (List)fieldList.stream().filter((field) -> {
            return !field.getClass().getName().equals("serialVersionUID");
        }).collect(Collectors.toList());
        return (Field[])result.toArray(new Field[result.size()]);
    }

    static enum MethodPrefix {
        get,
        set;

        private MethodPrefix() {
        }
    }
}
