package com.squid.sdk.addgas.api;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/14 0014.
 */
public class ResultBean<T> implements Serializable {
    private String msgcode;
    private T msg;
    private String business;

    public String getMsgcode() {
        return msgcode;
    }

    public void setMsgcode(String msgcode) {
        this.msgcode = msgcode;
    }

    public T getMsg() {
        return msg;
    }

    public void setMsg(T msg) {
        this.msg = msg;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }
}
