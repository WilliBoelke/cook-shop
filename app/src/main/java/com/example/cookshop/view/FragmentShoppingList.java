package com.example.cookshop.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cookshop.R;
import com.example.cookshop.items.Article;
import com.example.cookshop.items.Category;
import com.example.cookshop.model.listManagement.DataAccess;
import com.example.cookshop.view.SwipeToDelete.BuyingArticleRecyclerAdapter;
import com.example.cookshop.view.SwipeToDelete.SwipeCallbackLeft;
import com.example.cookshop.view.SwipeToDelete.SwipeCallbackRight;

import java.util.ArrayList;


public class FragmentShoppingList extends FragmentArticleList
{

    //------------Instance Variables------------


    /**
     * Log Tag
     */
    private final String TAG = "FragmentShoppingList";


    //------------Constructors------------

    public FragmentShoppingList()
    {
        // Required empty public constructor
    }



    //------------Activity/Fragment Lifecycle------------


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        /**
         *    register at onBuyingChangeListener, so the
         *    DataAccess will call this {@link #onChange()} method
         */
        DataAccess.getInstance().registerOnBuyingListChangeListener(this);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        insertArticles();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        DataAccess.getInstance().unregisterOnBuyingListChangeListener(this);
    }


    @Override
    protected ArticleRecyclerViewAdapter initializeRecyclerViewAdapter()
    {
        return new BuyingArticleRecyclerAdapter(this.getCorrespondingList(), this.getContext());
    }



    //------------FragmentArticleList Overrides------------


    @Override
    protected ArrayList<Article> getCorrespondingList()
    {
        return DataAccess.getInstance().getBuyingList();
    }


    @Override
    protected void setupSwipeGestures()
    {

        Log.e(TAG, "setupSwipeGestures");
        swipeCallbackLeft = position -> {
            Log.e(TAG,"-----------------------------");
            DataAccess.getInstance().deleteArticleShoppingList(position);
        };

        swipeCallbackRight = position -> {

        };
    }

    //------------Observer------------

    @Override
    public void onChange()
    {
        Log.e(TAG,"onChange");
        this.recyclerAdapter.notifyDataSetChanged();
    }


    //------------Setup views------------
    protected void startArticleViewerActivity(int position)
    {
        /**
         Intent displayIntent = new Intent(getActivity(), ArticleViewer.class);
         displayIntent.putExtra("position", position);
         displayIntent.putExtra("belonging", "buy");
         startActivity(displayIntent);
         **/
    }




    /**
     * insert test articles
     * TODO remove
     */
    private void insertArticles()
    {
        DataAccess.getInstance().addArticleToBuyingList(new Article("test", "beschreibung",  Category.FRUIT, 2, 1));
        DataAccess.getInstance().addArticleToBuyingList(new Article("anderer Article", "beschreibung",  Category.FRUIT, 2, 1));
        DataAccess.getInstance().addArticleToBuyingList(new Article("bananen", "beschreibung",  Category.FRUIT, 2, 1));
    }


}
