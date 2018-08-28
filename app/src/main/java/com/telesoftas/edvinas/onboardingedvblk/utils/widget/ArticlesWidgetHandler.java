package com.telesoftas.edvinas.onboardingedvblk.utils.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.telesoftas.edvinas.onboardingedvblk.R;

public class ArticlesWidgetHandler extends IntentService {
    public static final String ACTION_UPDATE_ARTICLES_WIDGET =
            "com.telesoftas.edvinas.onboardingedvblk.utils.widget.action.update_articles_widget";

    public ArticlesWidgetHandler() {
        super("ArticlesWidgetHandler");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_ARTICLES_WIDGET.equals(action)) {
                handleActionUpdateArticlesWidget();
            }
        }
    }

    public static void startActionChangeArticlesList(Context context) {
        Intent intent = new Intent(context, ArticlesWidgetHandler.class);
        intent.setAction(ACTION_UPDATE_ARTICLES_WIDGET);
        context.startService(intent);
    }

    private void handleActionUpdateArticlesWidget() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        ComponentName name = new ComponentName(this, ArticlesWidgetProvider.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(name);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.listView);
        ArticlesWidgetProvider.updateArticlesWidgets(this, appWidgetManager, appWidgetIds);
    }
}
