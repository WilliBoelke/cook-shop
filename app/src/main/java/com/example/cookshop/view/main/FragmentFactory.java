package com.example.cookshop.view.main;

import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.cookshop.view.settings.FragmentSettings;


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
        if (className.equals(FragmentSyc.class.getName()))
        {
            Log.d(TAG, "Instantiate : FragmentRecommendedList");
            return new FragmentSyc();
        }
        if (className.equals(FragmentToCookList.class.getName()))
        {
            Log.d(TAG, "Instantiate : FragmentToCookList");
            return new FragmentToCookList();
        }
        if (className.equals(FragmentRecipeList.class.getName()))
        {
            Log.d(TAG, "Instantiate : FragmentRecipeList");
            return new FragmentRecipeList();
        }
        if (className.equals(FragmentSettings.class.getName()))
        {
            Log.d(TAG, "Instantiate : FragmentSettings");
            return new FragmentSettings();
        }
        else
        {
            super.instantiate(classLoader, className);
        }

        return new FragmentToCookList();
    }

}

