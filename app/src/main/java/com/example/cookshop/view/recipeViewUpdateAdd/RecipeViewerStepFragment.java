package com.example.cookshop.view.recipeViewUpdateAdd;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.cookshop.R;
import com.example.cookshop.items.Step;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class RecipeViewerStepFragment extends Fragment
{
    private static final String ARG_PARAM1 = "step";
    private Step step;

    public RecipeViewerStepFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RecipeViewerStepFragment.
     */
    public static RecipeViewerStepFragment newInstance(Step s)
    {
        RecipeViewerStepFragment fragment = new RecipeViewerStepFragment();
        Bundle                   args     = new Bundle();
        args.putSerializable(ARG_PARAM1, s);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            this.step = (Step) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_viewer_step, container, false);

        TextView name = view.findViewById(R.id.name_textview);
        name.setText(this.step.getName());

        TextView description = view.findViewById(R.id.description_textview);
        description.setText(this.step.getDescription());

        TextView             timer    = view.findViewById(R.id.timer_textview);
        FloatingActionButton timerBtn = view.findViewById(R.id.timer_fab);

        //Set the textview and the button to invisible if there is no timer given
        if (this.step.getTimerInSeconds() <= 0)
        {
            timer.setVisibility(View.INVISIBLE);
            timerBtn.setVisibility(View.GONE);
        }
        else
        {
            timer.setText("" + this.step.getTimerInSeconds());
            //TODO BTN onClick
        }
        return view;
    }
}
