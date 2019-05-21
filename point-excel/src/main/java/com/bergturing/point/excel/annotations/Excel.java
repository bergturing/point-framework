package com.bergturing.point.excel.annotations;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

import static com.bergturing.point.excel.constants.ExcelConstants.DEFAULT_EXCEL_NAME;

/**
 * excel的注解
 *
 * @author bergturing@qq.com
 * @apiNote 2019/5/21
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
    String excelName() default DEFAULT_EXCEL_NAME;

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
