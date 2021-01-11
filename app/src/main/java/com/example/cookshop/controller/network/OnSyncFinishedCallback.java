package com.example.cookshop.controller.network;

import java.util.ArrayList;

public interface OnSyncFinishedCallback<T>
{
     String RESULT_OKAY = "OK";
     String RESULT_ERROR = "ERROR";

     void onSyncFinished(ArrayList<T> syncedList, String result);
}
