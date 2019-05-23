package io.github.bergturing.point.dataset.defaults;

import io.github.bergturing.point.dataset.*;

/**
 * 属性字段的接口的简单实现
 *
 * @author bergturing@qq.com
 */
public class SimpleField implements Field {
    /**
     * 字段配置对象
     */
    private final FieldProps props;

    /**
     * 数据集对象
     */
    private final DataSet dataSet;

    /**
     * 记录对象
     */
    private Record record;

    /**
     * 校验器对象
     */
    private Validator<?, ?> validator;

    /**
     * 构造器
     *
     * @param fieldProps 字段属性对象
     * @param dataSet    数据集对象
     */
    public SimpleField(FieldProps fieldProps, DataSet dataSet) {
        this.props = DataSetFactory.createFieldProps(fieldProps);
        this.dataSet = dataSet;
    }

    /**
     * 构造器
     *
     * @param fieldProps 字段属性对象
     * @param dataSet    数据集对象
     */
    public SimpleField(FieldProps fieldProps, DataSet dataSet, Record record) {
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
