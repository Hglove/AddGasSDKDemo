package com.squid.sdk.addgas.api;

/**
 * Created by lanluo on 16/9/14.
 */
public class HttpSubseiber <T>{
    public  interface  ResponseHandler<T>{

        abstract void onSucceed(T data);
        abstract void onFail(String msg);
    }


}
