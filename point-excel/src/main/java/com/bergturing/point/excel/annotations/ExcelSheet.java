package com.bergturing.point.excel.annotations;

import java.lang.annotation.*;

import static com.bergturing.point.excel.constants.ExcelConstants.DEFAULT_SHEET_NAME;

/**
 * sheet页的注解
 *
 * @author bergturing@qq.com
 * @apiNote 2019/5/21
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ExcelSheet {
    /**
     * 当前Sheet页的名称
     *
     * @return 当前Sheet页的名称
     */
    String sheetName() default DEFAULT_SHEET_NAME;
}
