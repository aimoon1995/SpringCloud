package com.moon.moon_commons.entity.constants;

/**
 * @ClassName RabbitConstant
 * @Description: 交换机和队列
 * @Author zyl
 * @Date 2020/8/5
 * @Version V1.0
 **/
public class RabbitConstant {
    /**
     * 创建订单后将消息发送到此队列
     */
    public static final String DD_ORDER_POOL_QUEUE="dd.order.pool.queue";
    /**
     * 创建订单后将消息发送到此交换机
     */
    public static final String DD_ORDER_POOL_EXCHANGE="dd.order.pool.exchange";
    /**
     * ttl(延时)消息通知队列名称--将绑定的司机信息发送到此队列
     */
    public static final String DD_ORDER_MATCH_TTL_QUEUE="dd.order.match.ttl.queue";
    /**
     * ttl(延时)交换机名称 --将绑定的司机信息发送到此交换机
     */
    public static final String DD_ORDER_MATCH_TTL_EXCHANGE="dd.order.match.ttl.exchange";

    /**
     * 消息通知队列名称--将绑定的司机信息延时转置发送到此队列
     */
    public static final String DD_ORDER_MATCH_QUEUE="dd.order.match.queue";
    /**
     * 交换机名称 --将绑定的司机信息延时转置发送到此交换机
     */
    public static final String DD_ORDER_MATCH_EXCHANGE="dd.order.match.exchange";

    /**
     * 消息通知队列名称--将绑定的司机信息延时转置发送到此队列
     */
    public static final String DD_ORDER_QUEUE="dd_order";
    /**
     * 关闭订单队列
     */
    public static final String DD_CLOSE_ORDER_QUEUE="dd_close_order";
    /**
     * 改价队列
     */
    public static final String DD_CHANGE_PRICE_QUEUE="dd_change_price";
    /**
     * 免单队列
     */
    public static final String DD_FREE_CHARGE_QUEUE="dd_free_charge";
    /**
     * 退款队列
     */
    public static final String DD_BILL_REFUND_QUEUE="dd_bill_refund";

    /**
     * 消息通知队列名称--将绑定的司机信息延时转置发送到此队列
     */
    public static final String DD_TAXI_ORDER_QUEUE="dd_taxi_order";

    /**
     * 交换机名称 --将绑定的司机信息延时转置发送到此交换机
     */
    public static final String DD_ORDER_EXCHANGE="dd_order";

    /**
     * push将订单费用以及司机位置信息消息发送到此交换机
     */
    public static final String DD_DATA_SYNC_EXCHANGE="data-sync";

    /**
     * 消息通知队列名称--将绑定的司机信息发送到此队列
     */
    public static final String DD_REALTIME_FEE_QUEUE="realtime_fee_sync";

    /**
     * 消息通知队列名称--将绑定的司机信息发送到此队列
     */
    public static final String DD_REALTIME_POSITION_QUEUE="realtime_location_sync";

}
