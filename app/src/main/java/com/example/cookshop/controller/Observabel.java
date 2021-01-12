package com.example.cookshop.controller;

public interface Observabel
{
        void registerOnRecipeListChangeListener(Observer observer);


        void unregisterOnRecipeListChangeListener(Observer observer);


        void registerOnShoppingListChangeListener(Observer observer);


        void unregisterOnShoppingListChangeListener(Observer observer);


        void registerOnAvailableListChangeListener(Observer observer);


        void unregisterOnAvailableListChangeListener(Observer observer);

        void registerOnToCookListChangeListener(Observer observer);

        void unregisterOnToCookListChangeListener(Observer observer);

        void onAvailableListChange();

        void onShoppingListChange();

        void onRecipeListChange();

        void onToCookListChange();
}
