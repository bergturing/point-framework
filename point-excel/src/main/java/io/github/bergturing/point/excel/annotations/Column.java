package io.github.bergturing.point.excel.annotations;

import io.github.bergturing.point.excel.ExcelConstants;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * Excel列的注解
 *
 * @author bergturing@qq.com
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Column {
    /**
     * 列名
     *
     * @return 列名
     */
    String columnName() default ExcelConstants.DEFAULT_COLUMN_NAME;

    /**
     * excel列的标签
     *
     * @return excel列的标签
     */
    @AliasFor("value")
    String label();

    /**
     * excel列的标签
     *
     * @return excel列的标签
     */
    @AliasFor("label")
    String value();

    /**
     * 单元格默认值
     *
     * @return 单元格默认值
     */
    String defaultValue() default "";
}
