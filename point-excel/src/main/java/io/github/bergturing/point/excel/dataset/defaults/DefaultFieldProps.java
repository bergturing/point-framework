package io.github.bergturing.point.excel.dataset.defaults;

import io.github.bergturing.point.excel.dataset.FieldProps;
import io.github.bergturing.point.excel.dataset.impl.SimpleFieldProps;
import io.github.bergturing.point.excel.enums.FieldType;
import io.github.bergturing.point.excel.validator.Validator;

import java.math.BigDecimal;

/**
 * 默认的参数对象
 *
 * @author bergturing@qq.com
 * @apiNote 2019/5/22
 */
public class DefaultFieldProps extends SimpleFieldProps {
    /**
     * 字段配置对象
     */
    private final FieldProps fieldProps;

    public DefaultFieldProps(FieldProps fieldProps) {
        this.fieldProps = fieldProps;
    }

    @Override
    public String getName() {
        return this.fieldProps.getName();
    }

    @Override
    public void setName(String name) {
    }

    @Override
    public FieldType getType() {
        return this.fieldProps.getType();
    }

    @Override
    public void setType(FieldType type) {
    }

    @Override
    public String getLabel() {
        return this.fieldProps.getLabel();
    }

    @Override
    public void setLabel(String label) {
    }

    @Override
    public String getFormat() {
        return this.fieldProps.getFormat();
    }

    @Override
    public void setFormat(String format) {
    }

    @Override
    public String getPattern() {
        return this.fieldProps.getPattern();
    }

    @Override
    public void setPattern(String pattern) {
    }

    @Override
    public Integer getMinLength() {
        return this.fieldProps.getMinLength();
    }

    @Override
    public void setMinLength(Integer minLength) {
    }

    @Override
    public Integer getMaxLength() {
        return this.fieldProps.getMaxLength();
    }

    @Override
    public void setMaxLength(Integer maxLength) {
    }

    @Override
    public BigDecimal getMin() {
        return this.fieldProps.getMin();
    }

    @Override
    public void setMin(BigDecimal min) {
    }

    @Override
    public BigDecimal getMax() {
        return this.fieldProps.getMax();
    }

    @Override
    public void setMax(BigDecimal max) {
    }

    @Override
    public Validator<?, ?> getValidator() {
        return this.fieldProps.getValidator();
    }

    @Override
    public void setValidator(Validator<?, ?> validator) {
    }

    @Override
    public Boolean getRequired() {
        return this.fieldProps.getRequired();
    }

    @Override
    public void setRequired(Boolean required) {
    }

    @Override
    public String getTrueValue() {
        return this.fieldProps.getTrueValue();
    }

    @Override
    public void setTrueValue(String trueValue) {
    }

    @Override
    public String getFalseValue() {
        return this.fieldProps.getFalseValue();
    }

    @Override
    public void setFalseValue(String falseValue) {
    }

    @Override
    public String getDefaultValue() {
        return this.fieldProps.getDefaultValue();
    }

    @Override
    public void setDefaultValue(String defaultValue) {
    }
}
