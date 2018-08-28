package com.telesoftas.edvinas.onboardingedvblk.article.fragment;

import com.telesoftas.edvinas.onboardingedvblk.article.details.fragment.ArticleDetailsPresenterImpl;
import com.telesoftas.edvinas.onboardingedvblk.article.details.fragment.ArticleDetailsView;
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
    private ArticleDetailsView view;
    private ArticleDetailsPresenterImpl presenter;

    @Before
    public void setUp() throws Exception {
        view = mock(ArticleDetailsView.class);
        presenter = new ArticleDetailsPresenterImpl();
        presenter.takeView(view);
        article = new Article(DEFAULT_IMAGE_URL, DEFAULT_TITLE,
                DEFAULT_DESCRIPTION, DEFAULT_DATE, DEFAULT_URL);
    }

    @Test
    public void onViewCreated_hasView_callsViewSetTexts() {
        presenter.onViewCreated(article);

        verify(view).setTexts(article);
    }

    @Test
    public void onViewCreated_hasNoView_doesNotCallViewMethods() {
        presenter.dropView();

        presenter.onViewCreated(article);

        verifyZeroInteractions(view);
    }

    @Test
    public void onArticleButtonClick_hasView_callsViewOpenBrowser() {
        presenter.onArticleButtonClick(DEFAULT_URL);

        view.openBrowser(DEFAULT_URL);
    }

    @Test
    public void onArticleButtonClick_hasNoView_doesNotCallViewMethods() {
        presenter.dropView();

        presenter.onArticleButtonClick(DEFAULT_URL);

        verifyZeroInteractions(view);
    }
}