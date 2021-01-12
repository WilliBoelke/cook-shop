package com.example.cookshop.model.listManagement;

import com.example.cookshop.items.Item;
import com.example.cookshop.items.Recipe;
import com.example.cookshop.model.database.DatabaseHelper;

import java.util.ArrayList;

public class ToCookListManager extends ItemListManager{
  public ToCookListManager(DatabaseHelper databaseService)
  {
    super(databaseService);
  }


  @Override
  protected void removeItemFromDatabase(String name)
  {
    this.getDatabase().deleteRecipe(name);
  }

  @Override
  protected void addItemToDatabase(Item object)
  {
    this.getDatabase().insertRecipe((Recipe) object);
  }

  @Override
  protected void addItemsToDatabase(ArrayList list)
  {
    this.getDatabase().insertSeveralRecipes(list);
  }
}
