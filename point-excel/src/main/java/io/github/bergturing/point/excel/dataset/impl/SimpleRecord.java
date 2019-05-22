package io.github.bergturing.point.excel.dataset.impl;

import io.github.bergturing.point.excel.dataset.Record;
import io.github.bergturing.point.utils.EntityUtils;

/**
 * 抽象的记录实现
 *
 * @author bergturing@qq.com
 * @apiNote 2019/5/22
 */
public class SimpleRecord<E> implements Record<E> {
    /**
     * 数据对象
     */
    private E data;

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
