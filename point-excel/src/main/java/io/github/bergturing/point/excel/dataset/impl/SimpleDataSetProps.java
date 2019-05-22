package io.github.bergturing.point.excel.dataset.impl;

import io.github.bergturing.point.core.prototype.Prototype;
import io.github.bergturing.point.core.prototype.defaults.AbstractPrototype;
import io.github.bergturing.point.excel.dataset.DataSetProps;
import io.github.bergturing.point.excel.dataset.Field;

import java.util.List;
import java.util.Locale;

/**
 * 数据集配置接口的简单实现
 *
 * @author bergturing@qq.com
 * @apiNote 2019/5/22
 */
public class SimpleDataSetProps implements DataSetProps {
    /**
     * 默认的数据集属性对象
     */
    private static final SimpleDataSetProps DEFAULT_SIMPLE_DATA_SET_PROPS = new SimpleDataSetProps();

    /**
     * 初始化原型对象
     */
    public static final Prototype<SimpleDataSetProps> PROTOTYPE =
            new AbstractPrototype<SimpleDataSetProps>(DEFAULT_SIMPLE_DATA_SET_PROPS) {
                @Override
                protected SimpleDataSetProps clone(SimpleDataSetProps prototype) throws CloneNotSupportedException {
                    return (SimpleDataSetProps) prototype.clone();
                }
            };

    // 静态初始化代码块
    static {
        // 设置默认属性值
        // 设置是否分页   是
        DEFAULT_SIMPLE_DATA_SET_PROPS.setPaging(Boolean.TRUE);
        // 设置分页大小   1000
        DEFAULT_SIMPLE_DATA_SET_PROPS.setPageSize(1000);
    }

    /**
     * 语言环境
     */
    private Locale locale;

    /**
     * 字段组
     */
    private List<Field> fields;

    /**
     * 初始化数据
     */
    private List<Object> data;

    /**
     * 分页大小
     */
    private Integer pageSize;

    /**
     * 是否分页标识
     */
    private Boolean paging;

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public List<Field> getFields() {
        return fields;
    }

    @Override
    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    @Override
    public List<Object> getData() {
        return data;
    }

    @Override
    public void setData(List<Object> data) {
        this.data = data;
    }

    @Override
    public Integer getPageSize() {
        return pageSize;
    }

    @Override
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public Boolean getPaging() {
        return paging;
    }

    @Override
    public void setPaging(Boolean paging) {
        this.paging = paging;
    }
}
