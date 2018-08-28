package com.telesoftas.edvinas.onboardingedvblk.utils.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class ArticlesWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext());
    }
}