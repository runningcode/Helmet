package com.sebastian.helmet;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

public class Utils {

    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String MAC = "00:14:01:10:27:57";
    //private static final String MAC = "00:14:01:14:33:82";
    private static final String TAG = "Utils";

    public static BluetoothSocket connect() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            Log.i(TAG, "Bluetooth adapter was null or disabled");
            return null;
        }
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(MAC);
        BluetoothSocket bluetoothSocket = null;
        Log.d("", "Connecting to ... " + device);
        bluetoothAdapter.cancelDiscovery();
        try {
            bluetoothSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
            bluetoothSocket.connect();
            Log.d("", "Connection made.");
        } catch (IOException e) {
            closeBluetoothConnection(bluetoothSocket);
            e.printStackTrace();
        }
        return bluetoothSocket;
    }

    public static void closeBluetoothConnection(BluetoothSocket btSocket) {
        try {
            if (btSocket!= null)
                btSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeData(String data, BluetoothSocket btSocket) {
        try {
            btSocket.getOutputStream().write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
