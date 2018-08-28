package com.telesoftas.edvinas.onboardingedvblk.utils.widget;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;
import com.telesoftas.edvinas.onboardingedvblk.R;
import com.telesoftas.edvinas.onboardingedvblk.base.BaseApplication;
import com.telesoftas.edvinas.onboardingedvblk.news.articles.Article;
import com.telesoftas.edvinas.onboardingedvblk.news.details.ArticleDetailsActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import dagger.Component;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    private List<Article> articles = new ArrayList<>();

    public ListRemoteViewsFactory(Context applicationContext) {
        context = applicationContext;
    }

    @Override
    public void onCreate() {
        // empty
    }

    @Override
    public void onDataSetChanged() {
        articles = BaseApplication.getArticleHolder(context).getArticles();
    }

    @Override
    public void onDestroy() {
        // empty
    }


    @Override
    public int getCount() {
        if (articles != null) {
            return articles.size();
        } else {
            return 0;
        }
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews row = new RemoteViews(context.getPackageName(), R.layout.widget_list_item);
        Article article = articles.get(position);
        row.setTextViewText(R.id.newsItemTitleText, article.getTitle());
        row.setTextViewText(R.id.newsItemDateText, article.getDate());
        loadImage(row, article);
        Intent fillInIntent = new Intent(Intent.ACTION_MAIN);
        fillInIntent.putExtra(ArticleDetailsActivity.KEY_ARTICLE, article);
        row.setOnClickFillInIntent(R.id.parentLayout, fillInIntent);
        return row;
    }

    private void loadImage(RemoteViews row, Article article) {
        try {
            row.setImageViewBitmap(R.id.widgetNewsItemImage, getBitmap(article));
        } catch (IOException exception) {
            Bitmap errorBitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.news_error_loading_image);
            row.setImageViewBitmap(R.id.widgetNewsItemImage, errorBitmap);
            Timber.e(exception);
        }
    }

    private Bitmap getBitmap(Article article) throws IOException {
        return Picasso.get()
                .load(article.getUrlToImage())
                .get();
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}