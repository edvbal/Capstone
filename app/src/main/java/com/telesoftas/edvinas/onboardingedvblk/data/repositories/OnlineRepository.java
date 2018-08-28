package com.telesoftas.edvinas.onboardingedvblk.data.repositories;

import com.telesoftas.edvinas.onboardingedvblk.news.articles.Article;
import com.telesoftas.edvinas.onboardingedvblk.utils.Mapper;
import com.telesoftas.edvinas.onboardingedvblk.utils.network.ArticlesService;
import com.telesoftas.edvinas.onboardingedvblk.utils.network.response.ArticlesResponse;
import com.telesoftas.edvinas.onboardingedvblk.data.preferences.PreferencesConstants;
import com.telesoftas.edvinas.onboardingedvblk.data.preferences.StorageManager;

import java.util.List;

import io.reactivex.Single;


public class OnlineRepository {
    private final ArticlesService service;
    private final StorageManager storageManager;

    public OnlineRepository(ArticlesService service, StorageManager storageManager) {
        this.service = service;
        this.storageManager = storageManager;
    }

    public Single<List<Article>> getArticles() {
        String defaultSource = ArticlesService.DEFAULT_SOURCE;
        String sourceId = PreferencesConstants.KEY_SOURCE_ID;
        return service.getArticle(storageManager.getString(sourceId, defaultSource))
                .map((Mapper<ArticlesResponse, List<Article>>) ArticlesResponse::getArticles);
    }
}
