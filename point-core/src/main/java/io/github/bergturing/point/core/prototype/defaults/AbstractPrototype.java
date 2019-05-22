package io.github.bergturing.point.core.prototype.defaults;

import io.github.bergturing.point.core.prototype.Prototype;
import io.github.bergturing.point.core.prototype.exceptions.CloneFailureException;
import io.github.bergturing.point.core.prototype.exceptions.NotCloneableInstanceException;
import org.springframework.beans.BeanUtils;

import java.util.Objects;

/**
 * 原型拷贝的抽象实现
 *
 * @author bergturing@qq.com
 */
public abstract class AbstractPrototype<T> implements Prototype<T> {
    /**
     * 原型对象
     */
    private final T prototype;

    /**
     * 构造方法
     *
     * @param prototype 原型对象
     */
    protected AbstractPrototype(T prototype) {
        Objects.requireNonNull(prototype, "Prototype require non null");

        if (!(prototype instanceof Cloneable)) {
            // 不是可克隆的实例对象
            throw new NotCloneableInstanceException();
        }

        // 原型对象赋值
        this.prototype = prototype;
    }

    @Override
    public T prototypeClone() {
        // 克隆结果对象
        T cloneResult = null;

        try {
            //克隆对象
            cloneResult = this.clone(this.prototype);
        } catch (CloneNotSupportedException e) {
            throw new CloneFailureException(this.prototype.getClass().getName() + " clone failure");
        }

        // 返回克隆结果
        return cloneResult;
    }

    @Override
    public T prototypeClone(Object source) {
        // 获取克隆结果
        T cloneResult = this.prototypeClone();

        // 来源数据不为空
        if (source != null) {
            // 给克隆的对象赋值
            BeanUtils.copyProperties(source, cloneResult);
        }

        // 返回克隆并赋值的数据对象
        return cloneResult;
    }

    /**
     * 根据原型对象克隆对象
     *
     * @param prototype 原型对象
     * @return 克隆结果
     * @throws CloneNotSupportedException 不支持克隆的异常
     */
    abstract protected T clone(T prototype) throws CloneNotSupportedException;
}
