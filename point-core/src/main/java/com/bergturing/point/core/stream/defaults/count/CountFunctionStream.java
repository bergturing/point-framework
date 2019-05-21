package com.bergturing.point.core.stream.defaults.count;

import com.bergturing.point.core.stream.FunctionStream;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * 基于数量的流策略(方法)
 *
 * @author bo.he02@hand-china.com
 * @apiNote 2019/5/9
 */
public class CountFunctionStream<E, R> extends AbstractCountStream implements FunctionStream<E, R> {
    public CountFunctionStream() {
    }

    public CountFunctionStream(int count) {
        super(count);
    }

    @Override
    public R process(Collection<E> collection, Function<Stream<E>, R> streamRFunction) {
        if (collection.size() >= this.getCount()) {
            return streamRFunction.apply(collection.parallelStream());
        } else {
            return streamRFunction.apply(collection.stream());
        }
    }
}
