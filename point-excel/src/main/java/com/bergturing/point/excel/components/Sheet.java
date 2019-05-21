package com.bergturing.point.excel.components;

import java.util.List;

/**
 * excel的sheet页的抽象
 *
 * @param <R> 每一行的实体对象泛型
 * @author bergturing@qq.com
 * @apiNote 2019/5/21
 */
public class Sheet<R> {
    /**
     * 默认的sheet页最大的数据量
     */
    public static final Integer DEFAULT_MAX_COUNT = 5000;

    /**
     * sheet页的名称
     */
    private String sheetName;

    /**
     * sheet页最大的数据量
     */
    private Integer maxCount;

    /**
     * sheet页的列
     */
    private List<Column<?, R>> columnList;
}
