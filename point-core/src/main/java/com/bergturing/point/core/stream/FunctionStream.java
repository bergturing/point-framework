package com.bergturing.point.core.stream;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * 集合的策略流，有处理结果的
 *
 * @author bo.he02@hand-china.com
 * @apiNote 2019/5/9
 */
@FunctionalInterface
public interface FunctionStream<E, R> {
    /**
     * 根据内定的策略处理流，有返回值
     *
     * @param collection      集合对象
     * @param streamRFunction 流的处理逻辑
     * @return 处理的返回结果
     */
    R process(Collection<E> collection, Function<Stream<E>, R> streamRFunction);
}
