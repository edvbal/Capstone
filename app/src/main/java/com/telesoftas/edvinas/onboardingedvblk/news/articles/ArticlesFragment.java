package com.telesoftas.edvinas.onboardingedvblk.news.articles;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.telesoftas.edvinas.onboardingedvblk.R;
import com.telesoftas.edvinas.onboardingedvblk.base.BaseApplication;
import com.telesoftas.edvinas.onboardingedvblk.base.BaseMvvmFragment;
import com.telesoftas.edvinas.onboardingedvblk.news.details.ArticleDetailsActivity;
import com.telesoftas.edvinas.onboardingedvblk.utils.DateFormatter;
import com.telesoftas.edvinas.onboardingedvblk.utils.ImageManager;
import com.telesoftas.edvinas.onboardingedvblk.utils.network.NetworkCheckerTask;
import com.telesoftas.edvinas.onboardingedvblk.utils.network.listeners.NetworkStatusListener;
import com.telesoftas.edvinas.onboardingedvblk.utils.widget.ArticlesWidgetHandler;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class ArticlesFragment extends BaseMvvmFragment<ArticlesViewModel>
        implements NewsItemClickListener, NetworkStatusListener {
    @BindView(R.id.newsSwipeToRefresh)
    SwipeRefreshLayout newsSwipeToRefresh;
    @BindView(R.id.newsRecyclerView)
    RecyclerView newsRecyclerView;
    @BindView(R.id.newsProgressBar)
    ProgressBar progressBar;
    @BindView(R.id.newsErrorTextView)
    TextView newsEmptyView;
    private ArticlesAdapter adapter;
    @Inject
    ImageManager imageManager;
    @Inject
    DateFormatter dateFormatter;
    private ArticlesViewModel articlesViewModel;
    private boolean isRefresh = false;

    public static Fragment newInstance() {
        return new ArticlesFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_articles;
    }

    @Override
    protected Class<ArticlesViewModel> getViewModel() {
        return ArticlesViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, ArticlesViewModel articlesViewModel) {
        this.articlesViewModel = articlesViewModel;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpSwipeToRefresh();
        setUpRecycler();
        articlesViewModel.observeProgressState().observe(this, this::setProgressBar);
        articlesViewModel.observeRefreshingState().observe(this, newsSwipeToRefresh::setRefreshing);
        articlesViewModel.observeArticlesState().observe(this, this::populateView);
        articlesViewModel.observeEmptyState().observe(this, this::showEmptyView);
    }

    private void showEmptyView(Boolean isEmpty) {
        if (isEmpty) {
            newsEmptyView.setVisibility(View.VISIBLE);
        } else {
            newsEmptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        new NetworkCheckerTask(getContext(), this).execute();
    }

    private void setUpSwipeToRefresh() {
        newsSwipeToRefresh.setOnRefreshListener(() -> {
            isRefresh = true;
            new NetworkCheckerTask(getContext(), this).execute();
        });
    }


    public void setUpRecycler() {
        adapter = new ArticlesAdapter(imageManager, this, dateFormatter);
        newsRecyclerView.setAdapter(adapter);
    }

    public void populateView(List<Article> articles) {
        adapter.clearArticles();
        adapter.setArticles(articles);
        BaseApplication.getArticleHolder(getContext()).setArticles(articles);
        ArticlesWidgetHandler.startActionChangeArticlesList(getContext());
    }

    private void setProgressBar(boolean isProgress) {
        if (isProgress) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNewsItemClick(Article article) {
        startActivity(ArticleDetailsActivity.createIntent(getContext(), article));
    }

    @Override
    public void isNetworkConnected(boolean isConnected) {
        articlesViewModel.onLoad(isRefresh, isConnected);
        if (isRefresh) {
            newsSwipeToRefresh.setRefreshing(false);
        }
        isRefresh = false;
    }
}
