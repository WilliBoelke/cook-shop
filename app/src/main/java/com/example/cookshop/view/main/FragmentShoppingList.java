package com.example.cookshop.view.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cookshop.R;
import com.example.cookshop.items.Article;
import com.example.cookshop.controller.applicationController.ApplicationController;
import com.example.cookshop.view.articleViewUpdateAdd.AddArticleActivity;
import com.example.cookshop.view.articleViewUpdateAdd.ArticleViewer;
import com.example.cookshop.view.adapter.ArticleRecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
        ApplicationController.getInstance().registerOnBuyingListChangeListener(this);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
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
        ApplicationController.getInstance().unregisterOnBuyingListChangeListener(this);
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
        return ApplicationController.getInstance().getBuyingList();
    }


    @Override
    protected void setupSwipeGestures()
    {

        swipeCallbackLeft = position -> {
            ApplicationController.getInstance().transferArticleFromBuyingToAvailableList(position);
        };

        swipeCallbackRight = position ->
        {
            ApplicationController.getInstance().deleteArticleShoppingList(position);
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

    @Override
    protected void startArticleViewerActivity(int position)
    {
         Intent displayIntent = new Intent(getActivity(), ArticleViewer.class);
         displayIntent.putExtra("position", position);
         displayIntent.putExtra("belonging", "buy");
         startActivity(displayIntent);
    }

    @Override
    protected void setupAddFab()
    {
        FloatingActionButton addFab = view.findViewById(R.id.add_item_fab);

        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent newBuyArticle = new Intent(getContext(), AddArticleActivity.class);
                newBuyArticle.putExtra("belonging", "buy");
                startActivity(newBuyArticle);
            }
        });
    }

}
