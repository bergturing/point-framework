package io.github.bergturing.point.utils;

import java.util.Map;

/**
 * Map工具类
 *
 * @author bergturing@qq.com
 * @date 2019/5/21
 */
public class MapUtils {
    /**
     * 判断map是否为空
     *
     * @param map 待判断的map
     * @return 判断结果
     */
    public static boolean isEmpty(Map map) {
        return (map == null || map.isEmpty());
    }

    /**
     * 判断map是否不为空
     *
     * @param map 待判断的map
     * @return 判断结果
     */
    public static boolean isNotEmpty(Map map) {
        return !isEmpty(map);
    }
}
