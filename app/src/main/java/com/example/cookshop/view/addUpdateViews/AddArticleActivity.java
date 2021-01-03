package com.example.cookshop.view.addUpdateViews;

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
    protected EditText name;
    protected EditText description;
    protected EditText amountTxt;
    protected EditText weightTxt;
    protected SeekBar amountSeekBar;
    protected SeekBar weightSeekbar;
    protected Spinner categorySpinner;
    protected FloatingActionButton addFab;
    protected Category category;
    protected Intent intent;
    protected String editBelonging;

    protected int position;

    protected Article editArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_add_edit);

        processIntent();

            name = findViewById(R.id.name_edittext);
            description = findViewById(R.id.description_edittext);
            amountTxt = findViewById(R.id.amount_edittext);
            amountSeekBar = findViewById(R.id.amount_seekbar);
            weightTxt = findViewById(R.id.weight_edittext);
            weightSeekbar = findViewById(R.id.weight_seekbar);
            categorySpinner = findViewById(R.id.category_spinner);
            addFab = findViewById(R.id.add_fab);


            setupCategorySpinner();
            setupAmountInput();
            setupWeightInput();

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
         * and get the exact article from the DataAccess - sp we can manipulate it.
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
                 * i will that call "editBelonging"
                 */
                editBelonging = intent.getStringExtra("editBelonging");
            }

            // now lets get the article
            if (editBelonging.equals("buy"))
            {
                editArticle = DataAccess.getInstance().getArticleFromBuyingList(position);
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
                weightTxt.setText("" + progress);
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
                amountTxt.setText("" + progress);
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





    /**
     * Generates an Article from the user input
     *
     * @return the article
     */
    protected Article getArticle()
    {
        // Declaring  standard values for the Article
        String name        = this.name.getText().toString();
        String description = "description";
        int    amount      = 1;
        double weight      = 0.0;

        //Overwrite standard values if there is an input

        if (!this.description.getText().toString().trim().equals(""))
        {
            description = this.description.getText().toString();
        }
        if (!this.weightTxt.getText().toString().trim().equals(""))
        {
            weight = Double.parseDouble(this.weightTxt.getText().toString());
        }
        if (!this.amountTxt.getText().toString().trim().equals(""))
        {
            amount = Integer.parseInt(this.amountTxt.getText().toString());
        }
        return new Article(name, description, category, amount, weight);
    }


    public void onAddArticleFabClick(View view)
    {

        if (!name.getText().toString().equals(""))
        {
            if (belonging.equals("buy"))
            {
                //Add Article to Database and ListServices
                DataAccess.getInstance().addArticleToshopingList(getArticle());
                finish();
            }
            else if (belonging.equals("available"))
            {
                //Add Article to Database and ListServices
                DataAccess.getInstance().addArticleToAvailableList(getArticle());
                finish();
            }
            else if (belonging.equals("newRecipe"))
            {
                //Return the article to the AddRecipe Activity via resultIntent
                Intent resultIntent = new Intent();
                resultIntent.putExtra("newArticle", (Serializable) getArticle());
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
