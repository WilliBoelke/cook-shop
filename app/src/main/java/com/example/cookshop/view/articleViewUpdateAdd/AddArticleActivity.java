package com.example.cookshop.view.articleViewUpdateAdd;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cookshop.R;
import com.example.cookshop.controller.viewController.ArticleController;
import com.example.cookshop.items.Article;
import com.example.cookshop.items.Category;
import com.example.cookshop.model.listManagement.DataAccess;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;

/**
 * This Activity allows the user to add and edit Articles
 */
public class AddArticleActivity extends AppCompatActivity
{


    //------------Instance Variables------------


    /**
     * The Log Tag
     */
    private final String TAG = this.getClass().getSimpleName();
    /**
     * String passed trough the Intend when starting the
     * activity, Indicates to which list the article shall be
     * added
     */
    protected String belonging;
    protected EditText nameTextView;
    protected EditText descriptionTextView;
    protected EditText amountTextView;
    protected EditText weightTextView;
    protected SeekBar amountSeekBar;
    protected SeekBar weightSeekbar;
    protected Spinner categorySpinner;
    protected FloatingActionButton addFab;
    protected Category category;
    protected Intent intent;
    protected String editBelonging;
    protected int position;
    protected Article editArticle;
    protected ArticleController controller;



    //------------Activity/Fragment Lifecycle------------

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_add_edit);
        controller = new ArticleController();
        processIntent();

            nameTextView = findViewById(R.id.name_edittext);
            descriptionTextView = findViewById(R.id.description_edittext);
            amountTextView = findViewById(R.id.amount_edittext);
            amountSeekBar = findViewById(R.id.amount_seekbar);
            weightTextView = findViewById(R.id.weight_edittext);
            weightSeekbar = findViewById(R.id.weight_seekbar);
            categorySpinner = findViewById(R.id.category_spinner);
            addFab = findViewById(R.id.add_fab);


            setupCategorySpinner();
            setupAmountInput();
            setupWeightInput();

    }



    //------------Setup views------------


    private void setupWeightInput()
    {
        weightSeekbar = findViewById(R.id.weight_seekbar);
        weightSeekbar.setMax(1000);
        weightSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                //set the value of the weight EditText
                weightTextView.setText("" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });
    }

    private void setupAmountInput()
    {
        amountSeekBar = findViewById(R.id.amount_seekbar);
        amountSeekBar.setMax(20);
        amountSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                //set the value of the amount EditText
                amountTextView.setText("" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });
    }

    private void setupCategorySpinner()
    {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getApplicationContext(), R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                switch (position)
                {
                    case 0:
                        category = Category.FRUIT;
                        break;
                    case 1:
                        category = Category.VEGETABLE;
                        break;
                    case 2:
                        category = Category.DRINK;
                        break;
                    case 3:
                        category = Category.MEAT;
                        break;
                    case 4:
                        category = Category.OTHERS;
                        break;
                    default:
                        category = Category.OTHERS;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

    }




    private void processIntent()
    {
        intent = getIntent();
        //get article object from the intent
        this.belonging = intent.getStringExtra("belonging");

        /*
         * Now it gets a little bit complicated,
         * this code is for EditArticle class/activity which inherits from this one.
         *
         * if the belonging string passed by the intend equals "edit"
         * this code will get 2 more values from the intent, so we can find
         * and get the exact article from  DataAccess
         */
        if (this.belonging.equals("edit"))
        {
            /*
             * first i get the position of the article
             */
            position = intent.getIntExtra("position", -1);
            if (position == -1)
            {
                Log.e(TAG, "no position specified");
            }
            else
            {
                /*
                 * Now we need to know the real belonging of the article
                 * i will  call that "editBelonging"
                 */
                editBelonging = intent.getStringExtra("editBelonging");
            }

            // now lets get the article
            if (editBelonging.equals("buy"))
            {
                editArticle = DataAccess.getInstance().getArticleFromShoppingList(position);
            }
            else if (editBelonging.equals("available"))
            {
                editArticle = DataAccess.getInstance().getArticleFromAvailableList(position);
            }
            //if its an article from a recipe
            else
            {
                editArticle = DataAccess.getInstance().getRecipe(editBelonging).getArticles().get(position);
            }
        }
    }



    public void onAddArticleFabClick(View view)
    {
        if (!nameTextView.getText().toString().equals(""))
        {
            Article newArticle = controller.generateArticleFromInput(nameTextView, descriptionTextView, category, weightTextView, amountTextView);
            if (belonging.equals("buy"))
            {
                //Add Article to Database and ListServices
                DataAccess.getInstance().addArticleToshopingList(newArticle);
                finish();
            }
            else if (belonging.equals("available"))
            {
                //Add Article to Database and ListServices
                DataAccess.getInstance().addArticleToAvailableList(newArticle);
                finish();
            }
            else if (belonging.equals("newRecipe"))
            {
                Log.d(TAG, "onAddArticleFabClick: in else if(new Recipe)");
                //Return the article to the AddRecipe Activity via resultIntent
                Intent resultIntent = new Intent();
                resultIntent.putExtra("newArticle", (Serializable) newArticle);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        }
        else
        {
            Snackbar.make(view, R.string.no_name_warning, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }
}
