package com.telesoftas.edvinas.onboardingedvblk.utils.firebase;

import com.google.firebase.messaging.FirebaseMessaging;

public class NotificationSubscriber {
    public void subscribeNotifications(String topic) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic);
    }

    public void unsubscribeNotifications(String topic) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic);
    }
}