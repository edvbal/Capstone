package com.telesoftas.edvinas.onboardingedvblk.utils.popup;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.telesoftas.edvinas.onboardingedvblk.R;
import com.telesoftas.edvinas.onboardingedvblk.landing.SplashActivity;

public class NotificationProvider {
    private static final int REQUEST_CODE = 0;
    private static final int DEFAULT_NOTIFICATION_ID = 0;
    private final Context context;

    public NotificationProvider(Context context) {
        this.context = context;
    }

    public void sendNotification(String messageBody) {
        Intent intent = getIntent();
        PendingIntent pendingIntent = PendingIntent.getActivity(context, REQUEST_CODE, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification notification = getNotification(messageBody, pendingIntent, defaultSoundUri);
        NotificationManager notificationManager = getNotificationManager();
        notificationManager.notify(DEFAULT_NOTIFICATION_ID, notification);
    }

    @NonNull
    private Intent getIntent() {
        Intent intent = SplashActivity.createIntent(context);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    private Notification getNotification(
            String messageBody,
            PendingIntent pendingIntent,
            Uri defaultSoundUri
    ) {
        return new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(context.getString(R.string.notification_label))
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(context, R.color.colorAccent))
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .build();
    }

    private NotificationManager getNotificationManager() {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }
}
