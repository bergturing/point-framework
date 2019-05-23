package io.github.bergturing.point.dataset.defaults;

import io.github.bergturing.point.dataset.DataSet;
import io.github.bergturing.point.dataset.DataSetComponent;
import io.github.bergturing.point.dataset.DataSetComponentProps;

import java.util.Locale;
import java.util.Objects;

/**
 * 使用数据集的组件接口的基本实现
 *
 * @author bergturing@qq.com
 */
public class BaseDataSetComponent<T extends DataSetComponentProps> implements DataSetComponent<T> {
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

    @Override
    public Locale getLocale() {
        // 判断数据集的语言环境是否为空
        if (Objects.nonNull(this.dataSet) && Objects.nonNull(this.dataSet.getLocale())) {
            // 数据集的语言环境存在，就返回数据集的语言环境
            return this.dataSet.getLocale();
        }

        // 默认的语言环境
        return Locale.getDefault();
    }
}
