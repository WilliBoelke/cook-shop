package com.example.cookshop.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.cookshop.R;
import com.example.cookshop.view.articleViewUpdateAdd.ArticleViewer;
import com.example.cookshop.view.articleViewUpdateAdd.EditArticle;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentRecommendedList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentRecommendedList extends Fragment
{

View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_recommended, container, false);


        ssetupSynchronizeButoon();
        return view;
    }

    private void ssetupSynchronizeButoon()
    {
        Button synchronize = view.findViewById(R.id.sync_button);
        synchronize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent synchIntent = new Intent(getContext(), SynchronieActivity.class);
                startActivity(synchIntent);
            }
        });
    }


}