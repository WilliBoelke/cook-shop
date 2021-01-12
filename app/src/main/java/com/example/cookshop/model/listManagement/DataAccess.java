package com.example.cookshop.model.listManagement;

import android.content.Context;
import android.util.Log;

import com.example.cookshop.items.Article;
import com.example.cookshop.items.Recipe;
import com.example.cookshop.model.Observabel;
import com.example.cookshop.model.Observer;
import com.example.cookshop.model.database.DatabaseHelper;
import com.example.cookshop.view.main.FragmentToCookList;

import java.util.ArrayDeque;
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
  private final String TAG = this.getClass().getSimpleName();
  /**
     * The saved instance of this class
     * (singleton design Pattern)
     */
    private static DataAccess ourInstance;

    private AvailableListManager availableListService;
    private RecipeListManager recipeListService;
    private ShoppingListManager shoppingListService;
    private ToCookListManager toCookListService;

    private static ArrayList<Observer> onRecipeListChangeListener;
    private static ArrayList<Observer> onAvailableListChangeListener;
    private static ArrayList<Observer> onBuyingListChangeListener;
    private static ArrayList<Observer> onToCookListChangeListener;


  //....Constructor..........


    /**
     * Private constructor
     */
    private DataAccess()
    {

    }

    public void initialize(Context context, RecipeListManager recipeListService, ShoppingListManager shoppingListService, AvailableListManager availableListService,
                           ToCookListManager toCookListService)
    {
            DatabaseHelper databaseService = new DatabaseHelper(context);

            this.availableListService = availableListService;
            this.shoppingListService = shoppingListService;
            this.recipeListService = recipeListService;
            this.toCookListService = toCookListService;

            //Observer Lists :
            onAvailableListChangeListener = new ArrayList<>();
            onBuyingListChangeListener = new ArrayList<>();
            onRecipeListChangeListener = new ArrayList<>();
            onToCookListChangeListener = new ArrayList<>();

            if(onRecipeListChangeListener==null){
              Log.e(TAG,"onRecipeListChangeListener is null (initialize)");
            }
    }

    public static DataAccess getInstance()
    {
        if (ourInstance == null)
        {
            ourInstance = new DataAccess();
        }
        return ourInstance;
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
     * Adds one article to the shopping(and database)
     *
     * @param article
     *         the article to be added
     */
    public void addArticleToShoppingList(Article article)
    {
        this.shoppingListService.addArticleIntelligent(article);
        this.onShoppingListChange();
    }

    /**
     * deletes the article at index from the shopping list (and database)
     *
     * @param index
     *         the index of the article
     */
    public void deleteArticleShoppingList(int index)
    {
        this.shoppingListService.removeItem(index);
        this.onShoppingListChange();
    }

    /**
     * returns the article at index
     *
     * @param index
     *         the list index
     * @return the article at index
     */
    public Article getArticleFromShoppingList(int index)
    {
        return (Article) this.shoppingListService.getItem(index);
    }

    /**
     * Returns the article  with the name from the buyingList
     *
     * @param name
     * @return
     */
    public Article getArticleFromShoppingList(String name)
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
        this.onShoppingListChange();
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
        this.onShoppingListChange();
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
        this.onShoppingListChange();
        this.onAvailableListChange();
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
        this.recipeListService.updateRecipe(index, newRecipe);
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

    public void addRecipeFromRecipeToToCookList(int index){
      Recipe recipe = (Recipe) this.recipeListService.getItem(index);
      ArrayList neededArticles = recipe.getArticles();
      ArrayList notAvailableArticles = this.availableListService.getListOfNotAvailableArticles(neededArticles);
      if(notAvailableArticles.size()!=0){
        Article article = (Article) notAvailableArticles.get(0);
        Log.e(TAG, ": addRecipeFromRecipeToToCookList: neededArticles!=null: " + article.getName());
        recipeToBuyingList(index);
        this.onShoppingListChange();
      }else{
        this.toCookListService.addItem(recipe);
        this.onToCookListChange();
      }
      this.onRecipeListChange();

    }

    public void deleteArticlesWhenCooked(int position)
    {
      Recipe recipe = (Recipe) this.toCookListService.getItem(position);
      ArrayList neededArticles = recipe.getArticles();

      for(int i = 0; i < neededArticles.size(); i++)
      {
        // hier müssen entsprechende Article von AvailableList entfernt werden
      }



    }


  public void recipeToBuyingList(int index)
    {
        Recipe  recipe   = (Recipe) this.recipeListService.getItem(index);
        ArrayList neededArticles = recipe.getArticles();
        ArrayList notAvailableArticles = this.availableListService.getListOfNotAvailableArticles(neededArticles);
        this.shoppingListService.addSeveralArticlesIntelligent(notAvailableArticles);
        this.onShoppingListChange();
        this.onRecipeListChange();
    }

    @Override
    public void registerOnRecipeListChangeListener(Observer observer)
    {
      if(observer==null){
        Log.e(TAG,"Observer is null");
      }
      if(onRecipeListChangeListener==null){
        Log.e(TAG,"onRecipeListChangeListener is null");
      }
      Log.e(TAG, ": registerOnRecipeListChangeListener");
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
    public void registerOnToCookListChangeListener(Observer observer)
    {
        if(observer==null){
          Log.e(TAG, ": registerOnToCookListchangeListener: Observer = null");
        }else{
          Log.e(TAG, ": registerOnToCookListchangeListener: Observer != null");
        }
      if(onToCookListChangeListener==null){
        Log.e(TAG, ": registerOnToCookListchangeListener: onToCookListChangeListener = null");
      }else{
        Log.e(TAG, ": registerOnToCookListchangeListener: onToCookListChangeListener != null");
      }
        onToCookListChangeListener.add(observer);
    }

    @Override
    public void unregisterOnToCookListChangeListener(Observer observer)
    {
        onToCookListChangeListener.remove(observer);
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
    public void onShoppingListChange()
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

    @Override
    public void onToCookListChange()
    {
      for (int i = 0; i < this.onToCookListChangeListener.size(); i++)
      {
        this.onToCookListChangeListener.get(i).onChange();
      }
    }


  //....Synchronization..........


    public void overrideShoppingListCompletely(ArrayList<Article> synchronizedList)
    {
        this.shoppingListService.overrideListCompletely(synchronizedList);
    }

    public ArrayList<Recipe> getToCookList(){ return this.toCookListService.getItemList();}


}
