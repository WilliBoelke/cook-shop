package com.example.cookshop.controller.viewController;

import com.example.cookshop.items.Article;
import com.example.cookshop.items.Category;
import com.example.cookshop.model.listManagement.DataAccess;

import java.util.ArrayList;

/**
 * This Class coordinates the Communication between View and Model
 * when it comes to Articles, it implements the code to check user input
 *
 *
 */
public class ArticleController
{

    /**
     * The belonging String determines which list is the  articles belong to
     * (Available, Shopping or and Recipe)
     *
     * it needs to be passed  from  the Activity
     */
    private String belonging;

    public void ArticleController(String belonging)
    {
        this.belonging = belonging;
    }


    /**
     * Checks user input for a recipe
     * returns 0 if everything is correct
     * @param name
     * @param description
     * @param category
     * @param weight
     * @param amount
     * @return
     */
    public int checkInput(String name, String description, Category category, float weight, int amount)
    {
        return 1;
    }

    public void  deleteArticle(int index, String belonging)
    {
    }


    public void updateArticle(int index, String belonging, Article newArticle)
    {

    }



    public ArrayList<Article> getArticleList()
    {
         DataAccess.getInstance().getBuyingList();
         return null;
    }
}
