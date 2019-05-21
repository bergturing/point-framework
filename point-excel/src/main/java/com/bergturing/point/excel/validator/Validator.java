package com.bergturing.point.excel.validator;

/**
 * 校验器接口
 *
 * @author bergturing@qq.com
 * @apiNote 2019/5/21
 */
public interface Validator<V, R> {
    /**
     * 校验数据
     *
     * @param value  当前列的值
     * @param name   当前列的字段名
     * @param record 当前行对象数据
     * @return 校验结果
     * true 表示校验成功
     * false 表示校验失败
     */
    boolean validate(V value, String name, R record);
}
