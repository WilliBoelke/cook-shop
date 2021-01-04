package com.example.cookshop.model.listManager;

import com.example.cookshop.items.Article;
import com.example.cookshop.items.Item;
import com.example.cookshop.items.Recipe;
import com.example.cookshop.model.database.DatabaseHelper;

import java.util.ArrayList;

/**
 * Superclass for the other list manager classes
 * Wrapper for an ArrayList of Items.
 * Loads the content of the {@link DatabaseHelper} into an ArrayList at runtime
 * Inserts new Items to the DatabaseHelper.
 *
 * @param <T> an {@link Article} or {@link Recipe}
 * @author willi
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
        private DatabaseHelper databaseHelper;


        //....Constructor..........


        public ItemListManager(DatabaseHelper databaseHelper)
        {
            this.itemList = new ArrayList<>();
            this.databaseHelper = databaseHelper;
        }


        //....Methods..........



        /**
         * Return the complete list
         *
         * @return
         */
        protected ArrayList<T> getItemList()
        {
            return this.itemList;
        }

        /**
         * Should be used to set the ItemList after retrieving the data from the DatabaseHelper (at application start)
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
        protected <T> Object getItem(int position)
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
        protected <T> Object getItem(String name)
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
         * which is overridden in {@link } , {@link ShoppingListManager}
         * and {@link RecipeListManager}, to delete the article not only from the ItemList but also from the database
         * (Implemented as:  template method design pattern)
         *
         * @param position
         *         the index of the Item in the lItemList
         */
        protected void removeItem(int position)
        {
            this.removeItemFromDatabase(itemList.get(position).getName());
            itemList.remove(position);
        }

        /**
         * This method adds one Item from the  item from the ItemList
         * and calls the  abstract method {@link #addItemToDatabase(T +Object)}
         * which is overridden in {@link AvailableListManager} , {@link ShoppingListManager}
         * and {@link RecipeListManager}, o add the items to the Database
         * (Implemented as:  template method design pattern)
         *
         * @param object
         *         the Item to be added to the ItemList and to the Database
         */
        protected void addItem(T object)
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
            return this.databaseHelper;
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
