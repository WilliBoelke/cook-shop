package com.example.cookshop.view.recyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookshop.R;
import com.example.cookshop.items.Article;

import java.util.ArrayList;


/**
 * Recycler View Adapter for articles
 */
public class ArticleRecyclerViewAdapter extends RecyclerView.Adapter<ArticleRecyclerViewAdapter.ArticleViewHolder>
{


    private ArrayList<Article> articleArrayList;
    private OnItemClickListener onItemClickListener;
    private Context context;


    public ArticleRecyclerViewAdapter(ArrayList<Article> articleArrayList, Context context)
    {
        this.articleArrayList = articleArrayList;
        this.context = context;
    }



    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_card_article, parent, false);
        ArticleViewHolder articleViewHolder = new ArticleViewHolder(view, this.onItemClickListener);
        return articleViewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder articleViewHolder, int position)
    {
        Article currentArticle = this.articleArrayList.get(position);

        switch (currentArticle.getCategory())
        {
            // TODO images
        }
       articleViewHolder.nameTextView.setText(currentArticle.getName());
        articleViewHolder.amountTextView.setText(currentArticle.getAmount() + " stück");
        articleViewHolder.weightTextView.setText(currentArticle.getWeight() + " gramm");
    }



    @Override
    public int getItemCount()
    {
        return articleArrayList.size();
    }


    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.onItemClickListener = listener;
    }


    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }


    public static class ArticleViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView articleImageView;
        public TextView nameTextView;
        public TextView  weightTextView;
        public TextView  amountTextView;

        public ArticleViewHolder(@NonNull View itemView, final OnItemClickListener listener)
        {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.article_name_textview);
            weightTextView = itemView.findViewById(R.id.article_weight_textview);
            amountTextView = itemView.findViewById(R.id.article_amount_textview);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
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
