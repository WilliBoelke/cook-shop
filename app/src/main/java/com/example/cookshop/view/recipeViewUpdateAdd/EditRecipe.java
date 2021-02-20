package com.example.cookshop.view.recipeViewUpdateAdd;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.cookshop.R;
import com.example.cookshop.items.Recipe;
import com.google.android.material.snackbar.Snackbar;

public class EditRecipe extends AddRecipeActivity {

  private String TAG = this.getClass().getSimpleName();

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

    Recipe updatedValues = this.recipeController.generateRecipeFromInput(nameTextView.getText().toString(), descriptionTextView.getText().toString(), articleList, stepList);

    if (!nameTextView.getText().toString().equals(""))
    {
      Log.d(TAG, " onSaveButtonclick: Position: "+position);
      this.recipeController.updateRecipe(this.position, updatedValues);
      finish();

    }
        else
    {
      Snackbar.make(view, R.string.no_name_warning, Snackbar.LENGTH_LONG)
        .setAction("Action", null).show();
    }

  }
}
