package com.telesoftas.edvinas.onboardingedvblk.utils.network;

import com.telesoftas.edvinas.onboardingedvblk.utils.network.response.SourcesResponse;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;

public interface SourcesService {
    @GET("sources?language=en")
    Single<SourcesResponse> getSources();
}
