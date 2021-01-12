package com.example.cookshop.view.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookshop.R;
import com.example.cookshop.items.Recipe;
import com.example.cookshop.controller.Observer;
import com.example.cookshop.view.adapter.RecipeRecyclerViewAdapter;
import com.example.cookshop.view.adapter.RecyclerAdapterSwipeGestures;
import com.example.cookshop.view.adapter.SwipeCallbackLeft;
import com.example.cookshop.view.adapter.SwipeCallbackRight;

import java.util.ArrayList;

public abstract class FragmentRecipeTypeAbstract extends Fragment implements Observer {
  /**
   * Log Tag
   */
  private final String TAG = this.getClass().getSimpleName();

  protected RecipeRecyclerViewAdapter recyclerAdapter;

  private RecyclerView recyclerView;

  private RecyclerView.LayoutManager recyclerLayoutManager;

  private ItemTouchHelper itemTouchHelper;

  protected View view;

  protected SwipeCallbackLeft swipeCallbackLeft;

  protected SwipeCallbackRight swipeCallbackRight;

  public FragmentRecipeTypeAbstract(){

  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    Log.e(TAG, "onCreate (superclass): before super call");
    super.onCreate(savedInstanceState);
    Log.e(TAG, "onCreate (superclass): after super call");}

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
    Log.e(TAG, ": onCreateView() (super) ");
    /*if(TAG == "FragmentToCookList"){
      Log.e(TAG, ": onCreateView()(super): in if Bracket.");
      view= inflater.inflate(R.layout.main_fratgment_list_no_button, container, false);
    }else{
      view = inflater.inflate(R.layout.main_fratgment_list, container, false);
    }*/


    /*setupSwipeGestures();
    setupRecyclerView();
    setupAddFab();*/

    return view;
  }

  protected abstract RecipeRecyclerViewAdapter initializeRecyclerViewAdapter();

  protected abstract ArrayList<Recipe> getCorrespondingList();

  protected abstract void startRecipeViewerActivity(int position);

  protected abstract void setupSwipeGestures();

  protected abstract void setupAddFab();

  protected void setupRecyclerView()
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

    recyclerAdapter.setOnItemClickListener(new RecipeRecyclerViewAdapter.OnItemClickListener()
    {
      @Override
      public void onItemClick(int position)
      {
        startRecipeViewerActivity(position);
      }
    });
  }
}
