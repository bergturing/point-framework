package com.bergturing.point.core.stream.defaults.count;

import com.bergturing.point.core.stream.ConsumerStream;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * 基于数量的流策略(消费)
 *
 * @author bo.he02@hand-china.com
 * @apiNote 2019/5/9
 */
public class CountConsumerStream<E> extends AbstractCountStream implements ConsumerStream<E> {

    public CountConsumerStream() {
    }

    public CountConsumerStream(int count) {
        super(count);
    }

    @Override
    public void process(Collection<E> collection, Consumer<Stream<E>> streamConsumer) {
        if (collection.size() >= getCount()) {
            streamConsumer.accept(collection.parallelStream());
        } else {
            streamConsumer.accept(collection.stream());
        }
    }
}
