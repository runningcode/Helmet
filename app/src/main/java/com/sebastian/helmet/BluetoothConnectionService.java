package com.sebastian.helmet;

import android.app.Service;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class BluetoothConnectionService extends Service {

    private static final String TAG = "BluetoothConnectionService";

    private BluetoothSocket bluetoothSocket;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
        bluetoothSocket = Utils.connect();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        if (intent.getExtras() != null) {
            Boolean right = (Boolean) intent.getExtras().get("right");
            Log.i(TAG, "right is " + right);
        }
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Utils.closeBluetoothConnection(bluetoothSocket);
    }
}
