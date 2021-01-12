package com.example.cookshop.model;

import com.example.cookshop.items.Recipe;
import com.example.cookshop.view.main.FragmentToCookList;

import java.util.ArrayList;

public interface Observabel
{
        void registerOnRecipeListChangeListener(Observer observer);


        void unregisterOnRecipeListChangeListener(Observer observer);


        void registerOnBuyingListChangeListener(Observer observer);


        void unregisterOnBuyingListChangeListener(Observer observer);


        void registerOnAvailableListChangeListener(Observer observer);


        void unregisterOnAvailableListChangeListener(Observer observer);

        void registerOnToCookListChangeListener(Observer observer);

        void unregisterOnToCookListChangeListener(Observer observer);

        void onAvailableListChange();

        void onShoppingListChange();

        void onRecipeListChange();

        void onToCookListChange();
}
