package io.github.bergturing.point.excel.dataset;

import io.github.bergturing.point.excel.enums.FieldType;
import io.github.bergturing.point.excel.validator.Validator;

import java.math.BigDecimal;

/**
 * 字段属性配置接口
 *
 * @author bergturing@qq.com
 */
public interface FieldProps {
    /**
     * 获取字段名
     *
     * @return 字段名
     */
    String getName();

    /**
     * 设置字段名
     *
     * @param name 字段名
     */
    void setName(String name);

    /**
     * 获取字段类型
     *
     * @return 字段类型
     */
    FieldType getType();

    /**
     * 设置字段类型
     *
     * @param type 字段类型
     */
    void setType(FieldType type);

    /**
     * 获取字段标签
     *
     * @return 字段标签
     */
    String getLabel();

    /**
     * 设置字段标签
     *
     * @param label 字段标签
     */
    void setLabel(String label);

    /**
     * 获取日期类型字段值格式化
     *
     * @return 日期类型字段值格式化
     */
    String getFormat();

    /**
     * 设置日期类型字段值格式化
     *
     * @param format 日期类型字段值格式化
     */
    void setFormat(String format);

    /**
     * 获取正则
     *
     * @return 正则
     */
    String getPattern();

    /**
     * 设置正则
     *
     * @param pattern 正则
     */
    void setPattern(String pattern);

    /**
     * 获取最小长度
     *
     * @return 最小长度
     */
    Integer getMinLength();

    /**
     * 设置最小长度
     *
     * @param minLength 最小长度
     */
    void setMinLength(Integer minLength);

    /**
     * 获取最大长度
     *
     * @return 最大长度
     */
    Integer getMaxLength();

    /**
     * 设置最大长度
     *
     * @param maxLength 最大长度
     */
    void setMaxLength(Integer maxLength);

    /**
     * 获取最小值
     *
     * @return 最小值
     */
    BigDecimal getMin();

    /**
     * 设置最小值
     *
     * @param min 最小值
     */
    void setMin(BigDecimal min);

    /**
     * 获取最大值
     *
     * @return 最大值
     */
    BigDecimal getMax();

    /**
     * 设置最大值
     *
     * @param max 最大值
     */
    void setMax(BigDecimal max);

    /**
     * 获取校验器
     *
     * @return 校验器
     */
    Validator<?, ?> getValidator();

    /**
     * 设置校验器
     *
     * @param validator 校验器
     */
    void setValidator(Validator<?, ?> validator);

    /**
     * 获取是否必须
     *
     * @return 是否必须
     */
    Boolean getRequired();

    /**
     * 设置是否必须
     *
     * @param required 是否必须
     */
    void setRequired(Boolean required);

    /**
     * 获取类型为boolean时，true对应的值
     *
     * @return 类型为boolean时，true对应的值
     */
    String getTrueValue();

    /**
     * 设置类型为boolean时，true对应的值
     *
     * @param trueValue 类型为boolean时，true对应的值
     */
    void setTrueValue(String trueValue);

    /**
     * 获取类型为boolean时，false对应的值
     *
     * @return 类型为boolean时，false对应的值
     */
    String getFalseValue();

    /**
     * 设置类型为boolean时，false对应的值
     *
     * @param falseValue 类型为boolean时，false对应的值
     */
    void setFalseValue(String falseValue);

    /**
     * 获取默认值
     *
     * @return 默认值
     */
    String getDefaultValue();

    /**
     * 设置默认值
     *
     * @param defaultValue 默认值
     */
    void setDefaultValue(String defaultValue);
}
