package com.telesoftas.edvinas.onboardingedvblk.utils.network;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.telesoftas.edvinas.onboardingedvblk.BuildConfig.API_KEY;

public class AuthorizationInterceptor implements Interceptor {
    private static final String AUTHORIZATION_HEADER = "X-Api-Key";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder builder = original.newBuilder()
                .header(AUTHORIZATION_HEADER, API_KEY);
        Request request = builder.build();
        return chain.proceed(request);
    }
}
