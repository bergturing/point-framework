package com.bergturing.point.stream.strategy.defaults.count;

/**
 * 抽象的计数流策略
 *
 * @author bergturing@qq.com
 * @apiNote 2019/5/13
 */
abstract class AbstractCountStream {
    /**
     * 使用并行流的最小数据量，默认为1000，
     * 即当集合的数据量大于等于当前值时，使用并行流
     */
    private int count = 1000;

    /**
     * 无参构造方法
     */
    AbstractCountStream() {
    }

    /**
     * 自定义使用并行流的最小数据量值的构造方法
     *
     * @param count 使用并行流的最小数据量
     */
    AbstractCountStream(int count) {
        this.count = count;
    }

    /**
     * 获取计数
     *
     * @return 集合计数的分界点
     */
    int getCount() {
        return count;
    }
}
