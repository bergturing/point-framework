package io.github.bergturing.point.dal.annotations;

import java.lang.annotation.*;

/**
 * 加锁的节点，一般用于集合类型
 *
 * @author bergturing@qq.com
 * @apiNote 2019/4/24
 */
@Inherited
@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface LockNode {
}