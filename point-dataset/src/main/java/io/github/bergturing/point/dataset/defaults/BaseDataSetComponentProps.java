package io.github.bergturing.point.dataset.defaults;

import io.github.bergturing.point.dataset.DataSet;
import io.github.bergturing.point.dataset.DataSetComponentProps;

/**
 * 使用数据集的组件配置的简单实现
 *
 * @author bergturing@qq.com
 */
public abstract class BaseDataSetComponentProps implements DataSetComponentProps {
    /**
     * 数据集对象
     */
    private DataSet dataSet;

    @Override
    public DataSet getDataSet() {
        return this.dataSet;
    }

    @Override
    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }
}
