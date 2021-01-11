package com.example.cookshop.controller.viewController;

import android.widget.EditText;
import android.widget.TextView;

import com.example.cookshop.items.Article;
import com.example.cookshop.items.Recipe;
import com.example.cookshop.items.Step;
import com.example.cookshop.model.listManagement.DataAccess;
import com.example.cookshop.view.adapter.ListItemWithDeleteButtonAdapter;

import java.util.ArrayList;

public class RecipeController
{

  DataAccess dataAccessInstance;

  public RecipeController(DataAccess dataAccess)
  {
    dataAccessInstance = dataAccess;
  }

  public Recipe generateRecipeFromInput(String name, String description, ArrayList<Article> articles, ArrayList<Step> steps)
  {
      String descriptionString = "default description";

      if (!description.trim().equals(""))
      {
          descriptionString =description;
      }
      return new Recipe(name, descriptionString, articles, steps);
  }


  public void addRecipe(Recipe recipe)
  {
      dataAccessInstance.addRecipe(recipe);
  }


  public void updateRecipe(int position, Recipe recipe)
  {
      dataAccessInstance.updateRecipe(position, recipe);
  }

  public Recipe getRecipe(int index)
  {
    return dataAccessInstance.getRecipe(index);
  }

  public Recipe getRecipe(String name)
  {
    return dataAccessInstance.getRecipe(name);
  }

  public boolean checkUserInput(String name, String description)
  {

    if(name.trim().equals(""))
    {
      return false;
    }
    if(description.trim().equals(""))
    {
      return false;
    }
    return true;
  }
}
