package com.telesoftas.edvinas.onboardingedvblk.main.news;

import com.telesoftas.edvinas.onboardingedvblk.news.articles.Article;
import com.telesoftas.edvinas.onboardingedvblk.article.news.NewsModel;
import com.telesoftas.edvinas.onboardingedvblk.article.news.NewsPresenterImpl;
import com.telesoftas.edvinas.onboardingedvblk.article.news.NewsView;
import com.telesoftas.edvinas.onboardingedvblk.utils.network.listeners.ArticlesRequestListener;
import com.telesoftas.edvinas.onboardingedvblk.utils.network.response.ArticlesResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.util.Collections;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ArticlesViewModelTest {
    private static final String DEFAULT_IMAGE_URL = "defaultUrl";
    private static final String DEFAULT_URL = "defaultUrl";
    private static final String DEFAULT_DATE = "defaultDate";
    private static final String DEFAULT_DESCRIPTION = "defaultDescription";
    private static final String DEFAULT_TITLE = "defaultTitle";
    private static final boolean SOURCE_UPDATED = true;
    private static final boolean SOURCE_NOT_UPDATED = false;
    private Article article;
    private NewsView view;
    private ArticlesRequestListener listener;
    private NewsModel model;
    private ArticlesResponse response;
    private NewsPresenterImpl presenter;
    private ArgumentCaptor<ArticlesRequestListener> articlesRequestCaptor;
    private Throwable throwable;

    @Before
    public void setUp() throws Exception {
        view = mock(NewsView.class);
        model = mock(NewsModel.class);
        response = mock(ArticlesResponse.class);
        listener = mock(ArticlesRequestListener.class);
        articlesRequestCaptor = ArgumentCaptor.forClass(ArticlesRequestListener.class);
        presenter = new NewsPresenterImpl(model);
        presenter.takeView(view);
        article = new Article(DEFAULT_IMAGE_URL, DEFAULT_TITLE,
                DEFAULT_DESCRIPTION, DEFAULT_DATE, DEFAULT_URL);
        throwable = mock(IOException.class);
    }

    @Test
    public void onNewsItemSelected_hasView_callsViewOpenArticleScreen() {
        presenter.onNewsItemSelected(article);

        verify(view).openArticleScreen(article);
    }

    @Test
    public void onNewsItemSelected_hasNoView_doesNotCallViewMethods() {
        presenter.dropView();

        presenter.onNewsItemSelected(article);

        verifyZeroInteractions(view);
    }

    @Test
    public void onRefresh_hasView_callsModelGetArticles() {
        presenter.onRefresh();

        verify(model).getArticles(articlesRequestCaptor.capture());
    }

    @Test
    public void onRefresh_hasNoView_doesNotCallModelMethods() {
        presenter.dropView();

        presenter.onRefresh();

        verifyZeroInteractions(model);
    }

    @Test
    public void setArticles_onArticlesReceived_callsViewPopulateView() {
        presenter.onRefresh();
        verify(model).getArticles(articlesRequestCaptor.capture());

        articlesRequestCaptor.getValue().onArticlesReceived(response);

        verify(view).populateView(response.getArticles());
    }

    @Test
    public void setArticles_onArticlesReceived_callsViewHideProgress() {
        presenter.onRefresh();
        verify(model).getArticles(articlesRequestCaptor.capture());

        articlesRequestCaptor.getValue().onArticlesReceived(response);

        verify(view).hideProgress();
    }

    @Test
    public void setArticles_onArticlesReceived_callsViewSetRefreshingFalse() {
        presenter.onRefresh();
        verify(model).getArticles(articlesRequestCaptor.capture());

        articlesRequestCaptor.getValue().onArticlesReceived(response);

        verify(view).setRefreshingFalse();
    }

    @Test
    public void setArticles_onArticlesReceived_callsViewHideNoInternetError() {
        presenter.onRefresh();
        verify(model).getArticles(articlesRequestCaptor.capture());

        articlesRequestCaptor.getValue().onArticlesReceived(response);

        verify(view).hideNoInternetError();
    }

    @Test
    public void setArticles_onFailureThrowableIOException_callsViewPopulateViewWithEmptyList() {
        presenter.onRefresh();
        verify(model).getArticles(articlesRequestCaptor.capture());

        articlesRequestCaptor.getValue().onFailure(throwable);

        verify(view).populateView(Collections.emptyList());
    }

    @Test
    public void setArticles_onFailureThrowableIOException_callsViewShowNoInternetError() {
        presenter.onRefresh();
        verify(model).getArticles(articlesRequestCaptor.capture());

        articlesRequestCaptor.getValue().onFailure(throwable);

        verify(view).showNoInternetError();
    }

    @Test
    public void setArticles_onFailureThrowableIOException_callsViewSetRefreshingFalse() {
        presenter.onRefresh();
        verify(model).getArticles(articlesRequestCaptor.capture());

        articlesRequestCaptor.getValue().onFailure(throwable);

        verify(view).setRefreshingFalse();
    }

    @Test
    public void setArticles_onFailureThrowableIOException_callsViewHideProgress() {
        presenter.onRefresh();
        verify(model).getArticles(articlesRequestCaptor.capture());

        articlesRequestCaptor.getValue().onFailure(throwable);

        verify(view).hideProgress();
    }

    @Test
    public void setArticles_onFailureThrowableNotIOException_doesNotCallViewMethods() {
        presenter.onRefresh();
        Throwable throwable = mock(Exception.class);
        verify(model).getArticles(articlesRequestCaptor.capture());

        articlesRequestCaptor.getValue().onFailure(throwable);

        verifyZeroInteractions(view);
    }

    @Test
    public void setArticles_onFailureHasNoView_doesNotCallViewMethods() {
        presenter.onRefresh();
        verify(model).getArticles(articlesRequestCaptor.capture());
        presenter.dropView();

        articlesRequestCaptor.getValue().onFailure(throwable);

        verifyZeroInteractions(view);
    }

    @Test
    public void setArticles_hasNoView_doesNotCallView() {
        presenter.onRefresh();
        presenter.dropView();

        listener.onArticlesReceived(response);

        verifyZeroInteractions(view);
    }

    @Test
    public void onResume_hasViewSourceIsUpdated_callsModelSetArticles() throws Exception {
        when(model.isSourceUpdated()).thenReturn(SOURCE_UPDATED);

        presenter.onResume();

        verify(model).getArticles(articlesRequestCaptor.capture());
    }

    @Test
    public void onResume_hasViewSourceIsUpdated_callsViewShowProgress() throws Exception {
        when(model.isSourceUpdated()).thenReturn(SOURCE_UPDATED);

        presenter.onResume();

        verify(view).showProgress();
    }

    @Test
    public void onResume_hasViewSourceIsUpdated_callsModelResetIsSourceUpdatedFlag()
            throws Exception {
        when(model.isSourceUpdated()).thenReturn(SOURCE_UPDATED);

        presenter.onResume();

        verify(model).resetIsSourceUpdatedFlag();
    }

    @Test
    public void onResume_hasViewSourceIsNotUpdated_doesNotCallViewMethods()
            throws Exception {
        when(model.isSourceUpdated()).thenReturn(SOURCE_NOT_UPDATED);

        presenter.onResume();

        verifyZeroInteractions(view);
    }

    @Test
    public void onResume_hasNoView_doesNotCallViewOrModelMethods() {
        presenter.dropView();

        presenter.onResume();

        verifyZeroInteractions(view);
        verifyZeroInteractions(model);
    }
}