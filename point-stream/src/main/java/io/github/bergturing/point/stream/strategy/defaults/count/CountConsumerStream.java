package io.github.bergturing.point.stream.strategy.defaults.count;

import io.github.bergturing.point.stream.strategy.ConsumerStream;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * 基于数量的流策略(消费)
 *
 * @author bergturing@qq.com
 * @date 2019/5/9
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
