package com.example.cookshop.view.recipeViewUpdateAdd;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cookshop.R;
import com.example.cookshop.controller.viewController.ArticleController;
import com.example.cookshop.controller.viewController.RecipeController;
import com.example.cookshop.items.Article;
import com.example.cookshop.items.Category;
import com.example.cookshop.items.Recipe;
import com.example.cookshop.items.Step;
import com.example.cookshop.model.listManagement.DataAccess;
import com.example.cookshop.view.adapter.ListItemWithDeleteButtonAdapter;
import com.example.cookshop.view.articleViewUpdateAdd.AddArticleActivity;
import com.example.cookshop.view.stepViewUpdateAdd.AddStepActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class AddRecipeActivity extends AppCompatActivity {

  private final String TAG = this.getClass().getSimpleName();
  private final int NEW_ARTICLE = 1;
  private final int NEW_STEP = 2;

  protected String belonging;
  protected EditText nameTextView;
  protected EditText descriptionTextView;
  protected ListView articles;
  protected ListView steps;
  protected FloatingActionButton addFab;
  protected Intent intent;
  protected String editBelonging;
  protected int position;
  protected Recipe editRecipe;
  protected ArrayList<Article> articleList = new ArrayList();
  protected ArrayList<Step> stepList = new ArrayList<>();

  protected ListItemWithDeleteButtonAdapter<Article> articleListAdapter;
  protected ListItemWithDeleteButtonAdapter<Step> stepListAdapter;



  @Override
  protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.recipe_add_edit);
    processIntent();
    {
      nameTextView = findViewById(R.id.name_edittext);
      descriptionTextView = findViewById(R.id.description_edittext);
      articles = findViewById(R.id.articles_listview);
      steps = findViewById(R.id.steps_listview);
    }

    articleListAdapter = new ListItemWithDeleteButtonAdapter(articleList, this);
    articleListAdapter.setOnDeleteButtonClickListener((position) -> {
      articleList.remove(position);
      articleListAdapter.notifyDataSetChanged();
    });

    articles.setAdapter(articleListAdapter);

    stepListAdapter = new ListItemWithDeleteButtonAdapter( stepList, this);
    stepListAdapter.setOnDeleteButtonClickListener((position) -> {
      stepList.remove(position);
      stepListAdapter.notifyDataSetChanged();
    });

  }

  private void processIntent() {
    intent = getIntent();

    this.belonging = intent.getStringExtra("belonging");

    if(this.belonging.equals("edit"))
    {
      position = intent.getIntExtra("position", -1);

      if (position== -1){
        Log.e(TAG, "no position specified");
      }else{
        editBelonging = intent.getStringExtra("editBelonging");
      }

      if (editBelonging.equals("recipe")){
        editRecipe = DataAccess.getInstance().getRecipe(position);
      }
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
    super.onActivityResult(requestCode, resultCode, data);

    switch (requestCode){
      case NEW_ARTICLE:
        if (resultCode == RESULT_OK){
          Article newArticle = (Article) data.getExtras().getSerializable("newArticle");
          articleList.add(newArticle);
          articleListAdapter.notifyDataSetChanged();
          }
        break;
      case NEW_STEP:
        if (resultCode == RESULT_OK){
          Step newStep = (Step) data.getExtras().getSerializable("newStep");
          newStep.setOrderPosition(stepList.size()+1);
          stepList.add(newStep);
          stepListAdapter.notifyDataSetChanged();
        }
        break;
    }
  }

  public void onAddArticleButtonClick(View view){
    Intent addArticleIntent = new Intent (this, AddArticleActivity.class);
    addArticleIntent.putExtra("belonging", "newRecipe");
    startActivityForResult(addArticleIntent, NEW_ARTICLE);
  }

  public void onAddStepButtonClick(View view){
    Intent addStepIntent = new Intent(this, AddStepActivity.class);
    addStepIntent.putExtra("belonging", "newRecipe");
    startActivityForResult(addStepIntent, NEW_STEP);
  }

  public void onSaveButtonClick(View view)
  {
    if (!nameTextView.getText().toString().equals("") && !descriptionTextView.getText().toString().equals(""))
    {
      RecipeController.getInstance().addRecipe(this.getRecipe());
      finish();
    }
    else
    {
      Snackbar.make(view, R.string.no_name_warning, Snackbar.LENGTH_LONG)
        .setAction("Action", null).show();
    }
  }

  protected Recipe getRecipe(){
    String name = this.nameTextView.getText().toString();
    String description = this.descriptionTextView.getText().toString();
    return new Recipe(name, description, articleList, stepList);
  }
}
