package com.telesoftas.edvinas.onboardingedvblk.main.news;

import com.telesoftas.edvinas.onboardingedvblk.article.news.NewsModelImpl;
import com.telesoftas.edvinas.onboardingedvblk.utils.network.ArticlesService;
import com.telesoftas.edvinas.onboardingedvblk.utils.network.listeners.ArticlesRequestListener;
import com.telesoftas.edvinas.onboardingedvblk.utils.network.response.ArticlesResponse;
import com.telesoftas.edvinas.onboardingedvblk.data.preferences.StorageManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.telesoftas.edvinas.onboardingedvblk.article.news.NewsModelImpl.DEFAULT_SOURCE;
import static com.telesoftas.edvinas.onboardingedvblk.data.preferences.PreferencesConstants.KEY_IS_SOURCE_UPDATED;
import static com.telesoftas.edvinas.onboardingedvblk.data.preferences.PreferencesConstants.KEY_SOURCE_ID;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ArticlesModelImplTest {
    public static final String DEFAULT_ID = "defaultId";
    public static final String DEFAULT_VALUE = "defaultValue";
    public static final String NEW_SOURCE = "newSource";
    public static final boolean DEFAULT_IS_SOURCE_UPDATED = true;
    private NewsModelImpl newsModel;
    private ArticlesRequestListener listener;
    private ArticlesService service;
    private Call<ArticlesResponse> call;
    private ArgumentCaptor<Callback<ArticlesResponse>> captor;
    private ArticlesResponse response;
    private StorageManager storageManager;

    @Before
    public void setUp() throws Exception {
        listener = mock(ArticlesRequestListener.class);
        service = mock(ArticlesService.class);
        storageManager = mock(StorageManager.class);
        newsModel = new NewsModelImpl(service, storageManager);
        call = mock(Call.class);
        captor = ArgumentCaptor.forClass(Callback.class);
        when(storageManager.getString(KEY_SOURCE_ID, DEFAULT_SOURCE)).thenReturn(DEFAULT_VALUE);
        when(service.getArticle(DEFAULT_VALUE)).thenReturn(call);
        doNothing().when(call).enqueue(captor.capture());
    }

    @Test
    public void getArticles_callsArticlesServiceGetArticle() {
        newsModel.getArticles(listener);

        verify(service).getArticle(DEFAULT_VALUE);
    }

    @Test
    public void getArticles_onSuccessfulResponse_callsListenerOnArticlesReceived() {
        newsModel.getArticles(listener);

        captor.getValue().onResponse(call, Response.success(response));

        verify(listener).onArticlesReceived(response);
    }

    @Test
    public void getArticles_onFailure_callsListenerOnFailure() {
        when(storageManager.getString(DEFAULT_ID, DEFAULT_VALUE)).thenReturn(DEFAULT_VALUE);
        Throwable throwable = mock(Throwable.class);
        newsModel.getArticles(listener);

        captor.getValue().onFailure(call, throwable);

        verify(listener).onFailure(throwable);
    }

    @Test
    public void getSavedSource_returnsSourceFromStorageManager() {
        when(storageManager.getString(KEY_SOURCE_ID, DEFAULT_SOURCE)).thenReturn(NEW_SOURCE);

        String actual = newsModel.getSavedSource();

        Assert.assertEquals(NEW_SOURCE, actual);
    }


    @Test
    public void isSourceUpdated_returnsIsSourceUpdatedFromStorageManager() {
        when(storageManager.getBoolean(KEY_IS_SOURCE_UPDATED, DEFAULT_IS_SOURCE_UPDATED))
                .thenReturn(false);

        boolean actual = newsModel.isSourceUpdated();

        Assert.assertFalse(actual);
    }
}