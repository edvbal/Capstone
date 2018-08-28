package com.telesoftas.edvinas.onboardingedvblk.data.repositories;

import android.arch.lifecycle.LiveData;

import com.telesoftas.edvinas.onboardingedvblk.data.database.ArticleDao;
import com.telesoftas.edvinas.onboardingedvblk.data.database.ArticleEntity;
import com.telesoftas.edvinas.onboardingedvblk.news.articles.Article;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.Single;

public class OfflineRepository {
    private final ArticleDao articleDao;
    private final Scheduler scheduler;

    public OfflineRepository(ArticleDao articleDao, Scheduler scheduler) {
        this.articleDao = articleDao;
        this.scheduler = scheduler;
    }

    public Single<LiveData<List<ArticleEntity>>> getArticles() {
        return Single.fromCallable(articleDao::getArticles).subscribeOn(scheduler);
    }

    public Completable updateArticles(List<ArticleEntity> articles) {
        return Completable.fromRunnable(() -> {
            articleDao.deleteArticles();
            articleDao.insertArticles(articles);
        }).subscribeOn(scheduler);
    }
}
