package io.github.bergturing.point.dal.exceptions;

/**
 * 锁的group值无效的异常
 *
 * @author bergturing@qq.com
 * @apiNote 2019/3/14
 */
public class LockGroupInvalidException extends RuntimeException {
    private static final long serialVersionUID = -5147566483258179234L;

    /**
     * 构造方法
     */
    public LockGroupInvalidException() {
        super("lock group invalid exception");
    }
}
