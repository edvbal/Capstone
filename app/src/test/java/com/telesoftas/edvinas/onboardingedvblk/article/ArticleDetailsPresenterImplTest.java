package com.telesoftas.edvinas.onboardingedvblk.article;

import com.telesoftas.edvinas.onboardingedvblk.article.details.ArticlePresenterImpl;
import com.telesoftas.edvinas.onboardingedvblk.article.details.ArticleView;
import com.telesoftas.edvinas.onboardingedvblk.news.articles.Article;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ArticleDetailsPresenterImplTest {
    private static final String DEFAULT_IMAGE_URL = "defaultUrl";
    private static final String DEFAULT_URL = "defaultUrl";
    private static final String DEFAULT_DATE = "defaultDate";
    private static final String DEFAULT_DESCRIPTION = "defaultDescription";
    private static final String DEFAULT_TITLE = "defaultTitle";
    private Article article;
    private ArticleView view;
    private ArticlePresenterImpl presenter;

    @Before
    public void setUp() throws Exception {
        view = mock(ArticleView.class);
        presenter = new ArticlePresenterImpl();
        presenter.takeView(view);
        article = new Article(DEFAULT_IMAGE_URL, DEFAULT_TITLE,
                DEFAULT_DESCRIPTION, DEFAULT_DATE, DEFAULT_URL);
    }

    @Test
    public void setImage_presenterHasNoView_doesNotCallViewMethods() {
        presenter.dropView();

        presenter.setImage(article);

        verifyZeroInteractions(view);
    }

    @Test
    public void setImage_articleIsNotNull_callsViewSetImage() {
        presenter.setImage(article);

        verify(view).setImage(article.getUrlToImage());
    }

    @Test
    public void setImage_articleIsNull_doesNotCallViewMethods() {
        presenter.setImage(null);

        verifyZeroInteractions(view);
    }
}