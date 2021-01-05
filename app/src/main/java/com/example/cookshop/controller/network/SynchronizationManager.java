package com.example.cookshop.controller.network;

import android.bluetooth.BluetoothDevice;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.util.UUID;

public class SynchronizationManager extends AsyncTask<String, String, String>
{

    private final String TAG = getClass().getSimpleName();

    private OnReceiveCallback onReceiveCallback;

    /**
     * It seems like i cant update the TextView in the {@link com.example.cookshop.view.SynchronizeActivity}
     * using a callback method -so i will pass it in the constructor ...hope for a better solution
     */
    private TextView updateTextView;

    private OnUpdateListener onUpdateListener;

    private NetworkConnection networkConnection;

    private BluetoothDevice mDevice;

    private final UUID MY_UUID_INSECURE =  UUID.fromString("bebde602-4ee1-11eb-ae93-0242ac130002");

    public SynchronizationManager(NetworkConnection networkConnection, BluetoothDevice device, OnUpdateListener onUpdateListener, TextView updateTextView)
    {
        this.networkConnection = networkConnection;
        this.mDevice = device;
        this.onUpdateListener = onUpdateListener;
        this.updateTextView = updateTextView;
    }


    @Override
    protected void onPreExecute()
    {
        //Starting as a Server, waiting for incoming connections
        networkConnection.startServer();


    }

    @Override
    protected void onPostExecute(String s)
    {
        super.onPostExecute(s);

    }

    @Override
    protected String doInBackground(String...values)
    {

        networkConnection.setOnReceiveListener(new OnReceiveCallback()
        {

            @Override
            public void onIncomingMessage(String Message)
            {
                onProgressUpdate(Message);
            }
        });


        if (!networkConnection.isConnected())
        {
            networkConnection.startClient(mDevice, MY_UUID_INSECURE);


            while (true)
            {
                if (networkConnection.isConnected())
                {
                    if (networkConnection.isServer())
                    {
                        networkConnection.write("I am the server");
                    }
                    else
                    {
                        networkConnection.write("I am the client");
                    }
                }
                else
                {
                    Log.d(TAG, "not connected");
                }
            }
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(String...values)
    {
        super.onProgressUpdate(values);

       updateTextView.setText(values[1]);

    }
}
