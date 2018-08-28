package com.telesoftas.edvinas.onboardingedvblk.utils.dagger.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import com.telesoftas.edvinas.onboardingedvblk.base.BaseApplication;
import com.telesoftas.edvinas.onboardingedvblk.utils.DateFormatter;
import com.telesoftas.edvinas.onboardingedvblk.utils.ImageManager;
import com.telesoftas.edvinas.onboardingedvblk.data.preferences.PreferencesStorageManager;
import com.telesoftas.edvinas.onboardingedvblk.data.preferences.StorageManager;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class AppModule {
    @Binds
    public abstract Context bindApplicationContext(BaseApplication application);

    @Provides
    public static Resources provideResources(Context context) {
        return context.getResources();
    }

    @Provides
    public static DateFormatter provideDateFormatter() {
        return new DateFormatter();
    }

    @Provides
    public static ImageManager provideImageManager() {
        return new ImageManager();
    }

    @Provides
    public static StorageManager provideStorageManager(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return new PreferencesStorageManager(preferences);
    }
}
