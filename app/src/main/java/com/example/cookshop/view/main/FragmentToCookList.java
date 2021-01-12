package com.example.cookshop.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cookshop.R;
import com.example.cookshop.items.Recipe;
import com.example.cookshop.model.listManagement.DataAccess;
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

      DataAccess.getInstance().registerOnToCookListChangeListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.main_fratgment_list, container, false);
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
      DataAccess.getInstance().unregisterOnToCookListChangeListener(this);
    }

    @Override
    protected ArrayList<Recipe> getCorrespondingList(){
      return DataAccess.getInstance().getToCookList();
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
        DataAccess.getInstance().deleteArticlesWhenCooked(position);
      };
    }

    @Override
    protected void setupAddFab()
    {

    }


    @Override
    public void onChange()
    {

    }
}
