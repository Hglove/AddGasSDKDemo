package com.squid.sdk.addgas.api;


import java.util.HashMap;
import java.util.List;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;


/**
 * Created by Administrator on 2016/11/15 0015.
 */

public interface NetService {
    //设缓存有效期为1天
    public static final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
    //查询缓存的Cache-Control设置，使用缓存
    public static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置。不使用缓存
    public static final String CACHE_CONTROL_NETWORK = "max-age=0";

   
    @GET(com.squid.sdk.addgas.api.Url.GET_PAY_TYPE)
    Observable<ResultBean> getPayType();

    @GET(com.squid.sdk.addgas.api.Url.GET_MONEY_LIMIT)
    Observable<ResultBean> getMoneyLimit();

    @GET(com.squid.sdk.addgas.api.Url.GET_OIL_NO)
    Observable<ResultBean> getOilNo();

    @GET(com.squid.sdk.addgas.api.Url.GET_PAY_ORDER_RECORD)
    Observable<ResultBean> getPayOrderRecord(@Query("pageSize") int pageSize, @Query("targetPage") int targetPage, @Query("status") String status);
   
    @POST(com.squid.sdk.addgas.api.Url.GET_PAY_ORDER)
    Observable<ResultBean> getPayOrder(@Body RequestBody body);

    @POST(com.squid.sdk.addgas.api.Url.PAY_ORDER)
    Observable<ResultBean> payOrder(@Body RequestBody bodyo);

    @GET(com.squid.sdk.addgas.api.Url.GET_PAY_DESCRIBE)
    Observable<ResultBean> getPayDescribe();
   
    @GET(com.squid.sdk.addgas.api.Url.GET_OIL_STATION_LIST)
    Observable<ResultBean> getOilStationList(@Query("pageSize") int pageSize, @Query("point_lat") double point_lat, @Query("point_lon") double point_lon, @Query("targetPage") int targetPage);

    @POST(com.squid.sdk.addgas.api.Url.GET_PAY_CODE)
    Observable<ResultBean> getPayCode();

    @POST(com.squid.sdk.addgas.api.Url.PAY_ADD_GAS_MONEY)
    Observable<ResultBean> payAddGasMoney(@Path("orderNo") String orderNo, @Body RequestBody body);

    @GET(com.squid.sdk.addgas.api.Url.GET_DISCOUNT_LIST)
    Observable<ResultBean> getDiscountList();

    @POST(com.squid.sdk.addgas.api.Url.GET_COUPON)
    Observable<ResultBean> getCoupon(@Body RequestBody body);

    @GET(com.squid.sdk.addgas.api.Url.GET_COUPON_LIST)
    Observable<ResultBean> getCouponList(@Query("totalFee") String totalFee, @Query("stationId") String stationId);

    @GET(com.squid.sdk.addgas.api.Url.GET_PERSON_COUPON_LIST)
    Observable<ResultBean> getPersonCouponList(@Query("status") String status);

    @GET(com.squid.sdk.addgas.api.Url.GET_ADD_GAS_ORDER_LIST)
    Observable<ResultBean> getAddGasOrderList(@Query("pageSize") int pageSize, @Query("pageSize") int targetPage);

    @GET(com.squid.sdk.addgas.api.Url.GET_ORDER_DETAIL)
    Observable<ResultBean> getOrderDetail(@Path("orderNo") String orderNo);

}
