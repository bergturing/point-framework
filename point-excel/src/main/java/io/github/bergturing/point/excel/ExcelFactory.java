package io.github.bergturing.point.excel;

import io.github.bergturing.point.excel.dataset.DataSet;
import io.github.bergturing.point.excel.dataset.Field;
import io.github.bergturing.point.excel.dataset.FieldProps;
import io.github.bergturing.point.excel.dataset.Record;
import io.github.bergturing.point.excel.dataset.impl.SimpleField;

/**
 * excel相关的实体的简单工厂类
 *
 * @author bergturing@qq.com
 * @apiNote 2019/5/22
 */
public class ExcelFactory {
    /**
     * 创建属性字段对象
     *
     * @param fieldProps 属性配置对象
     * @param dataSet    数据集对象
     * @param <E>        数据集内元素的泛型
     * @return 属性字段对象
     */
    public static <E> Field<E> createField(FieldProps fieldProps, DataSet<E> dataSet) {
        return new SimpleField<>(fieldProps, dataSet);
    }

    /**
     * 创建Record对象
     *
     * @param data    数据对象
     * @param dataSet 数据集对象
     * @param <E>     数据对象的泛型
     * @return 记录对象
     */
    public static <E> Record<E> createRecord(E data, DataSet<E> dataSet) {
        return null;
    }
}
