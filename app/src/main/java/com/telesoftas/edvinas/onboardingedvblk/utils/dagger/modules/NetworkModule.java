package com.telesoftas.edvinas.onboardingedvblk.utils.dagger.modules;

import android.content.Context;

import com.google.gson.Gson;
import com.telesoftas.edvinas.onboardingedvblk.BuildConfig;
import com.telesoftas.edvinas.onboardingedvblk.utils.dagger.scopes.AppScope;
import com.telesoftas.edvinas.onboardingedvblk.utils.network.AuthorizationInterceptor;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public final class NetworkModule {
    private NetworkModule() {
    }

    @Provides
    public static Gson provideGson() {
        return new Gson();
    }

    @Provides
    public static OkHttpClient provideHttpClient(Context context, Gson gson) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
        AuthorizationInterceptor authorizationInterceptor = new AuthorizationInterceptor();
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(authorizationInterceptor)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    @AppScope
    @Provides
    public static Retrofit provideRetrofit(OkHttpClient client, Gson gson) {
        Scheduler scheduler = Schedulers.io();
        return new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(scheduler))
                .baseUrl(BuildConfig.BASE_URL)
                .build();
    }
}
