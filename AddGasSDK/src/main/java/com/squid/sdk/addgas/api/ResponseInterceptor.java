package com.squid.sdk.addgas.api;


import android.text.TextUtils;
import android.util.Log;
import com.google.gson.Gson;
import java.io.IOException;
import java.nio.charset.Charset;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * @Title 拦截器
 * @Date 2016-05-23 17:14
 */

/**
 * An OkHttp interceptor which logs request and response information. Can be applied as an
 * {@linkplain OkHttpClient#interceptors() application interceptor} or as a {@linkplain
 * OkHttpClient#networkInterceptors() network interceptor}. <p> The format of the logs created by
 * this class should not be considered stable and may change slightly between releases. If you need
 * a stable logging format, use your own interceptor.
 */
public final class ResponseInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        //判断请求是否成功
        if (response.code() == 200) {
            ResponseBody responseBody = response.body();
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();
            String baseResult = buffer.clone().readString(UTF8);
            //判断是否有返回数据
            if (!TextUtils.isEmpty(baseResult)) {
                //获取返回的实体（最外层）
                Gson gson = new Gson();
                ResultBean result =  gson.fromJson(baseResult,ResultBean.class);
                Log.i("result","-----------------"+result.getMsg());
                if (result != null) {
                    //此处处理服务器返回的判断
                    if (!"100".equals(result.getMsgcode())) {
                        try {
                            throw new  ApiException(result);
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw  e;
                        }
                    }
                }
            } else {
                //将服务器的异常抛出处理
                try {
                    throw new  Exception();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                throw new  Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }
}
