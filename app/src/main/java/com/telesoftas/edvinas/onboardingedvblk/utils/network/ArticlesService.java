package com.telesoftas.edvinas.onboardingedvblk.utils.network;

import com.telesoftas.edvinas.onboardingedvblk.utils.network.response.ArticlesResponse;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ArticlesService {
    public static final String DEFAULT_SOURCE = "techcrunch";

    @GET("articles")
    Single<ArticlesResponse> getArticle(@Query("source") String source);
}
