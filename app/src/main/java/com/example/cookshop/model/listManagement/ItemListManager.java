package com.example.cookshop.model.listManagement;

import com.example.cookshop.items.Article;
import com.example.cookshop.items.Item;
import com.example.cookshop.items.Recipe;
import com.example.cookshop.model.database.DatabaseHelper;

import java.util.ArrayList;

/**
 * An ItemListService is a Wrapper class for an ArrayList of items.
 * Its Loads its content from the {@link DatabaseHelper} at the start of the App
 * to make it accessible at runtime.
 *
 * Example {@link ArticleListManager} and its subclasses
 *
 *
 * @param <T> an {@link Article} or {@link Recipe}
 * @author WilliBoelke
 */
public abstract class ItemListManager<T extends Item & Comparable<T>>
{

    /**
     * The Item ArrayList
     */
    private ArrayList<T> itemList;

    /**
     * The {@link DatabaseHelper} for persisting the data
     */
    private DatabaseHelper databaseService;


    //....Constructor..........


    public ItemListManager(DatabaseHelper databaseService)
    {
        this.itemList = new ArrayList<>();
        this.databaseService = databaseService;
    }


    //....Methods..........



    /**
     * Return the complete list
     *
     * @return
     */
    public ArrayList<T> getItemList()
    {
        return this.itemList;
    }

    /**
     * Should be used to set the ItemList after retrieving the data from the DatabaseService (at application start)
     *
     * @param itemList
     *         an list of Items (Which was retrieved from the Database)
     */
    protected void setItemList(ArrayList<T> itemList)
    {
        this.itemList = itemList;
    }

    /**
     * Returns a single Item from the ItemList
     *
     * @param position
     *         the index of the item in the list
     * @return the Item at position
     */
    public <T> Object getItem(int position)
    {
        return itemList.get(position);
    }

    /**
     * Returns a single Item from the ItemList
     *
     * @param name
     *         of the Item
     * @return the Item with the name
     */
    public <T> Object getItem(String name)
    {

        for (int i = 0; i < itemList.size(); i++)
        {
            if (itemList.get(i).getName().equals(name))
            {
                return itemList.get(i);
            }
        }
        return null;
    }

    /**
     * This method removes the Item from the ItemList
     * and calls the  abstract method {@link #removeItemFromDatabase(String)}
     * which is overridden in {@link AvailableListManager} , {@link ShoppingListManager}
     * and {@link RecipeListManager}, to delete the article not only from the ItemList but also from the database
     * (Implemented as:  template method design pattern)
     *
     * @param position
     *         the index of the Item in the lItemList
     */
    public void removeItem(int position)
    {
        this.removeItemFromDatabase(itemList.get(position).getName());
        itemList.remove(position);
    }

    /**
     * This method adds one Item from the  item from the ItemList
     * and calls the  abstract method {@link #addItemToDatabase(T +Object)}
     * which is overridden in {@link AvailableListManager} , {@link ShoppingListManager}
     * and RecipeListService, o add the items to the Database
     * (Implemented as:  template method design pattern)
     *
     * @param object
     *         the Item to be added to the ItemList and to the Database
     */
    public void addItem(T object)
    {
        addItemToDatabase(object);
        this.itemList.add(object);
    }

    /**
     * Returns the DatabaseService to the calling Subclasses
     *
     * @return
     */
    protected DatabaseHelper getDatabase()
    {
        return this.databaseService;
    }


    //....Database..........

    /**
     * To be overridden
     *
     * @param name
     */
    protected abstract void removeItemFromDatabase(String name);

    /**
     * To be overridden
     *
     * @param object
     */
    protected abstract void addItemToDatabase(T object);

    /**
     * To be overridden
     *
     * @param list
     */
    protected abstract void addItemsToDatabase(ArrayList<T> list);


}
