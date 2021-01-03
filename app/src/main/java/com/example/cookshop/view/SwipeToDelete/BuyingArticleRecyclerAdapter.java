package com.example.cookshop.view.SwipeToDelete;

import android.content.Context;

import com.example.cookshop.items.Article;
import com.example.cookshop.model.listManagement.DataAccess;
import com.example.cookshop.view.ArticleRecyclerViewAdapter;

import java.util.ArrayList;

public class BuyingArticleRecyclerAdapter extends ArticleRecyclerViewAdapter
{
    public BuyingArticleRecyclerAdapter(ArrayList<Article> articleArrayList, Context context)
    {
        super(articleArrayList, context);
    }

    @Override
    public void switchLists(int position)
    {
        DataAccess.getInstance().transferArticleFromBuyingToAvailableList(position);
        notifyDataSetChanged();
    }

    @Override
    public void deleteItem(int position)
    {
        DataAccess.getInstance().deleteArticleShoppingList(position);
        notifyItemRemoved(position);
    }
}
