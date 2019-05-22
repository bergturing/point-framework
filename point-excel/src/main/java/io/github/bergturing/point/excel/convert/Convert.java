package io.github.bergturing.point.excel.convert;

/**
 * 内容转换接口
 *
 * @author bergturing@qq.com
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
