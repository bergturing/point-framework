package com.bergturing.point.dal.constants;

public class LockConstants {
    /**
     * 单个实体锁分组
     */
    public static final String LOCK_GROUP_ENTITY = "point:dal:lock:test:entity:";

    /**
     * 关联实体锁分组
     */
    public static final String LOCK_GROUP_ASSOCIATION = "point:dal:lock:test:association:";

    /**
     * 常数锁分组
     */
    public static final String LOCK_GROUP_CONSTANTS = "point:dal:lock:test:constants:";

    /**
     * 对属性加锁的服务对象
     */
    public static final String SERVICE_FIELD = "lockFieldService";

    /**
     * 对方法加锁的服务对象
     */
    public static final String SERVICE_METHOD = "lockMethodService";
}
