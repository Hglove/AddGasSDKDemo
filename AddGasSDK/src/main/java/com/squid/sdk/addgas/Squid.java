package com.squid.sdk.addgas;

import android.content.Context;
import com.google.gson.Gson;
import com.squid.sdk.addgas.api.ApiManager;
import com.squid.sdk.addgas.api.CallBack;
import com.squid.sdk.addgas.api.ResultBean;
import com.squid.sdk.addgas.api.SubscriberOnNextListener;
import com.squid.sdk.addgas.utils.TLog;

/**
 * Created by hg on 2017/12/26.
 */

public class Squid {
    private static Squid squid;
    private static Context context;
    public Squid(Context context) {
        this.context = context;
    }
    public static void init(Context context){
        if(squid==null){
            squid= new Squid(context);
        }
    }
    public static Context getContext() {
        return context;
    }
    public static Squid getInstance(){
        if(squid!=null){
            return squid;
        }else{
            TLog.e("Squid","Squid class is null");
            return null;
        }
    }

    public static void  getOrderDetail(Context context, String orderNo, final CallBack callBack) {
        ApiManager.getInstance().getOrderDetail(context, true, orderNo, new SubscriberOnNextListener<ResultBean>() {
            @Override
            public void onNext(ResultBean resultBean) {
                Gson gson=new Gson();
                String str=gson.toJson(resultBean);
                callBack.Success(str);
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                callBack.Error(e);
            }
        });
    }
}
