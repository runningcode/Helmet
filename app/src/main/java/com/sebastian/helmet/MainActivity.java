package com.sebastian.helmet;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private static final String HOME_TAG = "homeFrag";
    private static final String TEST_TAG = "testFrag";
    private static final String SCENARIOS_TAG = "scenariosFrag";
    private static final String SYSTEM_TAG = "systemFrag";
    private static final String SETUP_TAG = "setupFrag";

    private NavigationDrawerFragment mNavigationDrawerFragment;

    private CharSequence mTitle;
    private boolean connected;
    private BluetoothSocket btSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);

        mTitle = getTitle();
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();

        switch (position){
            case 0:
                Fragment homeFragment = fragmentManager.findFragmentByTag(HOME_TAG);
                if (homeFragment == null) {
                    homeFragment = HomeFragment.newInstance(position + 1, new HomeFragment());
                }
                fragmentManager.beginTransaction()
                        .replace(R.id.container, homeFragment, HOME_TAG).addToBackStack(null)
                        .commit();
                break;
            case 1:
                Fragment testFragment = fragmentManager.findFragmentByTag(TEST_TAG);
                if (testFragment == null) {
                    testFragment = BluetoothFragment.newInstance(position + 1, new BluetoothFragment());
                }
                fragmentManager.beginTransaction()
                        .replace(R.id.container, testFragment, TEST_TAG).addToBackStack(null)
                        .commit();
                break;
            case 2:
                Fragment scenariosFragment = fragmentManager.findFragmentByTag(SCENARIOS_TAG);
                if (scenariosFragment == null) {
                    scenariosFragment = ScenarioFragment.newInstance(position + 1, new ScenarioFragment());
                }
                fragmentManager.beginTransaction()
                        .replace(R.id.container, scenariosFragment, SCENARIOS_TAG).addToBackStack(null)
                        .commit();
                break;
            case 3:
                Fragment systemFragment = new SettingsFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, systemFragment, SYSTEM_TAG).addToBackStack(null)
                        .commit();
                break;
            case 4:
                Fragment setupFragment = fragmentManager.findFragmentByTag(SETUP_TAG);
                if (setupFragment == null) {
                    setupFragment = SetupFragment.newInstance(position + 1, new SetupFragment());
                }
                fragmentManager.beginTransaction()
                        .replace(R.id.container, setupFragment, SETUP_TAG).addToBackStack(null)
                        .commit();
                break;
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
            case 5:
                mTitle = getString(R.string.title_section5);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.blue_menu, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            /*case R.id.blue_connect:
                if (!connected) {
                    new AsyncTask<Void, Void, BluetoothSocket>() {
                        ProgressDialog progressDialog;

                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            progressDialog = ProgressDialog.show(MainActivity.this, "", "Connecting...");

                        }

                        @Override
                        protected BluetoothSocket doInBackground(Void... params) {
                            return Utils.connect();
                        }

                        @Override
                        protected void onPostExecute(BluetoothSocket bluetoothSocket) {
                            btSocket = bluetoothSocket;
                            if (btSocket != null && btSocket.isConnected()) {
                                connected = true;
                            } else {
                                connected = false;
                            }
                            progressDialog.cancel();
                        }
                    }.execute();
                }
                return true;*/
        }
        return super.onOptionsItemSelected(item);
    }
}
