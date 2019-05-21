package com.bergturing.point.dal.annotations;

import com.bergturing.point.dal.Lock;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 分布式锁加锁的字段
 *
 * @author bergturing@qq.com
 * @apiNote 2019/4/24
 */
@Inherited
@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface LockItem {
    /**
     * 分组的名称，默认为默认分组
     *
     * @return 分组名
     */
    @AliasFor("value")
    String group() default Lock.DEFAULT_GROUP;

    /**
     * 分组的名称，默认为默认分组，group的别名
     *
     * @return 分组名
     */
    @AliasFor("group")
    String value() default Lock.DEFAULT_GROUP;

    /**
     * 锁超时释放时间，默认为默认超时时间
     *
     * @return 锁超时释放时间
     */
    long expire() default Lock.DEFAULT_EXPIRE;
}
