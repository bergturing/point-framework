package io.github.bergturing.point.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 实体工具类
 *
 * @author bergturing@qq.com
 * @apiNote 2019/5/19
 */
public class EntityUtils {
    /**
     * 获取对象的值
     *
     * @param source    数据来源对象
     * @param fieldName 字段名
     * @param <T>       数据类型的泛型
     * @return 对应字段的值
     */
    public static <T> Object getDataValue(T source, String fieldName) {
        Objects.requireNonNull(source, "source can not be null");
        Objects.requireNonNull(fieldName, "field name can not be null");

        // 字段值
        Object fieldValue = null;

        try {
            // 获取字段对应的get方法
            Method getMethod = source.getClass().getDeclaredMethod("get" + StringUtils.toUpperCaseFirstOne(fieldName));
            // 存在对应的get方法，就通过get方法获取值
            fieldValue = getMethod.invoke(source);
        } catch (NoSuchMethodException e) {
            try {
                // 不存在get方法，就通过属性获取
                Field field = source.getClass().getDeclaredField(fieldName);
                // 获取值
                field.setAccessible(true);
                fieldValue = field.get(source);
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                ex.printStackTrace();
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        // 返回结果
        return fieldValue;
    }

    /**
     * 设置对象的值
     *
     * @param source    数据来源对象
     * @param fieldName 字段名
     * @param value     数据的值
     * @param <S>       数据类型的泛型
     * @param <V>       值的泛型
     * @return 如果对应字段的set方法的返回值不为null，就返回方法的返回值，否则就返回数据来源对象
     */
    public static <S, V> Object setDataValue(S source, String fieldName, V value) {
        Objects.requireNonNull(source, "source can not be null");
        Objects.requireNonNull(fieldName, "field name can not be null");

        //返回结果对象
        Object methodResult = null;

        try {
            //获取当前字段的set方法
            Method setMethod = source.getClass().getDeclaredMethod("set" + StringUtils.toUpperCaseFirstOne(fieldName), value.getClass());
            // 获取set方法
            methodResult = setMethod.invoke(source, value);
        } catch (NoSuchMethodException e) {
            try {
                // 获取字段对象
                Field field = source.getClass().getDeclaredField(fieldName);

                // 对字段设置值
                field.setAccessible(true);
                field.set(source, value);
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                ex.printStackTrace();
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        //返回结果
        return null == methodResult ? source : methodResult;
    }
}
