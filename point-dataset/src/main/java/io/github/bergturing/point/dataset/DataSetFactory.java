package io.github.bergturing.point.dataset;

import io.github.bergturing.point.dataset.defaults.*;

/**
 * 数据集工厂类
 *
 * @author bergturing@qq.com
 */
public class DataSetFactory {
    /**
     * 创建属性字段对象
     *
     * @param fieldProps 属性配置对象
     * @param dataSet    数据集对象
     * @return 属性字段对象
     */
    public static Field createField(FieldProps fieldProps, DataSet dataSet) {
        return new SimpleField(fieldProps, dataSet);
    }

    /**
     * 创建Record对象
     *
     * @param data    数据对象
     * @param dataSet 数据集对象
     * @return 记录对象
     */
    public static Record createRecord(Object data, DataSet dataSet) {
        return new SimpleRecord(data, dataSet);
    }

    /**
     * 创建数据集对象
     *
     * @param dataSetProps 数据集配置对象
     * @return 创建的数据集对象
     */
    public static DataSet createDataSet(DataSetProps dataSetProps) {
        return new SimpleDataSet(dataSetProps);
    }

    /**
     * 创建字段配置对象
     *
     * @return 字段配置对象
     */
    public static FieldProps createFieldProps() {
        return SimpleFieldProps.PROTOTYPE.prototypeClone();
    }

    /**
     * 根据指定的字段属性对象创建字段配置对象
     *
     * @param fieldProps 指定的字段属性
     * @return 字段配置对象
     */
    public static FieldProps createFieldProps(FieldProps fieldProps) {
        return SimpleFieldProps.PROTOTYPE.prototypeClone(fieldProps);
    }

    /**
     * 创建数据集配置对象
     *
     * @return 数据集配置对象
     */
    public static DataSetProps createDataSetProps() {
        // 创建数据集配置对象
        return SimpleDataSetProps.PROTOTYPE.prototypeClone();
    }

    /**
     * 根据指定的数据集配置对象创建数据集配置对象
     *
     * @param dataSetProps 指定的数据集配置对象
     * @return 数据集配置对象
     */
    public static DataSetProps createDataSetProps(DataSetProps dataSetProps) {
        // 创建数据集配置对象
        return SimpleDataSetProps.PROTOTYPE.prototypeClone(dataSetProps);
    }
}
