package com.example.cookshop.model.database;

import com.example.cookshop.items.Article;
import com.example.cookshop.items.Recipe;
import com.example.cookshop.items.Step;

import java.util.ArrayList;

/**
 * The Interface for {@link DatabaseHelper}
 */
public interface Database
{

    //....Insertion..........

    /**
     * Inserts a single Recipe (plus its steps and articles) into the database
     *
     * @param recipe
     *         the recipe to be inserted
     * @return true if the recipe was successfully inserted / false if not
     */
    public boolean insertRecipe(Recipe recipe);

    /**
     * Inserts a list of  recipes (plus its steps and articles) into the database,
     *
     * @param recipes
     *         the list of recipes
     * @return true if the recipes are successfully inserted / false if not
     */
    public boolean insertSeveralRecipes(ArrayList<Recipe> recipes);

    /**
     * Inserts on {@link Step } into the Database
     *
     * @param belonging
     *         the name of the recipe to which the article belongs
     * @param step
     *         The step to be inserted
     * @return true, if the articl e was inserted successfully else false
     */
    public boolean insertStep(String belonging, Step step);

    /**
     * Inserts a list of Steps into the database
     *
     * @param recipeName#
     *         the name of the Recipe / the belonging of the steps
     * @param steps
     *         the list of steps
     * @return
     */
    public boolean insertSeveralSteps(String recipeName, ArrayList<Step> steps);
    /**
     * Inserts a list of articles to the database, uses insertArticle for every article in the list
     *
     * @param belonging
     * @param articles
     * @return
     */
    public boolean insertSeveralArticles(String belonging, ArrayList<Article> articles);

    /**
     * Inserts one Article
     *
     * @param belonging
     *         the belonging of the article
     * @param article
     *         the article itself
     * @return
     */
    public boolean insertArticle(String belonging, Article article);


    //....Deletion..........

    /**
     * Delete the recipe and all of its steps and articles from the database
     *
     * @param name
     *         the name of the recipe
     */
    public void deleteRecipe(String name);

    /**
     * This method removes the article with "name" and "belonging" from the database
     * both parameters are needed, because there can be 2 or more articles with the same name, but for example one in a recipe and another in the buyinglist.
     * That means it is needed to differentiate between them to not unintentionally delete another article
     *
     * @param name
     *         the name of the Article
     * @param belonging
     *         the belonging of the Article
     */
    public void deleteArticle(String name, String belonging);

    /**
     * Removes one specific step from one recipe
     *
     * @param name
     *         the name of the Step
     * @param belonging
     *         the name of the recipe to which the step belongs
     */
    public void deleteStep(String name, String belonging);


    //....Retrieve ..........

    /**
     * This method retrieves all Recipes from the Database
     * It is used by the recipeListService
     *
     * @return ArrayList<Article> with all matching Articles
     */
    public ArrayList<Recipe> retrieveAllRecipes();

    /**
     * This method retrieves all Articles with
     * COLUMN_ARTICLE_BELONGING = belonging
     * It is used by the ListServices to retrieve their data
     * at the start of the application
     *
     * @param belonging
     *         and String which indicates to which list or recipe
     *         an article in the database belongs
     * @return ArrayList<Article> with all matching Articles
     */
    public ArrayList<Article> retrieveAllArticlesFrom(String belonging);


    //....Update ..........

    public void updateRecipe(String oldName, Recipe newRecipe);

    /**
     * Updates the Article (identified through oldName a
     * nd belonging), with the values of
     * article
     *
     * @param oldName
     *         The name of the updated Article(before changes was made
     *         / can be the same as the name of the new article ).
     * @param belonging
     *         Teh belonging of the Article (Available, Buying or the name of the Recipe)
     * @param article
     *         the updated version of the article
     */
    public void updateArticle(String oldName, String belonging, Article article);

    /**
     * Updates the Step  (identified through oldName a
     * nd belonging), with the values of
     * step
     *
     * @param oldName
     *         The name of the updated step (before changes was made
     *         / can be the same as the name of the new step ).
     * @param belonging
     *         The belonging of the step (Available, Buying or the name of the Recipe)
     * @param step
     *         the updated version of the step
     */
    public void updateStep(String oldName, String belonging, Step step);


    public void reset();
}
