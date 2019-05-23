package io.github.bergturing.point.excel.defaults;

import io.github.bergturing.point.core.prototype.Prototype;
import io.github.bergturing.point.core.prototype.defaults.AbstractPrototype;
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
     * 默认的简单Sheet配置对象
     */
    private static final SimpleSheetProps DEFAULT_SIMPLE_SHEET_PROPS = new SimpleSheetProps();

    /**
     * 初始化原型对象
     */
    public static final Prototype<SimpleSheetProps> PROTOTYPE =
            new AbstractPrototype<SimpleSheetProps>(DEFAULT_SIMPLE_SHEET_PROPS) {
                @Override
                protected SimpleSheetProps clone(SimpleSheetProps prototype) throws CloneNotSupportedException {
                    return (SimpleSheetProps) prototype.clone();
                }
            };

    // 静态代码初始化块
    static {
        // 初始化默认属性
        // 默认行高     30
        DEFAULT_SIMPLE_SHEET_PROPS.setRowHeight(BigDecimal.valueOf(30));
        // 默认边框     显示
        DEFAULT_SIMPLE_SHEET_PROPS.setBorder(Boolean.TRUE);
    }

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

    @Override
    public List<ColumnProps> getColumns() {
        return columns;
    }

    @Override
    public void setColumns(List<ColumnProps> columns) {
        this.columns = columns;
    }

    @Override
    public String getSheetName() {
        return sheetName;
    }

    @Override
    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    @Override
    public Boolean getBorder() {
        return border;
    }

    @Override
    public void setBorder(Boolean border) {
        this.border = border;
    }

    @Override
    public BigDecimal getRowHeight() {
        return rowHeight;
    }

    @Override
    public void setRowHeight(BigDecimal rowHeight) {
        this.rowHeight = rowHeight;
    }
}
