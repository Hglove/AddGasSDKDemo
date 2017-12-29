package com.squid.sdk.addgas.api;

import com.google.gson.TypeAdapter;
import com.squid.sdk.addgas.utils.TLog;

import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by Administrator on 2016/11/16 0016.
 */

public class DecodeResponseBodyConverter <T> implements Converter<ResponseBody, T> {
    private final TypeAdapter<T> adapter;

    DecodeResponseBodyConverter(TypeAdapter<T> adapter) {
        TLog.e("DecodeResponseBodyConverter","DecodeResponseBodyConverter");
        this.adapter = adapter;
        TLog.e("DecodeResponseBodyConverter","DecodeResponseBodyConverter1");
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        //解密字符串
        TLog.e("DecodeResponseBodyConverter","ResponseBody1");
        String str=value.string();
        String str2="";
        try{
            str2=str;
//            str2=new String(MCrypt.getInstance().decrypt(str));
        }catch (Exception e){
            str2=str;
        }
        TLog.e("DecodeResponseBodyConverter","ResponseBody2"+str2);
        return adapter.fromJson(str2);
    }
}
