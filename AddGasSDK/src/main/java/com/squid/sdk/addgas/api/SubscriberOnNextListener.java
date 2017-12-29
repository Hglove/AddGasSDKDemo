package com.squid.sdk.addgas.api;

/**
 * Created by liukun on 16/3/10.
 */
public interface SubscriberOnNextListener<T> {
    void onNext(T t);
    void onCompleted();
    void onError(Throwable e);
}
