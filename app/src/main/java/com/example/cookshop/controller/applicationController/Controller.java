package com.example.cookshop.controller.applicationController;

import com.example.cookshop.items.Article;
import com.example.cookshop.items.Recipe;

import java.util.ArrayList;

public interface Controller
{


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
     * Adds one article to the shopping(and database)
     *
     * @param article the article to be added
     */
    void addArticleToShoppingList(Article article);


    /**
     * deletes the article at index from the shopping list (and database)
     *
     * @param index the index of the article
     */
    void deleteArticleFromShoppingList(int index);

    /**
     * returns the article at index
     *
     * @param index the list index
     * @return the article at index
     */
    Article getArticleFromShoppingList(int index);


    /**
     * Returns the article  with the name from the buyingList
     *
     * @param name
     * @return
     */
    Article getArticleFromShoppingList(String name);


    /**
     * return the complete ItemList from the buyingListService
     * mainly used for the representation of the complete list in and Recycler- or LisView
     *
     * @return the ItemList of the buyingListService
     */
    public ArrayList<Article> getShoppingList();

    /**
     * @param index
     * @param newArticle
     */
    void updateArticleFromShoppingList(int index, Article newArticle);


    //....exchange between the article list..........


    /**
     * Transfers one article from the buying- to the availableList
     * i guess there is a better way /willi
     *
     * @param index
     */
    void transferArticleFromShoppingToAvailableList(int index);


    /**
     * Transfers one article from the available- to the buyingList
     * again i guess there is a better way /willi
     *
     * @param index
     */
    void transferArticleFromAvailableToShoppingList(int index);


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
     * @param index
     *         the index of the recipe
     */
    void deleteRecipe(int index);

    /**
     * returns the recipe at index
     *
     * @param index
     *         the list index
     * @return the recipe at index
     */
    Recipe getRecipe(int index);

    Recipe getRecipe(String name);

    void updateRecipe(int index, Recipe newRecipe);

    //....Observer..........

    Article getArticleFromRecipe(String recipeName, int articleIndex);


    /**
     * returns the complete ItemList from the recipeListService
     * mainly used for the representation of the complete list in and Recycler- or LisView
     *
     * @return the ItemList of the recipeListService
     */
    ArrayList<Recipe> getRecipeList();

    void addRecipeFromRecipeToToCookList(int index);

    void deleteArticlesWhenCooked(int position);


    void recipeToShoppingList(int index);

    void deleteFromToCook(int position);

    void overrideShoppingListCompletely(ArrayList<Article> synchronizedList);

     ArrayList<Recipe> getToCookList();
}



