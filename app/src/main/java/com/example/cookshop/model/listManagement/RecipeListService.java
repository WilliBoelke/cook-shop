package com.example.cookshop.model.listManagement;

import com.example.cookshop.items.Item;
import com.example.cookshop.model.database.DatabaseHelper;

import java.util.ArrayList;

public class RecipeListService extends ItemListService
{
    public RecipeListService(DatabaseHelper databaseService) {
        super(databaseService);
    }

    @Override
    protected void removeItemFromDatabase(String name) {

    }

    @Override
    protected void addItemToDatabase(Item object) {

    }

    @Override
    protected void addItemsToDatabase(ArrayList list) {

    }
}
