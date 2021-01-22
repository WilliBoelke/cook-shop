package com.example.cookshop.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.cookshop.items.Article;
import com.example.cookshop.items.Category;
import com.example.cookshop.items.Recipe;
import com.example.cookshop.items.Step;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.example.cookshop.model.database.DatabaseNamingContract.COLUMN_STEP_BELONGING;

public class DatabaseHelper  extends SQLiteOpenHelper implements  Database, DatabaseNamingContract
{
    /**
     * Log tag
     */
    private final String  TAG = this.getClass().getSimpleName();
    private Context context;
     private  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
    //....Constructor..........

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // creating the Database at first app start
        String createRecipeTable = "CREATE TABLE IF NOT EXISTS " + TABLE_RECIPES + " (" + COLUMN_RECIPE_NAME + " TEXT PRIMARY KEY, " +
                COLUMN_RECIPE_ON_TO_COOK + " INTEGER, " +
                COLUMN_RECIPE_DESCRIPTION + " TEXT );";
        String createArticleTable = "CREATE TABLE IF NOT EXISTS " + TABLE_ARTICLE + " (" + COLUMN_ARTICLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ARTICLE_NAME + " TEXT NOT NULL, " +
                COLUMN_ARTICLE_DESCRIPTION + " TEXT, " +
                COLUMN_ARTICLE_AMOUNT + " INEGER,  " +
                COLUMN_ARTICLE_WEIGHT + " REAL,  " +
                COLUMN_ARTICLE_CATEGORY + " TEXT, " +
                COLUMN_ARTICLE_CREATION_DATE + " TEXT, " +
                COLUMN_ARTICLE_UPDATE_DATE + " TEXT, " +
                COLUMN_ARTICLE_BELONGING + " TEXT );";
        String createStepTable = "CREATE TABLE IF NOT EXISTS " + TABLE_STEPS + " (" + COLUMN_STEP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_STEP_NAME + " TEXT NOT NULL, " +
                COLUMN_STEP_DESCRIPTION + " TEXT, " +
                COLUMN_STEP_POSITION + " INTEGER, " +
                COLUMN_STEP_TIMER + " INTEGER, " +
                COLUMN_STEP_BELONGING + " TEXT NOT NULL );";

        db.execSQL(createRecipeTable);
        db.execSQL(createArticleTable);
        db.execSQL(createStepTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //TODO : We need a better solution here, but that works for now
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STEPS);
    }


    //....Insertion..........


    @Override
    public boolean insertRecipe(Recipe recipe)
    {
        Log.d(TAG, "Try To Insert Recipe .....");
        SQLiteDatabase database      = this.getWritableDatabase();
        ContentValues contentValues = generateRecipeContentValues(recipe);
        long           result1       = database.insert(TABLE_RECIPES, null, contentValues);
        database.close();

        boolean result2 = insertSeveralArticles(recipe.getName(), recipe.getArticles());
        boolean result3 = insertSeveralSteps(recipe.getName(), recipe.getSteps());

        //If one of the 3 insertion operation goes wrong we will fall into this if
        if (result1 == -1 || !result2 || !result3)
        {
            //Detailed Log output
            Log.e(TAG, "insertRecipe : Something went wrong while inserting the Recipe : ");
            Log.e(TAG, "result1 = " + result1 + "result2 = " + result2 + "result3 = " + result3);
            return false;
        }
        Log.v(TAG, "Recipe Inserted");
        return true;
    }

    @Override
    public boolean insertSeveralRecipes(ArrayList<Recipe> recipes)
    {
        Log.v(TAG, "insertSeveralRecipes : Trying to add a list of Recipes to the database ..." );
        for (int i = 0; i < recipes.size(); i++)
        {
            boolean check = this.insertRecipe(recipes.get(i));

            if (check == false)
            {
                /* Interrupts the complete method if
            something goes wrong, wont even save
            the rest of the list...i don't know if this is the best solution */
                Log.e(TAG, "insertSeveralRecipes : Something went wrong while inserting the Recipe  " + recipes.get(i).getName() + " at index " + i );
                return  false;
            }
        }
        Log.v(TAG, "insertSeveralRecipes : Inserted recipes successfully" );
        return true;
    }

    @Override
    public boolean insertStep(String belonging, Step step)
    {
        Log.v(TAG, "insertStep : Trying to add step " + step.getName() + " to " + belonging );
        SQLiteDatabase database      = this.getWritableDatabase();
        ContentValues  contentValues = generateStepContentValues(step, belonging);
        long           result        = database.insert(TABLE_STEPS, null, contentValues);
        database.close();
        Log.v(TAG, "insertStep : Added  " + step.getName() + " to " + belonging );
        return result != -1;
    }


    @Override
    public boolean insertSeveralSteps(String recipeName, ArrayList<Step> steps)
    {
        Log.v(TAG, "insertSeveralSteps : Trying to add a list of steps to the database ..." );
        for (int i = 0; i < steps.size(); i++)
        {
            boolean check = this.insertStep(recipeName, steps.get(i));
            if (check == false)
            {
                Log.e(TAG, "insertSeveralSteps: could not add Step " + steps.get(i).getName() + " at index " + i);
                return false;
            }
        }
        Log.v(TAG, "insertSeveralRecipes : Inserted steps successfully" );
        return true;
    }

    @Override
    public boolean insertSeveralArticles(String belonging, ArrayList<Article> articles)
    {
        for (int i = 0; i < articles.size(); i++)
        {
            boolean check = this.insertArticle(belonging, articles.get(i));
            if (check == false)
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean insertArticle(String belonging, Article article)
    {
        SQLiteDatabase database      = this.getWritableDatabase();

        ContentValues  contentValues = generateArticleContentValues(article, belonging);

        long result1 = database.insert(TABLE_ARTICLE, null, contentValues);
        database.close();

        return result1 != -1;
    }


    //....Deletion..........


    @Override
    public void deleteRecipe(String name)
    {
        Log.v(TAG, "deleteRecipe : Try to delete " + name + "..." );
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_RECIPES, COLUMN_RECIPE_NAME + " = ? ", new String[]{name});
        database.delete(TABLE_ARTICLE, COLUMN_ARTICLE_BELONGING + " = ? ", new String[]{name});
        database.delete(TABLE_STEPS, COLUMN_STEP_BELONGING + " = ? ", new String[]{name});
        database.close();
        Log.v(TAG, "deleteRecipe : Deleted " + name  );
    }

    @Override
    public void deleteArticle(String name, String belonging)
    {
        Log.v(TAG, "deleteArticle : Try to delete " + name + ", from " + belonging + "...");
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_ARTICLE, COLUMN_ARTICLE_BELONGING + " = ?  AND " + COLUMN_ARTICLE_NAME + " = ? ", new String[]{belonging, name});
        Log.v(TAG, "deleteArticle : Deleted  " + name + ", from " + belonging);
        database.close();
    }

    @Override
    public void deleteStep(String name, String belonging)
    {
        Log.v(TAG, "deleteStep : Try to delete " + name + ", from " + belonging + "...");
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_STEPS, COLUMN_STEP_BELONGING + " = ?  AND " + COLUMN_STEP_NAME + " = ? ", new String[]{belonging, name});
        database.close();
        Log.v(TAG, "deleteStep : Deleted  " + name + ", from " + belonging);
    }


    //....Retrieve ..........


    @Override
    public ArrayList<Recipe> retrieveAllRecipes()
    {
        SQLiteDatabase    database        = this.getReadableDatabase();
        Cursor cursor          = database.query(TABLE_RECIPES, null, null, null, null, null, null);
        ArrayList<Recipe> recipeArrayList = new ArrayList<>();
        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                do
                {
                    String name = cursor.getString(cursor.getColumnIndex(COLUMN_RECIPE_NAME)); // I store the name in a variable because its needed 3 times (name for the recipe, to retrieve the steps and the articles)
                    Boolean onToCook = cursor.getInt(cursor.getColumnIndex(COLUMN_RECIPE_ON_TO_COOK)) !=0;
                    Recipe recipe = new Recipe(name, cursor.getString(cursor.getColumnIndex(COLUMN_RECIPE_DESCRIPTION)), retrieveAllArticlesFrom(name), retrieveAllStepsFrom(name));
                    recipe.setOnToCook(onToCook);
                    recipeArrayList.add(recipe);

                }
                while (cursor.moveToNext());
            }
        }
        database.close();
        return recipeArrayList;
    }

    @Override
    public ArrayList<Article> retrieveAllArticlesFrom(String belonging)
    {
        SQLiteDatabase     database         = this.getReadableDatabase();
        Cursor             cursor           = database.query(TABLE_ARTICLE, null, COLUMN_ARTICLE_BELONGING + " = '" + belonging + "'", null, null, null, null);
        ArrayList<Article> articleArrayList = new ArrayList<>();
        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                do
                {
                    String name        = cursor.getString(cursor.getColumnIndex(COLUMN_ARTICLE_NAME));
                    String description = cursor.getString(cursor.getColumnIndex(COLUMN_ARTICLE_DESCRIPTION));
                    int    amount      = cursor.getInt(cursor.getColumnIndex(COLUMN_ARTICLE_AMOUNT));
                    double weight      = cursor.getDouble(cursor.getColumnIndex(COLUMN_ARTICLE_WEIGHT));
                    Date  creationDate = Calendar.getInstance().getTime();
                    Date lastChangedDate =   Calendar.getInstance().getTime();
                    try
                    {
                        creationDate = simpleDateFormat.parse(cursor.getString(cursor.getColumnIndex(COLUMN_ARTICLE_CREATION_DATE)));
                      lastChangedDate = simpleDateFormat.parse(cursor.getString(cursor.getColumnIndex(COLUMN_ARTICLE_UPDATE_DATE)));
                    }
                     catch (ParseException e)
                     {
                          e.printStackTrace();
                     }
                    Category category;
                    //Needed to hard code these strings because i cant use the Categorys toString method inside the switch
                    switch (cursor.getString(cursor.getColumnIndex(COLUMN_ARTICLE_CATEGORY)))
                    {
                        case "Drink":
                            category = Category.DRINK;
                            break;
                        case "Meat":
                            category = Category.MEAT;
                            break;
                        case "Fruit":
                            category = Category.FRUIT;
                            break;
                        case "Vegetable":
                            category = Category.VEGETABLE;
                            break;
                        default:
                            category = Category.OTHERS;
                    }
                    articleArrayList.add(new Article(name, description, category, amount, weight, creationDate, lastChangedDate));
                }
                while (cursor.moveToNext());
            }
        }
        return articleArrayList;
    }

    /**
     * This method retrieves all Articles with
     * COLUMN_STEP_BELONGING = belonging
     * It is used by the RecipeListService while retrieving
     * the Recipe data at the application start
     *
     * @param belonging
     *         and String which indicates to which  recipe a step
     *         in the database belongs
     * @return ArrayList<Step> with all matching Steps
     */
    private ArrayList<Step> retrieveAllStepsFrom(String belonging)
    {
        SQLiteDatabase  database       = this.getReadableDatabase();
        Cursor          cursor         = database.query(TABLE_STEPS, null, COLUMN_ARTICLE_BELONGING + " = '" + belonging + "'", null, null, null, null);
        ArrayList<Step> stepsArrayList = new ArrayList<>();

        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                do
                {
                    stepsArrayList.add(new Step(cursor.getString(cursor.getColumnIndex(COLUMN_STEP_NAME)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_STEP_DESCRIPTION)),
                            cursor.getInt(cursor.getColumnIndex(COLUMN_STEP_TIMER)),
                            cursor.getInt(cursor.getColumnIndex(COLUMN_STEP_TIMER))));
                }
                while (cursor.moveToNext());
            }
        }
        database.close();
        return stepsArrayList;
    }


    //....Update ..........

    @Override
    public void updateRecipe(String oldName, Recipe newRecipe)
    {
        Log.v(TAG, "updateRecipe : Try to update Recipe with name  " + oldName + " to new Recipe  " + newRecipe.toString()  + "...");;

        SQLiteDatabase database  = this.getWritableDatabase();
        ContentValues  newValues = generateRecipeContentValues(newRecipe);
        database.update(TABLE_RECIPES, newValues, COLUMN_RECIPE_NAME + " = '" + oldName + "'", null);


        //TODO find a better solution
        database = this.getWritableDatabase();
        database.delete(TABLE_ARTICLE, COLUMN_ARTICLE_BELONGING + " = '" + oldName + "'", null);
        insertSeveralArticles(newRecipe.getName(), newRecipe.getArticles());

        //TODO find a better solution
        database = this.getWritableDatabase();
        database.delete(TABLE_STEPS, COLUMN_STEP_BELONGING + " = '" + oldName + "'", null);
        insertSeveralSteps(newRecipe.getName(), newRecipe.getSteps());
        database.close();
    }


    @Override
    public void updateArticle(String oldName, String belonging, Article article)
    {
        Log.d(TAG, "updateArticle: called with :  oldArticle Name : " +oldName + " new Article Name : " +article.getName() + " in list : " + belonging );
        SQLiteDatabase database  = this.getWritableDatabase();
        ContentValues  newValues = generateArticleContentValues(article, belonging);
        database.update(TABLE_ARTICLE, newValues, COLUMN_ARTICLE_NAME + " = '" + oldName + "' AND " + COLUMN_ARTICLE_BELONGING + " = '" + belonging + "'", null);
        database.close();
    }


    @Override
    public void updateStep(String oldName, String belonging, Step step)
    {
        SQLiteDatabase database  = this.getReadableDatabase();
        ContentValues  newValues = generateStepContentValues(step, belonging);
        database.update(TABLE_STEPS, newValues, COLUMN_STEP_NAME + " = '" + oldName + "' AND " + COLUMN_STEP_BELONGING + " = '" + belonging + "'", null);
        database.close();
    }


    //....GetItemContentValues ..........


    /**
     * Generates ContentValues vor a Single Article
     *
     * @param article
     *         the Article
     * @param belonging
     *         the belonging of the Article
     * @return the content values
     */
    private ContentValues generateArticleContentValues(Article article, String belonging)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ARTICLE_NAME, article.getName());
        contentValues.put(COLUMN_ARTICLE_DESCRIPTION, article.getDescription());
        contentValues.put(COLUMN_ARTICLE_AMOUNT, article.getAmount());
        contentValues.put(COLUMN_ARTICLE_WEIGHT, article.getWeight());
        contentValues.put(COLUMN_ARTICLE_CATEGORY, article.getCategory().toString());
        contentValues.put(COLUMN_ARTICLE_CREATION_DATE, simpleDateFormat.format(article.getDateOfCreation()));
       contentValues.put(COLUMN_ARTICLE_UPDATE_DATE, simpleDateFormat.format(article.dateOfUpdate));
        contentValues.put(COLUMN_ARTICLE_BELONGING, belonging);
        return contentValues;
    }

    /**
     * Generates ContentValues vor a Single Step
     *
     * @param step
     *         the Step
     * @param belonging
     *         the belonging of the step
     * @return the content values
     */
    private ContentValues generateStepContentValues(Step step, String belonging)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STEP_NAME, step.getName());
        contentValues.put(COLUMN_STEP_DESCRIPTION, step.getDescription());
        contentValues.put(COLUMN_STEP_POSITION, step.getOrderPosition());
        contentValues.put(COLUMN_STEP_TIMER, step.getTimerInSeconds());
        contentValues.put(COLUMN_STEP_BELONGING, belonging);
        return contentValues;
    }

    /**
     * Generates ContentValues vor a Single Recipe
     *
     * @param recipe
     *         the recipe
     * @return the content values
     */
    private ContentValues generateRecipeContentValues(Recipe recipe)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_RECIPE_NAME, recipe.getName());
        contentValues.put(COLUMN_RECIPE_ON_TO_COOK, (recipe.getOnToCook() ?1:0));
        contentValues.put(COLUMN_RECIPE_DESCRIPTION, recipe.getDescription());

        return contentValues;
    }

    @Override
    public void reset()
    {
        SQLiteDatabase database  = this.getWritableDatabase();

        String createRecipeTable = "CREATE TABLE IF NOT EXISTS " + TABLE_RECIPES + " (" + COLUMN_RECIPE_NAME + " TEXT PRIMARY KEY, " +
                COLUMN_RECIPE_ON_TO_COOK + " INTEGER, " +
                COLUMN_RECIPE_DESCRIPTION + " TEXT );";
        String createArticleTable = "CREATE TABLE IF NOT EXISTS " + TABLE_ARTICLE + " (" + COLUMN_ARTICLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ARTICLE_NAME + " TEXT NOT NULL, " +
                COLUMN_ARTICLE_DESCRIPTION + " TEXT, " +
                COLUMN_ARTICLE_AMOUNT + " INTEGER,  " +
                COLUMN_ARTICLE_WEIGHT + " REAL,  " +
                COLUMN_ARTICLE_CATEGORY + " TEXT, " +
                COLUMN_ARTICLE_CREATION_DATE + " TEXT, " +
                COLUMN_ARTICLE_UPDATE_DATE + " TEXT, " +
                COLUMN_ARTICLE_BELONGING + " TEXT );";
        String createStepTable = "CREATE TABLE IF NOT EXISTS " + TABLE_STEPS + " (" + COLUMN_STEP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_STEP_NAME + " TEXT NOT NULL, " +
                COLUMN_STEP_DESCRIPTION + " TEXT, " +
                COLUMN_STEP_POSITION + " INTEGER, " +
                COLUMN_STEP_TIMER + " INTEGER, " +
                COLUMN_STEP_BELONGING + " TEXT NOT NULL );";
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLE);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_STEPS);
        database.execSQL(createRecipeTable);
        database.execSQL(createArticleTable);
        database.execSQL(createStepTable);
    }
}
