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
import com.example.cookshop.model.listManagement.DataAccess;
import com.example.cookshop.view.recipeViewUpdateAdd.AddRecipeActivity;
import com.example.cookshop.view.recipeViewUpdateAdd.RecipeViewer;
import com.example.cookshop.view.recyclerViews.RecipeRecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class FragmentRecipeList extends FragmentRecipeTypeAbstract {

    private final String TAG = "FragmentRecipeList";

    public FragmentRecipeList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DataAccess.getInstance().registerOnRecipeListChangeListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
      super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy(){
      super.onDestroy();
      DataAccess.getInstance().unregisterOnRecipeListChangeListener(this);
    }

    @Override
    protected RecipeRecyclerViewAdapter initializeRecyclerViewAdapter() {
      return new RecipeRecyclerViewAdapter(this.getCorrespondingList(), this.getContext());
    }

    @Override
    protected ArrayList<Recipe> getCorrespondingList() {
      return DataAccess.getInstance().getRecipeList();
    }

    @Override
    protected void startRecipeViewerActivity(int position) {
      Intent displayIntent = new Intent(getActivity(), RecipeViewer.class);
      displayIntent.putExtra("position", position);
      displayIntent.putExtra("belonging", "recipe");
      startActivity(displayIntent);
    }

    @Override
    protected void setupSwipeGestures() {
      //nach links soll das Rezept auf die To-Cook List kommen
      // prüfen ob alle artikel available falls ja, rezept auf tocook, falls nicht
      swipeCallbackLeft = position -> {
        DataAccess.getInstance().recipeToBuyingList(position);
      };

      swipeCallbackRight = position -> {
        DataAccess.getInstance().deleteRecipe(position);
      };

    }

    @Override
    protected void setupAddFab() {
      FloatingActionButton addFab = view.findViewById(R.id.add_item_fab);

      addFab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v){
          Intent newRecipeRecipe = new Intent(getContext(), AddRecipeActivity.class);
          newRecipeRecipe.putExtra("belonging", "buy");
          startActivity(newRecipeRecipe);
        }
      });
    }


    @Override
    public void onChange() {
      Log.e(TAG, "onChange");
      this.recyclerViewAdapter.notifyDataSetChanged();
    }
}
