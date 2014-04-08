package com.sebastian.helmet;

import android.bluetooth.BluetoothSocket;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.util.Log;

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = "SettingsFragment";
    private BluetoothSocket btSocket;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_setup);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("pref_bliss_strength") || key.equals("pref_turn_strength") || key.equals("pref_nav_strength")) {
            ListPreference pref = (ListPreference) findPreference(key);
            Log.i(TAG, "Entry is " + pref.getEntry());
            if (pref.getEntry().equals("weak")) {
                Utils.writeData((byte) 100, btSocket);
            } else if (pref.getEntry().equals("medium")) {
                Utils.writeData((byte) 200, btSocket);

            } else if (pref.getEntry().equals("strong")) {
                Utils.writeData((byte) 255, btSocket);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
