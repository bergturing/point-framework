package io.github.bergturing.point.excel.dataset;

import java.util.List;
import java.util.Locale;

/**
 * 数据集配置接口
 *
 * @author bergturing@qq.com
 */
public interface DataSetProps {
    /**
     * 获取语言环境
     *
     * @return 语言环境
     */
    Locale getLocale();

    /**
     * 设置语言环境
     *
     * @param locale 语言环境
     */
    void setLocale(Locale locale);

    /**
     * 获取字段组
     *
     * @return 字段组
     */
    List<Field> getFields();

    /**
     * 设置字段组
     *
     * @param fields 字段组
     */
    void setFields(List<Field> fields);

    /**
     * 获取初始化数据
     *
     * @return 初始化数据
     */
    List<Object> getData();

    /**
     * 设置初始化数据
     *
     * @param data 初始化数据
     */
    void setData(List<Object> data);

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
     * 获取是否分页标识
     *
     * @return 是否分页标识
     */
    Boolean getPaging();

    /**
     * 设置是否分页标识
     *
     * @param paging 是否分页标识
     */
    void setPaging(Boolean paging);
}
