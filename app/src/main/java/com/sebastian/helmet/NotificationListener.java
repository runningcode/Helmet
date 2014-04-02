package com.sebastian.helmet;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

public class NotificationListener extends NotificationListenerService {

    private static final String TAG = "NotificationListener";

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        if (sbn.getPackageName().equals("com.google.android.apps.maps")) {
            Bundle extras = sbn.getNotification().extras;
            String text;
            if (extras.get(Notification.EXTRA_TEXT) instanceof String) {
                text = (String) extras.get(Notification.EXTRA_TEXT);
            } else {
                text = extras.get(Notification.EXTRA_TEXT).toString();
            }
            Log.i(TAG, text);
            Intent intent = new Intent(this, BluetoothConnectionService.class);
            if (text.contains("right")) {
                intent.putExtra("right", true);
            } else if (text.contains("left")) {
                intent.putExtra("right", false);
            }
            startService(intent);
        }

    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        if (sbn.getPackageName().equals("com.google.android.apps.maps")) {
            Intent intent = new Intent(this, BluetoothConnectionService.class);
            stopService(intent);
        }
    }
}
