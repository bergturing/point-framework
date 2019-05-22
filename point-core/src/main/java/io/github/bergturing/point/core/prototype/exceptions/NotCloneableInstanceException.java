package io.github.bergturing.point.core.prototype.exceptions;

/**
 * 不是可克隆的实例对象
 *
 * @author bergturing@qq.com
 * @apiNote 2019/5/21
 */
public class NotCloneableInstanceException extends RuntimeException {
    public NotCloneableInstanceException() {
        super("not cloneable instance");
    }
}
