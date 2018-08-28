package com.telesoftas.edvinas.onboardingedvblk.news.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.telesoftas.edvinas.onboardingedvblk.R;
import com.telesoftas.edvinas.onboardingedvblk.base.BaseDaggerActivity;
import com.telesoftas.edvinas.onboardingedvblk.news.details.fragment.ArticleDetailsFragment;
import com.telesoftas.edvinas.onboardingedvblk.news.articles.Article;
import com.telesoftas.edvinas.onboardingedvblk.utils.ImageManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;

public class ArticleDetailsActivity extends BaseDaggerActivity {
    public static final String KEY_ARTICLE = "key.article";
    private static final int TRANSPARENT_COLOR_ID = R.color.transparentWhite;
    @BindView(R.id.articleToolbar)
    Toolbar articleToolbar;
    @BindView(R.id.articleCollapsingToolbar)
    CollapsingToolbarLayout articleCollapsingToolbar;
    @BindView(R.id.articleImage)
    ImageView articleImage;
    @BindView(R.id.articleAppBar)
    AppBarLayout articleAppBar;
    @Inject
    ImageManager imageManager;
    private Article article;

    public static Intent createIntent(Context context, Article article) {
        Intent intent = new Intent(context, ArticleDetailsActivity.class);
        intent.putExtra(KEY_ARTICLE, article);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        article = getIntent().getExtras().getParcelable(KEY_ARTICLE);
        setArticleFragment(savedInstanceState);
        setSupportActionBar(articleToolbar);
        setCollapsingToolbar();
        imageManager.loadImageFromUrl(articleImage, article.getUrlToImage());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_details;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return true;
    }

    private void setArticleFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.articleFragmentContainer, ArticleDetailsFragment.newInstance(article))
                    .commit();
        }
    }

    private void setCollapsingToolbar() {
        int titleColor = ContextCompat.getColor(this, TRANSPARENT_COLOR_ID);
        String titleText = getResources().getString(R.string.app_name);
        articleCollapsingToolbar.setTitle(titleText);
        articleCollapsingToolbar.setExpandedTitleColor(titleColor);
    }
}
