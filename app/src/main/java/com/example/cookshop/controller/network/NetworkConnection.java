package com.example.cookshop.controller.network;

import android.bluetooth.BluetoothDevice;

import java.util.UUID;


/**
 * Interface for a NetworkConnection which l
 * enables sending and receiving data from and to
 * other devices.
 */
public interface NetworkConnection
{

    /**
     * Starts the NotworkConnection to accept other
     * connections
     */
    void startServer();

    /**
     * Starts the Network connection to connect to other devices
     * @param device
     * @param uuid
     */
      void startClient(BluetoothDevice device, UUID uuid);

    /**
     * Sends a String to the connected device
     * @param message
     */
     void write(String message);

    /**
     * Setter for an {@link OnReceiveCallback}
     * which can be implemented to react to incoming transmissions
     * @param listener
     */
     void setOnReceiveListener(OnReceiveCallback listener);

    /**
     * @return
     */
     boolean isServer();

    /**
     * Returns true if a connection is established
     * @return
     */
     boolean isConnected();


    /**
     * closes the NetworkConnection again,
     * this should be called ever time the connection isnt used anymore
     * to prevent the threads from running indefinitely
     */
    void closeConnection();
}
