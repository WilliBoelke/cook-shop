package com.example.cookshop.controller.network;

import android.bluetooth.BluetoothDevice;

import java.util.UUID;

public interface NetworkConnection
{


      void startServer();

      void startClient(BluetoothDevice device, UUID uuid);

     void write(String message);

     void setOnReceiveListener(OnReceiveCallback listener);

     boolean isServer();

     boolean isConnected();

     void closeConnection();
}
