package com.sebastian.helmet;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class BluetoothConnectionService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
