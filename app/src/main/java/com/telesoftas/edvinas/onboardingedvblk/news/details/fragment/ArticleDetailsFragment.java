package com.telesoftas.edvinas.onboardingedvblk.news.details.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.telesoftas.edvinas.onboardingedvblk.R;
import com.telesoftas.edvinas.onboardingedvblk.base.BaseDaggerFragment;
import com.telesoftas.edvinas.onboardingedvblk.news.articles.Article;
import com.telesoftas.edvinas.onboardingedvblk.utils.CustomTabIntentLauncher;
import com.telesoftas.edvinas.onboardingedvblk.utils.IntentLauncher;
import com.telesoftas.edvinas.onboardingedvblk.utils.popup.SnackbarProvider;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

public class ArticleDetailsFragment extends BaseDaggerFragment{
    @BindView(R.id.articleTitleTextView)
    TextView articleTitleTextView;
    @BindView(R.id.articleDescriptionTextView)
    TextView articleDescriptionTextView;
    @BindView(R.id.articleDateTextView)
    TextView articleDateTextView;
    private static final String KEY_ARTICLE = "key.article";
    private Article article;
    private SnackbarProvider snackbarProvider;
    private IntentLauncher intentLauncher;

    public static ArticleDetailsFragment newInstance(Article article) {
        ArticleDetailsFragment fragment = new ArticleDetailsFragment();
        Bundle articleBundle = new Bundle();
        articleBundle.putParcelable(KEY_ARTICLE, article);
        fragment.setArguments(articleBundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        article = getArguments().getParcelable(KEY_ARTICLE);
        intentLauncher = new CustomTabIntentLauncher();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_details;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTexts();
        snackbarProvider = new SnackbarProvider(view);
    }

    @OnClick(R.id.articleGoToWebButton)
    void onArticleButtonClick() {
        try {
            intentLauncher.launch(getActivity(), Uri.parse(article.getUrl()));
        } catch (NullPointerException exception) {
            Timber.e(exception);
            snackbarProvider.showErrorSnackbar(getString(R.string.error_opening_article));
        }
    }

    public void setTexts() {
        articleTitleTextView.setText(article.getTitle());
        articleDescriptionTextView.setText(article.getDescription());
        articleDateTextView.setText(article.getDate());
    }
}
