package com.gm.soft.sqlite;


import android.app.DownloadManager;
import android.net.Uri;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {
    public final String TYPE_POST="POST";
    public final String TYPE_GET="GET";
    private static String MEDIA_TYPE="application/json;charset=utf-8";
    //private final String BASE_PATH="http://192.168.0.105:8090/testWeb_war_exploded";
    private final String BASE_PATH="http://192.168.254.172:8090/testWeb_war_exploded";
    private OkHttpClient okHttpClient;
    private static HttpUtil httpUtil;
    private HttpUtil(){
        if (okHttpClient==null){
            okHttpClient=new OkHttpClient();
            OkHttpClient.Builder builder=new OkHttpClient.Builder();
            builder.readTimeout(3000, TimeUnit.MILLISECONDS);
            builder.writeTimeout(3000, TimeUnit.MILLISECONDS);
            builder.connectTimeout(30000, TimeUnit.MILLISECONDS);
            okHttpClient=builder.build();
        }
    }
    public static HttpUtil getInstance(){
        if(httpUtil==null){
            synchronized (HttpUtil.class){
                if(httpUtil==null){
                    httpUtil=new HttpUtil();
                }
            }
        }
        return httpUtil;
    }

    public String synch(String url, String type, Map<String,String> params){
        String result="";
        Response response=null;
        if(TextUtils.isEmpty(type)){
            type=TYPE_GET;
        }
        MediaType mediaType = MediaType.parse(MEDIA_TYPE);
        try{
            if(TYPE_GET.equals(type)){
                StringBuffer strB=new StringBuffer();
                String urlAndParams="";
                if(params!=null&&params.size()>0){
                    urlAndParams="?";
                    for(Map.Entry<String, String> entry:params.entrySet()){
                        strB.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                    }
                    urlAndParams+=strB.toString();
                }
                Request request=new Request.Builder().url(BASE_PATH+url+urlAndParams).build();
                response=okHttpClient.newCall(request).execute();
            }else if(TYPE_POST.equals(type)){
                FormBody.Builder formBody=new FormBody.Builder();
                if(params!=null&&params.size()>0){
                    for(Map.Entry<String, String> entry:params.entrySet()){
                       formBody.add(entry.getKey(),entry.getValue());
                    }
                }
                Request request=new Request.Builder().url(BASE_PATH+url).post(formBody.build()).build();
                response=okHttpClient.newCall(request).execute();
            }
            if(response.isSuccessful()){
                result=response.body().string();
            }
        }catch (IOException e){

        }
        return result;
    }

    public void asynch(String url, String type, Map<String,String> params, Callback callback){
        if(TextUtils.isEmpty(type)){
            type=TYPE_GET;
        }
            if(TYPE_GET.equals(type)){
                StringBuffer strB=new StringBuffer();
                String urlAndParams="";
                if(params!=null&&params.size()>0){
                    urlAndParams="?";
                    for(Map.Entry<String, String> entry:params.entrySet()){
                        strB.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                    }
                    urlAndParams+=strB.toString();
                }
                Request request=new Request.Builder().url(BASE_PATH+url+urlAndParams).build();
                okHttpClient.newCall(request).enqueue(callback);
            }else if(TYPE_POST.equals(type)){
                FormBody.Builder formBody=new FormBody.Builder();
                if(params!=null&&params.size()>0){
                    for(Map.Entry<String, String> entry:params.entrySet()){
                        formBody.add(entry.getKey(),entry.getValue());
                    }
                }
                Request request=new Request.Builder().url(BASE_PATH+url).post(formBody.build()).build();
                okHttpClient.newCall(request).enqueue(callback);
            }
    }

    public void asynchFile(String url, Map<String,String> params, File[] files, Callback callback){
            RequestBody requestBody=new MultipartBody.Builder().setType(MultipartBody.FORM).build();
            Request request=new Request.Builder().url(BASE_PATH+url).post(requestBody).build();
            okHttpClient.newCall(request).enqueue(callback);

    }

    public long downFileHttp(Uri uri, String path, String filePath, DownloadManager downloadManager){
        DownloadManager.Request request=new DownloadManager.Request(uri);
        request.setNotificationVisibility(request.VISIBILITY_HIDDEN);
        request.setDestinationInExternalPublicDir(path,filePath);
        long id=downloadManager.enqueue(request);
        return  id;
    }


}
