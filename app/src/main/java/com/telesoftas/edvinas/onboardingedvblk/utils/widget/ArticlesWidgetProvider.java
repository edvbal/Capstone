package com.telesoftas.edvinas.onboardingedvblk.utils.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.telesoftas.edvinas.onboardingedvblk.R;
import com.telesoftas.edvinas.onboardingedvblk.base.BaseApplication;
import com.telesoftas.edvinas.onboardingedvblk.news.articles.Article;
import com.telesoftas.edvinas.onboardingedvblk.news.details.ArticleDetailsActivity;

import java.util.List;

public class ArticlesWidgetProvider extends AppWidgetProvider {
    public static void updateRecipeWidget(
            Context context,
            AppWidgetManager appWidgetManager,
            int appWidgetId
    ) {
        List<Article> articles = BaseApplication.getArticleHolder(context).getArticles();
        if (articles != null) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.articles_widget);
            Intent intent = new Intent(context, ArticlesWidgetService.class);
            views.setRemoteAdapter(R.id.listView, intent);
            Intent articleDetailsIntent = new Intent(context, ArticleDetailsActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    context, 0, articleDetailsIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setPendingIntentTemplate(R.id.listView, pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        ArticlesWidgetHandler.startActionChangeArticlesList(context);
    }

    public static void updateArticlesWidgets(
            Context context,
            AppWidgetManager appWidgetManager,
            int[] recipeWidgetIds
    ) {
        for (int appWidgetId : recipeWidgetIds) {
            updateRecipeWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Perform any action when an AppWidget for this provider is instantiated
    }

    @Override
    public void onDisabled(Context context) {
        // Perform any action when the last AppWidget instance for this provider is deleted
    }
}

