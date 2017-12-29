package com.squid.sdk.addgas.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.widget.Toast;
import com.squid.sdk.addgas.Squid;
import com.squid.sdk.addgas.utils.TLog;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Administrator on 2016/11/15 0015.
 */

public class OkHttp3Utils {
    private static  final String TAG="OkHttp3Utils";
    private static OkHttpClient mOkHttpClient;

    //设置缓存目录
    private static File cacheDirectory = new File(Squid.getContext().getApplicationContext().getCacheDir().getAbsolutePath(), "MyCache");
    private static Cache cache = new Cache(cacheDirectory, 10 * 1024 * 1024);


    /**
     * 获取OkHttpClient对象
     *
     * @return
     */
    public static OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLogger());
        if (null == mOkHttpClient) {
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            mOkHttpClient = new OkHttpClient.Builder()
                    //.cookieJar(new CookiesManager())
                    //.addNetworkInterceptor(new CookiesInterceptor(MyApplication.getInstance().getApplicationContext()))
                    //设置请求读写的超时时间
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                     .cache(cache)
                    //.addInterceptor(new MyIntercepter())
                    .addInterceptor(new ResponseInterceptor())
                    .addNetworkInterceptor(mTokenInterceptor)
                    .addNetworkInterceptor(logInterceptor)
                    .build();
        }
        return mOkHttpClient;
    }

    /**
     * 拦截器
     */
    private static class MyIntercepter implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long startTime = System.currentTimeMillis();
            okhttp3.Response response = chain.proceed(chain.request());
            long endTime = System.currentTimeMillis();
            long duration=endTime-startTime;
            LogQuest(request,response);
            TLog.d(TAG,"----------End:"+duration+"毫秒----------");
            TLog.e("OkHttp3Utils",request.url().toString());
            if(request.body()!=null){
                TLog.e("OkHttp3Utils",request.body().toString());
            }
            request = request.newBuilder()
                    .header("User-Agent",System.getProperty("http.agent"))
                    .build();
            if (!isNetworkReachable(Squid.getContext().getApplicationContext())) {
                Toast.makeText(Squid.getContext().getApplicationContext(), "暂无网络", Toast.LENGTH_SHORT).show();
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)//无网络时只从缓存中读取
                        .header("User-Agent",System.getProperty("http.agent"))
                        .build();
            }

            if (isNetworkReachable(Squid.getContext().getApplicationContext())) {
                int maxAge = 60 * 60; // 有网络时 设置缓存超时时间1个小时
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .header("User-Agent",System.getProperty("http.agent"))
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // 无网络时，设置超时为4周
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    }

public static void LogQuest(Request request,Response response)throws IOException{
   // String content = response.body().string();
    TLog.e(TAG,"\n");
    TLog.e(TAG,"----------Start----------------");
    TLog.e(TAG, "| "+request.toString());
    String method=request.method();
    if("POST".equals(method)){
        StringBuilder sb = new StringBuilder();
        if (request.body() instanceof FormBody) {
            FormBody body = (FormBody) request.body();
            for (int i = 0; i < body.size(); i++) {
                sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
            }
            sb.delete(sb.length() - 1, sb.length());
            TLog.e(TAG, "| RequestParams:{"+sb.toString()+"}");
        }
    }
   // TLog.d(TAG, "| Response:" + content);
}
    /**
     * 自动管理Cookies
     */
    private static class CookiesManager implements CookieJar {
        private final PersistentCookieStore cookieStore = new PersistentCookieStore(Squid.getContext().getApplicationContext());

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            if (cookies != null && cookies.size() > 0) {
                for (Cookie item : cookies) {
                    cookieStore.add(url, item);
                }
            }
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url);
            return cookies;
        }
    }

    /**
     * 判断网络是否可用
     *
     * @param context Context对象
     */
    public static Boolean isNetworkReachable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = cm.getActiveNetworkInfo();
        if (current == null) {
            return false;
        }
        return (current.isAvailable());
    }


    static Interceptor mTokenInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            //没有token
            String token="";
            if(TextUtils.isEmpty(token)){
                return chain.proceed(originalRequest);
            }
            Request authorised = originalRequest.newBuilder()
                    .header("param-token", token)
                    .build();
            return chain.proceed(authorised);
        }
    };

    public static class  HttpLogger implements HttpLoggingInterceptor.Logger {
        @Override
        public void log(String message) {
            TLog.d("HttpLogInfo", message);
        }
    }
}
