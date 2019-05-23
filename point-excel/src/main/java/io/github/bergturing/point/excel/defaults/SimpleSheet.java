package io.github.bergturing.point.excel.defaults;

import io.github.bergturing.point.dataset.defaults.BaseDataSetComponent;
import io.github.bergturing.point.excel.Sheet;
import io.github.bergturing.point.excel.SheetProps;

/**
 * Sheet接口的简单实现
 *
 * @author bergturing@qq.com
 */
public class SimpleSheet extends BaseDataSetComponent<SheetProps> implements Sheet {
    /**
     * Sheet页的配置对象
     */
    private SheetProps sheetProps;
}
