package io.github.bergturing.point.utils;

/**
 * 字符串工具类
 *
 * @author bergturing@qq.com
 */
public class StringUtils {
    /**
     * 判断字符串是否为空
     *
     * @param string 待判断的字符串
     * @return 判断结果
     */
    public static boolean isEmpty(String string) {
        return string == null || string.length() == 0;
    }

    /**
     * 判断字符串是否不为空
     *
     * @param string 待判断的字符串
     * @return 判断结果
     */
    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }

    /**
     * 判断字符串是否为空白
     *
     * @param string 待判断的字符串
     * @return 判断结果
     */
    public static boolean isBlank(String string) {
        // 字符串的长度
        int stringLength;

        // 判断字符串是否为空
        if (string == null || (stringLength = string.length()) == 0) {
            return true;
        }

        // 判断剩下的字符是否全是空白
        for (int i = 0; i < stringLength; i++) {
            if ((!Character.isWhitespace(string.charAt(i)))) {
                return false;
            }
        }

        return true;
    }

    /**
     * 判断字符串是否不为空白
     *
     * @param string 待判断的字符串
     * @return 判断结果
     */
    public static boolean isNotBlank(String string) {
        return !isBlank(string);
    }

    /**
     * 字符串首字母转大写
     *
     * @param string 待处理的字符串
     * @return 转换结果
     */
    public static String toUpperCaseFirstOne(String string) {
        if (isBlank(string)) {
            return string;
        } else if (Character.isUpperCase(string.charAt(0))) {
            // 如果首字符是大写的，就直接返回当前字符串
            return string;
        } else {
            return Character.toUpperCase(string.charAt(0)) + string.substring(1);
        }
    }
}
