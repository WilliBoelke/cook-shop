package com.example.cookshop.controller;

import com.example.cookshop.model.Observabel;
import com.example.cookshop.model.Observer;

public class SynchronizationManager
{

    private final String TAG = getClass().getSimpleName();
    private NetworkConnection networkConnection;

    public SynchronizationManager(NetworkConnection networkConnection)
    {
        this.networkConnection = networkConnection;
    }

}
