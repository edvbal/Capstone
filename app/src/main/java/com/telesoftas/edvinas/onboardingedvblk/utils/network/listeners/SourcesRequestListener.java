package com.telesoftas.edvinas.onboardingedvblk.utils.network.listeners;

import com.telesoftas.edvinas.onboardingedvblk.utils.network.response.SourcesResponse;

public interface SourcesRequestListener {
    void onSourcesReceived(SourcesResponse response);

    void onFailure(Throwable throwable);
}