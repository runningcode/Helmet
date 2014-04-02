package com.sebastian.helmet;

import android.app.Notification;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

public class NotificationListener extends NotificationListenerService {

    private static final String TAG = "NotificationListener";

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        if (sbn.getPackageName().equals("com.google.android.apps.maps")) {
            Notification notification = sbn.getNotification();
            String text = (String) notification.extras.get(Notification.EXTRA_TEXT);
            Log.i(TAG, text);
        }

    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {

    }
}
