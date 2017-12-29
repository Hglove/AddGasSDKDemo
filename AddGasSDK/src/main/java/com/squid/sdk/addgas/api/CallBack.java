package com.squid.sdk.addgas.api;

/**
 * Created by liukun on 16/3/10.
 */
public interface CallBack<T> {
    void Success(String result);
    void Error(Throwable e);
}
