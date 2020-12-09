package com.example.cookshop.model.listManager;

import com.example.cookshop.items.Item;
import com.example.cookshop.items.Recipe;
import com.example.cookshop.model.database.DatabaseHelper;

import java.util.ArrayList;

public class RecipeListManager extends  ItemListManager
{
    private final String TAG = this.getClass().getSimpleName();

    public RecipeListManager(DatabaseHelper databaseHelper) {
        super(databaseHelper);
        this.retrieveDataFromDatabase();
    }

    private void retrieveDataFromDatabase()
    {
        this.setItemList(this.getDatabase().retrieveAllRecipes());
    }

    @Override
    protected void addItemToDatabase(Item Object)
    {
        this.getDatabase().insertRecipe((Recipe) Object);
    }

    @Override
    protected void removeItemFromDatabase(String name)
    {
        this.getDatabase().deleteRecipe(name);
    }

    @Override
    protected void addItemsToDatabase(ArrayList list)
    {
        this.getDatabase().insertSeveralRecipes(list);
    }

    protected  void updateRecipe(int index, Recipe newRecipe)
    {
        Recipe oldRecipe = (Recipe) this.getItem(index);

        this.getItemList().set(index, newRecipe);
        this.getDatabase().updateRecipe(oldRecipe.getName(), newRecipe);
    }
}
