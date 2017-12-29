package com.squid.sdk.addgas.api;

/**
 * Created by Administrator on 2016/5/9.
 */
public class Url {
    // 测试服务器地址
    public static final String SOCKET = "47.96.17.98";//接口服务
    public static final String GO = "http://47.96.17.98:9021";//接口服务

//    //正式服务器地址
//    public static final String SOCKET = "socket.xjiye.cn";//接口服务
//    public static final String GO = "http://app.xjiye.cn";//接口服务

    public static final String GET_PAY_TYPE= GO + "/deposit/paymode";//获取支持的支付方式
    public static final String GET_MONEY_LIMIT= GO + "/deposit/amount/restrict";//金额大小限制
    public static final String GET_OIL_NO= GO + "/deposit/oilno";//获取油品标号
    public static final String GET_PAY_ORDER= GO + "/deposit";//下单（下订单获取订单编号）
    public static final String PAY_ORDER=GO + "/deposit/prepay";//支付订单
    public static final String GET_PAY_ORDER_RECORD= GO + "/deposit";//获取购油订单流水
    public static final String GET_PAY_DESCRIBE= GO + "/deposit/description";//购油描述
    public static final String GET_OIL_STATION_LIST= GO + "/gasStation/distance/list";//获取油站列表

    public static final String GET_DISCOUNT_LIST= GO + "/customer/account/discount";//获取折扣账户列表
    public static final String GET_ADD_GAS_ORDER_LIST= GO + "/order/list";//获取加油订单列表
    public static final String GET_COUPON= GO + "/coupon/receiveCoupon";//获取优惠券
    public static final String GET_COUPON_LIST= GO + "/coupon/getPayCoupon";//获取优惠券列表
    public static final String GET_PERSON_COUPON_LIST= GO + "/coupon/myCoupon";//所有优惠券列表
    public static final String GET_ORDER_DETAIL= GO + "/order/{orderNo}";//获取加油订单详情
    public static final String GET_PAY_CODE= GO + "/order/paycode";//获取支付码
    public static final String PAY_ADD_GAS_MONEY= GO + "/order/pay/{orderNo}";//支付加油订单

}
