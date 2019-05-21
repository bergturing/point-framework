package com.bergturing.point.core.prototype;

/**
 * 原型拷贝接口(主要用于原型的浅拷贝)
 *
 * @author bergturing@qq.com
 * @apiNote 2019/5/21
 */
public interface Prototype<T> {
    /**
     * 原型克隆
     *
     * @return 克隆结果
     */
    T prototypeClone();

    /**
     * 根据源数据进行原型克隆
     *
     * @param source 源数据
     * @return 克隆结果
     */
    T prototypeClone(Object source);
}
