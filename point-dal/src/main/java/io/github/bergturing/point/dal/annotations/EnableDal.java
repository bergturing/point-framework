package io.github.bergturing.point.dal.annotations;

import io.github.bergturing.point.dal.config.DalConfigurationSelector;
import io.github.bergturing.point.dal.enums.DalType;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 启用分布式应用锁的注解
 *
 * @author bergturing@qq.com
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DalConfigurationSelector.class)
public @interface EnableDal {
    /**
     * 用户自定义的注解
     *
     * @return 用户自定义的注解
     */
    Class<? extends Annotation> annotation() default Annotation.class;

    /**
     * 分布式锁的类型，默认为redis分布式锁
     *
     * @return 分布式锁的类型
     */
    @AliasFor("value")
    DalType dalType() default DalType.REDIS;

    /**
     * 分布式锁的类型，默认为redis分布式锁，dalType的别名
     *
     * @return 分布式锁的类型
     */
    @AliasFor("dalType")
    DalType value() default DalType.REDIS;

    /**
     * 标明是否需要创建CGLIB子类代理，AdviceMode=PROXY时才适用。
     * 注意设置为true时，其它spring管理的bean也会升级到CGLIB子类代理
     *
     * @return 是否需要创建CGLIB子类代理
     */
    boolean proxyTargetClass() default false;

    /**
     * 标明分布式应用锁通知的实现方式，默认PROXY，如需支持同一个类中非异步方法调用另一个异步方法，需要设置为ASPECTJ
     *
     * @return 分布式应用锁通知的实现方式
     */
    AdviceMode mode() default AdviceMode.PROXY;

    /**
     * 标明分布式应用锁注解bean处理器应该遵循的执行顺序，默认最低的优先级（Integer.MAX_VALUE，值越小优先级越高）
     *
     * @return 分布式应用锁注解bean处理器应该遵循的执行顺序
     */
    int order() default Ordered.LOWEST_PRECEDENCE;
}
