package com.example.cookshop.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.cookshop.R;
import com.example.cookshop.view.SynchronizeActivity;


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


        setupSynchronizationButton();
        return view;
    }

    private void setupSynchronizationButton()
    {
        Button synchronize = view.findViewById(R.id.sync_button);
        synchronize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent syncIntent = new Intent(getContext(), SynchronizeActivity.class);
                startActivity(syncIntent);
            }
        });
    }


}