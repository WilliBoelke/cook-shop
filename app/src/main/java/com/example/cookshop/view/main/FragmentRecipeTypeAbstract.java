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
import com.example.cookshop.items.Recipe;
import com.example.cookshop.model.Observer;
import com.example.cookshop.view.adapter.RecipeRecyclerViewAdapter;

import java.util.ArrayList;

public abstract class FragmentRecipeTypeAbstract extends Fragment implements Observer {
  /**
   * Log Tag
   */
  private final String TAG = "FragmentRecipeTypeList";

  protected RecipeRecyclerViewAdapter recyclerViewAdapter;

  private RecyclerView recyclerView;

  private RecyclerView.LayoutManager recyclerLayoutManager;

  private ItemTouchHelper itemTouchHelper;

  protected View view;

  protected SwipeCallbackLeft swipeCallbackLeft;

  protected SwipeCallbackRight swipeCallbackRight;

  public FragmentRecipeTypeAbstract(){

  }

  @Override
  public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);}

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

    view = inflater.inflate(R.layout.main_fratgment_list, container, false);

    setupSwipeGestures();
    setupRecyclerView();
    setupAddFab();

    return view;
  }

  protected abstract RecipeRecyclerViewAdapter initializeRecyclerViewAdapter();

  protected abstract ArrayList<Recipe> getCorrespondingList();

  protected abstract void startRecipeViewerActivity(int position);

  protected abstract void setupSwipeGestures();

  protected abstract void setupAddFab();

  private void setupRecyclerView()
  {

    recyclerView = view.findViewById(R.id.article_recycler);
    recyclerLayoutManager = new LinearLayoutManager(this.getContext());
    recyclerViewAdapter = this.initializeRecyclerViewAdapter();
    itemTouchHelper = new ItemTouchHelper(new RecyclerAdapterSwipeGestures(swipeCallbackRight, swipeCallbackLeft));
    itemTouchHelper.attachToRecyclerView(recyclerView);
    recyclerView.setLayoutManager(recyclerLayoutManager);
    recyclerView.setAdapter(recyclerViewAdapter);
    recyclerViewAdapter.notifyDataSetChanged();
    //nnClickListener

    recyclerViewAdapter.setOnItemClickListener(new RecipeRecyclerViewAdapter.OnItemClickListener()
    {
      @Override
      public void onItemClick(int position)
      {
        startRecipeViewerActivity(position);
      }
    });
  }
}
