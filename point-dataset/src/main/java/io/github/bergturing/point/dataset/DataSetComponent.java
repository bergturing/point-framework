package io.github.bergturing.point.dataset;

import java.util.Locale;

/**
 * 使用数据集的组件
 *
 * @author bergturing@qq.com
 */
public interface DataSetComponent<T extends DataSetComponentProps> {
    /**
     * 获取数据集对象
     *
     * @return 数据集对象
     */
    DataSet getDataSet();

    /**
     * 设置数据集
     *
     * @param dataSet 数据集对象
     */
    void setDataSet(DataSet dataSet);

    /**
     * 获取语言环境对象
     *
     * @return 语言环境对象
     */
    Locale getLocale();
}
