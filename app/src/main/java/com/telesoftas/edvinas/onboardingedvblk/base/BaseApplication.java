package com.telesoftas.edvinas.onboardingedvblk.base;

import android.content.Context;
import android.preference.PreferenceManager;

import com.telesoftas.edvinas.onboardingedvblk.utils.ImageManager;
import com.telesoftas.edvinas.onboardingedvblk.utils.dagger.DaggerAppComponent;
import com.telesoftas.edvinas.onboardingedvblk.utils.network.NetworkManager;
import com.telesoftas.edvinas.onboardingedvblk.data.preferences.PreferencesStorageManager;
import com.telesoftas.edvinas.onboardingedvblk.data.preferences.StorageManager;
import com.telesoftas.edvinas.onboardingedvblk.utils.widget.ArticleHolder;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public final class BaseApplication extends DaggerApplication {
    private StorageManager storageManager;
    private NetworkManager networkManager;
    private ImageManager imageManager;
    private ArticleHolder articleHolder;

    @Override
    public void onCreate() {
        super.onCreate();
//        Fabric.with(this, new Crashlytics());
        initStorageManager();
        initNetworkManager();
        initImageManager();
        articleHolder = new ArticleHolder();
    }


    private ArticleHolder getArticleHolder() {
        return articleHolder;
    }

    public static ArticleHolder getArticleHolder(Context context) {
        return getBaseApplication(context).getArticleHolder();
    }

    private static BaseApplication getBaseApplication(Context context) {
        return ((BaseApplication) context.getApplicationContext());
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }

    private void initStorageManager() {
        storageManager = new PreferencesStorageManager(
                PreferenceManager.getDefaultSharedPreferences(this));
    }

    public static StorageManager getStorageManager(Context context) {
        return ((BaseApplication) context.getApplicationContext()).getStorageManager();
    }

    private StorageManager getStorageManager() {
        return storageManager;
    }

    private void initNetworkManager() {
        networkManager = new NetworkManager();
    }

    public static NetworkManager getNetworkManager(Context context) {
        return ((BaseApplication) context.getApplicationContext()).getNetworkManager();
    }

    private NetworkManager getNetworkManager() {
        return networkManager;
    }

    private void initImageManager() {
        imageManager = new ImageManager();
    }

    public static ImageManager getImageManager(Context context) {
        return ((BaseApplication) context.getApplicationContext()).getImageManager();
    }

    private ImageManager getImageManager() {
        return imageManager;
    }
}
