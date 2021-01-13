package com.example.cookshop.controller.network;

import com.example.cookshop.items.Item;

import java.util.ArrayList;

/**
 * Can be registered in a SynchronisationManager
 * @param <T>  A item class
 */
public interface OnSyncFinishedCallback<T extends Item>
{
     String RESULT_OKAY = "OK";
     String RESULT_ERROR = "ERROR";

     /**
      * Called in the SynchronizationManagers onPostExecuteMethod
      * @param syncedList The final  list, which also is saved in the Model
      * @param result one of the above result strings
      */
     void onSyncFinished(ArrayList<T> syncedList, String result);
}
