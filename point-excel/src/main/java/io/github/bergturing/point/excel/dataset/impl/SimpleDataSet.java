package io.github.bergturing.point.excel.dataset.impl;

import io.github.bergturing.point.excel.ExcelFactory;
import io.github.bergturing.point.excel.dataset.*;

import java.util.*;

import static io.github.bergturing.point.stream.strategy.StrategyStreamUtils.countFunctionStream;
import static io.github.bergturing.point.stream.strategy.StrategyStreamUtils.process;
import static io.github.bergturing.point.utils.CollectionUtils.isNotEmpty;
import static java.util.stream.Collectors.toList;

/**
 * 数据集接口的简单实现
 *
 * @author bergturing@qq.com
 * @date 2019/5/22
 */
public class SimpleDataSet implements DataSet {
    /**
     * 数据集配置对象
     */
    private DataSetProps props;

    /**
     * 语言环境
     */
    private Locale locale;

    /**
     * 字段对象
     */
    private Map<String, Field> fields = new HashMap<>(16);

    /**
     * 数据集中的数据
     */
    private List<Record> records = Collections.emptyList();

    /**
     * 标识是否分页
     */
    private Boolean paging;

    /**
     * 分页大小
     */
    private Integer pageSize;

    /**
     * 总的数据量
     */
    private Integer totalCount;

    /**
     * 当前页
     */
    private Integer currentPage;

    public SimpleDataSet(DataSetProps dataSetProps) {
        // 属性赋值
        this.props = ExcelFactory.createDataSetProps(dataSetProps);
    }

    @Override
    public Locale getLocale() {
        return this.locale;
    }

    @Override
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public Boolean getPaging() {
        return this.paging;
    }

    @Override
    public void setPaging(Boolean paging) {
        this.paging = paging;
    }

    @Override
    public Integer getPageSize() {
        return this.pageSize;
    }

    @Override
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public Integer getTotalPage() {
        return this.getPaging() ? (int) Math.ceil(this.totalCount * 1.0 / this.pageSize) : 1;
    }

    @Override
    public Integer getLength() {
        return this.records.size();
    }

    @Override
    public boolean validate() {
        // 返回校验结果
        return process(this.records, countFunctionStream(),
                // 判断所有的数据行都通过了校验
                stream -> stream
                        .allMatch(Record::validate))
                // 没有校验的返回结果就是校验失败
                .orElse(Boolean.FALSE);
    }

    @Override
    public Field getField(String name) {
        // 根据名字获取字段对象
        return this.fields.get(name);
    }

    @Override
    public void initFields(List<FieldProps> fieldPropsList) {
        if (isNotEmpty(fieldPropsList)) {
            for (FieldProps fieldProps : fieldPropsList) {
                this.addField(fieldProps.getName(), fieldProps);
            }
        }
    }

    @Override
    public Field addField(String name, FieldProps fieldProps) {
        // 创建属性字段
        final Field field = ExcelFactory.createField(fieldProps, this);
        // 设置属性字段
        this.fields.put(name, field);

        // 返回属性字段对象
        return field;
    }

    @Override
    public DataSet loadData(List<Object> dataList, Integer totalCount) {
        if (isNotEmpty(dataList)) {
            // 获取数据
            dataList = this.getPaging() ? dataList.subList(0, this.getPageSize()) : dataList;

            // 设置值
            this.records = process(dataList, countFunctionStream(),
                    stream -> stream
                            .map(data -> ExcelFactory.createRecord(data, this))
                            .collect(toList()))
                    .orElse(Collections.emptyList());

            // 设置数据总数
            if (paging && totalCount != null && !totalCount.equals(0)) {
                this.totalCount = totalCount;
            } else {
                this.totalCount = this.records.size();
            }
        }

        // 返回当前数据集对象
        return this;
    }
}
