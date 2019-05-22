package io.github.bergturing.point.dal.annotations;

import io.github.bergturing.point.dal.enums.Union;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 标识注解
 * 当前注解应用到参数上，表明需要对改参数进行加锁
 *
 * @author bergturing@qq.com
 * @date 3/26/2019
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Locking {
    /**
     * 启用的加锁组合，默认是启用所有加锁组合
     * 没有配置加锁组合的加锁项，都会被加锁
     * <p>
     * 即Locking指定的加锁参数可能有多个加锁项，但不是同时加锁，需要根据一个指定的标识来加锁
     * 比如 类A 有两个加锁的属性 propertyA 和 propertyB
     * propertyA 的 prefix = {"A", "C"}
     * propertyB 的 prefix = {"B", "C"}
     * 如果加锁时设置 @Locking("A") 那么分布式锁只加锁 propertyA
     * 如果加锁时设置 @Locking("B") 那么分布式锁只加锁 propertyB
     * 如果加锁时设置 @Locking("C") 那么分布式锁会加锁加锁 propertyA 和 propertyB
     * <p>
     * 内部默认定义的字段值
     *
     * @return 启用的加锁组合
     * @see Union
     */
    @AliasFor("value")
    Union[] enabledUnions() default {};

    /**
     * enabledUnions 的别名
     *
     * @return 启用的加锁组合
     */
    @AliasFor("enabledUnions")
    Union[] value() default {};
}
