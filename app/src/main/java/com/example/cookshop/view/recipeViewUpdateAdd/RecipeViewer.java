package com.example.cookshop.view.recipeViewUpdateAdd;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.cookshop.R;
import com.example.cookshop.controller.viewController.RecipeController;
import com.example.cookshop.items.Recipe;
import com.example.cookshop.controller.applicationController.ApplicationController;
import com.example.cookshop.view.adapter.ArticleSectionPagerAdapter;
import com.example.cookshop.view.adapter.StepsSectionPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class RecipeViewer extends AppCompatActivity {
  private String TAG =this.getClass().getSimpleName();

  private Recipe viewedRecipe;
  private int    position;

  private Toolbar toolbar;
  private TextView description;
  private ViewPager stepsViewPager;
  private ViewPager articlesViewPager;
  private RecipeController recipeController;
  private String belonging;

  private FloatingActionButton editFab;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.recipe_viewer);
    Intent intent = getIntent();
    belonging = intent.getStringExtra("belonging");
    position = intent.getIntExtra("position", -1);

    recipeController = new RecipeController(ApplicationController.getInstance());
    this.viewedRecipe = recipeController.getRecipe(position);
    this.toolbar = findViewById(R.id.toolbar);
    this.description = findViewById(R.id.description_textview);
    this.stepsViewPager = findViewById(R.id.steps_viewpager);
    this.articlesViewPager = findViewById(R.id.articles_viewpager);
    this.editFab = findViewById(R.id.fab);

    this.setRecipe();
    this.setupViews();
  }

  /**
   * Builds A List of {@link RecipeViewerStepFragment}s which contains
   * one Fragment for each Step in the Recipe
   *
   * @return a list of fragments
   *
   */
  private ArrayList<RecipeViewerStepFragment> buildStepFragments()
  {
    ArrayList<RecipeViewerStepFragment> fragments = new ArrayList();
    for (int i = 0; i < viewedRecipe.getSteps().size(); i++)
    {
      fragments.add(RecipeViewerStepFragment.newInstance(this.viewedRecipe.getSteps().get(i)));
    }
    return fragments;
  }

  private ArrayList<RecipeViewerArticleFragment> buildArticleFragments()
  {
    ArrayList<RecipeViewerArticleFragment> fragments = new ArrayList();
    for (int i = 0; i < viewedRecipe.getArticles().size(); i++)
    {
      fragments.add(RecipeViewerArticleFragment.newInstance(this.viewedRecipe.getArticles().get(i)));
    }

    return fragments;
  }

  public void onEditButtonClick(View view)
  {
    Log.e(TAG,"onEditFabClick");
    Intent intent = new Intent(getApplicationContext(), EditRecipe.class);
    intent.putExtra("belonging", "edit");
    intent.putExtra("position", position);
    intent.putExtra("editBelonging", belonging);
    startActivity(intent);

  }

  /*public void onShareFabClick(View view)
  {
    Log.e(TAG,"onShareFabClick");
    Intent intent = new Intent(getApplicationContext(), ShareRecipe.class);
    intent.putExtra(ShareRecipe.RECIPE, this.position);
    startActivity(intent);
  }*/


  @Override
  public void onResume()
  {
    super.onResume();
    this.setRecipe();
    this.setupViews();
  }

  private void setupViews()
  {
    toolbar.setTitle(viewedRecipe.getName());
    setSupportActionBar(toolbar);

    description.setText(viewedRecipe.getDescription());

    //Steps ViePager:
    ArrayList<RecipeViewerStepFragment> stepFragments = this.buildStepFragments();
    stepsViewPager = findViewById(R.id.steps_viewpager);

    //Set Margin and Padding for the ViewPager - need to to it here because it seems that there isn't any XML equivalent
    stepsViewPager.setClipToPadding(false);
    stepsViewPager.setPadding(70, 0, 70, 0);
    stepsViewPager.setPageMargin(40);

    StepsSectionPagerAdapter stepsAdapter = new StepsSectionPagerAdapter(this, getSupportFragmentManager(), stepFragments);
    stepsViewPager.setAdapter(stepsAdapter);

    //Article ViewPager
    ArrayList<RecipeViewerArticleFragment> articleFragments = buildArticleFragments();
    articlesViewPager = findViewById(R.id.articles_viewpager);

    //Set Margin and Padding for the ViewPager - need to to it here because it seems that there isn't any XML equivalent
    articlesViewPager.setClipToPadding(false);
    articlesViewPager.setPadding(70, 0, 70, 0);
    articlesViewPager.setPageMargin(40);

    ArticleSectionPagerAdapter articlesAdapter = new ArticleSectionPagerAdapter(this, getSupportFragmentManager(), articleFragments);
    articlesViewPager.setAdapter(articlesAdapter);
  }

  private void setRecipe()
  {
    this.viewedRecipe = recipeController.getRecipe(position);
  }
}
