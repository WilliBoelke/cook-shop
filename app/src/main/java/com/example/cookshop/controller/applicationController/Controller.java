package com.example.cookshop.controller.applicationController;

import android.content.Context;

import com.example.cookshop.items.Article;
import com.example.cookshop.items.Recipe;

import java.util.ArrayList;

public interface Controller {

    /**
     * Adds one article to the availableList (and database)
     *
     * @param article the article to be added
     */
    void addArticleToAvailableList(Article article);

    /**
     * deletes the article at index from the availableList (and database)
     *
     * @param index the index of the article
     */
    void deleteArticleFromAvailableList(int index);

    /**
     * returns the article at index
     *
     * @param index the list index
     * @return teh article at index
     */
    Article getArticleFromAvailableList(int index);

    //....BuyingListAccess..........

    /**
     * Returns the article  with the name from the availableList
     *
     * @param name
     * @return
     */
    Article getArticleFromAvailableList(String name);

    /**
     * return the complete ItemList from the availableLists service
     * mainly used for the representation of the complete list in and Recycler- or LisView
     *
     * @return the ItemList of the availableList
     */
    ArrayList<Article> getAvailableList();

    /**
     * @param index
     * @param newArticle
     */
    void updateArticleFromAvailableList(int index, Article newArticle);

    /**
     * Adds one article to the buyingList(and database)
     *
     * @param article the article to be added
     */
    void addArticleToBuyingList(Article article);

    /**
     * deletes the article at index from the buyingList (and database)
     *
     * @param index the index of the article
     */
    void deleteArticleFromBuyingList(int index);

    /**
     * returns the article at index
     *
     * @param index the list index
     * @return the article at index
     */
    public Article getArticleFromBuyingList(int index);

    /**
     * Returns the article  with the name from the buyingList
     *
     * @param name
     * @return
     */
    Article getArticleFromBuyingList(String name);

    /**
     * return the complete ItemList from the buyingListService
     * mainly used for the representation of the complete list in and Recycler- or LisView
     *
     * @return the ItemList of the buyingListService
     */
    public ArrayList<Article> getBuyingList();

    /**
     * @param index
     * @param newArticle
     */
    void updateArticleFromBuyingList(int index, Article newArticle);

    //....exchange between the article list..........


    /**
     * Transfers one article from the buying- to the availableList
     * i guess there is a better way /willi
     *
     * @param index
     */
    void transferArticleFromBuyingToAvailableList(int index);

    /**
     * Transfers one article from the available- to the buyingList
     * again i guess there is a better way /willi
     *
     * @param index
     */
    void transferArticleFromAvailableToBuyingList(int index);

    //....Recipe List Access..........

    /**
     * Adds one recipe to the recipeList(and database)
     *
     * @param recipe the recipe to be added
     */
    void addRecipe(Recipe recipe);

    /**
     * deletes the recipe at index from the recipeList (and database)
     *
     * @param index the index of the recipe
     */
    void deleteRecipe(int index);

    /**
     * returns the recipe at index
     *
     * @param index the list index
     * @return the recipe at index
     */
    Recipe getRecipe(int index);

    Recipe getRecipe(String name);

    void updateRecipe(int index, Recipe newRecipe);

    Article getArticleFromRecipe(String recipeName, int articleIndex);

    /**
     * returns the complete ItemList from the recipeListService
     * mainly used for the representation of the complete list in and Recycler- or LisView
     *
     * @return the ItemList of the recipeListService
     */
    ArrayList<Recipe> getRecipeList();


    void recipeToBuyingList(int index);

}

