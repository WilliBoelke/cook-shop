package com.example.cookshop.model.listManagement;

import com.example.cookshop.items.Item;
import com.example.cookshop.items.Recipe;
import com.example.cookshop.model.database.DatabaseHelper;

import java.util.ArrayList;

public class RecipeListManager extends ItemListManager
{
    public RecipeListManager(DatabaseHelper databaseService) {
      super(databaseService);
      this.loadData();
    }

    private void loadData(){
      this.setItemList(this.getDatabase().retrieveAllRecipes());
    }

    @Override
    protected void removeItemFromDatabase(String name) {
      this.getDatabase().deleteRecipe(name);
    }

    @Override
    protected void addItemToDatabase(Item object) {
      this.getDatabase().insertRecipe((Recipe) object);
    }

    @Override
    protected void addItemsToDatabase(ArrayList list) {
      this.getDatabase().insertSeveralRecipes(list);
    }

    protected Recipe searchForRecipe(String name){
      ArrayList<Recipe> toSearch = (ArrayList<Recipe>) this.getItemList();

      for (int i = 0; i < toSearch.size(); i++)
      {
        if (toSearch.get(i).getName().equals(name))
        {
          return toSearch.get(i);
        }
      }
      return null;
    }


    public void updateRecipe(int index, Recipe newRecipe){
      Recipe oldRecipe = (Recipe) this.getItem(index);

      this.getItemList().set(index, newRecipe);

      this.getDatabase().updateRecipe(oldRecipe.getName(), newRecipe);
    }


}
