package com.telesoftas.edvinas.onboardingedvblk.tutorial;

import android.content.Context;
import android.preference.PreferenceManager;

import com.telesoftas.edvinas.onboardingedvblk.data.preferences.PreferencesStorageManager;
import com.telesoftas.edvinas.onboardingedvblk.data.preferences.StorageManager;

class TutorialPresenterFactory {
    private final Context context;

    public TutorialPresenterFactory(Context context) {
        this.context = context.getApplicationContext();
    }

    public TutorialPresenter create() {
        StorageManager storageManager = new PreferencesStorageManager(
                PreferenceManager.getDefaultSharedPreferences(context));
        TutorialModel model = new TutorialModelImpl(storageManager);
        return new TutorialPresenterImpl(model);
    }
}
