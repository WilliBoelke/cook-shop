package com.example.cookshop.view;

import android.util.Log;

import androidx.fragment.app.Fragment;


public class FragmentFactory extends androidx.fragment.app.FragmentFactory
{

    private String TAG = getClass().getSimpleName();

    /**
     * Public constructor
     */
    public FragmentFactory()
    {
        // Used to inject dependencies ;
    }

    @Override
    public Fragment instantiate(ClassLoader classLoader, String className)
    {

        if (className.equals(FragmentShoppingList.class.getName()))
        {
            Log.d(TAG, "Instantiate : FragmentShoppingList");
            return new FragmentShoppingList();
        }
        if (className.equals(FragmentAvailableList.class.getName()))
        {
            Log.d(TAG, "Instantiate : FragmentAvailableList");
            return new FragmentAvailableList();
        }
        if (className.equals(FragmentRecommendedList.class.getName()))
        {
            Log.d(TAG, "Instantiate : FragmentRecommendedList");
            return new FragmentRecommendedList();
        }
        if (className.equals(FragmentToCookList.class.getName()))
        {
            Log.d(TAG, "Instantiate : FragmentToCookList");
            return new FragmentToCookList();
        }
        if (className.equals(FragmentRecipeList.class.getName())) {
            Log.d(TAG, "Instantiate : FragmentRecipeList");
            return new FragmentRecipeList();
        }
        else
        {
            super.instantiate(classLoader, className);
        }

        return new FragmentToCookList();
    }

}

