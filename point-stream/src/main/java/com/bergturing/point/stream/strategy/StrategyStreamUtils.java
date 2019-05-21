package com.bergturing.point.stream.strategy;

import com.bergturing.point.stream.strategy.defaults.count.CountConsumerStream;
import com.bergturing.point.stream.strategy.defaults.count.CountFunctionStream;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.bergturing.point.utils.CollectionUtils.isNotEmpty;

/**
 * 集合策略流工具类
 *
 * @author bergturing@qq.com
 * @apiNote 2019/5/9
 */
public class StrategyStreamUtils {
    /**
     * 策略流，根据指定的策略来使用流，没有处理结果
     *
     * @param collection     处理的集合对象
     * @param consumerStream 使用的策略
     * @param streamConsumer 流的处理逻辑
     * @param <E>            集合内部元素的泛型
     */
    public static <E> void process(final Collection<E> collection,
                                   final ConsumerStream<E> consumerStream,
                                   final Consumer<Stream<E>> streamConsumer) {
        // 消费流
        if (consumerStream != null && streamConsumer != null && isNotEmpty(collection)) {
            consumerStream.process(collection, streamConsumer);
        }
    }

    /**
     * 策略流，根据指定的策略来使用流，有处理结果
     *
     * @param collection     处理的集合对象
     * @param functionStream 使用的策略
     * @param streamFunction 流的处理逻辑
     * @param <E>            集合内部元素的泛型
     * @param <R>            集合处理结果泛型
     * @return 集合的处理结果
     */
    public static <E, R> Optional<R> process(final Collection<E> collection,
                                             final FunctionStream<E, R> functionStream,
                                             final Function<Stream<E>, R> streamFunction) {
        // 判断参数
        if (functionStream != null && streamFunction != null && isNotEmpty(collection)) {
            // 返回处理结果
            return Optional.ofNullable(functionStream.process(collection, streamFunction));
        } else {
            return Optional.empty();
        }
    }

    /**
     * [策略工厂方法]根据集合的数量的策略使用流(默认的使用并行流最小数据量)
     *
     * @param <E> 集合元素的泛型
     * @return 策略对象
     */
    public static <E> CountConsumerStream<E> countConsumerStream() {
        return new CountConsumerStream<>();
    }

    /**
     * [策略工厂方法]根据集合的数量的策略使用流(自定义使用并行流最小数据量)
     *
     * @param count 使用并行流的最小数据量
     * @param <E>   集合元素的泛型
     * @return 策略对象
     */
    public static <E> CountConsumerStream<E> countConsumerStream(int count) {
        return new CountConsumerStream<>(count);
    }

    /**
     * [策略工厂方法]根据集合的数量的策略使用流(默认的使用并行流最小数据量)
     *
     * @param <E> 集合元素的泛型
     * @param <R> 处理结果的返回值
     * @return 策略对象
     */
    public static <E, R> CountFunctionStream<E, R> countFunctionStream() {
        return new CountFunctionStream<>();
    }

    /**
     * [策略工厂方法]根据集合的数量的策略使用流(自定义使用并行流最小数据量)
     *
     * @param count 使用并行流的最小数据量
     * @param <E>   集合元素的泛型
     * @param <R>   处理结果的返回值
     * @return 策略对象
     */
    public static <E, R> CountFunctionStream<E, R> countFunctionStream(int count) {
        return new CountFunctionStream<>(count);
    }
}
