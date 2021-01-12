package com.example.cookshop.view.articleViewUpdateAdd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.cookshop.R;
import com.example.cookshop.controller.viewController.ArticleController;
import com.example.cookshop.items.Article;
import com.example.cookshop.controller.applicationController.ApplicationController;
import com.google.android.material.appbar.AppBarLayout;

import java.text.SimpleDateFormat;

/**
 * This shows all the details about an article,
 * its name, description, amount and weight
 * from here the user can edit the article.
 */
public class ArticleViewer extends AppCompatActivity {

    //------------Instance Variables------------

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

    private Article viewedArticle;

    /**
     * The amount TextView
     */
    private TextView amount;

    /**
     * The weight TextView
     */
    private TextView weight;
    /**
     * The description text TextView
     */
    private TextView description;
    /**
     * The position of the Article in the listService
     */
    private int    position;
    /**
     * the belonging of the article
     * (an article can belong to different lists, shopping, available or a recipe)
     */
    private String   belonging;

    /**
     *
     * @param savedInstanceState
     */
    private ArticleController controller;


    //------------Activity/Fragment Lifecycle------------


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_viewer);
        this.controller = new ArticleController();
        //get intent
        Intent intent = getIntent();
        belonging = intent.getStringExtra("belonging");
        position = intent.getIntExtra("position", -1);

        this.setArticle();
        this.setupToolbarImage();
        this.setupWeightTextView();
        this.setupAmountTextView();
        this.setupDescriptionTextView();
    }


    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs)
    {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        this.setArticle();
        this.setupToolbarImage();
        this.setupWeightTextView();
        this.setupAmountTextView();
        this.setupDescriptionTextView();
        this.setupLastChangedTextView();
        this.setupCreationDateTextView();
    }



    //------------Setup Views------------


    private void setupToolbarImage()
    {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(viewedArticle.getName());

        switch (viewedArticle.getCategory())
        {
            case DRINK:

                break;
            case MEAT:

                break;
            case VEGETABLE:

                break;
            case FRUIT:

                break;
            case OTHERS:


                break;
        }
    }

    private void setupDescriptionTextView()
    {
        description = findViewById(R.id.description_textview);
        description.setText(viewedArticle.getDescription());
    }
    private void setupAmountTextView()
    {
        amount = findViewById(R.id.amount_textview);
        amount.setText(Integer.toString(viewedArticle.getAmount()));
    }
    private void setupWeightTextView()
    {
        weight = findViewById(R.id.weight_textview);
        weight.setText(Double.toString(viewedArticle.getWeight()));
    }

    private void setupCreationDateTextView()
    {
        TextView creationDate = findViewById(R.id.creation_date_textview);
        creationDate.setText(simpleDateFormat.format(viewedArticle.getDateOfCreation()));
    }

    private void setupLastChangedTextView()
    {
        TextView lastChanged = findViewById(R.id.last_changed_textview);
        lastChanged.setText(simpleDateFormat.format(viewedArticle.getDateOfUpdate()));
    }


    /**
     * Needed this, because using the images directly caused major performance loss
     * @param drawable
     */
    private void setHeaderBackground (int drawable)
    {
        final AppBarLayout ctl = findViewById(R.id.app_bar);

        Glide.with(this).load(drawable).asBitmap().into(new SimpleTarget<Bitmap>(1600,900)
        {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation)
            {
                Drawable drawable = new BitmapDrawable(resource);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                {
                    ctl.setBackground(drawable);
                }
            }
        });
    }


    private void setArticle()
    {
        if (belonging.equals("buy"))
        {
            viewedArticle = ApplicationController.getInstance().getArticleFromShoppingList(position);
        }
        else if (belonging.equals("available"))
        {
            viewedArticle = ApplicationController.getInstance().getArticleFromAvailableList(position);
        }
        else
        {
            //Recipe
            viewedArticle = ApplicationController.getInstance().getArticleFromRecipe(belonging, position);
        }
    }


    public void onEditButtonClick(View view)
    {
        Intent intent = new Intent(getApplicationContext(), EditArticle.class);
        intent.putExtra("belonging", "edit");
        intent.putExtra("position", position);
        intent.putExtra("editBelonging", belonging);
        startActivity(intent);
    }
}
