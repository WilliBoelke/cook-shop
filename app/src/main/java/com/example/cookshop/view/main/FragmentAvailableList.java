package com.example.cookshop.view.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.example.cookshop.items.Article;
import com.example.cookshop.items.Category;
import com.example.cookshop.model.listManagement.DataAccess;
import com.example.cookshop.view.recyclerViews.ArticleRecyclerViewAdapter;

import java.util.ArrayList;


public class FragmentAvailableList extends FragmentArticleList {

    //------------Instance Variables------------


    /**
     * Log Tag
     */
    private final String TAG = "FragmentShoppingList";


    //------------Constructors------------

    public FragmentAvailableList()
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
        DataAccess.getInstance().registerOnAvailableListChangeListener(this);

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
        DataAccess.getInstance().unregisterOnAvailableListChangeListener(this);
    }



    //------------FragmentArticleList Overrides------------

    @Override
    protected ArticleRecyclerViewAdapter initializeRecyclerViewAdapter()
    {
        return new ArticleRecyclerViewAdapter(this.getCorrespondingList(), this.getContext());
    }

    @Override
    protected ArrayList<Article> getCorrespondingList()
    {
        return DataAccess.getInstance().getAvailableList();
    }


    @Override
    protected void setupSwipeGestures()
    {

        swipeCallbackLeft = position -> {
            DataAccess.getInstance().transferArticleFromAvailableToBuyingList(position);
        };

        swipeCallbackRight = position ->
        {
            DataAccess.getInstance().deleteArticleFromAvailableList(position);
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
        DataAccess.getInstance().addArticleToAvailableList(new Article("test", "beschreibung",  Category.FRUIT, 2, 1));
        DataAccess.getInstance().addArticleToAvailableList(new Article("anderer Article", "beschreibung",  Category.FRUIT, 2, 1));
        DataAccess.getInstance().addArticleToAvailableList(new Article("bananen", "beschreibung",  Category.FRUIT, 2, 1));
    }



}