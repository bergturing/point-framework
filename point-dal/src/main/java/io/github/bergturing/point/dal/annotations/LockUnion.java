package io.github.bergturing.point.dal.annotations;

import io.github.bergturing.point.dal.enums.Union;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Inherited
@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LockUnion {
    /**
     * 加锁组合
     *
     * @return 加锁组合
     */
    @AliasFor("value")
    Union[] unions() default {};

    /**
     * 加锁组合，unions的别名
     *
     * @return 加锁组合
     */
    @AliasFor("unions")
    Union[] value() default {};
}
