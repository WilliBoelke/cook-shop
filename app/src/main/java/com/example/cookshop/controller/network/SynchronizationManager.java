
package com.example.cookshop.controller.network;

import android.bluetooth.BluetoothDevice;

import android.os.AsyncTask;
import android.util.Log;

import com.example.cookshop.items.Article;
import com.example.cookshop.controller.applicationController.ApplicationController;

import java.util.ArrayList;
import java.util.UUID;

/**
 * This AsyncTasks uses a {@link BluetoothConnection} to synchronize the
 * Shopping list between two devices
 *
 * -------------How it works------------------
 * We have two devices A and B connected via bluetooth
 * Device A is the Bluetooth Server and B the client.. this we can get from the .isClient()
 * method of the {@link NetworkConnection}
 *
 * Depending on which it is it will start sending its articles {@link #sendArticles()}
 * A as Server will start.
 * After each Article it will wait for the client to send its acknowledgment {@link #ACKNOWLEDGED}
 * When all articles are transferred it fill send {@link #FINISHED} .
 *
 * The client (B) will then go into its {@link #synchronize()} method and there
 * compare the received articles with its local list.
 * It will compare them by name, should the any doubling names, compare their dateOfUpdate
 * and only save the newer one.
 *
 * The server A meanwhile is waiting for articles to be send to him.
 * When B has finished synchronizing the lists, it will start sending
 * it to A.
 * Again Article by Article and waiting for the ACK of A.
 *
 *When all articles are send, it again wills end FINISHED
 *
 * A then dont need to compare the lists again. It can replace its complete local list with
 * the received one.
 *
 * In the end, both, A and B have the same list, with the most recently updated articles.
 *
 *
 * @author WilliBoelke
 *
 */
public class SynchronizationManager extends AsyncTask<String, String, String>
{



    //------------Instance Variables------------

    /**
     * Log Tag
     */
    private final String TAG = getClass().getSimpleName();

    /**
     * The UUID
     */
    private final UUID MY_UUID_INSECURE =  UUID.fromString("bebde602-4ee1-11eb-ae93-0242ac130002");

    /**
     * OnReceive callback can be implemented in the
     * Activity to get a callback on certain events
     * (just on post execute at the moment)
     */
    private OnSyncFinishedCallback<Article>  onSyncFinished;

    /**
     * Thats the onReceive callback which will be set tom the BluetoothConnection
     * For testing (where we just have a mock BtConnection)
     * we can get this onReceiveCallback through the getter
     * and call its onIncomingMessage method manually
     */
    private OnReceiveCallback thisOnReceiveCallback;

    /**
     * Array list of articles
     * this list will be filled with the articles from the remote device
     * and later be merged into the current list
     * (or replace it completely)
     */
    private ArrayList<Article> receivedArticles;

    /**
     * A Network Connection
     * in this case a {@link BluetoothConnection}
     */
    private NetworkConnection networkConnection;

    /**
     * The remote bluetooth device
     */
    private BluetoothDevice mDevice;

    /**
     * This boolean is true at the beginning
     * when e started as bluetooth server
     * else its false, it will change after the first FINISH
     */
    private boolean sender;

    /**
     * This will stay true or false,
     * depending on if we started as server or not.
     * Needed to determine if we can replace the complete list in the end
     */
    private boolean startedAsSender;

    /*
     * The ACK send after each received article
     */
    public static final String ACKNOWLEDGED = "ACK";

    /**
     * The FIN send after all articles are transferred
     */
    public static final String FINISHED = "FIN";

    /**
     * If the in/output stream closes unexpected unexpected
     * the BluetoothConnection writes that
     */
    public static final String DISCONNECT="DISCONN";

    /**
     * instance of the model,
     * passed through the constructor to enable testing
     */
    private ApplicationController applicationControllerInstance;

    /**
     * There are two states, sender and receiver
     * i will increment that when the state changed
     * and use it as loop condition
     */
    private int stateChanged = 1;

    /**
     * Stops the doInBackground method
     */
    private boolean isCancelled = false;

    /**
     * True when the out- or input stream closes unexpected
     * @param networkConnection
     * @param device
     * @param onSyncFinished
     * @param applicationController
     */
    private boolean disconnected = false;


    //------------Constructors------------


    public SynchronizationManager(NetworkConnection networkConnection, BluetoothDevice device, OnSyncFinishedCallback<Article> onSyncFinished, ApplicationController applicationController)
    {
        Log.d(TAG, "Initialize SyncManager");
        this.networkConnection = networkConnection;
        this.receivedArticles =  new ArrayList<>();
        this.mDevice = device;
        this.onSyncFinished = onSyncFinished;
        Log.d(TAG, "Starting NetworkConnection as server");
        networkConnection.startServer();
        this.applicationControllerInstance = applicationController;
        networkConnection.setOnReceiveListener(new OnReceiveCallback()
        {
            @Override
            public void onIncomingMessage(String message)
            {
                //Noting to do here - just avoiding null pointer
            }
        });
    }



    //------------AsyncTask------------


    @Override
    public void onPreExecute()
    {
        Log.d(TAG, "onPreExecute:");
        //Starting as a Server, waiting for incoming connections

    }

    @Override
    public void onPostExecute(String s)
    {
        Log.d(TAG, "onPostExecute:");
        super.onPostExecute(s);
        if(!disconnected)
        {
            this.networkConnection.closeConnection();
            this.onSyncFinished.onSyncFinished(receivedArticles, OnSyncFinishedCallback.RESULT_OKAY);
        }
        else
        {
            this.networkConnection.closeConnection();
            this.onSyncFinished.onSyncFinished(receivedArticles, OnSyncFinishedCallback.RESULT_ERROR);
        }
    }

    @Override
    public String doInBackground(String...values)
    {

        Log.d(TAG, "doingInBackground :  checking if connected ");
        if (!networkConnection.isConnected())
        {
            Log.d(TAG, "doingInBackground :  Was not  connected starting as client");
            networkConnection.startClient(mDevice, MY_UUID_INSECURE);
        }

        Log.d(TAG, "doingInBackground :  Starting Sync loop ...see you on the other side");
        while (stateChanged<3)
        {
            Log.d(TAG, "doingInBackground :  state  = " + stateChanged);

            if(isCancelled)
            {
                break;
            }

            if (networkConnection.isConnected())
            {
                Log.d(TAG, "doingInBackground :  Both devices should now be connected...starting sync");
                if(stateChanged == 1)
                {
                    // Only on first state
                    this.sender = networkConnection.isServer();
                    this.startedAsSender = sender;
                }
                Log.d(TAG, "doingInBackground :  sender  = " + sender  );

                if(sender)
                {
                    sendArticles();
                }
                else
                {
                    receivedArticles();
                    if(!startedAsSender)
                    {
                        synchronize();
                    }
                }
            }
            else
            {
                Log.d(TAG, "not connected");
            }
        }
        if(startedAsSender)
        {
            synchronize();
        }
        return null;
    }


    public void cancelTask()
    {
        isCancelled = true;
    }


    //------------Send and Receive------------


    /**
     * Transfers Articles through the NetworkConnection to the remote device
     */
    private void receivedArticles()
    {
        Log.d(TAG, "Set onReceiveListener");

        this.thisOnReceiveCallback = new OnReceiveCallback()
        {
            @Override
            public void onIncomingMessage(String message)
            {
                    Log.d(TAG, "Received message = " + message);
                    if(message.equals(FINISHED))
                    {
                        Log.d(TAG, "Sender finished transmission, going to send mode");
                        sender = !sender;
                        stateChanged++;
                        synchronized (SynchronizationManager.this)
                        {
                            SynchronizationManager.this.notify();
                        }
                    }
                    else if(message.equals(DISCONNECT))
                    {
                        disconnected = true;
                    }
                    else
                    {
                        Log.d(TAG, "Received Patter = " + message);
                        Article newArticle = new Article(message);
                        Log.e(TAG, " Date 00 " + newArticle.getDateOfUpdate());
                        receivedArticles.add(newArticle);
                        networkConnection.write(ACKNOWLEDGED);
                    }
            }
        };

        this.networkConnection.setOnReceiveListener(thisOnReceiveCallback);

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


    /**
     * Transfers Articles through the NetworkConnection to the remote device
     */
    private void sendArticles()
    {

        //RECEIVE

        this.networkConnection.setOnReceiveListener(new OnReceiveCallback()
        {
            @Override
            public void onIncomingMessage(String message)
            {
                if(message.equals(ACKNOWLEDGED))
                {
                    Log.d(TAG, "sendArticles: onReceive: Article acknowledged");
                    synchronized (SynchronizationManager.this)
                    {
                        SynchronizationManager.this.notify();
                    }
                }
                else if(message.equals(DISCONNECT))
                {
                    disconnected = true;
                }
            }
        });


        //SEND

        ArrayList<Article> shoppingList = applicationControllerInstance.getShoppingList();
        for (Article a: shoppingList)
        {
            Log.d(TAG, "sendArticles: pattern" + a.getMementoPattern() );
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



    //------------Synchronize------------


    /**
     * Compares and synchronizes both lists (Local and the just received list)
     *
     */
    private void synchronize()
    {
        Log.d(TAG, "synchronize");
        if(disconnected)
        {
            return; //No sync when disconnected
        }
        if(startedAsSender)
        {
            //If we started as sender, the remote device will already have compared his list
            // with ours and synchronized it (see else block)
            //It then will send the complete list, including our article back to here.
            //so we can just replace the whole list without the nned of comparing it again
            Log.d(TAG, "synchronize: started as sender, saving articles ");
            applicationControllerInstance.overrideShoppingListCompletely(receivedArticles);
        }
        else
        {
            Log.d(TAG, "synchronize: started as receiver, comparing and snc lists ");
            Log.d(TAG, "synchronize: received  " + receivedArticles.size() + " Articles");

            //To compare bot lists (local and received) we need to keep them as they are
            //during the process.
            //The change will take place after the comparison , tmp values will be stored in the following Lists
            ArrayList<Integer> toDeleteIndex = new ArrayList();
            ArrayList<Article> toAddArticles = new ArrayList();
            boolean matched = false;
            for (Article a: receivedArticles)
            {
                matched = false;

                /**
                 * Okay so... here i compare both lists, the local ne and the transferred one:
                 * I will compare the Articles name Name if they are in both lists i will take the one with the more recent "lastUpdateDate"
                 * And write a information about it into the description.
                 */
                for (int index = 0; index < applicationControllerInstance.getShoppingList().size(); index++)
                {

                    if(!matched)
                    {
                        // Get the Article once to minimize method calls
                        Article tempArticle = applicationControllerInstance.getShoppingList().get(index);

                        //save the names of both articles / trimmed and in lowercase
                        String trimmedArticleName = a.getName().trim().toLowerCase();
                        String trimmedTempArticleName = tempArticle.getName().trim().toLowerCase();

                        // Check if the names are the same
                        Log.d(TAG, "synchronize: comparing " + a.getName());
                        if (trimmedArticleName.equals(trimmedTempArticleName))
                        {
                            matched = true;

                            Log.d(TAG, "synchronize: found match");
                            // local article is older then received article
                            if (a.getDateOfUpdate().after(tempArticle.getDateOfUpdate()))
                            {
                                Log.d(TAG, "synchronize: local article is older then received article ...replacing " + a.getName() );

                                toDeleteIndex.add(index);
                                toAddArticles.add(a);
                            }
                        }
                    }
                }
                if(!matched)
                {
                    toAddArticles.add(a);
                }
            }

            for (Integer i : toDeleteIndex)
            {
                Log.d(TAG, "Delete article at " + i);
                applicationControllerInstance.deleteArticleFromShoppingList(i.intValue());
            }
            for (Article a: toAddArticles)
            {
                Log.d(TAG, "Add article with name  "+ a.getName());
                Log.d(TAG, "Add article with date  "+ a.getDateOfUpdate());
                applicationControllerInstance.addArticleToShoppingList(a);
            }
        }
    }


}
