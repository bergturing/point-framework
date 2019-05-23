package io.github.bergturing.point.excel.dataset.impl;

import io.github.bergturing.point.excel.dataset.DataSet;
import io.github.bergturing.point.excel.dataset.Field;
import io.github.bergturing.point.excel.dataset.Record;
import io.github.bergturing.point.utils.EntityUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据集记录接口的简单实现
 *
 * @author bergturing@qq.com
 */
public class SimpleRecord implements Record {
    /**
     * 数据集对象
     */
    private DataSet dataSet;
    /**
     * 字段对象
     */
    private Map<String, Field> fields;
    /**
     * 数据对象
     */
    private Object data;

    /**
     * 构造函数
     *
     * @param data    数据对象
     * @param dataSet 数据集对象
     */
    public SimpleRecord(Object data, DataSet dataSet) {
        this.fields = new HashMap<>(16);

        this.dataSet = dataSet;
        this.data = data;
    }

    @Override
    public Object get(String fieldName) {
        // 获取属性值
        return EntityUtils.getDataValue(this.data, fieldName);
    }

    @Override
    public boolean validate() {
        return false;
    }
}
