
package com.example.cookshop.controller.network;

import android.bluetooth.BluetoothDevice;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.cookshop.items.Article;
import com.example.cookshop.model.listManagement.DataAccess;
import com.example.cookshop.view.SynchronizeActivity;

import java.util.ArrayList;
import java.util.UUID;

public class SynchronizationManager extends AsyncTask<String, String, String>
{

    private final String TAG = getClass().getSimpleName();

    private OnReceiveCallback onReceiveCallback;

    private final String ACKNOWLEDGED = "ACK";

    private final String FINISHED = "FIN";

    private OnUpdateListener onUpdateListener;

    private NetworkConnection networkConnection;

    private BluetoothDevice mDevice;

    private boolean sender;

    /**
     * There are two states, sender and receiver
     * i will increment that when the state changed
     * and use it as loop condition
     */
    private int stateChanged = 1;

    private final UUID MY_UUID_INSECURE =  UUID.fromString("bebde602-4ee1-11eb-ae93-0242ac130002");

    private Article receivedArticle;

    public SynchronizationManager(NetworkConnection networkConnection, BluetoothDevice device, OnUpdateListener onUpdateListener)
    {
        Log.d(TAG, "Initialize SyncManager");
        this.networkConnection = networkConnection;
        this.mDevice = device;
        this.onUpdateListener = onUpdateListener;
        Log.d(TAG, "Starting NetworkConnection as server");
        networkConnection.startServer();
        networkConnection.setOnReceiveListener(new OnReceiveCallback()
        {
            @Override
            public void onIncomingMessage(String Message)
            {
                //Noting to do here - just avoiding null pointer
            }
        });
    }


    @Override
    protected void onPreExecute()
    {
        Log.d(TAG, "onPreExecute:");
        //Starting as a Server, waiting for incoming connections

    }

    @Override
    protected void onPostExecute(String s)
    {
        Log.d(TAG, "onPostExecute:");
        super.onPostExecute(s);

    }

    @Override
    protected String doInBackground(String...values)
    {

        Log.d(TAG, "doingInBackground :  checking if connected ");
        if (!networkConnection.isConnected())
        {
            Log.d(TAG, "doingInBackground :  Was not  connected starting as client");
            networkConnection.startClient(mDevice, MY_UUID_INSECURE);
        }

        Log.d(TAG, "doingInBackground :  Starting Sync loop ...seeing you on the other side");
            while (stateChanged<3)
            {
                Log.d(TAG, "doingInBackground :  state  = " + stateChanged);

                if (networkConnection.isConnected())
                {
                    Log.d(TAG, "doingInBackground :  Both devices should now be connected...starting sync");
                    if(stateChanged == 1)
                    {
                        // Only on first state
                        this.sender = networkConnection.isServer();
                    }
                    Log.d(TAG, "doingInBackground :  sender  = " + sender  );

                    if(sender)
                    {
                        sendArticles();
                    }
                    else
                    {
                        receivedArticles();
                    }
                }
                else
                {
                    Log.d(TAG, "not connected");
                }
            }
            networkConnection.closeConnection();

       return null;
    }




    private void receivedArticles()
    {
        this.networkConnection.setOnReceiveListener(new OnReceiveCallback()
        {
            @Override
            public   void onIncomingMessage(String Message)
            {
                Log.d(TAG, "Received message = " + Message);
                if(Message.equals(FINISHED))
                {
                    Log.d(TAG, "Sender finished transmission, going to send mode");
                    sender = !sender;
                    stateChanged++;
                    synchronized (SynchronizationManager.this)
                    {
                        SynchronizationManager.this.notify();
                    }
                }
                else
                {
                    Article newArticle = new Article();
                    newArticle.setObjectFromMementoPattern(Message);
                    DataAccess.getInstance().addArticleToshopingList(newArticle);
                    networkConnection.write(ACKNOWLEDGED);
                }
            }
        });

        try
        {
            Log.d(TAG, "set up listener, waiting for transmission to finish");
            synchronized(this)
            {
                this.wait();
            }
            Log.d(TAG, "received FINISH, going on");
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }







    private void sendArticles()
    {

        //RECEIVE

        this.networkConnection.setOnReceiveListener(new OnReceiveCallback()
        {
            @Override
            public void onIncomingMessage(String Message)
            {
                if(Message.equals(ACKNOWLEDGED))
                {
                    Log.d(TAG, "sendArticles: onReceive: Article acknowledged");
                    synchronized (SynchronizationManager.this)
                    {
                        SynchronizationManager.this.notify();
                    }
                }
            }
        });


        //SEND

        ArrayList<Article> shoppingList = DataAccess.getInstance().getBuyingList();
        for (Article a: shoppingList)
        {
            networkConnection.write(a.getMementoPattern());
            Log.d(TAG, "sendArticles: sending: Article send");
            try
            {
                Log.d(TAG, "sendArticles: sending: waiting for ACK");
                synchronized(this)
                {
                    this.wait();
                }
                Log.d(TAG, "sendArticles: sending: ACK received, sending next article");
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        Log.d(TAG, "sendArticles: sending: finished Article Transfer");
        networkConnection.write(FINISHED);
        sender = !sender;
        stateChanged++;

    }
}
