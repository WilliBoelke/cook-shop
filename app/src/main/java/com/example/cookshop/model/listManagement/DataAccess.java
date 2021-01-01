package com.example.cookshop.model.listManagement;

import android.content.Context;

import com.example.cookshop.items.Article;
import com.example.cookshop.items.Recipe;
import com.example.cookshop.model.Observabel;
import com.example.cookshop.model.Observer;
import com.example.cookshop.model.database.DatabaseHelper;

import java.util.ArrayList;

/**
 * This class is firstly the Controller of the application.
 * -It is the only objects which communicates with the model (namely the ListServices and the DatabaseService)
 * -It sets/updates the Views via the implemented ObserverPattern (see {@link Observer } and {@link Observabel})
 * and the implemented methods in here and in the Activities.
 * <p>
 * Secondly it is the only Object which Provides Access to all Model interactions
 *
 * @author WilliBoelke
 */
public class DataAccess implements Observabel
{
    /**
     * The saved instance of this class
     * (singleton design Pattern)
     */
    private static DataAccess ourInstance;

    private AvailableListService availableListService;
    private RecipeListService recipeListService;
    private ShoppingListService shoppingListService;

    private ArrayList<Observer> onRecipeListChangeListener;
    private ArrayList<Observer> onAvailableListChangeListener;
    private ArrayList<Observer> onBuyingListChangeListener;


    //....Constructor..........


    public DataAccess(Context context)
    {
        DatabaseHelper databaseService = new DatabaseHelper(context);
       // availableListService = new AvailableListService(databaseService);
        shoppingListService = new ShoppingListService(databaseService);

        //Observer Lists :
        onAvailableListChangeListener = new ArrayList<>();
        onBuyingListChangeListener = new ArrayList<>();
        onRecipeListChangeListener = new ArrayList<>();
    }

    /**
     * Constructor to set mockObjects to test this class
     *
     * @param mockRecipeListService
     * @param mockBuyingListService
     * @param mockAvailableListService
     */
    public DataAccess(RecipeListService mockRecipeListService, ShoppingListService mockBuyingListService, AvailableListService mockAvailableListService)
    {

        availableListService = mockAvailableListService;
        shoppingListService = mockBuyingListService;
        recipeListService = mockRecipeListService;

        //Observer Lists :
        onAvailableListChangeListener = new ArrayList<>();
        onBuyingListChangeListener = new ArrayList<>();
        onRecipeListChangeListener = new ArrayList<>();
    }

    /**
     * Constructor to set a mockDatabaseService.
     * To test the ListServices
     * @param mockDatabaseService
     */
    public DataAccess(DatabaseHelper mockDatabaseService)
    {
        availableListService = new AvailableListService(mockDatabaseService);
        shoppingListService = new ShoppingListService(mockDatabaseService);
        recipeListService = new RecipeListService(mockDatabaseService);

        //Observer Lists :
        onAvailableListChangeListener = new ArrayList<>();
        onBuyingListChangeListener = new ArrayList<>();
        onRecipeListChangeListener = new ArrayList<>();
    }

    /**
     * Adds one article to the availableList (and database)
     *
     * @param article
     *         the article to be added
     */
    public void addArticleToAvailableList(Article article)
    {
        this.availableListService.addArticleIntelligent(article);
        this.onAvailableListChange();
    }

    /**
     * deletes the article at index from the availableList (and database)
     *
     * @param index
     *         the index of the article
     */
    public void deleteArticleFromAvailableList(int index)
    {
        this.availableListService.removeItem(index);
        this.onAvailableListChange();
    }

    /**
     * returns the article at index
     *
     * @param index
     *         the list index
     * @return teh article at index
     */
    public Article getArticleFromAvailableList(int index)
    {
        return (Article) this.availableListService.getItem(index);
    }

    //....BuyingListAccess..........

    /**
     * Returns the article  with the name from the availableList
     *
     * @param name
     * @return
     */
    public Article getArticleFromAvailableList(String name)
    {
        return this.availableListService.searchForArticle(name);
    }

    /**
     * return the complete ItemList from the availableLists service
     * mainly used for the representation of the complete list in and Recycler- or LisView
     *
     * @return the ItemList of the availableList
     */
    public ArrayList<Article> getAvailableList()
    {
        return this.availableListService.getItemList();
    }

    /**
     * @param index
     * @param newArticle
     */
    public void updateArticleFromAvailableList(int index, Article newArticle)
    {
        this.availableListService.updateArticle(index, newArticle);
        this.onAvailableListChange();
    }

    /**
     * Adds one article to the buyingList(and database)
     *
     * @param article
     *         the article to be added
     */
    public void addArticleToBuyingList(Article article)
    {
        this.shoppingListService.addArticleIntelligent(article);
        this.onBuyingListChange();
    }

    /**
     * deletes the article at index from the buyingList (and database)
     *
     * @param index
     *         the index of the article
     */
    public void deleteArticleTheBuyingList(int index)
    {
        this.shoppingListService.removeItem(index);
        this.onBuyingListChange();
    }

    /**
     * returns the article at index
     *
     * @param index
     *         the list index
     * @return the article at index
     */
    public Article getArticleFromBuyingList(int index)
    {
        return (Article) this.shoppingListService.getItem(index);
    }

    /**
     * Returns the article  with the name from the buyingList
     *
     * @param name
     * @return
     */
    public Article getArticleFromBuyingList(String name)
    {
        return this.shoppingListService.searchForArticle(name);
    }

    /**
     * return the complete ItemList from the buyingListService
     * mainly used for the representation of the complete list in and Recycler- or LisView
     *
     * @return the ItemList of the buyingListService
     */
    public ArrayList<Article> getBuyingList()
    {
        return this.shoppingListService.getItemList();
    }


    /**
     * @param index
     * @param newArticle
     */
    public void updateArticleFromBuyingList(int index, Article newArticle)
    {
        this.shoppingListService.updateArticle(index, newArticle);
        this.onBuyingListChange();
    }

    //....exchange between the article list..........


    /**
     * Transfers one article from the buying- to the availableList
     * i guess there is a better way /willi
     *
     * @param index
     */
    public void transferArticleFromBuyingToAvailableList(int index)
    {
        Article transferredArticle = (Article) this.shoppingListService.getItem(index);
        this.shoppingListService.removeItem(index);
        this.availableListService.addArticleIntelligent(transferredArticle);
        this.onAvailableListChange();
    }

    /**
     * Transfers one article from the available- to the buyingList
     * again i guess there is a better way /willi
     *
     * @param index
     */
    public void transferArticleFromAvailableToBuyingList(int index)
    {
        Article transferredArticle = (Article) this.availableListService.getItem(index);
        this.availableListService.removeItem(index);
        this.shoppingListService.addArticleIntelligent(transferredArticle);
        this.onBuyingListChange();
    }


    //....Recipe List Access..........

    /**
     * Adds one recipe to the recipeList(and database)
     *
     * @param recipe
     *         the recipe to be added
     */
    public void addRecipe(Recipe recipe)
    {
        this.recipeListService.addItem(recipe);
        this.onRecipeListChange();
    }

    /**
     * deletes the recipe at index from the recipeList (and database)
     *
     * @param index
     *         the index of the recipe
     */
    public void deleteRecipe(int index)
    {
        this.recipeListService.removeItem(index);
        this.onRecipeListChange();
    }

    /**
     * returns the recipe at index
     *
     * @param index
     *         the list index
     * @return the recipe at index
     */
    public Recipe getRecipe(int index)
    {
        return (Recipe) this.recipeListService.getItem(index);
    }


    public Recipe getRecipe(String name)
    {
        return (Recipe) this.recipeListService.getItem(name);
    }


    public void updateRecipe(int index, Recipe newRecipe)
    {
        //this.recipeListService.updateRecipe(index, newRecipe);
        this.onRecipeListChange();
    }


    //....Observer..........

    public Article getArticleFromRecipe(String recipeName, int articleIndex)
    {
        Recipe recipe = (Recipe) recipeListService.getItem(recipeName);
        return recipe.getArticles().get(articleIndex);
    }

    /**
     * returns the complete ItemList from the recipeListService
     * mainly used for the representation of the complete list in and Recycler- or LisView
     *
     * @return the ItemList of the recipeListService
     */
    public ArrayList<Recipe> getRecipeList()
    {
        return this.recipeListService.getItemList();
    }

    public void recipeToBuyingList(int index)
    {
        Recipe  recipe   = (Recipe) this.recipeListService.getItem(index);
        ArrayList neededArticles = recipe.getArticles();
        ArrayList notAvailableArticles = this.availableListService.getListOfNotAvailableArticles(neededArticles);
        this.shoppingListService.addSeveralArticlesIntelligent(notAvailableArticles);
        this.onBuyingListChange();
    }

    @Override
    public void registerOnRecipeListChangeListener(Observer observer)
    {
        onRecipeListChangeListener.add(observer);
    }

    @Override
    public void unregisterOnRecipeListChangeListener(Observer observer)
    {
        onRecipeListChangeListener.remove(observer);
    }

    @Override
    public void registerOnBuyingListChangeListener(Observer observer)
    {
        onBuyingListChangeListener.add(observer);
    }

    @Override
    public void unregisterOnBuyingListChangeListener(Observer observer)
    {
        onBuyingListChangeListener.remove(observer);
    }

    @Override
    public void registerOnAvailableListChangeListener(Observer observer)
    {
        onAvailableListChangeListener.add(observer);
    }

    @Override
    public void unregisterOnAvailableListChangeListener(Observer observer)
    {
        onAvailableListChangeListener.remove(observer);
    }

    @Override
    public void onAvailableListChange()
    {
        for (int i = 0; i < this.onAvailableListChangeListener.size(); i++)
        {
            this.onAvailableListChangeListener.get(i).onChange();
        }
    }

    @Override
    public void onBuyingListChange()
    {
        for (int i = 0; i < this.onBuyingListChangeListener.size(); i++)
        {
            this.onBuyingListChangeListener.get(i).onChange();
        }
    }

    @Override
    public void onRecipeListChange()
    {
        for (int i = 0; i < this.onRecipeListChangeListener.size(); i++)
        {
            this.onRecipeListChangeListener.get(i).onChange();
        }
    }

}