package com.squid.sdk.addgas.api;

import android.content.Context;

/**
 * @Title  接口请求异常
 * @Author luojiang
 * @Date 2016-05-23 17:14
 */
public class ApiException extends RuntimeException {

    public static final int USER_NOT_EXIST = 100;
    public static final int WRONG_PASSWORD = 101;
    private static Context mContext;
    public ApiException(ResultBean result) {
        this(getApiExceptionMessage(result));
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }
//    public ApiException(int code,String detailMessage) {
//        super(detailMessage);
//    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     * @return
     */
    private static String getApiExceptionMessage(ResultBean result){
        int code=0;
        try{
            code=Integer.parseInt(result.getMsgcode());
        }catch (Exception e){
            e.printStackTrace();
            code=0;
        }
        final String msg=(String) result.getMsg();
        String business=(String) result.getBusiness();
        String message = "网络请求错误";
        switch (code) {
            case 102:
                message = "用户未登录";
                break;
            case 109:
                message="系统异常";
                break;
            case 900:
                if("1003".equals(business)){
                    message="business="+business+msg;
                }else if("1004".equals(business)){
                    message="business="+business+msg;
                }else{
                    message="showToast:"+msg;
                }
                break;
            default:
                message=msg;
//            case WRONG_PASSWORD:
//                message = "密码错误";
//                break;

        }
        return message;
    }
}

