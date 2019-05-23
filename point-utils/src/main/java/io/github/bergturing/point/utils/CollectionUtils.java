package io.github.bergturing.point.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.function.BiPredicate;

/**
 * 集合工具类
 *
 * @author bergturing@qq.com
 */
public class CollectionUtils {
    /**
     * 判断集合是否为空
     *
     * @param collection 判断的集合对象
     * @return 判断结果
     */
    public static boolean isEmpty(Collection<?> collection) {
        // 返回判断结果
        return collection == null || collection.isEmpty();
    }

    /**
     * 判断集合是否不为空
     *
     * @param collection 判断的集合对象
     * @return 判断结果
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        // 返回为空相反的结果
        return !isEmpty(collection);
    }

    /**
     * 根据传入的判断函数，判断两个集合内的数据是否相同
     *
     * @param collectionT 集合1
     * @param collectionU 集合2
     * @param biPredicate 每个元素的比较判定
     * @param <T>         集合1内元素的泛型
     * @param <U>         集合2内元素的泛型
     * @return 判断结果，true代表两个集合在biPredicate条件下相同；false代表两个集合在biPredicate条件下不相同
     */
    public static <T, U> boolean isEqual(Collection<T> collectionT, Collection<U> collectionU, BiPredicate<T, U> biPredicate) {
        // 两个都为空就返回true
        if (isEmpty(collectionT) && isEmpty(collectionU)) {
            return true;
        }

        // 只要存在为空就返回false
        if (isEmpty(collectionT) || isEmpty(collectionU)) {
            return false;
        }

        // 比较大小
        if (collectionT.size() != collectionU.size()) {
            return false;
        }

        // 返回比较结果
        return collectionT.stream().allMatch(
                itemT -> collectionU.stream().anyMatch(
                        itemU -> biPredicate.test(itemT, itemU)
                )
        );
    }

    /**
     * 根据传入的判断函数，判断两个集合内的数据是否相同，去重比较(集合的大小不作为一个判断依据)
     *
     * @param collectionT 集合1
     * @param collectionU 集合2
     * @param biPredicate 每个元素的比较判定
     * @param <T>         集合1内元素的泛型
     * @param <U>         集合2内元素的泛型
     * @return 判断结果，true代表两个集合在biPredicate条件下相同；false代表两个集合在biPredicate条件下不相同
     */
    public static <T, U> boolean distinctEqual(Collection<T> collectionT, Collection<U> collectionU, BiPredicate<T, U> biPredicate) {
        // 对数据去重处理，并进行比较
        return isEqual(isEmpty(collectionT) ? null : new HashSet<>(collectionT),
                isEmpty(collectionU) ? null : new HashSet<>(collectionU),
                biPredicate);
    }

    /**
     * 根据传入的判断函数，判断集合1是否包含集合2
     *
     * @param collectionT 集合1
     * @param collectionU 集合2
     * @param biPredicate 每个元素的比较判定
     * @param <T>         集合1内元素的泛型
     * @param <U>         集合2内元素的泛型
     * @return 判断结果，true代表两个集合在biPredicate条件下相同；false代表两个集合在biPredicate条件下不相同
     */
    public static <T, U> boolean include(Collection<T> collectionT, Collection<U> collectionU, BiPredicate<T, U> biPredicate) {
        // 两个都为空就返回true
        if (isEmpty(collectionT) && isEmpty(collectionU)) {
            return true;
        }

        // 只要集合1为空就返回false
        if (isEmpty(collectionT)) {
            return false;
        }

        // 只要集合2为空就返回true
        if (isEmpty(collectionU)) {
            return true;
        }

        // 都不为空

        // 比较大小
        if (collectionT.size() < collectionU.size()) {
            return false;
        }

        // 返回比较结果
        return collectionU.stream().allMatch(
                itemU -> collectionT.stream().anyMatch(
                        itemT -> biPredicate.test(itemT, itemU)
                )
        );
    }

    /**
     * 融合输入的两个集合
     *
     * @param collectionA 集合A
     * @param collectionB 集合B
     * @param <E>         集合内对象的泛型
     * @return 融合结果
     */
    public static <E> Collection<E> combine(Collection<E> collectionA, Collection<E> collectionB) {
        // 融合的结果集合
        Collection<E> combineCollection;

        // 集合A为空
        boolean aIsEmpty = isEmpty(collectionA);
        // 集合B为空
        boolean bIsEmpty = isEmpty(collectionB);

        // 创建结果集合
        combineCollection = new ArrayList<>((aIsEmpty ? 0 : collectionA.size()) + (bIsEmpty ? 0 : collectionB.size()));

        // 集合A不为空
        if (!aIsEmpty) {
            combineCollection.addAll(collectionA);
        }

        // 集合B不为空
        if (!bIsEmpty) {
            combineCollection.addAll(collectionB);
        }

        // 返回融合结果
        return combineCollection;
    }

    /**
     * 融合输入的两个List
     *
     * @param listA List A
     * @param listB List B
     * @param <E>   List内元素泛型
     * @return 融合结果
     */
    public static <E> List<E> combineList(List<E> listA, List<E> listB) {
        return (List<E>) combine(listA, listB);
    }
}
