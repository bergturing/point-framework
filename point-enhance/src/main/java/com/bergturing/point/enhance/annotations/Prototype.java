package com.bergturing.point.enhance.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对象原型
 *
 * @author bergturing@qq.com
 * @apiNote 2019/5/21
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface Prototype {
}
