package com.example.cookshop.model.listManagement;

import android.util.Log;

import com.example.cookshop.items.Item;
import com.example.cookshop.items.Recipe;
import com.example.cookshop.model.database.DatabaseHelper;

import java.util.ArrayList;

public class ToCookListManager extends ItemListManager{

  String TAG = this.getClass().getSimpleName();

  public ToCookListManager(DatabaseHelper databaseService)
  {
    super(databaseService);
    this.loadData();
  }

  private void loadData(){
    ArrayList<Recipe> tempList = this.getDatabase().retrieveAllRecipes();
    for (Recipe r: tempList)
    {
      if (r.getOnToCook()){
        this.getItemList().add(r);
      }
    }
  }


  @Override
  protected void removeItemFromDatabase(String name)
  { }

  @Override
  protected void addItemToDatabase(Item object)
  {
    this.getDatabase().updateRecipe(object.getName(), (Recipe) object);
  }

  @Override
  protected void addItemsToDatabase(ArrayList list)
  {
    // not needed
    //this.getDatabase().insertSeveralRecipes(list);
  }

  protected void addRecipeIntelligent(Recipe recipe){
    if(this.getItemList().size()==0){
      this.addItem(recipe);
      this.addItemToDatabase(recipe);
      return;
    }

    for(int i = 0; i <= this.getItemList().size(); i++){

      Recipe tempRecipe = (Recipe) this.getItem(i);

      String trimmedRecipeName = recipe.getName().trim().toLowerCase();
      String trimmedTempRecipeName = tempRecipe.getName().trim().toLowerCase();
      Log.d(TAG, ": addRecipeIntelligent: trimmedRecipeName: "+trimmedRecipeName+"; trimmedTempRecipeName: "+trimmedTempRecipeName);

      if(trimmedRecipeName.equals(trimmedTempRecipeName)){
        Log.e(TAG, ": Recipe: "+recipe.getName()+" already on To-Cook-List.");

      }else{
        this.addItem(recipe);
        this.addItemToDatabase(recipe);
        break;
      }
    }
  }
}
