package com.example.cookshop.controller.viewController;

import android.provider.ContactsContract;
import android.widget.TextView;

import com.example.cookshop.items.Article;
import com.example.cookshop.items.Recipe;
import com.example.cookshop.items.Step;
import com.example.cookshop.model.listManagement.DataAccess;

import java.util.ArrayList;

public class RecipeController {

  private static RecipeController ourInstance;

  public Recipe generateRecipeFromInput(TextView name,TextView description){
    String nameString = name.getText().toString();
    String descriptionString = "default description";

    if (!description.getText().toString().trim().equals("")){
      descriptionString =description.getText().toString();
    }
    return new Recipe(nameString, descriptionString, new ArrayList<Article>(), new ArrayList<Step>() );
  }


  public void addRecipe(Recipe recipe){ DataAccess.getInstance().addRecipe(recipe);}



  public static RecipeController getInstance(){

    if (ourInstance == null){
      ourInstance = new RecipeController();
    }
    return ourInstance;
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
