package io.github.bergturing.point.core.prototype.exceptions;

/**
 * 克隆失败的异常
 *
 * @author bergturing@qq.com
 * @date 2019/5/21
 */
public class CloneFailureException extends RuntimeException {
    public CloneFailureException() {
    }

    public CloneFailureException(String s) {
        super(s);
    }
}
