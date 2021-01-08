package com.example.cookshop.view.recipeViewUpdateAdd;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.cookshop.R;
import com.example.cookshop.items.Article;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class RecipeViewerArticleFragment extends Fragment
{

    private static final String  ARG_PARAM1 = "article";
    private Article article;


    public RecipeViewerArticleFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RecipeViewerStepFragment.
     */
    public static RecipeViewerArticleFragment newInstance(Article a)
    {
        RecipeViewerArticleFragment fragment = new RecipeViewerArticleFragment();
        Bundle                      args     = new Bundle();
        args.putSerializable(ARG_PARAM1, a);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            this.article = (Article) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_viewer_article, container, false);

        TextView name = view.findViewById(R.id.name_textview);
        name.setText(this.article.getName());

        TextView description = view.findViewById(R.id.description_textview);
        description.setText(this.article.getDescription());

        TextView amount = view.findViewById(R.id.amount_textview);
        amount.setText("" + this.article.getAmount());

        TextView weight = view.findViewById(R.id.weight_textview);
        weight.setText(Double.toString(this.article.getWeight()));

        return view;
    }
}
