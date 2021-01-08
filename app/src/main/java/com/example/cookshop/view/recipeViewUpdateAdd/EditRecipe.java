package com.example.cookshop.view.recipeViewUpdateAdd;

import android.os.Bundle;
import android.view.View;

import com.example.cookshop.R;
import com.example.cookshop.controller.viewController.RecipeController;
import com.google.android.material.snackbar.Snackbar;

public class EditRecipe extends AddRecipeActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);

    nameTextView.setText(this.editRecipe.getName());
    descriptionTextView.setText(this.editRecipe.getDescription());
    articleList.addAll(this.editRecipe.getArticles());
    stepList.addAll(this.editRecipe.getSteps());
    articleListAdapter.notifyDataSetChanged();
    stepListAdapter.notifyDataSetChanged();

  }

  @Override
  public void onSaveButtonClick(View view){
    if (!nameTextView.getText().toString().equals(""))
    {

      RecipeController.getInstance().updateRecipe(this.position, getRecipe());
      finish();

    }
        else
    {
      Snackbar.make(view, R.string.no_name_warning, Snackbar.LENGTH_LONG)
        .setAction("Action", null).show();
    }

  }
}
