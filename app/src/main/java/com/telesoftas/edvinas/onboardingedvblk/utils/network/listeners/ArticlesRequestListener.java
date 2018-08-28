package com.telesoftas.edvinas.onboardingedvblk.utils.network.listeners;

import com.telesoftas.edvinas.onboardingedvblk.utils.network.response.ArticlesResponse;

public interface ArticlesRequestListener {
    void onArticlesReceived(ArticlesResponse articles);

    void onFailure(Throwable throwable);
}
