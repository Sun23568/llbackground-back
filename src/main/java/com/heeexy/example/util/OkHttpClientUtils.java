package com.heeexy.example.util;

import com.alibaba.fastjson.JSONObject;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;

public class OkHttpClientUtils {
    /**
     * post请求
     *
     * @param url
     * @param param
     * @return
     */
    public static String post(String url, String param) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(param, MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder().url(url).post(body).build();
        try (okhttp3.Response clientResp = client.newCall(request).execute()) {
            if (!clientResp.isSuccessful()) {
                throw new IOException("Unexpected code " + clientResp);
            }
            return clientResp.body().string();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * get请求
     *
     * @param url
     * @return
     */
    public static String get(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        try (okhttp3.Response clientResp = client.newCall(request).execute()) {
            if (!clientResp.isSuccessful()) {
                throw new IOException("Unexpected code " + clientResp);
            }
            return clientResp.body().string();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}