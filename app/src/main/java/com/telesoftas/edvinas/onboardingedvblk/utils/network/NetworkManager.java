package com.telesoftas.edvinas.onboardingedvblk.utils.network;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.telesoftas.edvinas.onboardingedvblk.BuildConfig.BASE_URL;
import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;

public class NetworkManager {
    public <T> T getService(Class<T> clazz) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getHttpClient().build())
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
                .create(clazz);
    }

    private Gson getGson() {
        return new GsonBuilder().create();
    }

    private OkHttpClient.Builder getHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(getAuthorizationInterceptor())
                .addInterceptor(getHttpLoggingInterceptor());
    }

    private HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(BODY);
        return logger;
    }

    private Interceptor getAuthorizationInterceptor() {
        return new AuthorizationInterceptor();
    }
}
