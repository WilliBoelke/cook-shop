package cookshop.model.listManagement;


import com.example.cookshop.controller.applicationController.ApplicationController;
import com.example.cookshop.items.Article;
import com.example.cookshop.items.Category;
import com.example.cookshop.items.Recipe;
import com.example.cookshop.items.Step;
import com.example.cookshop.model.database.DatabaseHelper;
import com.example.cookshop.model.listManagement.AvailableListManager;
import com.example.cookshop.model.listManagement.ShoppingListManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;

public class ListManagementTest
{
    private final DatabaseHelper mockDatabaseHelper = Mockito.mock(DatabaseHelper.class);
    private ApplicationController applicationController;

    private final Article testArticle1 = new Article("Apfel", "4 Äpfel", Category.FRUIT, 4, 1.0);
    private final Article testArticle2 = new Article("Birne", "3 Birnen", Category.FRUIT, 3, 1.0);
    private final Article testArticle3 = new Article("Gurke", "Beschreibung", Category.VEGETABLE, 1, 13);

    private final Step testStep1 = new Step("Schritt 1", "Beschreibung Schritt eins", 0, 1);
    private final Step testStep2 = new Step("Schritt 2", "Beschreibung Schritt zwei", 0, 2);
    private final Step testStep3 = new Step("Schritt 3", "Beschreibung Schritt drei", 0, 3);

    private Recipe testRecipe1;
    private Recipe testRecipe2;
    private Recipe testRecipe3;

    private final ArrayList<Article> al = new ArrayList<>();
    private final ArrayList<Step> sl = new ArrayList<>();

    @Before
    public void setUp()
    {
        al.add(testArticle1);
        al.add(testArticle2);
        al.add(testArticle3);
        sl.add(testStep1);
        sl.add(testStep2);
        sl.add(testStep3);
        testRecipe1 = new Recipe("Spaghetti", "Mit blognese", al, sl);
        testRecipe2 = new Recipe("Kartoffelsuppe", "Beschreibung", al, sl);
        testRecipe3 = new Recipe("Rezept", "Beschreibung", al, sl);
        ApplicationController.getInstance().initialize(mockDatabaseHelper);
        applicationController = ApplicationController.getInstance();
    }


    //....Add Items..........


    /**
     * Test if an Article will be added tro the ItemList of the ShoppingListManager
     * and if the insertRecipe on the DatabaseHelper mock is called
     */
    @Test
    public void addArticleToShoppingListManagerTest1()
    {
        assertTrue(applicationController.getShoppingList().size() == 0);
        applicationController.addArticleToShoppingList(testArticle1);
        assertTrue(applicationController.getShoppingList().size() == 1);
        Mockito.verify(mockDatabaseHelper).insertArticle(anyString(), any(Article.class));
    }

    /**
     * Test if 2 Articles will be added tro the ItemList of the ShoppingListManager
     * and if the insertArticleMethod of the databaseManager will be called with the correct parameters
     */
    @Test
    public void addArticleToShoppingListManagerTest2()
    {
        assertTrue(applicationController.getShoppingList().size() == 0);
        applicationController.addArticleToShoppingList(testArticle1);
        applicationController.addArticleToShoppingList(testArticle2);
        assertTrue(applicationController.getShoppingList().size() == 2);
        Mockito.verify(mockDatabaseHelper).insertArticle(ShoppingListManager.BELONGING_TAG, testArticle2);
        Mockito.verify(mockDatabaseHelper).insertArticle(ShoppingListManager.BELONGING_TAG, testArticle2);
    }

    /**
     * Test if 2 Articles with the same name will be added only once
     */
    @Test
    public void addArticleToShoppingListManagerTest3()
    {
        assertTrue(applicationController.getShoppingList().size() == 0);
        applicationController.addArticleToShoppingList(testArticle1);
        applicationController.addArticleToShoppingList(testArticle1);
        assertTrue(applicationController.getShoppingList().size() == 1);
    }

    /**
     * Test if 2 Articles with the same names will be merged together
     * Test Amount
     */
    @Test
    public void addArticleToShoppingListManagerTest_MergeTest1()
    {
        int amount = 8;
        testArticle1.setAmount(amount / 2);
        assertTrue(applicationController.getShoppingList().size() == 0);
        applicationController.addArticleToShoppingList(testArticle1);
        assertTrue(applicationController.getArticleFromShoppingList(0).getAmount() == amount / 2);
        applicationController.addArticleToShoppingList(testArticle1);
        assertTrue(applicationController.getArticleFromShoppingList(0).getAmount() == amount);
    }

    /**
     * Test if 2 Articles with the same names will be merged together
     * Test: Weight
     */
    @Test
    public void addArticleToShoppingListManagerTest_MergeTest2()
    {
        double weight = 10.0;
        testArticle1.setWeight(weight / 2);
        assertTrue(applicationController.getShoppingList().size() == 0);
        applicationController.addArticleToShoppingList(testArticle1);
        applicationController.addArticleToShoppingList(testArticle1);
        assertTrue(applicationController.getArticleFromShoppingList(0).getWeight() == weight);
    }

    /**
     * Test if 2 Articles with the same names will be merged together
     * Test: Description
     */
    @Test
    public void addArticleToShoppingListManagerTest_MergeTest3()
    {
        String description1 = "Beschreib eins";
        String description2 = "Beschreib eins";
        testArticle1.setDescription(description1);
        assertTrue(applicationController.getShoppingList().size() == 0);
        applicationController.addArticleToShoppingList(testArticle1);
        testArticle1.setDescription(description2);
        applicationController.addArticleToShoppingList(testArticle1);

        assertTrue(applicationController.getArticleFromShoppingList(0).getDescription().contains(description1));
        assertTrue(applicationController.getArticleFromShoppingList(0).getDescription().contains(description2));
    }

    /**
     * Test if an Article will be added tro the ItemList of the AvailableListManager
     * and if the insertRecipe on the DatabaseHelper mock is called
     */
    @Test
    public void addArticleToAvailableListManagerTest1()
    {
        assertTrue(applicationController.getAvailableList().size() == 0);
        applicationController.addArticleToAvailableList(testArticle1);
        assertTrue(applicationController.getAvailableList().size() == 1);
        Mockito.verify(mockDatabaseHelper).insertArticle(anyString(), any(Article.class));
    }

    /**
     * Test if 2 Articles will be added tro the ItemList of the AvailableListManager
     * and if the insertArticleMethod of the databaseManager will be called with the correct parameters
     */
    @Test
    public void addArticleToAvailableListManagerTest2()
    {
        assertTrue(applicationController.getAvailableList().size() == 0);
        applicationController.addArticleToAvailableList(testArticle1);
        applicationController.addArticleToAvailableList(testArticle2);
        assertTrue(applicationController.getAvailableList().size() == 2);
        Mockito.verify(mockDatabaseHelper).insertArticle(AvailableListManager.BELONGING_TAG, testArticle1);
        Mockito.verify(mockDatabaseHelper).insertArticle(AvailableListManager.BELONGING_TAG, testArticle2);
    }

    /**
     * Test if 2 Articles with the same name will be added only once
     */
    @Test
    public void addArticleToAvailableListManagerTest3()
    {
        assertTrue(applicationController.getAvailableList().size() == 0);
        applicationController.addArticleToAvailableList(testArticle1);
        applicationController.addArticleToAvailableList(testArticle1);
        assertTrue(applicationController.getAvailableList().size() == 1);
    }

    /**
     * Test if 2 Articles with the same names will be merged together
     * Test Amount
     */
    @Test
    public void addArticleToAvailableListManagerTest_MergeTest1()
    {
        int amount = 8;
        testArticle1.setAmount(amount / 2);
        assertTrue(applicationController.getAvailableList().size() == 0);
        applicationController.addArticleToAvailableList(testArticle1);
        applicationController.addArticleToAvailableList(testArticle1);
        assertTrue(applicationController.getArticleFromAvailableList(0).getAmount() == amount);
    }

    /**
     * Test if 2 Articles with the same names will be merged together
     * Test: Weight
     */
    @Test
    public void addArticleToAvailableListManagerTest_MergeTest2()
    {
        double weight = 10.0;
        testArticle1.setWeight(weight / 2);
        applicationController.addArticleToAvailableList(testArticle1);
        applicationController.addArticleToAvailableList(testArticle1);
        assertTrue(applicationController.getArticleFromAvailableList(0).getWeight() == weight);
    }

    /**
     * Test if 2 Articles with the same names will be merged together
     * Test: Description
     */
    @Test
    public void addArticleToAvailableListManagerTest_MergeTest3()
    {
        String description1 = "Beschreib eins";
        String description2 = "Beschreib eins";
        testArticle1.setDescription(description1);
        applicationController.addArticleToAvailableList(testArticle1);
        testArticle1.setDescription(description2);
        applicationController.addArticleToAvailableList(testArticle1);

        assertTrue(applicationController.getArticleFromAvailableList(0).getDescription().contains(description1));
        assertTrue(applicationController.getArticleFromAvailableList(0).getDescription().contains(description2));
    }

    /**
     * Test if a Recipe will be added to the ItemList of the RecipeListManager
     * and if the insertRecipe method on the DatabaseHelper e will be called
     */
    @Test
    public void addRecipeTest1()
    {
        assertTrue(applicationController.getRecipeList().size() == 0);
        applicationController.addRecipe(testRecipe1);
        assertTrue(applicationController.getRecipeList().size() == 1);
        Mockito.verify(mockDatabaseHelper).insertRecipe(any(Recipe.class));
    }

    /**
     * Test if a Recipe will be added to the ItemList of the RecipeListManager
     * and if the insertRecipe method on the DatabaseHelper e will be called
     */
    @Test
    public void addRecipeTest2()
    {
        assertTrue(applicationController.getRecipeList().size() == 0);
        applicationController.addRecipe(testRecipe1);
        applicationController.addRecipe(testRecipe2);
        Mockito.verify(mockDatabaseHelper, times(2)).insertRecipe(any(Recipe.class));
        assertTrue(applicationController.getRecipeList().size() == 2);

    }

    /**
     * Test if a Recipe will be added to the ItemList of the RecipeListManager
     * and if the insertRecipe method on the DatabaseHelper  will be called
     */
    @Test
    public void addRecipeTest3()
    {
        assertTrue(applicationController.getRecipeList().size() == 0);
        applicationController.addRecipe(testRecipe1);
        applicationController.addRecipe(testRecipe2);
        assertTrue(applicationController.getRecipeList().size() == 2);
        Mockito.verify(mockDatabaseHelper, times(2)).insertRecipe(any(Recipe.class));
    }

    /**
     * Test if  the insertRecipe method on the DatabaseHelper  will be called
     * with the correct parameters
     */
    @Test
    public void addRecipeTest4()
    {
        applicationController.addRecipe(testRecipe1);
        Mockito.verify(mockDatabaseHelper).insertRecipe(testRecipe1);
    }


    //....Delete Items..........


    /**
     * Test if an article will be deleted
     */
    @Test
    public void deleteArticleFromShoppingListTest1()
    {
        assertTrue(applicationController.getShoppingList().size() == 0);
        applicationController.addArticleToShoppingList(testArticle1);
        assertTrue(applicationController.getShoppingList().size() == 1);

        applicationController.deleteArticleFromShoppingList(0);
        assertTrue(applicationController.getShoppingList().size() == 0);
    }

    /**
     * Test if the correct article will be deleted
     */
    @Test
    public void deleteArticleFromShoppingListTest2()
    {
        assertTrue(applicationController.getShoppingList().size() == 0);
        applicationController.addArticleToShoppingList(testArticle1);
        applicationController.addArticleToShoppingList(testArticle2);
        applicationController.addArticleToShoppingList(testArticle3);
        assertTrue(applicationController.getShoppingList().size() == 3);

        //Delete testArticle2
        applicationController.deleteArticleFromShoppingList(1);
        //Two articles should be still in the itemList:
        assertTrue(applicationController.getShoppingList().size() == 2);

        assertTrue(applicationController.getArticleFromShoppingList(0).equals(testArticle1));
        assertTrue(applicationController.getArticleFromShoppingList(1).equals(testArticle3));
    }

    /**
     * Verify method calls on the mocked DatabaseHelper
     */
    @Test
    public void deleteArticleFromShoppingListTest3()
    {
        applicationController.addArticleToShoppingList(testArticle1);
        applicationController.addArticleToShoppingList(testArticle2);
        applicationController.addArticleToShoppingList(testArticle3);
        Mockito.verify(mockDatabaseHelper, times(3)).insertArticle(anyString(), any(Article.class));

        //Delete testArticle2
        applicationController.deleteArticleFromShoppingList(1);
        Mockito.verify(mockDatabaseHelper).deleteArticle(anyString(), anyString());
    }

    /**
     * Verify method calls with correct parameter on the mocked DatabaseHelper
     */
    @Test
    public void deleteArticleFromShoppingListTest4()
    {
        applicationController.addArticleToShoppingList(testArticle1);
        Mockito.verify(mockDatabaseHelper).insertArticle(ShoppingListManager.BELONGING_TAG, testArticle1);

        //Delete testArticle2
        applicationController.deleteArticleFromShoppingList(0);
        Mockito.verify(mockDatabaseHelper).deleteArticle(testArticle1.getName(), ShoppingListManager.BELONGING_TAG);
    }

    /**
     * Test if an article will be deleted
     */
    @Test
    public void deleteArticleFromAvailableListTest1()
    {
        assertTrue(applicationController.getAvailableList().size() == 0);
        applicationController.addArticleToAvailableList(testArticle1);
        assertTrue(applicationController.getAvailableList().size() == 1);

        applicationController.deleteArticleFromAvailableList(0);
        assertTrue(applicationController.getAvailableList().size() == 0);
    }

    /**
     * Test if the correct article will be deleted
     */
    @Test
    public void deleteArticleFromAvailableListTest2()
    {
        assertTrue(applicationController.getAvailableList().size() == 0);
        applicationController.addArticleToAvailableList(testArticle1);
        applicationController.addArticleToAvailableList(testArticle2);
        applicationController.addArticleToAvailableList(testArticle3);
        assertTrue(applicationController.getAvailableList().size() == 3);

        //Delete testArticle2
        applicationController.deleteArticleFromAvailableList(1);
        //Two articles should be still in the itemList:
        assertTrue(applicationController.getAvailableList().size() == 2);

        assertTrue(applicationController.getArticleFromAvailableList(0).equals(testArticle1));
        assertTrue(applicationController.getArticleFromAvailableList(1).equals(testArticle3));
    }

    /**
     * Verify method calls on the mocked DatabaseHelper
     */
    @Test
    public void deleteArticleFromAvailableListTest3()
    {
        applicationController.addArticleToAvailableList(testArticle1);
        applicationController.addArticleToAvailableList(testArticle2);
        applicationController.addArticleToAvailableList(testArticle3);
        Mockito.verify(mockDatabaseHelper, times(3)).insertArticle(anyString(), any(Article.class));

        //Delete testArticle2
        applicationController.deleteArticleFromAvailableList(1);
        Mockito.verify(mockDatabaseHelper).deleteArticle(anyString(), anyString());
    }

    /**
     * Verify method calls with correct parameter on the mocked DatabaseHelper
     */
    @Test
    public void deleteArticleFromAvailableListTest4()
    {
        applicationController.addArticleToAvailableList(testArticle1);
        Mockito.verify(mockDatabaseHelper).insertArticle(AvailableListManager.BELONGING_TAG, testArticle1);

        //Delete testArticle2
        applicationController.deleteArticleFromAvailableList(0);
        Mockito.verify(mockDatabaseHelper).deleteArticle(testArticle1.getName(), AvailableListManager.BELONGING_TAG);
    }

    /**
     * Test if an recipe will be deleted
     */
    @Test
    public void deleteRecipeFromListTest1()
    {
        assertTrue(applicationController.getRecipeList().size() == 0);
        applicationController.addRecipe(testRecipe1);

        assertTrue(applicationController.getRecipeList().size() == 1);

        applicationController.deleteRecipe(0);
        assertTrue(applicationController.getRecipeList().size() == 0);
    }

    /**
     * Test if the correct recipe will be deleted
     */
    @Test
    public void deleteRecipeFromListTest2()
    {
        assertTrue(applicationController.getRecipeList().size() == 0);
        applicationController.addRecipe(testRecipe1);
        applicationController.addRecipe(testRecipe1);
        applicationController.addRecipe(testRecipe3);
        assertTrue(applicationController.getRecipeList().size() == 3);

        //Delete testArticle2
        applicationController.deleteRecipe(1);
        //Two articles should be still in the itemList:
        assertTrue(applicationController.getRecipeList().size() == 2);

        assertTrue(applicationController.getRecipe(0).equals(testRecipe1));
        assertTrue(applicationController.getRecipe(1).equals(testRecipe3));
    }

    /**
     * Verify method calls on the mocked DatabaseHelper
     */
    @Test
    public void deleteRecipeFromListTest3()
    {
        applicationController.addRecipe(testRecipe1);
        applicationController.addRecipe(testRecipe1);
        applicationController.addRecipe(testRecipe3);
        Mockito.verify(mockDatabaseHelper, times(3)).insertRecipe(any(Recipe.class));

        //Delete testArticle2
        applicationController.deleteRecipe(1);
        Mockito.verify(mockDatabaseHelper).deleteRecipe(anyString());
    }

    /**
     * Verify method calls with correct parameter on the mocked DatabaseHelper
     */
    @Test
    public void deleteRecipeFromListTest4()
    {
        applicationController.addRecipe(testRecipe1);
        Mockito.verify(mockDatabaseHelper).insertRecipe(testRecipe1);

        //Delete testArticle2
        applicationController.deleteRecipe(0);
        Mockito.verify(mockDatabaseHelper).deleteRecipe(testRecipe1.getName());
    }


    //....Update Items..........


    /**
     * Verify method call
     */
    @Test
    public void updateArticleFromShoppingListTest1()
    {
        applicationController.addArticleToShoppingList(testArticle2);
        applicationController.addArticleToShoppingList(testArticle3);
        applicationController.updateArticleFromShoppingList(1, testArticle1);
        Mockito.verify(mockDatabaseHelper).updateArticle(anyString(), anyString(), any(Article.class));
    }

    /**
     * Verify parameter
     */
    @Test
    public void updateArticleFromShoppingListTest2()
    {
        applicationController.addArticleToShoppingList(testArticle2);
        applicationController.addArticleToShoppingList(testArticle3);
        applicationController.updateArticleFromShoppingList(1, testArticle1);
        Mockito.verify(mockDatabaseHelper).updateArticle(testArticle3.getName(), ShoppingListManager.BELONGING_TAG, testArticle1);
    }

    /**
     * Verify that no articles was added / size is still the same
     */
    @Test
    public void updateArticleFromShoppingListTest3()
    {
        applicationController.addArticleToShoppingList(testArticle2);
        applicationController.addArticleToShoppingList(testArticle3);
        int size = applicationController.getShoppingList().size();

        applicationController.updateArticleFromShoppingList(1, testArticle1);
        assertTrue(size == applicationController.getShoppingList().size());
    }

    /**
     * Verify that the Article becomes updated
     */
    @Test
    public void updateArticleFromShoppingListTest4()
    {
        applicationController.addArticleToShoppingList(testArticle2);
        applicationController.addArticleToShoppingList(testArticle3);

        applicationController.updateArticleFromShoppingList(1, testArticle1);
        assertTrue(applicationController.getArticleFromShoppingList(1).equals(testArticle1));
        assertTrue(applicationController.getArticleFromShoppingList(0).equals(testArticle2));
    }

    /**
     * Verify method call
     */
    @Test
    public void updateArticleFromAvailableListTest1()
    {
        applicationController.addArticleToAvailableList(testArticle2);
        applicationController.addArticleToAvailableList(testArticle3);
        applicationController.updateArticleFromAvailableList(1, testArticle1);
        Mockito.verify(mockDatabaseHelper).updateArticle(anyString(), anyString(), any(Article.class));
    }

    /**
     * Verify parameter
     */
    @Test
    public void updateArticleFromAvailableListTest2()
    {
        applicationController.addArticleToAvailableList(testArticle2);
        applicationController.addArticleToAvailableList(testArticle3);
        applicationController.updateArticleFromAvailableList(1, testArticle1);
        Mockito.verify(mockDatabaseHelper).updateArticle(testArticle3.getName(), AvailableListManager.BELONGING_TAG, testArticle1);
    }

    /**
     * Verify that no articles was added / size is still the same
     */
    @Test
    public void updateArticleFromAvailableListTest3()
    {
        applicationController.addArticleToAvailableList(testArticle2);
        applicationController.addArticleToAvailableList(testArticle3);
        int size = applicationController.getAvailableList().size();

        applicationController.updateArticleFromAvailableList(1, testArticle1);
        assertTrue(size == applicationController.getAvailableList().size());
    }

    /**
     * Verify that the Article becomes updated
     */
    @Test
    public void updateArticleFromAvailableListTest4()
    {
        applicationController.addArticleToAvailableList(testArticle2);
        applicationController.addArticleToAvailableList(testArticle3);

        applicationController.updateArticleFromAvailableList(1, testArticle1);
        assertTrue(applicationController.getArticleFromAvailableList(1).equals(testArticle1));
        assertTrue(applicationController.getArticleFromAvailableList(0).equals(testArticle2));
    }

    /**
     * Verify method call
     */
    @Test
    public void updateRecipeTest1()
    {
        applicationController.addRecipe(testRecipe1);
        applicationController.addRecipe(testRecipe2);
        applicationController.updateRecipe(1, testRecipe3);
        Mockito.verify(mockDatabaseHelper).updateRecipe(anyString(), any(Recipe.class));
    }

    /**
     * Verify parameter
     */
    @Test
    public void updateRecipeTest2()
    {
        applicationController.addRecipe(testRecipe2);
        applicationController.addRecipe(testRecipe3);
        applicationController.updateRecipe(1, testRecipe1);
        Mockito.verify(mockDatabaseHelper).updateRecipe(testRecipe3.getName(), testRecipe1);
    }

    /**
     * Verify that no recipe was added / size is still the same
     */
    @Test
    public void updateRecipeTest3()
    {
        applicationController.addRecipe(testRecipe1);
        applicationController.addRecipe(testRecipe2);
        int size = applicationController.getRecipeList().size();

        applicationController.updateRecipe(1, testRecipe3);
        assertTrue(size == applicationController.getRecipeList().size());
    }

    /**
     * Verify that the Article becomes updated
     */
    @Test
    public void updateRecipeTest4()
    {
        applicationController.addRecipe(testRecipe1);
        applicationController.addRecipe(testRecipe2);

        applicationController.updateRecipe(1, testRecipe3);
        assertTrue(applicationController.getRecipe(1).equals(testRecipe3));
        assertTrue(applicationController.getRecipe(0).equals(testRecipe1));
    }


    //....Add Articles Intelligent..........


    /**
     * Verify that no article is added
     */
    @Test
    public void addArticlesIntelligentTest1()
    {
        applicationController.addArticleToShoppingList(testArticle1);
        applicationController.addArticleToShoppingList(testArticle2);
        applicationController.addArticleToShoppingList(testArticle3);
        assertTrue(applicationController.getShoppingList().size() == 3);

        // adding article with existing name and values
        applicationController.addArticleToShoppingList(testArticle1);
        assertTrue(applicationController.getShoppingList().size() == 3);

        // adding article with existing name but different values
        Article newArticle = new Article(testArticle1.getName(), "bla beschreibung 1234", Category.MEAT, 213, 2654);
        applicationController.addArticleToShoppingList(newArticle);
        assertTrue(applicationController.getShoppingList().size() == 3);
    }

    /**
     * Verify that the weight will be added
     */
    @Test
    public void addArticlesIntelligentTest2()
    {
        double weight = testArticle1.getWeight();
        applicationController.addArticleToShoppingList(testArticle1);
        applicationController.addArticleToShoppingList(testArticle2);
        assertTrue(applicationController.getArticleFromShoppingList(0).getWeight() == weight);
        applicationController.addArticleToShoppingList(testArticle1);
        assertTrue(applicationController.getArticleFromShoppingList(0).getWeight() == weight * 2);
    }

    /**
     * Verify that the name stays the same
     */
    @Test
    public void addArticlesIntelligentTest3()
    {
        String name = testArticle1.getName();
        applicationController.addArticleToShoppingList(testArticle1);
        applicationController.addArticleToShoppingList(testArticle2);
        assertTrue(applicationController.getArticleFromShoppingList(0).getName().equals(name));
        applicationController.addArticleToShoppingList(testArticle1);
        assertTrue(applicationController.getArticleFromShoppingList(0).getName().equals(name));
    }

    /**
     * Verify that the amount will be added
     */
    @Test
    public void addArticlesIntelligentTest4()
    {
        int amount = testArticle2.getAmount();
        applicationController.addArticleToShoppingList(testArticle1);
        applicationController.addArticleToShoppingList(testArticle2);
        assertTrue(applicationController.getArticleFromShoppingList(1).getAmount() == amount);
        applicationController.addArticleToShoppingList(testArticle2);
        assertTrue(applicationController.getArticleFromShoppingList(1).getAmount() == amount * 2);
    }

    /**
     * Verify that the category stays the same
     */
    @Test
    public void addArticlesIntelligentTest5()
    {
        Category cat = testArticle2.getCategory();
        applicationController.addArticleToShoppingList(testArticle1);
        applicationController.addArticleToShoppingList(testArticle2);
        System.out.println(applicationController.getArticleFromShoppingList(1).getCategory().toString());
        System.out.println(applicationController.getArticleFromShoppingList(1).getName());
        assertTrue(applicationController.getArticleFromShoppingList(1).getCategory() == cat);

        Article newArticle = new Article(testArticle2.getName(), "bla beschreibung 1234", Category.VEGETABLE, 213, 2654);
        applicationController.addArticleToShoppingList(newArticle);
        System.out.println(applicationController.getArticleFromShoppingList(1).getCategory().toString());
        System.out.println(applicationController.getArticleFromShoppingList(1).getName());
        assertTrue(applicationController.getArticleFromShoppingList(1).getCategory() == cat);
    }


    //....get list of now available articles ..........


    /**
     * Verify the number of not available articles
     */
    @Test
    public void getNotAvailableArticlesTest1()
    {
        applicationController.addArticleToAvailableList(testArticle1);
        applicationController.addRecipe(testRecipe1); // contains testArticle1, 2 and 3

        applicationController.recipeToShoppingList(0);

        //Article2 and 3 should be on the buying list
        assertTrue(applicationController.getShoppingList().size() == 2);
    }

    /**
     * Test if the articles on the buying list are correct
     */
    @Test
    public void getNotAvailableArticlesTest2()
    {
        applicationController.addArticleToAvailableList(testArticle1);
        applicationController.addRecipe(testRecipe1); // contains testArticle1, 2 and 3

        applicationController.recipeToShoppingList(0);

        //Article2 and 3 should be on the buying list
        assertTrue(applicationController.getArticleFromShoppingList(0).equals(testArticle2));
        assertTrue(applicationController.getArticleFromShoppingList(1).equals(testArticle3));
    }
}


