package com.bergturing.point.excel.components;

import com.bergturing.point.excel.convert.Convert;
import com.bergturing.point.excel.validator.Validator;

import java.math.BigDecimal;

/**
 * excel列的抽象
 *
 * @param <V> 当前列值类型泛型
 * @param <R> 每一行的实体对象泛型
 * @author bergturing@qq.com
 * @apiNote 2019/5/21
 */
public class Column<V, R> {
    /**
     * 字段名
     */
    private String name;

    /**
     * 字段标签
     */
    private String label;

    /**
     * 日期类型字段值格式化
     */
    private String format;

    /**
     * 正则校验
     */
    private String pattern;

    /**
     * 最大长度
     */
    private Integer maxLength;

    /**
     * 最小长度
     */
    private Integer minLength;

    /**
     * 最大值
     */
    private BigDecimal max;

    /**
     * 最小值
     */
    private BigDecimal min;

    /**
     * 步距
     */
    private BigDecimal step;

    /**
     * 是否必选
     */
    private boolean required;

    /**
     * 类型为boolean时，true对应的值
     */
    private String trueValue;

    /**
     * 类型为boolean时，false对应的值
     */
    private String falseValue;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 校验器，当返回值为 false 则为校验失败
     */
    private Validator<V, R> validator;

    /**
     * 值转换器
     */
    private Convert<V> convert;
}
