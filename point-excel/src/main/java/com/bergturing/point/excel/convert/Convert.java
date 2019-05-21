package com.bergturing.point.excel.convert;

/**
 * 内容转换接口
 *
 * @author bergturing@qq.com
 * @apiNote 2019/5/21
 */
@FunctionalInterface
public interface Convert<V> {
    /**
     * 将值对象转换成结果字符串
     *
     * @param value 值对象
     * @return 结果字符串
     */
    String convert(V value);
}
