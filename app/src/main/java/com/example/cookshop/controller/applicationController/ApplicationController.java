package com.example.cookshop.controller.applicationController;

import android.content.Context;
import android.util.Log;

import com.example.cookshop.items.Article;
import com.example.cookshop.items.Recipe;
import com.example.cookshop.controller.Observabel;
import com.example.cookshop.controller.Observer;
import com.example.cookshop.model.database.DatabaseHelper;
import com.example.cookshop.model.listManagement.AvailableListManager;
import com.example.cookshop.model.listManagement.RecipeListManager;
import com.example.cookshop.model.listManagement.ShoppingListManager;
import com.example.cookshop.model.listManagement.ToCookListManager;

import java.util.ArrayList;

/**
 * This class is  the Controller of the application.
 * -It is the only objects which communicates with the model (namely the ListServices and the DatabaseService)
 * -It sets/updates the Views via the implemented ObserverPattern (see {@link Observer } and {@link Observabel})
 * and the implemented methods in here and in the Activities.
 *
 */
public class ApplicationController implements Observabel, Controller
{
  private final String TAG = this.getClass().getSimpleName();
  /**
     * The saved instance of this class
     * (singleton design Pattern)
     */
    private static ApplicationController ourInstance;

    private AvailableListManager availableListManager;
    private RecipeListManager recipeListManager;
    private ShoppingListManager shoppingListManager;
    private ToCookListManager toCookListManager;

    private static ArrayList<Observer> onRecipeListChangeListener;
    private static ArrayList<Observer> onAvailableListChangeListener;
    private static ArrayList<Observer> onShoppingListChangeListener;
    private static ArrayList<Observer> onToCookListChangeListener;


  //....Constructor..........


    /**
     * Private constructor
     */
    private ApplicationController()
    {

    }


    public void initialize(Context context, RecipeListManager recipeListService, ShoppingListManager shoppingListService, AvailableListManager availableListService,
                           ToCookListManager toCookListService)
    {

            this.availableListManager = availableListService;
            this.shoppingListManager = shoppingListService;
            this.recipeListManager = recipeListService;
            this.toCookListManager = toCookListService;

            //Observer Lists :
            onAvailableListChangeListener = new ArrayList<>();
            onShoppingListChangeListener = new ArrayList<>();
            onRecipeListChangeListener = new ArrayList<>();
            onToCookListChangeListener = new ArrayList<>();

            if(onRecipeListChangeListener==null){
              Log.e(TAG,"onRecipeListChangeListener is null (initialize)");
            }
    }

    public static ApplicationController getInstance()
    {
        if (ourInstance == null)
        {
            ourInstance = new ApplicationController();
        }
        return ourInstance;
    }

    /**
     * Constructor to set a mockDatabaseService.
     * To test the ListServices
     * @param
     */
    public void initialize(DatabaseHelper mockDatabaseHelper)
    {
        this.availableListManager = new AvailableListManager(mockDatabaseHelper);
        this.shoppingListManager = new ShoppingListManager(mockDatabaseHelper);
        this.recipeListManager = new RecipeListManager(mockDatabaseHelper);
        this.toCookListManager = new ToCookListManager(mockDatabaseHelper);

        //Observer Lists :
        onAvailableListChangeListener = new ArrayList<>();
        onShoppingListChangeListener = new ArrayList<>();
        onToCookListChangeListener = new ArrayList<>();
        onRecipeListChangeListener = new ArrayList<>();
    }


    @Override
    public void addArticleToAvailableList(Article article)
    {
        this.availableListManager.addArticleIntelligent(article);
        this.onAvailableListChange();
    }


    @Override
    public void deleteArticleFromAvailableList(int index)
    {
        this.availableListManager.removeItem(index);
        this.onAvailableListChange();
    }


    @Override
    public Article getArticleFromAvailableList(int index)
    {
        return (Article) this.availableListManager.getItem(index);
    }




    //....shoppingList..........

    @Override
    public Article getArticleFromAvailableList(String name)
    {
        return this.availableListManager.searchForArticle(name);
    }


    @Override
    public ArrayList<Article> getAvailableList()
    {
        return this.availableListManager.getItemList();
    }


    @Override
    public void updateArticleFromAvailableList(int index, Article newArticle)
    {
        this.availableListManager.updateArticle(index, newArticle);
        this.onAvailableListChange();
    }


    @Override
    public void addArticleToShoppingList(Article article)
    {
        this.shoppingListManager.addArticleIntelligent(article);
        this.onShoppingListChange();
    }


    @Override
    public void deleteArticleFromShoppingList(int index)
    {
        this.shoppingListManager.removeItem(index);
        this.onShoppingListChange();
    }


    @Override
    public Article getArticleFromShoppingList(int index)
    {
        return (Article) this.shoppingListManager.getItem(index);
    }


    @Override
    public Article getArticleFromShoppingList(String name)
    {
        return this.shoppingListManager.searchForArticle(name);
    }


    @Override
    public ArrayList<Article> getShoppingList()
    {
        return this.shoppingListManager.getItemList();
    }


    @Override
    public void updateArticleFromShoppingList(int index, Article newArticle)
    {
        this.shoppingListManager.updateArticle(index, newArticle);
        this.onShoppingListChange();
    }


    @Override
    public void overrideShoppingListCompletely(ArrayList<Article> synchronizedList)
    {
        this.shoppingListManager.overrideListCompletely(synchronizedList);
    }




    //....exchange between the article list..........


    @Override
    public void transferArticleFromShoppingToAvailableList(int index)
    {
        Article transferredArticle = (Article) this.shoppingListManager.getItem(index);
        this.shoppingListManager.removeItem(index);
        this.availableListManager.addArticleIntelligent(transferredArticle);
        this.onShoppingListChange();
        this.onAvailableListChange();
    }


    @Override
    public void transferArticleFromAvailableToShoppingList(int index)
    {
        Article transferredArticle = (Article) this.availableListManager.getItem(index);
        this.availableListManager.removeItem(index);
        this.shoppingListManager.addArticleIntelligent(transferredArticle);
        this.onShoppingListChange();
        this.onAvailableListChange();
    }




    //....Recipe List Access..........

    @Override
    public void addRecipe(Recipe recipe)
    {
        this.recipeListManager.addItem(recipe);
        this.onRecipeListChange();
    }


    @Override
    public void deleteRecipe(int index)
    {
        this.recipeListManager.removeItem(index);
        this.onRecipeListChange();
    }


    @Override
    public Recipe getRecipe(int index)
    {
        return (Recipe) this.recipeListManager.getItem(index);
    }


    @Override
    public Recipe getRecipe(String name)
    {
        return (Recipe) this.recipeListManager.getItem(name);
    }


    @Override
    public void updateRecipe(int index, Recipe newRecipe)
    {
        this.recipeListManager.updateRecipe(index, newRecipe);
        this.onRecipeListChange();
    }


    @Override
    public Article getArticleFromRecipe(String recipeName, int articleIndex)
    {
        Recipe recipe = (Recipe) recipeListManager.getItem(recipeName);
        return recipe.getArticles().get(articleIndex);
    }


    @Override
    public ArrayList<Recipe> getRecipeList()
    {
        return this.recipeListManager.getItemList();
    }


    @Override
    public ArrayList<Recipe> getToCookList(){ return this.toCookListManager.getItemList();}


    public void addRecipeFromRecipeToToCookList(int index)
    {
      Recipe recipe = (Recipe) this.recipeListManager.getItem(index);
      ArrayList neededArticles = recipe.getArticles();
      ArrayList notAvailableArticles = this.availableListManager.getListOfNotAvailableArticles(neededArticles);

      Log.d(TAG, ": addRecipeFromRecipeToToCookList: before if-clause");

      if(notAvailableArticles.size()!=0)
      {
        Article article = (Article) notAvailableArticles.get(0);
        Log.d(TAG, ": addRecipeFromRecipeToToCookList: neededArticles!=null: " + article.getName());
        recipeToShoppingList(index);
        this.onShoppingListChange();
      }
      else
          {
        Log.d(TAG, ": addRecipeFromRecipeToToCookList: else, before we set up!");
        recipe.setOnToCook(true);
        Log.d(TAG, ": addRecipeFromRecipeToToCookList: else, after setOnToCook(true)");
        this.toCookListManager.addRecipeIntelligent(recipe);
        Log.d(TAG, ": addRecipeFromRecipeToToCookList: else, after addRecipeIntelligent(recipe)");
      }
      this.onToCookListChange();
      this.onRecipeListChange();

    }


    public void deleteArticlesWhenCooked(int position)
    {
      Recipe recipe = (Recipe) this.toCookListManager.getItem(position);
      ArrayList neededArticles = recipe.getArticles();

      for(int i = 0; i < neededArticles.size(); i++)
      {
        // hier müssen entsprechende Article von AvailableList entfernt werden
      }
    }


    @Override
  public void recipeToShoppingList(int index)
    {
        Recipe  recipe   = (Recipe) this.recipeListManager.getItem(index);
        ArrayList neededArticles = recipe.getArticles();
        ArrayList notAvailableArticles = this.availableListManager.getListOfNotAvailableArticles(neededArticles);
        this.shoppingListManager.addSeveralArticlesIntelligent(notAvailableArticles);
        this.onShoppingListChange();
        this.onRecipeListChange();
    }


    @Override
    public void deleteFromToCook(int position)
    {
      Recipe recipe = (Recipe) this.toCookListManager.getItem(position);
      recipe.setOnToCook(false);

      ArrayList<Recipe> tempRecipeList = this.recipeListManager.getItemList();

      for (int i = 0; i < tempRecipeList.size(); i++)
      {
        if(recipe.getName().equals(tempRecipeList.get(i).getName())){
          this.updateRecipe(i, recipe);
          break;
        }
      }
      this.toCookListManager.removeItem(position);
      this.onToCookListChange();
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
    public void registerOnShoppingListChangeListener(Observer observer)
    {
        onShoppingListChangeListener.add(observer);
    }


    @Override
    public void unregisterOnShoppingListChangeListener(Observer observer)
    {
        onShoppingListChangeListener.remove(observer);
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
        for (int i = 0; i < this.onShoppingListChangeListener.size(); i++)
        {
            this.onShoppingListChangeListener.get(i).onChange();
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

}
