package com.example.cookshop.controller.network;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.nfc.Tag;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Connects to other bluetooth enabled devices.
 *
 */
public class BluetoothConnection  implements NetworkConnection
{



    //------------Instance Variables------------

    /**
     * The name of the app
     * needed for the BluetoothAdapter
     */
    private static final String appName = "CookAndShop";

    /**
     * Log Tag
     */
    private final String TAG = this.getClass().getSimpleName();

    /**
     * A generated UUID needed for the BluetoothAdapter
     */
    private final UUID MY_UUID_INSECURE =  UUID.fromString("bebde602-4ee1-11eb-ae93-0242ac130002");

    /**
     * The BluetoothAdapter
     */
    private final BluetoothAdapter mBluetoothAdapter;

    /**
     * An instance of the AcceptThread
     */
    private AcceptThread mAcceptThread;

    /**
     * An instance of the ConnectThread
     */
    private ConnectThread mConnectThread;

    /**
     * An instance of the ConnectedThread
     */
    private ConnectedThread mConnectedThread;

    /**
     * The Bluetooth device to connect with
     */
    private BluetoothDevice mmDevice;

    private UUID deviceUUID;

    /**
     * true if Accept or ConnectThread finished and AcceptThread starts
     */
    boolean isConnected;

    /**
     * On Receive callback can be implemented somewhere else and set
     * through the setter, will execute when the ConnectedThread receives something
     */
    private OnReceiveCallback mOnReceiveCallback;


    /**
     * is true when the AcceptThread finishes before the connect thread
     * finished
     */
    private boolean isServer;





    //------------Constructors------------

    public BluetoothConnection(BluetoothAdapter mBluetoothAdapter)
    {
        this.mBluetoothAdapter = mBluetoothAdapter;
        isConnected = false;
    }





    //------------Network Connection Methods ------------




    @Override
    public synchronized void startServer()
    {
        Log.d(TAG, "start : starting BluetoothConnection");

        // Cancel running Threads
        if(mConnectThread != null)
        {
            mConnectThread.cancel();;
            mConnectThread = null;
        }
        // Starting AcceptThread if it isnt running already
        if(mAcceptThread == null)
        {
            mAcceptThread = new AcceptThread();
            mAcceptThread.start();;
        }
    }



    @Override
    public void startClient(BluetoothDevice device, UUID uuid)
    {
        Log.d(TAG, "startClient: started");
        mConnectThread = new ConnectThread(device, uuid);
        mConnectThread.start();
    }



    @Override
    public void write(String message)
    {
        mConnectedThread.write(message);
    }



    @Override
    public void setOnReceiveListener(OnReceiveCallback listener)
    {
        this.mOnReceiveCallback = listener;
    }



    @Override
    public boolean isServer()
    {
        return this.isServer;
    }



    @Override
    public boolean isConnected()
    {
        return  this.isConnected;
    }



    @Override
    public void closeConnection()
    {
        Log.d(TAG, "closeConnection: trying to close connection");
        try
        {
            this.mConnectedThread.cancel();
            Log.d(TAG, "closeConnection: closed ConnectedThread");
        }
        catch (NullPointerException e)
        {
            Log.d(TAG, "closeConnection: ConnectedThread was null");
        }
        try
        {
            this.mAcceptThread.cancel();
            Log.d(TAG, "closeConnection: closed AcceptThread");
        }
        catch (NullPointerException e)
        {
            Log.d(TAG, "closeConnection: AcceptThread was null");
        }
        try
        {
            this.mConnectThread.cancel();
            Log.d(TAG, "closeConnection: closed ConnectThread");
        }
        catch (NullPointerException e)
        {
            Log.d(TAG, "closeConnection: ConnectThread was null");
        }

        this.isConnected = false;
        this.mConnectThread = null;
        this.mAcceptThread = null;
        this.mConnectThread = null;

    }






    //------------Accept Thread ------------


    /**
     * the AcceptThreads runs at the beginning and waits for incoming connections
     * It runs as long as a connection is accepted
     */
    private class AcceptThread extends Thread
    {

        //------------Instance Variables------------

        private final BluetoothServerSocket mmServerSocket;



        //------------Constructors------------

        public AcceptThread()
        {

            BluetoothServerSocket tmp = null;


            try
            {
                tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(appName, MY_UUID_INSECURE);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            mmServerSocket = tmp;

            Log.d(TAG, "AcceptThread: Setting up Server ");

        }




        //------------Methods------------

        /**
         * Opens the ServerSocket and waits for incoming connections
         */
        public void run()
        {
            Log.d(TAG, "AcceptThread: run : AcceptThread Running");

            BluetoothSocket socket = null;

            //Blocking Call :
            //Accept thread waits here till another device connects (or canceled)
            Log.d(TAG, "AcceptThread: run : RFCOM server socket started, waiting for connections ...");
            try
            {
                socket = mmServerSocket.accept();
                Log.d(TAG, "AcceptThread: run : RFCOM server socket accepted connection.");
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            if (socket != null)
            {
                isServer = true;
                connected(socket, mmDevice);
            }

            Log.d(TAG, "AcceptThread: run : AcceptThread Ended ");
        }



        /**
         * Cancels the AcceptThread by closing the ServerSocket
         */
        public void cancel ()
        {
            Log.d(TAG, "AcceptThread: cancel: canceling AcceptThread");
            try {
                mmServerSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "AcceptThread: cancel: failed to close ServerSocket : " + e.getStackTrace());
            }
        }

    }





    //------------Connected Thread ------------


    /**
     * The ConnectThread trys to connect to another device (running in AcceptThread)
     */
    private class ConnectThread extends  Thread
    {


        //------------Instance Variables------------

        private BluetoothSocket mmSocket;



        //------------Constructors------------

        public ConnectThread(BluetoothDevice device, UUID uuid)
        {
            Log.d(TAG, "connectThread. ConnectThread started");
            mmDevice = device;
            deviceUUID = uuid;
        }



        //------------Methods------------


        /**
         * Starts to connect to another devices BluetoothServerSocket
         */
        public void run()
        {
            BluetoothSocket tmp = null;
            Log.e(TAG, "ConnectThread: run: ConnectThread is running");


            try
            {
                Log.d(TAG, "ConnectThread: run: trying to create a Rfcomm Socket ");
                tmp = mmDevice.createInsecureRfcommSocketToServiceRecord(deviceUUID);
            }
            catch (IOException e)
            {
                Log.d(TAG, "ConnectThread: run: could not create a Rfcomm Socket  " + e.getStackTrace());
            }

            mmSocket = tmp;

            mBluetoothAdapter.cancelDiscovery();

            // Blocking call:
            // only return on successful connection or exception
            try
            {
                mmSocket.connect();
            }
            catch (IOException e)
            {
                Log.e(TAG, "ConnectThread: run: could not make connection, socket closed  " + e.getStackTrace());
                try
                {
                    mmSocket.close();
                    Log.e(TAG, "ConnectThread: run: socked closed  ") ;
                }
                catch (IOException ioException)
                {
                    ioException.printStackTrace();
                    Log.e(TAG, "ConnectThread: run: could close socket   " + ioException.getStackTrace());
                }
            }
            Log.d(TAG, "ConnectThread: run: connection established ");
            isServer = false;
            connected(mmSocket, mmDevice);
        }


        /**
         * Cancels the AcceptThread by closing the ServerSocket
         */
        public void cancel ()
        {
            Log.d(TAG, "ConnectThread: cancel: canceling ConnectThread");
            try
            {
                mmSocket.close();
            }
            catch (IOException e)
            {
                Log.e(TAG, "ConnectThread: cancel: failed to close Socket : " + e.getStackTrace());
            }
        }

    }



    //------------Connect Thread ------------


    /**
     * Holds the connection
     * Manages in- and output streams on the Bluetooth Socket
     */
    private class ConnectedThread extends  Thread
    {

        //------------Instance Variables------------


        /**
         * The Connected Socked
         */
        private final BluetoothSocket mmSocket
                ;
        /**
         * OutputStream to send Bytes
         */
        private final OutputStream mmOutputStream;

        /**
         * InputStream to receive Bytes
         */
        private final InputStream mmInputStream;



        //------------Constructors------------

        public ConnectedThread(BluetoothSocket bluetoothSocket)
        {
            this.mmSocket = bluetoothSocket;

            // Setting up in and output stream
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try
            {
                tmpIn = mmSocket.getInputStream();
                tmpOut = mmSocket.getOutputStream();
            }
            catch (IOException e)
            {
                Log.e(TAG, "ConnectedThread: could not get In- or OutputStream from  from Socket " + e.getStackTrace());
            }

            mmInputStream = tmpIn;
            mmOutputStream = tmpOut;
            Log.d(TAG, "ConnectedThread: In- and OutputStream are ready to go ");

        }


        public void run()
        {
            byte[] buffer = new byte[2048]; // buffer for the stream

            int bytes; // bytes returned from read()

            //waiting for incoming transmissions
            while(true)
            {
                int count = 1;
                if(count == 1)
                {
                    isConnected = true;
                    count ++;
                }
                try
                {
                    //Blocking Call
                    bytes = mmInputStream.read(buffer);
                    String incomingTransmission = new String(buffer, 0, bytes);
                    Log.d(TAG, "  " + incomingTransmission);

                    mOnReceiveCallback.onIncomingMessage(incomingTransmission);
                }
                catch (IOException e)
                {
                    Log.e(TAG, "ConnectedThread: run: Problem reading from input stream" + e.getMessage());
                    mOnReceiveCallback.onIncomingMessage(SynchronizationManager.DISCONNECT);
                }
            }
        }


        /**
         * Writes  through the OutputSteam to the other device
         * @param string
         */
        public void write(String string)
        {
            Log.d(TAG, "ConnectedThread: write: writing to the OutputStream : " + string);
            try
            {
                mmOutputStream.write(string.getBytes());
            }
            catch (IOException e)
            {
                Log.e(TAG, "ConnectedThread: write: an exception occured while writing to the OutputStream " + e.getMessage());
            }
        }


        /**
         * Cancels the AcceptThread by closing the ServerSocket
         */
        public void cancel ()
        {
            Log.d(TAG, "ConnectedThread: cancel: canceling ConnectThread");
            try
            {
                mmSocket.close();
            }
            catch (IOException e)
            {
                Log.e(TAG, "ConnectedThread: cancel: failed to close Socket : " + e.getStackTrace());
            }
        }

    }


    /**
     * Starts the connectedThread after either Accept ot ConnectThread have finished
     * @param mmSocket
     * @param mmDevice
     */
    private void connected(BluetoothSocket mmSocket, BluetoothDevice mmDevice)
    {
        Log.e(TAG, "connected: Accept or ConnectThread finished - starting  ConnectedThread");
        mConnectedThread = new ConnectedThread(mmSocket);
        mConnectedThread.start();
    }


    public void triggerOnReceiveCallbackManually(String msg)
    {
        this.mOnReceiveCallback.onIncomingMessage(msg);
    }
}
