package com.example.cookshop.items;

import java.io.Serializable;

/**
 * Enum
 * contains categories for {@link Article}
 *
 * @author willi
 * @version 1.0
 */
public enum Category implements Serializable
{
    FRUIT("Fruit"), VEGETABLE("Vegetable"), MEAT("Meat"), DRINK("Drink"), OTHERS("Others");


    private String name;

    Category(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return this.name;
    }

}