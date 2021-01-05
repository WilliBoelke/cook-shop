package com.example.cookshop.controller.network;

public interface NetworkConnection
{


      void start();

     void write(String message);

     void setOnReceiveListener(OnReceiveCallback listener);
}
