package com.example.cookshop.view.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.cookshop.R;
import com.example.cookshop.items.Recipe;
import com.example.cookshop.controller.applicationController.ApplicationController;
import com.example.cookshop.view.recipeViewUpdateAdd.AddRecipeActivity;
import com.example.cookshop.view.recipeViewUpdateAdd.RecipeViewer;
import com.example.cookshop.view.adapter.RecipeRecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class FragmentRecipeList extends FragmentRecipeTypeAbstract {

    private final String TAG = this.getClass().getSimpleName();

    public FragmentRecipeList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ApplicationController.getInstance().registerOnRecipeListChangeListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      view = inflater.inflate(R.layout.main_fratgment_list, container, false);
      setupSwipeGestures();
      setupRecyclerView();
      setupAddFab();
      return view;
      //return super.onCreateView(inflater, container, savedInstanceState);
      //return inflater.inflate(R.layout.main_fratgment_list, container, false);
    }

    @Override
    protected RecipeRecyclerViewAdapter initializeRecyclerViewAdapter() {
      Log.e(TAG, getContext().toString());
      return new RecipeRecyclerViewAdapter(this.getCorrespondingList(), this.getContext());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
      super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy(){
      super.onDestroy();
      ApplicationController.getInstance().unregisterOnRecipeListChangeListener(this);
    }

    @Override
    public void onStop(){
        super.onStop();
        ApplicationController.getInstance().unregisterOnRecipeListChangeListener(this);
    }

    @Override
    protected ArrayList<Recipe> getCorrespondingList() {
      return ApplicationController.getInstance().getRecipeList();
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
        Log.e(TAG, ": setupSwipeGestures(): Left");
        ApplicationController.getInstance().addRecipeFromRecipeToToCookList(position);
      };

      swipeCallbackRight = position -> {
        alertDialog(position);
      };

    }

    @Override
    protected void setupAddFab() {
      FloatingActionButton addFab = view.findViewById(R.id.add_item_fab);

      addFab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v){
          Intent newRecipeRecipe = new Intent(getContext(), AddRecipeActivity.class);
          newRecipeRecipe.putExtra("belonging", "recipe");
          Log.e(TAG, "setupAddFab(): onClick(): intent: " + newRecipeRecipe.getDataString());
          startActivity(newRecipeRecipe);
        }
      });
    }


    @Override
    public void onChange() {
      Log.e(TAG, "onChange");
      this.recyclerAdapter.notifyDataSetChanged();
    }

    /**
     * Displays an alert dialog to warn the user before deleting a recipe
     * @param position
     */
    private void alertDialog(int position) {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this.getActivity());
        dialog.setTitle("Delete " + ApplicationController.getInstance().getRecipe(position).getName());
        dialog.setMessage("Do you want to permanently delete the recipe " + ApplicationController.getInstance().getRecipe(position).getName() + "?");
        dialog.setPositiveButton("YES", new DialogInterface.OnClickListener()
        {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        ApplicationController.getInstance().deleteRecipe(position);
                        Toast.makeText(getActivity(),"Recipe was deleted",Toast.LENGTH_LONG).show();
                    }
                });
        dialog.setNegativeButton("cancel",new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                recyclerAdapter.notifyDataSetChanged();
            }
        }
        );
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }
}
