package io.github.bergturing.point.excel.dataset.impl;

import io.github.bergturing.point.excel.dataset.FieldProps;
import io.github.bergturing.point.excel.enums.FieldType;
import io.github.bergturing.point.excel.validator.Validator;

import java.math.BigDecimal;

/**
 * 字段属性接口的默认实现
 *
 * @author bergturing@qq.com
 * @apiNote 2019/5/22
 */
public class SimpleFieldProps implements FieldProps {
    /**
     * 默认的字段属性对象
     */
    public static final FieldProps DEFAULT_SIMPLE_FIELD_PROPS = new SimpleFieldProps() {
        /**
         * 默认的字段属性对象
         */
        private final FieldProps defaultSimpleFieldProps = new SimpleFieldProps();

        {
            // 设置默认属性值
            // 是否必须
            this.defaultSimpleFieldProps.setRequired(Boolean.FALSE);
            // true的默认值
            this.defaultSimpleFieldProps.setTrueValue("TRUE");
            // false的默认值
            this.defaultSimpleFieldProps.setFalseValue("FALSE");
        }

        @Override
        public void setName(String name) {
        }

        @Override
        public void setType(FieldType type) {
        }

        @Override
        public void setLabel(String label) {
            super.setLabel(label);
        }

        @Override
        public void setFormat(String format) {
        }

        @Override
        public void setPattern(String pattern) {
        }

        @Override
        public void setMinLength(Integer minLength) {
        }

        @Override
        public void setMaxLength(Integer maxLength) {
        }

        @Override
        public void setMin(BigDecimal min) {
        }

        @Override
        public void setMax(BigDecimal max) {
        }

        @Override
        public void setValidator(Validator<?, ?> validator) {
        }

        @Override
        public void setRequired(Boolean required) {
        }

        @Override
        public void setTrueValue(String trueValue) {
        }

        @Override
        public void setFalseValue(String falseValue) {
        }

        @Override
        public void setDefaultValue(String defaultValue) {
        }
    };
    /**
     * 字段名
     */
    private String name;
    /**
     * 字段类型
     */
    private FieldType type;
    /**
     * 字段标签
     */
    private String label;
    /**
     * 日期类型字段值格式化
     */
    private String format;
    /**
     * 正则
     */
    private String pattern;
    /**
     * 最小长度
     */
    private Integer minLength;
    /**
     * 最大长度
     */
    private Integer maxLength;
    /**
     * 最小值
     */
    private BigDecimal min;
    /**
     * 最大值
     */
    private BigDecimal max;
    /**
     * 校验器
     */
    private Validator<?, ?> validator;
    /**
     * 是否必须
     */
    private Boolean required;
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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public FieldType getType() {
        return type;
    }

    @Override
    public void setType(FieldType type) {
        this.type = type;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String getFormat() {
        return format;
    }

    @Override
    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public String getPattern() {
        return pattern;
    }

    @Override
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Integer getMinLength() {
        return minLength;
    }

    @Override
    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    @Override
    public Integer getMaxLength() {
        return maxLength;
    }

    @Override
    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public BigDecimal getMin() {
        return min;
    }

    @Override
    public void setMin(BigDecimal min) {
        this.min = min;
    }

    @Override
    public BigDecimal getMax() {
        return max;
    }

    @Override
    public void setMax(BigDecimal max) {
        this.max = max;
    }

    @Override
    public Validator<?, ?> getValidator() {
        return validator;
    }

    @Override
    public void setValidator(Validator<?, ?> validator) {
        this.validator = validator;
    }

    @Override
    public Boolean getRequired() {
        return required;
    }

    @Override
    public void setRequired(Boolean required) {
        this.required = required;
    }

    @Override
    public String getTrueValue() {
        return trueValue;
    }

    @Override
    public void setTrueValue(String trueValue) {
        this.trueValue = trueValue;
    }

    @Override
    public String getFalseValue() {
        return falseValue;
    }

    @Override
    public void setFalseValue(String falseValue) {
        this.falseValue = falseValue;
    }

    @Override
    public String getDefaultValue() {
        return defaultValue;
    }

    @Override
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
