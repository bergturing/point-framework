package com.bergturing.point.enhance.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 返回当前设置值对象的设置方法
 * <p>
 * 例：
 * 类 ClassA 有一个属性 propertyA
 * 在使用了 @SetterR 注解后
 * 会增加一个
 * public ClassA setPropertyAR(PropertyA propertyA) {
 * this.propertyA = propertyA;
 * return this;
 * }
 * 的方法
 *
 * @author bergturing@qq.com
 * @apiNote 2019/5/19
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface SetterR {
}
