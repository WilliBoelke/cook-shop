package com.example.cookshop.view.stepViewUpdateAdd;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cookshop.R;
import com.example.cookshop.items.Step;
import com.example.cookshop.model.UserPreferences;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;

public class AddStepActivity extends AppCompatActivity{

  private EditText name;
  private EditText             description;
  private EditText             timer;
  private FloatingActionButton addFab;
  private final String TAG = this.getClass().getSimpleName();


  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setTheme();
    setContentView(R.layout.step_add_edit);
    //Getting all views
    {
      name = findViewById(R.id.name_edittext);
      description = findViewById(R.id.description_edittext);
      timer = findViewById(R.id.timer_edittext);
      addFab = findViewById(R.id.add_article_fab);
    }
  }


  private Step getStep()
  {
    int tempTimer = 0;

    if (!this.timer.getText().toString().trim().equals(""))
    {
      tempTimer = Integer.parseInt(timer.getText().toString());
    }
    return new Step(name.getText().toString(), description.getText().toString(), tempTimer);
  }

  public void onAddStepFabClick(View view)
  {
    if (!name.getText().toString().equals("") && !description.getText().toString().equals(""))
    {
      //Return the article to the AddRecipe Activity via resultIntent
      Intent resultIntent = new Intent();
      try{
        resultIntent.putExtra("newStep", (Serializable) getStep());
      }catch(NumberFormatException | NullPointerException e){
        Snackbar.make(view, R.string.string_timer_warning, Snackbar.LENGTH_LONG)
          .setAction("Action", null).show();
      }

      setResult(RESULT_OK, resultIntent);
      finish();
    }
    else
    {
      Snackbar.make(view, R.string.no_name_warning, Snackbar.LENGTH_LONG)
              .setAction("Action", null).show();
    }
  }


  private void setTheme()
  {
    String theme = UserPreferences.getInstance().getTheme();

    switch (theme)
    {
      case UserPreferences.DARK_MODE:
        setTheme(R.style.DarkTheme);
        Log.d(TAG, "setTheme :  app theme DARK");
        break;
      case UserPreferences.LIGHT_MODE:
        setTheme(R.style.LightTheme);
        Log.d(TAG, "setTheme :  app theme LIGHT");
        break;
      case UserPreferences.LILAH_MODE:
        setTheme(R.style.LilahTheme);
        Log.d(TAG, "setTheme :  app theme LILAH");
        break;
      default:
        setTheme(R.style.DarkTheme);
        Log.d(TAG, "setTheme :  default DARK");
        break;
    }
  }
}
