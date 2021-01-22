package com.example.cookshop.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cookshop.R;
import com.example.cookshop.items.Recipe;
import com.example.cookshop.controller.applicationController.ApplicationController;
import com.example.cookshop.view.adapter.RecipeRecyclerViewAdapter;
import com.example.cookshop.view.recipeViewUpdateAdd.RecipeViewer;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentToCookList} factory method to
 * create an instance of this fragment.
 */
public class FragmentToCookList extends FragmentRecipeTypeAbstract {

    private final String TAG = this.getClass().getSimpleName();

    public FragmentToCookList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ApplicationController.getInstance().registerOnToCookListChangeListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      view = inflater.inflate(R.layout.main_fratgment_list_no_button, container, false);
      setupSwipeGestures();
      setupRecyclerView();
      //setupAddFab();
      return view;
        //super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected RecipeRecyclerViewAdapter initializeRecyclerViewAdapter()
    {
      return new RecipeRecyclerViewAdapter(this.getCorrespondingList(), this.getContext());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
      super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy(){
      super.onDestroy();
      ApplicationController.getInstance().unregisterOnToCookListChangeListener(this);
    }

    @Override
    protected ArrayList<Recipe> getCorrespondingList(){
      if(ApplicationController.getInstance().getToCookList().size()==0){
        Log.e(TAG, ": getCorrespondingList(): ToCookList empty!");
      }
      return ApplicationController.getInstance().getToCookList();
    }

    @Override
    protected void startRecipeViewerActivity(int position){
      Intent displayIntent = new Intent(getActivity(), RecipeViewer.class);
      displayIntent.putExtra("position", position);
      displayIntent.putExtra("belonging", "toCook");
      startActivity(displayIntent);
    }

    @Override
    protected void setupSwipeGestures()
    {
      swipeCallbackLeft = position -> {
        ApplicationController.getInstance().deleteArticlesWhenCooked(position);
      };
      swipeCallbackRight = position -> {
        ApplicationController.getInstance().deleteFromToCook(position);
      };
    }

    @Override
    protected void setupAddFab()
    {
      Log.e(TAG, ": setupAddFab ( empty)");
    }


    @Override
    public void onChange()
    {
      this.recyclerAdapter.notifyDataSetChanged();
    }
}
