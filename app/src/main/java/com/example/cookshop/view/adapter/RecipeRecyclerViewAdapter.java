package com.example.cookshop.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.cookshop.R;
import com.example.cookshop.items.Recipe;

import java.util.ArrayList;

public class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<RecipeRecyclerViewAdapter.RecipeViewHolder> {

  private ArrayList<Recipe> recipeArrayList;
  private OnItemClickListener onItemClickListener;
  private Context context;

  public RecipeRecyclerViewAdapter(ArrayList<Recipe> recipeArrayList, Context context){
    this.recipeArrayList=recipeArrayList;
    this.context=context;
  }

  @NonNull
  @Override
  public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_card_recipe, parent, false);
    RecipeViewHolder recipeViewHolder = new RecipeViewHolder(view, this.onItemClickListener);
    return recipeViewHolder;
  }

  @Override
  public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
    Recipe currentRecipe = this.recipeArrayList.get(position);

    holder.nameTextView.setText(currentRecipe.getName());
    holder.descriptionTextView.setText(currentRecipe.getDescription());

  }

  @Override
  public int getItemCount() {
    return recipeArrayList.size();
  }

  public void setOnItemClickListener(OnItemClickListener listener){
    this.onItemClickListener = listener;
  }

  public interface OnItemClickListener{
    void onItemClick(int position);
  }

  public class RecipeViewHolder extends  RecyclerView.ViewHolder{
    public ImageView recipeImageView;
    public TextView nameTextView;
    public ViewPager stepsViewPager;
    public ListView articlesListView;
    public TextView descriptionTextView;

    public RecipeViewHolder(@NonNull View itemView, OnItemClickListener listener) {
      super(itemView);
      nameTextView = itemView.findViewById(R.id.recipe_name_textview);
      stepsViewPager = itemView.findViewById(R.id.steps_viewpager);
      articlesListView = itemView.findViewById(R.id.articles_listview);
      descriptionTextView = itemView.findViewById(R.id.recipe_description_textview);

      itemView.setOnClickListener(new View.OnClickListener(){
          @Override
          public void onClick(View v){
            if (listener != null)
            {
              int position = getAdapterPosition();
              if (position != RecyclerView.NO_POSITION)
              {
                listener.onItemClick(position);
              }
            }
          }
      });

    }
  }
}
