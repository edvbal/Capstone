package com.telesoftas.edvinas.onboardingedvblk.news.articles;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.telesoftas.edvinas.onboardingedvblk.data.database.ArticleEntity;
import com.telesoftas.edvinas.onboardingedvblk.data.repositories.OfflineRepository;
import com.telesoftas.edvinas.onboardingedvblk.data.repositories.OnlineRepository;
import com.telesoftas.edvinas.onboardingedvblk.utils.mappers.MovieEntityListMapper;

import java.util.Collections;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import timber.log.Timber;

public class ArticlesViewModel extends ViewModel {
    private final Scheduler mainScheduler;
    private final OnlineRepository onlineRepository;
    private final OfflineRepository offlineRepository;
    private final MovieEntityListMapper entityMapper;
    private Disposable disposable = Disposables.disposed();

    private MutableLiveData<Boolean> progressState = new MutableLiveData<>();
    private MutableLiveData<Boolean> refreshingState = new MutableLiveData<>();
    private MediatorLiveData<List<Article>> articlesState = new MediatorLiveData<>();
    private MutableLiveData<Boolean> emptyState = new MutableLiveData<>();

    public ArticlesViewModel(
            Scheduler mainScheduler,
            OnlineRepository onlineRepository,
            OfflineRepository offlineRepository,
            MovieEntityListMapper entityMapper) {
        super();
        this.mainScheduler = mainScheduler;
        this.onlineRepository = onlineRepository;
        this.offlineRepository = offlineRepository;
        this.entityMapper = entityMapper;
    }

    public LiveData<Boolean> observeProgressState() {
        return progressState;
    }

    public LiveData<List<Article>> observeArticlesState() {
        return articlesState;
    }

    public LiveData<Boolean> observeRefreshingState() {
        return refreshingState;
    }

    public LiveData<Boolean> observeEmptyState() {
        return emptyState;
    }

    public void onLoad(boolean isRefresh, boolean isConnected) {
        if (isConnected) {
            disposable = downloadArticles(!isRefresh);
        } else {
            disposable = pullArticles(!isRefresh);
        }
    }

    private Disposable pullArticles(boolean showProgress) {
        return offlineRepository.getArticles()
                .observeOn(mainScheduler)
                .doOnSubscribe(disposable -> {
                    if (showProgress) {
                        progressState.postValue(true);
                    }
                })
                .subscribe(this::onArticlesPulled, this::onRequestError);
    }

    private void onArticlesPulled(LiveData<List<ArticleEntity>> articlesLiveData) {
        articlesState.addSource(articlesLiveData, articleEntities -> {
            if (articleEntities == null) return;
            articlesState.postValue(entityMapper.mapDomainToBussiness(articleEntities));
            emptyState.postValue(articleEntities.isEmpty());
        });
        stopProgressAndRefreshing();
    }

    @NonNull
    private Disposable downloadArticles(boolean showProgress) {
        return onlineRepository.getArticles()
                .observeOn(mainScheduler)
                .doOnSubscribe(disposable -> {
                    if (showProgress) {
                        progressState.postValue(true);
                    }
                })
                .subscribe(this::onRequestSuccess, this::onRequestError);
    }

    private void onRequestSuccess(List<Article> articles) {
        offlineRepository
                .updateArticles(entityMapper.mapBussinessToDomain(articles))
                .subscribe();
        articlesState.postValue(articles);
        emptyState.postValue(articles.isEmpty());
        stopProgressAndRefreshing();
    }

    private void onRequestError(Throwable throwable) {
        Timber.e(throwable);
        articlesState.postValue(Collections.emptyList());
        emptyState.postValue(true);
        stopProgressAndRefreshing();
    }

    private void stopProgressAndRefreshing() {
        refreshingState.postValue(false);
        progressState.postValue(false);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
