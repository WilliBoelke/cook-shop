package com.example.cookshop.model;

public interface Observabel
{
        void registerOnRecipeListChangeListener(Observer observer);


        void unregisterOnRecipeListChangeListener(Observer observer);


        void registerOnBuyingListChangeListener(Observer observer);


        void unregisterOnBuyingListChangeListener(Observer observer);


        void registerOnAvailableListChangeListener(Observer observer);


        void unregisterOnAvailableListChangeListener(Observer observer);


        void onAvailableListChange();

        void onBuyingListChange();

        void onRecipeListChange();
}
