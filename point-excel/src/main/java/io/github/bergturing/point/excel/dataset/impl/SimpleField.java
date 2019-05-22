package io.github.bergturing.point.excel.dataset.impl;

import io.github.bergturing.point.excel.dataset.DataSet;
import io.github.bergturing.point.excel.dataset.Field;
import io.github.bergturing.point.excel.dataset.FieldProps;
import io.github.bergturing.point.excel.dataset.Record;
import io.github.bergturing.point.excel.validator.Validator;

/**
 * 属性字段的接口的抽象实现
 *
 * @author bergturing@qq.com
 * @apiNote 2019/5/22
 */
public class SimpleField<E> implements Field<E> {
    /**
     * 字段配置对象
     */
    private final FieldProps props;

    /**
     * 数据集对象
     */
    private final DataSet<E> dataSet;

    /**
     * 记录对象
     */
    private Record<E> record;

    /**
     * 校验器对象
     */
    private Validator<?, E> validator;

    /**
     * 构造器
     *
     * @param fieldProps 字段属性对象
     * @param dataSet    数据集对象
     */
    public SimpleField(FieldProps fieldProps, DataSet<E> dataSet) {
        this.props = fieldProps;
        this.dataSet = dataSet;
    }

    /**
     * 构造器
     *
     * @param fieldProps 字段属性对象
     * @param dataSet    数据集对象
     */
    public SimpleField(FieldProps fieldProps, DataSet<E> dataSet, Record<E> record) {
        this(fieldProps, dataSet);
        this.record = record;
    }

    @Override
    public String getName() {
        return this.props.getName();
    }

    @Override
    public Object getValue() {
        return this.record.get(this.getName());
    }

    @Override
    public String getText() {
        return null;
    }
}
