package com.example.cookshop.controller.viewController;

import android.widget.TextView;

import com.example.cookshop.items.Article;
import com.example.cookshop.items.Recipe;
import com.example.cookshop.items.Step;
import com.example.cookshop.model.listManagement.DataAccess;
import com.example.cookshop.view.adapter.ListItemWithDeleteButtonAdapter;

public class RecipeController
{

  DataAccess dataAccessInstance;

  public RecipeController(DataAccess dataAccess)
  {
    dataAccessInstance = dataAccess;
  }

  public Recipe generateRecipeFromInput(TextView name, TextView description,
                                        ListItemWithDeleteButtonAdapter<Article> articleListAdapter,
                                        ListItemWithDeleteButtonAdapter<Step> stepListAdapter){
    String nameString = name.getText().toString();
    String descriptionString = "default description";

    if (!description.getText().toString().trim().equals("")){
      descriptionString =description.getText().toString();
    }
    return new Recipe(nameString, descriptionString, articleListAdapter.getList(), stepListAdapter.getList() );
  }


  public void addRecipe(Recipe recipe)
  {
    DataAccess.getInstance().addRecipe(recipe);
  }


  public void updateRecipe(int position, Recipe recipe) {
    DataAccess.getInstance().updateRecipe(position, recipe);
  }

  public Recipe getRecipe(int index)
  {
    return DataAccess.getInstance().getRecipe(index);
  }

  public Recipe getRecipe(String name)
  {
    return DataAccess.getInstance().getRecipe(name);
  }
}
