package com.bergturing.point.core.result.exceptions;

/**
 * 增加批量值异常
 *
 * @author bergturing@qq.com
 * @apiNote 2019/3/18
 */
public class MultiValueException extends RuntimeException {
    private static final long serialVersionUID = 7133083114184061889L;

    /**
     * 构造方法
     */
    public MultiValueException() {
        super("multi value exception");
    }
}
