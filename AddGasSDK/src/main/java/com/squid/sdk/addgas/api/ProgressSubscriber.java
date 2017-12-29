package com.squid.sdk.addgas.api;

import android.content.Context;

import com.squid.sdk.addgas.utils.TLog;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 */

public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener{

    private SubscriberOnNextListener mSubscriberOnNextListener;
    private ProgressDialogHandler mProgressDialogHandler;

    private Context context;

    public ProgressSubscriber(SubscriberOnNextListener mSubscriberOnNextListener, Context context,Boolean showDialog) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = context;
        if(showDialog)mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
    }

    private void showProgressDialog(){
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog(){
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        showProgressDialog();
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onCompleted() {
        dismissProgressDialog();
        //Toast.makeText(context, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
        if (mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onCompleted();
        }
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof SocketTimeoutException) {
        } else if (e instanceof ConnectException) {
        } else if(e instanceof ApiException){
            String str=e.getMessage();
            if(str.contains("showToast:")){
            }else if("用户未登录".equals(str)){
                //App.showToast("用户未登录", Toast.LENGTH_SHORT);
            }else if("系统异常".equals(str)){
            }
            TLog.e("ProgressSubscriber",e.getMessage());
        }else{
        }
        dismissProgressDialog();
        if (mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onError(e);
        }
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        dismissProgressDialog();
        if (mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onNext(t);
        }
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    @Override
    public void onCancelProgress() {
        onCompleted();
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}