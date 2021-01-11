package com.example.cookshop.model.listManagement;

import com.example.cookshop.model.database.DatabaseHelper;

public class AvailableListManager extends ArticleListManager
{
    /**
     * Log tag for this class
     */
    private final String TAG           = this.getClass().getSimpleName();

    /**
     * Tag used to mark the belonging in the Database
     */
    public static final String BELONGING_TAG = "avail";


    public AvailableListManager(DatabaseHelper databaseService) {
        super(databaseService);
    }
}
