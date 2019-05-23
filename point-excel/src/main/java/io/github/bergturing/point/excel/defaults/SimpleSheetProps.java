package io.github.bergturing.point.excel.defaults;

import io.github.bergturing.point.dataset.defaults.BaseDataSetComponentProps;
import io.github.bergturing.point.excel.ColumnProps;
import io.github.bergturing.point.excel.SheetProps;

import java.math.BigDecimal;
import java.util.List;

/**
 * 简单sheet页配置接口的实现
 *
 * @author bergturing@qq.com
 */
public class SimpleSheetProps extends BaseDataSetComponentProps implements SheetProps {
    /**
     * 列的配置对象
     */
    private List<ColumnProps> columns;

    /**
     * sheet页的名称
     */
    private String sheetName;

    /**
     * 是否显示边框
     */
    private Boolean border;

    /**
     * 行高
     */
    private BigDecimal rowHeight;
}
