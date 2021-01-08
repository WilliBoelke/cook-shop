package com.example.cookshop.view.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.cookshop.view.recipeViewUpdateAdd.RecipeViewerStepFragment;

import java.util.ArrayList;


public class StepsSectionPagerAdapter extends FragmentPagerAdapter
{
    public static int                           position = 0;
    private final Context                       mContext;
    private ArrayList<RecipeViewerStepFragment> fragments;

    public StepsSectionPagerAdapter(Context context, FragmentManager fm, ArrayList<RecipeViewerStepFragment> fragments)
    {
        super(fm);
        this.fragments = fragments;
        mContext = context;
    }

    public static int getPos()
    {
        return position;
    }

    public static void setPos(int pos)
    {
        StepsSectionPagerAdapter.position = pos;
    }

    @Override
    public Fragment getItem(int position)
    {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        setPos(position);
        // I don't know if this is okay?
        return fragments.getClass().getSimpleName();
    }

    @Override
    public int getCount()
    {
        return fragments.size();
    }

}
