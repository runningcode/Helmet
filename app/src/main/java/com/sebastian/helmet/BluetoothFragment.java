package com.sebastian.helmet;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class BluetoothFragment extends NavigationSectionFragment {

    private BluetoothSocket btSocket = null;
    private InputStream inputStream = null;
    private Handler handler = new Handler();
    private TextView textView;
    private boolean run = false;
    private boolean connected = false;

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button pulse;
    private Button sawtooth;
    private Button turn;
    private Button sin;
    private Button extra;
    private Button extra2;
    private SeekBar seekBar;


    private static final String TAG = "BluetoothFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_test, container, false);
        textView = (TextView) rootView.findViewById(R.id.text);
        setHasOptionsMenu(true);

        button1 = (Button) rootView.findViewById(R.id.button1);
        button2 = (Button) rootView.findViewById(R.id.button2);
        button3 = (Button) rootView.findViewById(R.id.button3);
        button4 = (Button) rootView.findViewById(R.id.button4);
        seekBar = (SeekBar) rootView.findViewById(R.id.seek);
        pulse = (Button) rootView.findViewById(R.id.pulse);
        sawtooth = (Button) rootView.findViewById(R.id.sawtooth);
        turn = (Button) rootView.findViewById(R.id.turnsignal);
        sin = (Button) rootView.findViewById(R.id.sin);
        extra = (Button) rootView.findViewById(R.id.stuff);
        extra2 = (Button) rootView.findViewById(R.id.stuff2);


        button1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        checkConnection("L");
                        return true;
                    case MotionEvent.ACTION_UP:
                        checkConnection("q");
                        return true;
                }
                return false;
            }
        });
        button2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        checkConnection("R");
                        return true;
                    case MotionEvent.ACTION_UP:
                        checkConnection("q");
                        return true;
                }
                return false;
            }
        });
        button3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                 switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        checkConnection("B");
                        return true;
                    case MotionEvent.ACTION_UP:
                        checkConnection("q");
                        return true;
                }
                return false;
            }
        });
        button4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                 switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        checkConnection("C");
                        return true;
                    case MotionEvent.ACTION_UP:
                        checkConnection("q");
                        return true;
                 }
                 return false;
            }
        });

        pulse.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                checkConnection("p");
            }
        });
        sawtooth.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                checkConnection("T");
            }
        });
        turn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                checkConnection("t");
            }
        });
        sin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                checkConnection("s");
            }
        });
        extra.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                checkConnection("f");
            }
        });
        extra2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                checkConnection("d");
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            private int progress;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                this.progress = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (btSocket != null) {
                    Utils.writeData((byte) (progress * 2.55), btSocket);
                }
                Toast.makeText(getActivity(), "Value is " + progress, Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }

    private void checkConnection(String data) {
        if (!Utils.writeData(data, btSocket)) {
            connected = false;
            enableButtons(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //beginListenForData();
    }

    @Override
    public void onStop() {
        super.onStop();
        run = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        run = false;
        Utils.closeBluetoothConnection(btSocket);
        //TODO: Double check that this works!
        enableButtons(connected = false);
    }

    public void beginListenForData()   {
        if (btSocket == null || !btSocket.isConnected()) {
            return;
        }
        run = true;
        try {
            inputStream = btSocket.getInputStream();
            Thread workerThread = new Thread(new Runnable() {
                public void run() {
                    while(!Thread.currentThread().isInterrupted() && run) {
                        try {
                            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
                            final String line = rd.readLine();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    textView.append(line);
                                }
                            });
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                            run = false;
                            enableButtons(connected = false);
                        } catch (IOException e) {
                            e.printStackTrace();
                            run = false;
                            enableButtons(connected = false);
                        }
                    }
                }
            });
            workerThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void enableButtons(boolean enable) {
        button1.setEnabled(enable);
        button2.setEnabled(enable);
        button3.setEnabled(enable);
        button4.setEnabled(enable);
        pulse.setEnabled(enable);
        turn.setEnabled(enable);
        sawtooth.setEnabled(enable);
        sin.setEnabled(enable);
        extra.setEnabled(enable);
        extra2.setEnabled(enable);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.blue_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.blue_connect:
                if (!connected) {
                    new AsyncTask<Void, Void, BluetoothSocket>() {
                        ProgressDialog progressDialog;

                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            progressDialog = ProgressDialog.show(getActivity(), "", "Connecting...");

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
                                //beginListenForData();
                            } else {
                                connected = false;
                                run = false;
                            }
                            enableButtons(connected);
                            progressDialog.cancel();
                        }
                    }.execute();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
