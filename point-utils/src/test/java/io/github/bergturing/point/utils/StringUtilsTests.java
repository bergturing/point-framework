package io.github.bergturing.point.utils;

import org.junit.Test;

/**
 * 字符串工具类测试类
 *
 * @author bergturing@qq.com
 * @date 2019/5/19
 */
public class StringUtilsTests {
    @Test
    public void testIsEmpty() {
        assert StringUtils.isEmpty(null);
        assert StringUtils.isEmpty("");
        assert !StringUtils.isEmpty("Hello");
    }

    @Test
    public void testIsNotEmpty() {
        assert !StringUtils.isNotEmpty(null);
        assert !StringUtils.isNotEmpty("");
        assert StringUtils.isNotEmpty("Hello");
    }

    @Test
    public void testIsBlank() {
        assert StringUtils.isBlank(null);
        assert StringUtils.isBlank("");
        assert StringUtils.isBlank("     ");
        assert !StringUtils.isBlank("Hello");
    }

    @Test
    public void testIsNotBlank() {
        assert !StringUtils.isNotBlank(null);
        assert !StringUtils.isNotBlank("");
        assert !StringUtils.isNotBlank("     ");
        assert StringUtils.isNotBlank("Hello");
    }

    @Test
    public void testToUpperCaseFirstOne() {
        assert "Hello".equals(StringUtils.toUpperCaseFirstOne("hello"));
        assert "Hello".equals(StringUtils.toUpperCaseFirstOne("Hello"));
    }
}
