package io.github.bergturing.point.utils;

import org.junit.Test;

import java.util.List;
import java.util.stream.LongStream;

import static io.github.bergturing.point.utils.CollectionUtils.*;
import static java.util.stream.Collectors.toList;

/**
 * 集合工具类的测试类
 *
 * @author bergturing@qq.com
 * @apiNote 2019/5/19
 */
public class CollectionUtilsTests {
    @Test
    public void testIsEqual() {
        List<Long> collectionT;
        List<Long> collectionU;

        // 测试匹配
        for (int index = 10; index < 1000; index++) {
            collectionT = LongStream.rangeClosed(1, index).boxed().collect(toList());
            collectionU = LongStream.rangeClosed(1, index).boxed().collect(toList());
            assert isEqual(collectionT, collectionU, Long::equals);
        }

        // 测试不匹配
        for (int index = 10; index < 1000; index++) {
            collectionT = LongStream.rangeClosed(1, index).boxed().collect(toList());
            collectionU = LongStream.rangeClosed(1, index + 1).boxed().collect(toList());
            assert !isEqual(collectionT, collectionU, Long::equals);
        }
    }

    @Test
    public void testDistinctEqual() {
        List<Long> collectionT;
        List<Long> collectionU;

        // 测试匹配
        for (int index = 10; index < 1000; index++) {
            collectionT = LongStream.rangeClosed(1, index).boxed().collect(toList());
            collectionT.addAll(LongStream.rangeClosed(1, index).boxed().collect(toList()));

            collectionU = LongStream.rangeClosed(1, index).boxed().collect(toList());
            assert distinctEqual(collectionT, collectionU, Long::equals);
        }

        // 测试不匹配
        for (int index = 10; index < 1000; index++) {
            collectionT = LongStream.rangeClosed(1, index).boxed().collect(toList());
            collectionT.addAll(LongStream.rangeClosed(1, index).boxed().collect(toList()));

            collectionU = LongStream.rangeClosed(1, index + 1).boxed().collect(toList());
            assert !distinctEqual(collectionT, collectionU, Long::equals);
        }
    }

    @Test
    public void testInclude() {
        List<Long> collectionT;
        List<Long> collectionU;

        // 测试包含
        for (int index = 10; index < 1000; index++) {
            collectionT = LongStream.rangeClosed(1, index).boxed().collect(toList());
            collectionU = LongStream.rangeClosed(1, index / 2).boxed().collect(toList());
            assert include(collectionT, collectionU, Long::equals);
        }

        // 测试不包含
        for (int index = 10; index < 1000; index++) {
            collectionT = LongStream.rangeClosed(1, index).boxed().collect(toList());
            collectionU = LongStream.rangeClosed(1, index + 1).boxed().collect(toList());
            assert !include(collectionT, collectionU, Long::equals);
        }

        // 测试不包含
        for (int index = 10; index < 1000; index++) {
            collectionT = LongStream.rangeClosed(1, index).boxed().collect(toList());
            collectionU = LongStream.rangeClosed(-1, index / 2).boxed().collect(toList());
            assert !include(collectionT, collectionU, Long::equals);
        }
    }
}
