package com.bergturing.point.utils;

import org.junit.Test;

import static com.bergturing.point.utils.StringUtils.*;

/**
 * 字符串工具类测试类
 *
 * @author bergturing@qq.com
 * @apiNote 2019/5/19
 */
public class StringUtilsTests {
    @Test
    public void testIsEmpty() {
        assert isEmpty(null);
        assert isEmpty("");
        assert !isEmpty("Hello");
    }

    @Test
    public void testIsNotEmpty() {
        assert !isNotEmpty(null);
        assert !isNotEmpty("");
        assert isNotEmpty("Hello");
    }

    @Test
    public void testIsBlank() {
        assert isBlank(null);
        assert isBlank("");
        assert isBlank("     ");
        assert !isBlank("Hello");
    }

    @Test
    public void testIsNotBlank() {
        assert !isNotBlank(null);
        assert !isNotBlank("");
        assert !isNotBlank("     ");
        assert isNotBlank("Hello");
    }

    @Test
    public void testToUpperCaseFirstOne() {
        assert "Hello".equals(toUpperCaseFirstOne("hello"));
        assert "Hello".equals(toUpperCaseFirstOne("Hello"));
    }
}
