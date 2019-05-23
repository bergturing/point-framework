package io.github.bergturing.point.excel;

import io.github.bergturing.point.dataset.DataSetComponentProps;

import java.math.BigDecimal;
import java.util.List;

/**
 * sheet页的配置对象
 *
 * @author bergturing@qq.com
 */
public interface SheetProps extends DataSetComponentProps {
    /**
     * 获取列的配置对象
     *
     * @return 列的配置对象
     */
    List<ColumnProps> getColumns();

    /**
     * 设置列的配置对象
     *
     * @param columns 列的配置对象
     */
    void setColumns(List<ColumnProps> columns);

    /**
     * 获取sheet页的名称
     *
     * @return sheet页的名称
     */
    String getSheetName();

    /**
     * 设置sheet页的名称
     *
     * @param sheetName sheet页的名称
     */
    void setSheetName(String sheetName);

    /**
     * 获取是否显示边框
     *
     * @return 是否显示边框
     */
    Boolean getBorder();

    /**
     * 设置是否显示边框
     *
     * @param border 是否显示边框
     */
    void setBorder(Boolean border);

    /**
     * 获取行高
     *
     * @return 行高
     */
    BigDecimal getRowHeight();

    /**
     * 设置行高
     *
     * @param rowHeight 行高
     */
    void setRowHeight(BigDecimal rowHeight);
}
