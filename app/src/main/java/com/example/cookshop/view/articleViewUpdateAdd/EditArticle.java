package com.example.cookshop.view.articleViewUpdateAdd;

import android.os.Bundle;
import android.view.View;

import com.example.cookshop.R;
import com.example.cookshop.items.Article;
import com.example.cookshop.controller.applicationController.ApplicationController;
import com.google.android.material.snackbar.Snackbar;

public class EditArticle extends  AddArticleActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        nameTextView.setText(this.editArticle.getName());
        descriptionTextView.setText(this.editArticle.getDescription());
        amountSeekBar.setProgress(this.editArticle.getAmount());
        weightSeekbar.setProgress((int) this.editArticle.getWeight());
        switch (editArticle.getCategory())
        {
            case FRUIT:
                categorySpinner.setSelection(0);
                break;
            case VEGETABLE:
                categorySpinner.setSelection(1);
                break;
            case DRINK:
                categorySpinner.setSelection(2);
                break;
            case MEAT:
                categorySpinner.setSelection(3);
                break;
            case OTHERS:
                categorySpinner.setSelection(4);
                break;
            default:
                categorySpinner.setSelection(5);
        }
    }


    @Override
    public void onAddArticleFabClick(View view)
    {

        if (!nameTextView.getText().toString().equals(""))
        {
            Article updatedValues = controller.generateArticleFromInput(nameTextView, descriptionTextView, category, weightTextView, amountTextView, editArticle.getDateOfCreation());
            // this method is used in EditArticle
            if (this.belonging.equals("edit"))
            {
                if (this.editBelonging.equals("buy"))
                {
                    ApplicationController.getInstance().updateArticleFromShoppingList(this.position, updatedValues);
                }
                else if (this.editBelonging.equals("available"))
                {
                    ApplicationController.getInstance().updateArticleFromAvailableList(this.position, updatedValues);
                }

                finish();
            }
        }
        else
        {
            Snackbar.make(view, R.string.no_name_warning, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

}
