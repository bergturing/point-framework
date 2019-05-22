package io.github.bergturing.point.stream.strategy.defaults.grand;

import io.github.bergturing.point.stream.strategy.ConsumerStream;
import io.github.bergturing.point.stream.strategy.defaults.components.GrandEntity;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * 累计资源消耗的策略
 *
 * @author bergturing@qq.com
 * @apiNote 2019/5/9
 */
public class GrandConsumerStream<E> implements ConsumerStream<E> {
    /**
     * 最多缓存的流的消费信息
     */
    private static final int MAX_CACHE_COUNT = 1000;

    /**
     * 累积实体对象的缓存
     */
    private Map<String, GrandEntity> grandEntityCache = null;

    @Override
    public void process(Collection<E> collection, Consumer<Stream<E>> streamConsumer) {

    }

    public GrandConsumerStream grand() {
        return this;
    }
}
