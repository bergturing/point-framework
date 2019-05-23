package io.github.bergturing.point.dataset;

/**
 * 数据集使用组件属性接口
 *
 * @author bergturing@qq.com
 */
public interface DataSetComponentProps {
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
}
