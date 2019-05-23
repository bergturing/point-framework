package io.github.bergturing.point.dataset;

/**
 * 记录对象接口
 *
 * @author bergturing@qq.com
 */
public interface Record {
    /**
     * 获取指定属性字段的值
     *
     * @param fieldName 字段名
     * @return 字段值
     */
    Object get(String fieldName);

    /**
     * 校验当前记录的数据
     *
     * @return 数据校验结果
     */
    boolean validate();
}
