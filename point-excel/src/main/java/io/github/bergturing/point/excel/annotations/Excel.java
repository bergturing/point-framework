package io.github.bergturing.point.excel.annotations;

import io.github.bergturing.point.excel.constants.ExcelConstants;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * excel的注解
 *
 * @author bergturing@qq.com
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Excel {
    /**
     * 当前Excel的名称
     *
     * @return 当前Excel的名称
     */
    String excelName() default ExcelConstants.DEFAULT_EXCEL_NAME;

    /**
     * 当前Excel的sheet页的类
     *
     * @return 当前Excel的sheet页的类
     */
    @AliasFor("value")
    Class<?>[] sheetClasses();

    /**
     * 当前Excel的sheet页的类
     *
     * @return 当前Excel的sheet页的类
     */
    @AliasFor("sheetClasses")
    Class<?>[] value();
}
