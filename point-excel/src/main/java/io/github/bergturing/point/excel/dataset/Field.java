package io.github.bergturing.point.excel.dataset;

/**
 * 属性字段的接口
 *
 * @author bergturing@qq.com
 * @apiNote 2019/5/22
 */
public interface Field {
    /**
     * 获取字段名称
     *
     * @return 字段名称
     */
    String getName();

    /**
     * 获取字段值
     *
     * @return 字段值
     */
    Object getValue();

    /**
     * 获取字段标题
     *
     * @return 字段标题
     */
    String getText();
}
