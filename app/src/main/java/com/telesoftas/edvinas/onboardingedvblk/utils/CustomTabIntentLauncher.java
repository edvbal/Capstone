package com.telesoftas.edvinas.onboardingedvblk.utils;

import android.content.Context;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;

import com.telesoftas.edvinas.onboardingedvblk.R;

public class CustomTabIntentLauncher implements IntentLauncher {
    @Override
    public void launch(Context context, Uri uri) {
        new CustomTabsIntent.Builder()
                .setToolbarColor(
                        ContextCompat.getColor(context, R.color.colorPrimary))
                .setSecondaryToolbarColor(
                        ContextCompat.getColor(context, R.color.colorPrimaryDark))
                .setStartAnimations(
                        context, R.anim.slide_in_right, R.anim.slide_out_left)
                .setExitAnimations(
                        context, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .build()
                .launchUrl(context, uri);
    }
}
