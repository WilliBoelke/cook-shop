package com.example.cookshop.view.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookshop.R;
import com.example.cookshop.items.Article;
import com.example.cookshop.model.Observer;
import com.example.cookshop.view.recyclerViews.ArticleRecyclerViewAdapter;



import java.util.ArrayList;


/**
 * We have Several Fragments to display articles in recycler view.
 * This is the Superclass for those Fragments,  code shared in this
 * Fragments shall be implemented here
 */
public abstract class FragmentArticleList extends Fragment implements Observer
{

    //------------Static Variables------------


    //------------Instance Variables------------

    /**
     * Log Tag
     */
    private final String TAG = "FragmentArticleList";

    /**
     * The RecyclerView- Adapter
     */
    protected ArticleRecyclerViewAdapter recyclerAdapter;

    /**
     * the recycler view
     */
    private RecyclerView recyclerView;

    /**
     * The LayoutManager of the RecyclerView
     */
    private RecyclerView.LayoutManager recyclerLayoutManager;

    /**
     * The itemTouchHelper, is neccecary
     */
    private ItemTouchHelper itemTouchHelper;

    /**
     * The view
     */
    private View view;

    /**
     * Needs tio be implemented in the subclasses i the method
     * {@link FragmentArticleList#setupSwipeGestures()}
     */
    protected SwipeCallbackLeft swipeCallbackLeft;

    /**
     * Needs tio be implemented in the subclasses i the method
     * {@link FragmentArticleList#setupSwipeGestures()}
     */
    protected SwipeCallbackRight swipeCallbackRight;



    //------------Constructors------------

    public FragmentArticleList()
    {
        // Required empty public constructor
    }



    //------------Activity/Fragment Lifecycle------------


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.main_fratgment_list, container, false);

         setupSwipeGestures();//call before RecyclerView setup, else no wipe gestures
         setupRecyclerView();

        return view;
    }


    //------------Abstract Methods------------

    /**
     * To be overridden by the subclasses
     *
     * @return a ArticleRecyclerViewAdapter with the corresponding ItemList (Buy or Available)
     */
    protected abstract ArticleRecyclerViewAdapter initializeRecyclerViewAdapter();


    /**
     * To be overridden by the subclasses.
     *
     * @return the corresponding ItemList (Buy or Available)
     */
    protected abstract ArrayList<Article> getCorrespondingList();


    protected abstract void startArticleViewerActivity(int position);


    protected abstract void  setupSwipeGestures();


    //------------Setup Views------------

    private void setupRecyclerView()
    {

        recyclerView = view.findViewById(R.id.article_recycler);
        recyclerLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerAdapter = this.initializeRecyclerViewAdapter();
        itemTouchHelper = new ItemTouchHelper(new RecyclerAdapterSwipeGestures(swipeCallbackRight, swipeCallbackLeft));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(recyclerLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataSetChanged();
        //nnClickListener

        recyclerAdapter.setOnItemClickListener(new ArticleRecyclerViewAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(int position)
            {
                startArticleViewerActivity(position);
            }
        });
    }


}