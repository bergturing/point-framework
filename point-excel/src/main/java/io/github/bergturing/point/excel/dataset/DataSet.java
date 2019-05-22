package io.github.bergturing.point.excel.dataset;

import java.util.List;
import java.util.Locale;

/**
 * 数据集接口
 *
 * @author bergturing@qq.com
 * @apiNote 2019/5/22
 */
public interface DataSet<E> {
    /**
     * 获取语言环境对象
     *
     * @return 语言环境对象
     */
    Locale getLocale();

    /**
     * 设置语言环境对象
     *
     * @param locale 语言环境对象
     */
    void setLocale(Locale locale);

    /**
     * 获取是否分页
     *
     * @return 是否分页
     */
    Boolean getPaging();

    /**
     * 设置是否分页
     *
     * @param paging 是否分页
     */
    void setPaging(Boolean paging);

    /**
     * 获取分页大小
     *
     * @return 分页大小
     */
    Integer getPageSize();

    /**
     * 设置分页大小
     *
     * @param pageSize 分页大小
     */
    void setPageSize(Integer pageSize);

    /**
     * 获取数据集的总页数
     *
     * @return 数据集的总页数
     */
    Integer getTotalPage();

    /**
     * 记录数
     *
     * @return 记录数
     */
    Integer getLength();

    /**
     * 校验数据集的数据
     *
     * @return 数据校验结果
     */
    boolean validate();

    /**
     * 根据字段名获取字段对象
     *
     * @param name 字段名
     * @return 字段对象
     */
    Field getField(String name);

    /**
     * 初始化字段
     *
     * @param fieldProps 字段配置对象
     */
    void initFields(List<FieldProps> fieldProps);

    /**
     * 新增属性字段
     *
     * @param name       字段名称
     * @param fieldProps 字段配置属性
     * @return 新增的字段对象
     */
    Field addField(String name, FieldProps fieldProps);

    /**
     * 加载数据
     *
     * @param dataList   待加载的数据
     * @param totalCount 数据的总量
     * @return 数据集对象
     */
    DataSet<E> loadData(List<E> dataList, Integer totalCount);
}
