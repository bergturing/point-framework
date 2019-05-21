package com.bergturing.point.stream.strategy;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * 集合的策略流，没有处理结果的
 *
 * @author bergturing@qq.com
 * @apiNote 2019/5/9
 */
@FunctionalInterface
public interface ConsumerStream<E> {
    /**
     * 根据内定的策略处理流，只是使用流，没有返回值
     *
     * @param collection     集合对象
     * @param streamConsumer 流的处理逻辑
     */
    void process(Collection<E> collection, Consumer<Stream<E>> streamConsumer);
}