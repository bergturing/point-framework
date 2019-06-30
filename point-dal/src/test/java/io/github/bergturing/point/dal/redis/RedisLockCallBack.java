package io.github.bergturing.point.dal.redis;

/**
 * @author shencai.li@hand-china.com
 * @description
 * @date 2018/5/4
 */
public interface RedisLockCallBack {
    /**
     * Java 8 的Lambda 接口
     *
     * @throws Exception
     */
    void execute() throws Exception;
}
