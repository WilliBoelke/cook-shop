package com.example.cookshop.controller.network;

/**
 * Can be registered in a NetworkConnection
 * to to get the latest incoming message
 */
public interface OnReceiveCallback
{
    /**
     * Will be called in the network confection
     * when a transmission was  received
     * @param message the transmission as String
     */
    void onIncomingMessage(String message);
}
