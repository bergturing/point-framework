package io.github.bergturing.point.dal.aop.interceptor;

import io.github.bergturing.point.core.prototype.Prototype;
import io.github.bergturing.point.core.prototype.defaults.AbstractPrototype;
import io.github.bergturing.point.dal.DalExecutor;
import io.github.bergturing.point.dal.DalUtils;
import io.github.bergturing.point.dal.Lock;
import io.github.bergturing.point.dal.annotations.LockItem;
import io.github.bergturing.point.dal.annotations.LockNode;
import io.github.bergturing.point.dal.annotations.LockUnion;
import io.github.bergturing.point.dal.annotations.Locking;
import io.github.bergturing.point.dal.enums.Union;
import io.github.bergturing.point.utils.ArrayUtils;
import io.github.bergturing.point.utils.CollectionUtils;
import io.github.bergturing.point.utils.MapUtils;
import lombok.Data;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.Ordered;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

import static org.springframework.core.annotation.AnnotationUtils.findAnnotation;
import static org.springframework.util.ClassUtils.getMostSpecificMethod;

/**
 * 分布式应用锁执行的拦截器
 *
 * @author bergturing@qq.com
 * @date 2019/5/10
 */
public class DalExecutionInterceptor extends BaseDalExecutionAspectSupport implements MethodInterceptor, Ordered {
    public DalExecutionInterceptor(DalExecutor dalExecutor) {
        super(dalExecutor);
    }

    public DalExecutionInterceptor(DalExecutor dalExecutor, DalUncaughtExceptionHandler exceptionHandler) {
        super(dalExecutor, exceptionHandler);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public Object invoke(MethodInvocation invocation) {
        // 目标类
        Class<?> targetClass = (invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null);
        Method specificMethod = getMostSpecificMethod(invocation.getMethod(), targetClass);
        // 定义的方法
        final Method userDeclaredMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);

        try {
            // 加锁的锁列表
            List<Lock> lockList = this.getLockList(userDeclaredMethod.getParameters(), invocation.getArguments());

            // 锁列表不为空
            if (CollectionUtils.isNotEmpty(lockList)) {
                // 获取分布式应用锁执行器
                DalExecutor dalExecutor = this.getDalExecutor();

                // 判断锁服务对象是否存在
                if (null == dalExecutor) {
                    throw new RuntimeException("non dalExecutor server");
                }

                // 对执行加锁，并获取执行结果
                return DalUtils.self(lockList.size())
                        // 添加锁
                        .addAll(lockList)
                        // 加锁
                        .lock(dalExecutor, () -> {
                            try {
                                // 返回执行结果
                                return invocation.proceed();
                            } catch (Throwable throwable) {
                                // 出现异常
                                throw new RuntimeException(throwable);
                            }
                        });
            } else {
                // 直接执行业务逻辑
                return invocation.proceed();
            }
        } catch (Throwable ex) {
            this.handleError(ex.getCause(), userDeclaredMethod, invocation.getArguments());
            return null;
        }
    }

    /**
     * 获取锁对象
     *
     * @param parameters 方法参数列表
     * @param args       参数值列表
     * @return 锁对象
     * @throws IllegalAccessException    数据处理异常
     * @throws InvocationTargetException 数据处理异常
     */
    private List<Lock> getLockList(final Parameter[] parameters, final Object[] args)
            throws IllegalAccessException, InvocationTargetException {
        // 判断参数 是否为空
        if (ArrayUtils.isNullOrEmpty(args) || ArrayUtils.isNullOrEmpty(parameters)) {
            // 参数为空就直接返回空的锁对象
            return Collections.emptyList();
        }

        // 锁对象
        List<Lock> lockList = new ArrayList<>();

        for (int index = 0, count = parameters.length; index < count; index++) {
            // 参数对象
            Parameter parameter = parameters[index];

            // Locking 注解对象
            Locking locking = findAnnotation(parameter, Locking.class);
            // LockItem 注解对象
            LockItem lockItem = findAnnotation(parameter, LockItem.class);
            // LockNode 注解对象
            LockNode lockNode = findAnnotation(parameter, LockNode.class);

            // Locking 注解不为空
            if (locking != null) {
                // LockItem 与 LockNode 注解同时存在抛出异常
                if (lockItem != null && lockNode != null) {
                    throw new RuntimeException("lock field and lock node all exists in " + args[index].getClass().getName());
                } else if (lockItem != null) {
                    // 添加解析结果
                    lockList.addAll(this.resolveLockField(args[index], lockItem.group(), lockItem.expire()));
                } else {
                    // LockNode 和 没有注解情况
                    // 解析参数对象
                    lockList.addAll(this.parseLockNode(args[index], locking.enabledUnions()));
                }
            }
        }

        // 返回锁
        return lockList;
    }

    /**
     * 解析集合对象
     *
     * @param object        对象
     * @param enabledUnions 启用的加锁组合
     * @return 解析的锁对象
     * @throws IllegalAccessException    数据处理异常
     * @throws InvocationTargetException 数据处理异常
     */
    private List<Lock> parseLockNode(Object object, Union[] enabledUnions)
            throws IllegalAccessException, InvocationTargetException {
        if (object != null) {
            // 锁对象
            List<Lock> lockList = new ArrayList<>();

            // 获取参数的类型
            Class<?> clazz = object.getClass();

            // 判断参数是否继承自 Collection
            if (Collection.class.isAssignableFrom(clazz)) {
                // 如果参数是继承自 Collection
                Collection collection = (Collection) object;
                if (CollectionUtils.isNotEmpty(collection)) {
                    // 获取一个数据对象
                    Object firstObject = collection.iterator().next();
                    // 解析出锁信息
                    LockParseInfo lockParseInfo = this.parseLockMessage(firstObject, enabledUnions);
                    // 获取锁数据
                    for (Object item : collection) {
                        // 集合对象的锁信息解析
                        lockList.addAll(this.resolveParseLockMessage(item, lockParseInfo, enabledUnions));
                    }
                }
            } else {
                // 单个对象的锁信息解析
                lockList.addAll(this.resolveParseLockMessage(object, this.parseLockMessage(object, enabledUnions), enabledUnions));
            }

            // 返回解析出来的锁信息
            return lockList;
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * 解析解析出来的锁信息
     *
     * @param object        数据对象
     * @param lockParseInfo 解析出来的锁信息
     * @param enabledUnions 启用的加锁组合
     * @return 解析出来的锁对象
     * @throws IllegalAccessException    数据处理异常
     * @throws InvocationTargetException 数据处理异常
     */
    private List<Lock> resolveParseLockMessage(Object object, LockParseInfo lockParseInfo, Union[] enabledUnions)
            throws IllegalAccessException, InvocationTargetException {
        // 解析出的锁对象
        List<Lock> lockList = new ArrayList<>();

        // 获取解析出的字段 LockItem
        Map<Field, LockItem> lockItemFieldMap = lockParseInfo.getLockItemFieldMap();
        if (MapUtils.isNotEmpty(lockItemFieldMap)) {
            // 遍历字段
            for (Field field : lockItemFieldMap.keySet()) {
                // 获取值
                LockItem lockItem = lockItemFieldMap.get(field);

                // 获取属性值
                field.setAccessible(true);
                Object fieldValue = field.get(object);
                field.setAccessible(false);

                // 添加 LockItem 的解析结果
                lockList.addAll(this.resolveLockField(fieldValue, lockItem.group(), lockItem.expire()));
            }
        }

        // 获取解析出字段 LockNode 节点
        List<Field> lockNodeFieldList = lockParseInfo.getLockNodeFieldList();
        if (CollectionUtils.isNotEmpty(lockNodeFieldList)) {
            // 遍历所有的 LockNode 节点，并解析出锁信息
            for (Field field : lockNodeFieldList) {
                // 获取字段值
                field.setAccessible(true);
                Object fieldValue = field.get(object);
                field.setAccessible(false);

                if (fieldValue != null) {
                    // 将解析出的锁信息添加
                    lockList.addAll(this.parseLockNode(fieldValue, enabledUnions));
                }
            }
        }

        // 获取解析出的方法 LockItem
        Map<Method, LockItem> lockItemMethodMap = lockParseInfo.getLockItemMethodMap();
        if (MapUtils.isNotEmpty(lockItemMethodMap)) {
            // 遍历字段
            for (Method method : lockItemMethodMap.keySet()) {
                // 获取值
                LockItem lockItem = lockItemMethodMap.get(method);

                // 获取属性值
                method.setAccessible(true);
                Object methodResult = method.invoke(object);
                method.setAccessible(false);

                // 添加 LockItem 的解析结果
                lockList.addAll(this.resolveLockField(methodResult, lockItem.group(), lockItem.expire()));
            }
        }

        // 获取解析出方法 LockNode 节点
        List<Method> lockNodeMethodList = lockParseInfo.getLockNodeMethodList();
        if (CollectionUtils.isNotEmpty(lockNodeMethodList)) {
            // 遍历所有的 LockNode 节点，并解析出锁信息
            for (Method method : lockNodeMethodList) {
                // 获取字段值
                method.setAccessible(true);
                Object methodResult = method.invoke(object);
                method.setAccessible(false);

                if (methodResult != null) {
                    // 将解析出的锁信息添加
                    lockList.addAll(this.parseLockNode(methodResult, enabledUnions));
                }
            }
        }

        // 返回解析结果
        return lockList;
    }

    /**
     * 解析集合对象
     *
     * @param value  值
     * @param group  分组
     * @param expire 超时时间
     * @return 解析出的锁对象
     */
    private List<Lock> resolveLockField(Object value, String group, Long expire) {
        // 解析结果
        List<Lock> lockList = new ArrayList<>();

        // 当前属性值不为空
        if (value != null) {
            // 如果值的类型是集合
            if (Collection.class.isAssignableFrom(value.getClass())) {
                Collection collection = (Collection) value;
                // 值不为空
                if (CollectionUtils.isNotEmpty(collection)) {
                    // 遍历集合对象
                    for (Object key : collection) {
                        // 添加锁对象
                        lockList.add(Lock.PROTOTYPE.prototypeClone()
                                .setGroup(group)
                                .setExpire(expire)
                                .setKey(String.valueOf(key)));
                    }
                }
            } else {
                // 添加锁对象
                lockList.add(Lock.PROTOTYPE.prototypeClone()
                        .setGroup(group)
                        .setExpire(expire)
                        .setKey(String.valueOf(value)));
            }
        }

        // 返回解析结果
        return lockList;
    }

    /**
     * 解析锁信息
     *
     * @param object 解析的对象
     * @return 解析结果
     */
    private LockParseInfo parseLockMessage(Object object, Union[] enabledUnions) {
        // 字段注解解析结果
        LockParseInfo lockParseInfo = LockParseInfo.PROTOTYPE.prototypeClone();

        if (object != null) {
            // 遍历字段，并解析结果
            for (Field field : object.getClass().getDeclaredFields()) {
                // 获取 LockItem 注解
                LockItem lockItem = findAnnotation(field, LockItem.class);
                // 获取 LockNode注解
                LockNode lockNode = findAnnotation(field, LockNode.class);
                // 获取 LockUnion注解
                LockUnion lockUnion = findAnnotation(field, LockUnion.class);

                // 判断加锁项是否启用
                if (null == lockUnion || this.enabledLock(enabledUnions, lockUnion.unions())) {
                    // LockItem 和 LockNode 注解同时存在
                    if (lockItem != null && lockNode != null) {
                        // 抛出异常
                        throw new RuntimeException("lock field and lock node all exists in " + field.getName());
                    } else if (lockItem != null) {
                        // 添加 LockItem 注解信息
                        lockParseInfo.putField(field, lockItem);
                    } else if (lockNode != null) {
                        // 判断 LockNode
                        // 添加节点
                        lockParseInfo.addFieldNode(field);
                    }
                }
            }

            // 遍历方法，并解析结果
            for (Method method : object.getClass().getDeclaredMethods()) {
                // 获取 LockItem 注解
                LockItem lockItem = findAnnotation(method, LockItem.class);
                // 获取 LockNode注解
                LockNode lockNode = findAnnotation(method, LockNode.class);
                // 获取 LockUnion注解
                LockUnion lockUnion = findAnnotation(method, LockUnion.class);

                // 判断加锁项是否启用
                if (null == lockUnion || this.enabledLock(enabledUnions, lockUnion.unions())) {
                    // LockItem 和 LockNode 注解同时存在
                    if (lockItem != null && lockNode != null) {
                        // 抛出异常
                        throw new RuntimeException("lock field and lock node all exists in " + method.getName());
                    } else if (lockItem != null) {
                        // 添加 LockItem 注解信息
                        lockParseInfo.putMethod(method, lockItem);
                    } else if (lockNode != null) {
                        // 判断 LockNode
                        // 添加节点
                        lockParseInfo.addMethodNode(method);
                    }
                }
            }
        }

        // 返回解析结果信息
        return lockParseInfo;
    }

    /**
     * 判断是否启用当前加锁项
     *
     * @param enabledUnions 允许的组合
     * @param unions        当前加锁项已有的节点
     * @return 是否启用加锁项
     */
    private boolean enabledLock(Union[] enabledUnions, Union[] unions) {
        if (null == enabledUnions || 0 == enabledUnions.length) {
            // 没有启动加锁组合，就相当于启用全部
            return true;
        }

        if (null == unions || 0 == unions.length) {
            // 加锁项没有配置加锁组合，就相当于无论何时都需要加锁
            return true;
        }

        // 判断加锁项已有的加锁分组是否在启用的加锁分组中
        for (Union enabledUnion : enabledUnions) {
            for (Union union : unions) {
                // 存在，就加锁
                if (enabledUnion.equals(union)) {
                    return true;
                }
            }
        }

        // 没有启用
        return false;
    }

    /**
     * 锁解析的信息
     */
    @Data
    private static class LockParseInfo implements Cloneable {
        /**
         * 初始原型对象
         */
        static final Prototype<LockParseInfo> PROTOTYPE = new AbstractPrototype<LockParseInfo>(new LockParseInfo()) {
            @Override
            protected LockParseInfo clone(LockParseInfo prototype) throws CloneNotSupportedException {
                return (LockParseInfo) prototype.clone();
            }
        };

        /**
         * LockItem 字段节点
         */
        private Map<Field, LockItem> lockItemFieldMap;

        /**
         * LockNode 字段节点
         */
        private List<Field> lockNodeFieldList;

        /**
         * LockItem 方法节点
         */
        private Map<Method, LockItem> lockItemMethodMap;

        /**
         * LockNode 方法节点
         */
        private List<Method> lockNodeMethodList;

        /**
         * 存放LockItem 字段节点
         *
         * @param field    字段对象
         * @param lockItem LockItem注解对象
         */
        void putField(Field field, LockItem lockItem) {
            if (field != null && lockItem != null) {
                if (null == this.lockItemFieldMap) {
                    this.lockItemFieldMap = new HashMap<>();
                }

                this.lockItemFieldMap.put(field, lockItem);
            }
        }

        /**
         * 添加 LockNode 字段节点
         *
         * @param field LockNode注解对象
         */
        void addFieldNode(Field field) {
            if (field != null) {
                if (null == this.lockNodeFieldList) {
                    this.lockNodeFieldList = new ArrayList<>();
                }

                this.lockNodeFieldList.add(field);
            }
        }

        /**
         * 存放LockItem 方法节点
         *
         * @param method   方法对象
         * @param lockItem LockItem注解对象
         */
        void putMethod(Method method, LockItem lockItem) {
            if (method != null && lockItem != null) {
                if (null == this.lockItemMethodMap) {
                    this.lockItemMethodMap = new HashMap<>();
                }

                this.lockItemMethodMap.put(method, lockItem);
            }
        }

        /**
         * 添加 LockNode 方法节点
         *
         * @param method LockNode注解对象
         */
        void addMethodNode(Method method) {
            if (method != null) {
                if (null == this.lockNodeMethodList) {
                    this.lockNodeMethodList = new ArrayList<>();
                }

                this.lockNodeMethodList.add(method);
            }
        }
    }
}
