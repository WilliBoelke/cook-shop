package com.example.cookshop.controller.viewController;

import android.widget.TextView;

import com.example.cookshop.items.Article;
import com.example.cookshop.items.Category;

import java.util.Date;

/**
 * This Class coordinates the Communication between View and Model
 * when it comes to Articles, it implements the code to check user input
 *
 *
 */
public class ArticleController
{

    /**
     * Checks the userInput, returns a integer as feedback
     * 1 means everything is okay, 2 means the name is missing
     *
     * @param name
     * @return
     */
    public int checkUserInput(TextView name)
    {

        if(name.getText().toString().trim().equals(""))
        {
            return 2;
        }

        return 1;
    }

    /**
     * Generates and article from the UserInput
     * @param name
     * @param description
     * @param category
     * @param weight
     * @param amount
     * @return
     */
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


    /**
     * Generates and article from the UserInput
     * Used for editing an Article, here we need the Creation date, which should stay the same after editing
     * @param name
     * @param description
     * @param category
     * @param weight
     * @param amount
     * @return
     */
    public Article generateArticleFromInput(TextView name, TextView description, Category category, TextView weight, TextView amount, Date creation)
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
