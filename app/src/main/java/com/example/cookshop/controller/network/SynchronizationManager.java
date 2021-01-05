package com.example.cookshop.controller.network;

public class SynchronizationManager
{

    private final String TAG = getClass().getSimpleName();
    private NetworkConnection networkConnection;

    public SynchronizationManager(NetworkConnection networkConnection)
    {
        this.networkConnection = networkConnection;
    }

}
