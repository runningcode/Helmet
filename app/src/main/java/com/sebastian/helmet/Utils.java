package com.sebastian.helmet;

import android.bluetooth.BluetoothSocket;

import java.io.IOException;

public class Utils {

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
