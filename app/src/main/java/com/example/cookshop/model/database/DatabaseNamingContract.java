package com.example.cookshop.model.database;

/**
 * Names of tables and Rows  used in the {@link DatabaseHelper}
 * @author willi
 */
public interface DatabaseNamingContract
{
    /**
     * Name of the database
     */
    String DATABASE_NAME              = "buyingList.db";
    /**
     * Version of the database
     */
    int    DATABASE_VERSION           = 3;
    /**
     * name of the recipe table
     */
    String TABLE_RECIPES              = "recipes_table";
    /**
     * column name of the recipe table
     */
    String COLUMN_RECIPE_NAME         = "NAME"; //Primary Key
    /**
     * column description of the recipe table
     */
    String COLUMN_RECIPE_DESCRIPTION  = "DESCRIPTION";
    /**
     * column on_to_cook of the recipe table
     */
    String COLUMN_RECIPE_ON_TO_COOK = "ON_TO_COOK";
    /**
     * name of the steps table
     */
    String TABLE_STEPS                = "steps_table";
    /**
     * column id of the steps table
     */
    String COLUMN_STEP_ID             = "ID";//Primary Key
    /**
     * column position of the steps table
     */
    String COLUMN_STEP_POSITION       = "POSITION";
    /**
     * column name of the steps table
     */
    String COLUMN_STEP_NAME           = "NAME";
    /**
     * column description of the steps table
     */
    String COLUMN_STEP_DESCRIPTION    = "DESCRIPTION";
    /**
     * column belonging of the steps table
     */
    String COLUMN_STEP_BELONGING      = "BELONGING";//Foreign Key
    /**
     * column timer of the steps table
     */
    String COLUMN_STEP_TIMER          = "TIMER";
    /**
     * name of the article table
     */
    String TABLE_ARTICLE              = "article_table";
    /**
     * column id of the article table
     */
    String COLUMN_ARTICLE_ID          = "ID";//Primary Key
    /**
     * column name of the article table
     */
    String COLUMN_ARTICLE_NAME        = "NAME";
    /**
     * column description of the article table
     */
    String COLUMN_ARTICLE_DESCRIPTION = "DESCRIPTION";
    /**
     * column amount of the article table
     */
    String COLUMN_ARTICLE_AMOUNT      = "AMOUNT";
    /**
     * column weight of the article table
     */
    String COLUMN_ARTICLE_WEIGHT      = "WEIGHT";
    /**
     *
     */
    String COLUMN_ARTICLE_CREATION_DATE ="CREATION_DATE";
    /**
     *
     */
    String COLUMN_ARTICLE_UPDATE_DATE ="CHANGED_DATE";
    /**
     * column category of the article table
     */
    String COLUMN_ARTICLE_CATEGORY    = "CATEGORY";
    /**
     * column belonging of the article table
     */
    String COLUMN_ARTICLE_BELONGING   = "BELONGING"; //Foreign Key (Example: Name of the corresponding Recipe/ Buying- or AvailableList)
}
