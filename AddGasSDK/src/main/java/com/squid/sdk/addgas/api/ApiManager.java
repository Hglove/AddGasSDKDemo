package com.squid.sdk.addgas.api;

import android.content.Context;

import com.google.gson.Gson;
import com.squid.sdk.addgas.utils.MD5Encryption;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/11/2 0002.
 */
public class ApiManager extends RetrofitUtils {
    protected static final NetService service = getRetrofit().create(NetService.class);
    public static ApiManager instance = null;

    public ApiManager() {
    }

    private static synchronized void syncInit() {
        if (instance == null) instance = new ApiManager();
    }

    public static ApiManager getInstance() {
        if (instance == null) syncInit();
        return instance;
    }

    public static NetService getService() {
        return service;
    }


    public RequestBody getBody(HashMap<String,Object> hashMap){
        Gson gson = new Gson();
        String json = gson.toJson(hashMap);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        return body;
    }

    /**
     * 插入观察者
     *
     * @param observable
     * @param subscriber
     * @param <T>
     */
    public static <T> void setSubscribe(Observable<T> observable, Subscriber<T> subscriber) {
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(subscriber);
    }


    public void getPayType(Context context, boolean showDialog,SubscriberOnNextListener<ResultBean> subscriber ) {
//        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(context);
//        String is_real_user = sharedPreferencesUtil.getIs_real_user();
        setSubscribe(service.getPayType(), new ProgressSubscriber<ResultBean>(subscriber, context,showDialog));
    }

    public void getPayDescribe(Context context,boolean showDialog, SubscriberOnNextListener<ResultBean> subscriber ) {
        setSubscribe(service.getPayDescribe(), new ProgressSubscriber<ResultBean>(subscriber, context,showDialog));
    }

    public void getMoneyLimit(Context context, boolean showDialog,SubscriberOnNextListener<ResultBean> subscriber ) {
        setSubscribe(service.getMoneyLimit(), new ProgressSubscriber<ResultBean>(subscriber, context,showDialog));
    }
    public void getOilNo(Context context, boolean showDialog,SubscriberOnNextListener<ResultBean> subscriber ) {
        setSubscribe(service.getOilNo(), new ProgressSubscriber<ResultBean>(subscriber, context,showDialog));
    }

    public void getPayOrderRecord(Context context,boolean showDialog, int pageSize, int targetPage, String paySattus, SubscriberOnNextListener<ResultBean> subscriber ) {
        setSubscribe(service.getPayOrderRecord(pageSize, targetPage, paySattus), new ProgressSubscriber<ResultBean>(subscriber, context,showDialog));
    }

    public void getPayOrder(Context context,boolean showDialog, float amount, String oilNo, SubscriberOnNextListener<ResultBean> subscriber) {
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("amount",amount);
        hashMap.put("oilNo",oilNo);
        setSubscribe(service.getPayOrder(getBody(hashMap)), new ProgressSubscriber<ResultBean>(subscriber, context,showDialog));
    }

    public void payOrder(Context context,boolean showDialog, String orderNo, String payMode, SubscriberOnNextListener<ResultBean> subscriber) {
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("orderNo",orderNo);
        hashMap.put("payMode",payMode);
        setSubscribe(service.payOrder( getBody(hashMap)), new ProgressSubscriber<ResultBean>(subscriber, context,showDialog));
    }

    public void getOilStationList(Context context, boolean showDialog,int pageSize, double point_lat, double point_lon, int targetPage, SubscriberOnNextListener<ResultBean> subscriber ) {
        setSubscribe(service.getOilStationList(pageSize,point_lat,point_lon,targetPage), new ProgressSubscriber<ResultBean>(subscriber, context,showDialog));
    }

    public void getPayCode(Context context,boolean showDialog, SubscriberOnNextListener<ResultBean> subscriber ) {
        setSubscribe(service.getPayCode(), new ProgressSubscriber<ResultBean>(subscriber, context,showDialog));
    }

    public void payAddGasMoney(Context context, boolean showDialog,String orderNo, String couponId, String disAccountId, double point_lat, double point_lon, double payFee, String payPwd, String payType,SubscriberOnNextListener<ResultBean> subscriber ) {
        try {
            payPwd = MD5Encryption.getEncryption(payPwd);
        } catch (Exception e) {
            e.printStackTrace();
            payPwd = "";
        }
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("couponId",couponId);
        hashMap.put("disAccountId",disAccountId);
        hashMap.put("lat",point_lat);
        hashMap.put("lon",point_lon);
        hashMap.put("payFee",payFee);
        hashMap.put("payPwd",payPwd);
        hashMap.put("payType",payType);
        setSubscribe(service.payAddGasMoney(orderNo,getBody(hashMap)), new ProgressSubscriber<ResultBean>(subscriber, context,showDialog));
    }

    public void getDiscountList(Context context,boolean showDialog, SubscriberOnNextListener<ResultBean> subscriber ) {
        setSubscribe(service.getDiscountList(), new ProgressSubscriber<ResultBean>(subscriber, context,showDialog));
    }

    public void getCouponList(Context context,boolean showDialog, String totalFree,String stationId, SubscriberOnNextListener<ResultBean> subscriber ) {
        setSubscribe(service.getCouponList(totalFree,stationId), new ProgressSubscriber<ResultBean>(subscriber, context,showDialog));
    }
    public void getPersonCouponList(Context context,boolean showDialog, String ststus, SubscriberOnNextListener<ResultBean> subscriber ) {
        setSubscribe(service.getPersonCouponList(ststus), new ProgressSubscriber<ResultBean>(subscriber, context,showDialog));
    }
    public void getCoupon(Context context,boolean showDialog, String launchId, SubscriberOnNextListener<ResultBean> subscriber ) {
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("launchId",launchId);
        setSubscribe(service.getCoupon(getBody(hashMap)), new ProgressSubscriber<ResultBean>(subscriber, context,showDialog));
    }
    public void getAddGasOrderList(Context context,boolean showDialog, int pageSize, int targetPage, SubscriberOnNextListener<ResultBean> subscriber ) {
        setSubscribe(service.getAddGasOrderList(pageSize,targetPage), new ProgressSubscriber<ResultBean>(subscriber, context,showDialog));
    }

    public void getOrderDetail(Context context, boolean showDialog,String orderNo, SubscriberOnNextListener<ResultBean> subscriber ) {
        setSubscribe(service.getOrderDetail(orderNo), new ProgressSubscriber<ResultBean>(subscriber, context,showDialog));
    }

//
//    //加密params
//    public RequestParams encryptParams(RequestParams params, String url) {
//        RequestParams param = new RequestParams(url);
//        String guess = "";
//        try {
//            guess = Base64.encode(MCrypt.getInstance().encrypt(params.toJSONString()));
//        } catch (Exception e) {
//            guess = "";
//            e.printStackTrace();
//        }
//        param.addParameter("guess", guess);
//        return param;
//    }
//
//    //加密params
//    public String encryptParams2(RequestParams params) {
//        String guess = "";
//        try {
//            guess = Base64.encode(MCrypt.getInstance().encrypt(params.toJSONString()));
//        } catch (Exception e) {
//            guess = "";
//            e.printStackTrace();
//        }
//        return guess;
//    }
//
//    public static String addToken(Context context, JsonObject jsonObject) {
//        String token;
//        String guess = "";
//        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(context);
//        token = sharedPreferencesUtil.getToken();
//        jsonObject.addProperty("validate_token", token);
//        jsonObject.addProperty("requesttime", System.currentTimeMillis() / 1000 + "");
//        try {
//            guess = Base64.encode(MCrypt.getInstance().encrypt(jsonObject.toString()));
//        } catch (Exception e) {
//            guess = "";
//            e.printStackTrace();
//        }
//        return guess;
//    }
}
