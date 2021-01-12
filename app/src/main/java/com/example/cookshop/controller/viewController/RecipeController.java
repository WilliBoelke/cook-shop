package com.example.cookshop.controller.viewController;

import com.example.cookshop.items.Article;
import com.example.cookshop.items.Recipe;
import com.example.cookshop.items.Step;
import com.example.cookshop.controller.applicationController.ApplicationController;

import java.util.ArrayList;

public class RecipeController
{

  ApplicationController applicationControllerInstance;

  public RecipeController(ApplicationController applicationController)
  {
    applicationControllerInstance = applicationController;
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
      applicationControllerInstance.addRecipe(recipe);
  }


  public void updateRecipe(int position, Recipe recipe)
  {
      applicationControllerInstance.updateRecipe(position, recipe);
  }

  public Recipe getRecipe(int index)
  {
    return applicationControllerInstance.getRecipe(index);
  }

  public Recipe getRecipe(String name)
  {
    return applicationControllerInstance.getRecipe(name);
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
