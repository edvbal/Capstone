package com.telesoftas.edvinas.onboardingedvblk.utils.firebase;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.telesoftas.edvinas.onboardingedvblk.utils.popup.NotificationProvider;

public class NotificationService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        NotificationProvider provider = new NotificationProvider(this);
        if (remoteMessage.getNotification() != null) {
            provider.sendNotification(remoteMessage.getNotification().getBody());
        }
    }
}
