package com.bergturing.point.excel.components;

import java.util.List;

/**
 * excel对象的抽象
 *
 * @author bergturing@qq.com
 * @apiNote 2019/5/21
 */
public class Excel {
    /**
     * excel的名称
     */
    private String excelName;

    /**
     * excel的sheet页
     */
    private List<Sheet<?>> sheetList;
}
