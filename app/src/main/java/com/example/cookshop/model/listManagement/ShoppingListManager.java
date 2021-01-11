package com.example.cookshop.model.listManagement;


import com.example.cookshop.items.Article;
import com.example.cookshop.items.Item;
import com.example.cookshop.model.database.DatabaseHelper;

import java.util.ArrayList;

public class ShoppingListManager extends ArticleListManager
{
    private final String TAG           = "BuyingListService";
    public static final String BELONGING_TAG = "buy";


    public ShoppingListManager(DatabaseHelper databaseService)
    {
        super(databaseService);
        this.loadData();
    }

    private void loadData()
    {
        this.setItemList(this.getDatabase().retrieveAllArticlesFrom(this.BELONGING_TAG));
    }

    @Override
    protected void addItemToDatabase(Item Object)
    {
        super.addItemToDatabase(Object);
        this.getDatabase().insertArticle(BELONGING_TAG, (Article) Object);
    }

    @Override
    protected void removeItemFromDatabase(String name)
    {
        super.removeItemFromDatabase(name);
        this.getDatabase().deleteArticle(name, this.BELONGING_TAG);
    }

    @Override
    protected void addItemsToDatabase(ArrayList list)
    {
        super.addItemsToDatabase(list);
        this.getDatabase().insertSeveralArticles(BELONGING_TAG, list);
    }

    @Override
    protected String getBELONGING_TAG()
    {
        return this.BELONGING_TAG;
    }
}
