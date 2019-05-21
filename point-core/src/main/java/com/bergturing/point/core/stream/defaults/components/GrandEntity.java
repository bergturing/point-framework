package com.bergturing.point.core.stream.defaults.components;

/**
 * 累计实体对象
 *
 * @author bo.he02@hand-china.com
 * @apiNote 2019/5/9
 */
public class GrandEntity {
    /**
     * 累计次数
     */
    private int grandTotal;

    /**
     * 累加
     *
     * @return 当前累计实体对象
     */
    public GrandEntity increment() {
        this.grandTotal++;
        return this;
    }

    /**
     * 返回当前实体的累计次数
     *
     * @return 累计次数
     */
    public int getGrandTotal() {
        return this.grandTotal;
    }
}
