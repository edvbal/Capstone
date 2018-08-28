package com.telesoftas.edvinas.onboardingedvblk.landing;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.telesoftas.edvinas.onboardingedvblk.news.articles.ArticlesActivity;
import com.telesoftas.edvinas.onboardingedvblk.tutorial.TutorialActivity;
import com.telesoftas.edvinas.onboardingedvblk.data.preferences.PreferencesConstants;
import com.telesoftas.edvinas.onboardingedvblk.data.preferences.PreferencesStorageManager;
import com.telesoftas.edvinas.onboardingedvblk.data.preferences.StorageManager;

public class SplashActivity extends AppCompatActivity {
    public static Intent createIntent(Context context) {
        return new Intent(context, SplashActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startMainScreen();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        StorageManager storageManager = new PreferencesStorageManager(preferences);
        handleTutorialState(storageManager);
    }

    private void handleTutorialState(StorageManager storageManager) {
        if (isTutorialSeen(storageManager)) {
            finish();
        } else {
            startTutorialScreen();
        }
    }

    private boolean isTutorialSeen(StorageManager storageManager) {
        return storageManager.getBoolean(PreferencesConstants.KEY_IS_TUTORIAL_SEEN, false);
    }

    public void startTutorialScreen() {
        startActivity(TutorialActivity.createIntent(this));
    }

    public void startMainScreen() {
        startActivity(ArticlesActivity.createIntent(this));
    }
}
