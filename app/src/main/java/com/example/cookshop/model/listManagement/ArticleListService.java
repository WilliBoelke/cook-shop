package com.example.cookshop.model.listManagement;

import android.util.Log;

import com.example.cookshop.items.Article;
import com.example.cookshop.items.Category;
import com.example.cookshop.items.Item;
import com.example.cookshop.model.database.DatabaseHelper;

import java.util.ArrayList;

/**
 * Subclass of {@link ItemListService} and Superclass of {@link AvailableListService} and {@link ShoppingListService}
 * Adds more Article specific functionality to the {@link ItemListService}
 *
 * @author WilliBoelke
 * @version 1.0
 */
public class ArticleListService extends ItemListService
{

    //....Constructor..........


    protected ArticleListService(DatabaseHelper databaseService)
    {
        super(databaseService);
    }

    private final String TAG = this.getClass().getSimpleName();

    //....Methods..........


    protected void overrideListCompletely(ArrayList<Article> list)
    {
        int size = this.getItemList().size();
        for (int i = 0; i < size; i++)
        {
            Log.d("SynchronizationManager", "itemList size =  " + this.getItemList().size());
            removeItem(0); //Always remove to first item, (list will get smaller)
        }
        Log.d("SynchronizationManager", "Adding Articles  = " +  list.size());
        for (Article a: list)
        {

            addItem(a);
        }
    }



    protected Article searchForArticle(String name)
    {
        ArrayList<Article> toSearch = (ArrayList<Article>) this.getItemList();

        for (int i = 0; i < toSearch.size(); i++)
        {
            if (toSearch.get(i).getName().equals(name))
            {
                return toSearch.get(i);
            }
        }
        return null;
    }

    /**
     * Adds a List of Items to the ItemList.
     * Don't just append the Articles at the end of the list, but searches the
     * List for Articles with the same name and merges them together
     *
     * @param articles
     */
    protected void addSeveralArticlesIntelligent(ArrayList<Article> articles)
    {
        for (int i = 0; i < articles.size(); i++)
        {
            addArticleIntelligent(articles.get(i));
        }
    }

    /**
     * @param article
     */
    protected void addArticleIntelligent(Article article)
    {
        // Boolean to check if a match was found (and merged)
        Boolean merged = false;

        for (int index = 0; index < this.getItemList().size(); index++)
        {
            // Get the Article once to minimize method calls
            Article tempArticle = (Article) this.getItem(index);

            //save the names of both articles / trimmed and in lowercase
            String trimmedArticleName     = article.getName().trim().toLowerCase();
            String trimmedTempArticleName = tempArticle.getName().trim().toLowerCase();

            // Check if the names are the same
            if (trimmedArticleName.equals(trimmedTempArticleName))
            {
                merged = true;

                //merging the values of both articles

                String name     = article.getName();
                int      amount   = article.getAmount() + tempArticle.getAmount();
                double   weight   = article.getWeight() + tempArticle.getWeight();
                Category category = tempArticle.getCategory(); // category should say as it was
                String description;

                if (!article.getDescription().equals(tempArticle.getDescription()))
                {
                    description = article.getDescription().concat(" // merged // " + tempArticle.getDescription());
                }
                else
                {
                    description = article.getDescription();
                }

                // generation a new article frpm the merged values
                Article mergedArticle = new Article(name, description, category, amount, weight);

                // updating the old article with the merged article
                updateArticle(index, mergedArticle);
            }
        }
        // if nothing was merged
        if (!merged)
        {
            // just append the new article
            this.addItem(article);
        }
    }

    /**
     * @param index
     * @param newArticle
     */
    protected void updateArticle(int index, Article newArticle)
    {
        Article oldArticle = (Article) this.getItem(index);

        this.getItemList().set(index, newArticle);
        Log.d(TAG, "updateArticle :  oldArticle  = " +oldArticle.getName() + " new article = " + newArticle.getName());
        this.getDatabase().updateArticle(oldArticle.getName(), getBELONGING_TAG(), newArticle);
    }

    /**
     * @return
     */
    protected String getBELONGING_TAG()
    {
        // To be overridden in the subclasses
        return null;
    }


    public ArrayList<Article> getListOfNotAvailableArticles(ArrayList<Article> neededArticles)
    {
        Article neededArticle;
        Article availableArticle;
        Article newArticle = new Article();

        ArrayList<Article> result = new ArrayList<>();


        for (int i = 0; i < neededArticles.size(); i++)
        {
            neededArticle = neededArticles.get(i);
            Boolean foundMatch = false;

            for (int j = 0; j < this.getItemList().size(); j++)
            {
                Boolean changed = false;


                availableArticle = (Article) this.getItemList().get(j);


                if (neededArticle.getName().equals(availableArticle.getName()))
                {
                    foundMatch = true;
                    newArticle = ((Article) this.getItemList().get(j)).clone();
                    if (availableArticle.getAmount() < neededArticle.getAmount())
                    {
                        int sa = neededArticle.getAmount() - availableArticle.getAmount();
                        changed = true;
                        newArticle.setAmount(neededArticle.getAmount() - availableArticle.getAmount());
                    }
                    if (availableArticle.getWeight() < neededArticle.getWeight())
                    {
                        changed = true;
                        newArticle.setWeight(availableArticle.getWeight() - neededArticle.getWeight());
                    }
                }

                if (changed)
                {
                    if (!availableArticle.getDescription().contains("Dieser Zutat ist teilweise im lager Verfügbar") && !neededArticle.getDescription().contains("Dieser Zutat ist teilweise im lager Verfügbar"))
                    {
                        StringBuilder newDescription = new StringBuilder();

                        newDescription.append("\nDieser Zutat ist teilweise im lager Verfügbar :  \n ");
                        newDescription.append("------------------------------------------------  \n\n ");
                        newDescription.append("Im Lager :  " + availableArticle.getAmount() + " Stück. \n ");
                        newDescription.append("Beschreibung :  " + availableArticle.getDescription() + "  \n\n ");

                        newDescription.append("Gebaraucht :  " + neededArticle.getAmount() + " Stück. \n ");
                        newDescription.append("Beschreibung :  " + neededArticle.getDescription() + "  \n\n    ");

                        newArticle.setDescription(newDescription.toString());
                    }

                    result.add(newArticle);
                }

            }
            if (!foundMatch)
            {
                result.add(neededArticle);
            }
        }

        return result;
    }

    //....DatabaseServicePersistence..........


    @Override
    protected void removeItemFromDatabase(String name)
    {
        // No functionality on this layer, implemented in BuyingListService and AvailableListService
    }

    @Override
    protected void addItemToDatabase(Item Object)
    {
        // No functionality on this layer, implemented in BuyingListService and AvailableListService
    }

    @Override
    protected void addItemsToDatabase(ArrayList list)
    {
        // No functionality on this layer, implemented in BuyingListService and AvailableListService
    }


}

