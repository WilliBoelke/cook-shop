package com.example.cookshop.controller.viewController;

import android.util.Log;
import android.widget.TextView;

import com.example.cookshop.items.Article;
import com.example.cookshop.items.Category;
import com.example.cookshop.model.listManagement.DataAccess;

import java.util.ArrayList;
import java.util.Date;

/**
 * This Class coordinates the Communication between View and Model
 * when it comes to Articles, it implements the code to check user input
 *
 *
 */
public class ArticleController
{
private final String TAG = getClass().getSimpleName();
    public Article generateArticleFromInput(TextView name, TextView description, Category category, TextView weight, TextView amount)
    {
        // Declaring  standard values for the Article
        String nameString        = name.getText().toString(); // must always be != null so we can get it right here
        String descriptionString= "description";
        int    amountI     = 1;
        double weightD      = 0.0;

        //Overwrite standard values if there is an input

        if (!description.getText().toString().trim().equals(""))
        {
            descriptionString = description.getText().toString();
        }
        if (!weight.getText().toString().trim().equals(""))
        {
            weightD = Double.parseDouble(weight.getText().toString());
        }
        if (!amount.getText().toString().trim().equals(""))
        {
            amountI = Integer.parseInt(amount.getText().toString());
        }

        Article newArticle =  new Article(nameString, descriptionString, category, amountI, weightD);

        return newArticle;
    }

    public Article generateArticleFromInput(TextView name, TextView description, Category category, TextView weight, TextView amount, Date creation, Date changed)
    {
        // Declaring  standard values for the Article
        String nameString        = name.getText().toString(); // must always be != null so we can get it right here
        String descriptionString= "description";
        int    amountI     = 1;
        double weightD      = 0.0;

        //Overwrite standard values if there is an input

        if (!description.getText().toString().trim().equals(""))
        {
            descriptionString = description.getText().toString();
        }
        if (!weight.getText().toString().trim().equals(""))
        {
            weightD = Double.parseDouble(weight.getText().toString());
        }
        if (!amount.getText().toString().trim().equals(""))
        {
            amountI = Integer.parseInt(amount.getText().toString());
        }

        return new Article(nameString, descriptionString, category, amountI, weightD, creation);
    }


}