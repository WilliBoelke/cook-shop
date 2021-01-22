package cookshop.controller;


import android.content.Context;

import com.example.cookshop.controller.Observer;
import com.example.cookshop.controller.applicationController.ApplicationController;;
import com.example.cookshop.items.Article;
import com.example.cookshop.items.Category;
import com.example.cookshop.items.Recipe;
import com.example.cookshop.items.Step;
import com.example.cookshop.model.listManagement.AvailableListManager;
import com.example.cookshop.model.listManagement.RecipeListManager;
import com.example.cookshop.model.listManagement.ShoppingListManager;
import com.example.cookshop.model.listManagement.ToCookListManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the DataManagement Package.
 * the 3 ListManagers will be replaces with mock objects
 */
public class ApplicationControllerTest
{
    Context mockContext = mock(Context.class);
    ApplicationController testApplicationController ;
    private RecipeListManager mockRecipeListManager    = Mockito.mock(RecipeListManager.class);
    private ShoppingListManager mockShoppingListManager    = Mockito.mock(ShoppingListManager.class);
    private AvailableListManager mockAvailableListManager = Mockito.mock(AvailableListManager.class);
    private ToCookListManager mockToCookListManager = Mockito.mock(ToCookListManager.class);

    private Article testArticle1 = new Article("Apfel", "4 Äpfel", Category.FRUIT, 4, 1.0);
    private Article testArticle2 = new Article("Birne", "3 Birnen", Category.FRUIT, 3, 1.0);
    private Article testArticle3 = new Article("Gurke", "Beschreibung", Category.VEGETABLE, 1, 13);

    private Step testStep1 = new Step("Schritt 1", "Beschreibung Schritt eins", 0, 1);
    private Step testStep2 = new Step("Schritt 2", "Beschreibung Schritt zwei", 0, 2);
    private Step testStep3 = new Step("Schritt 3", "Beschreibung Schritt drei", 0, 3);

    private Recipe testRecipe1;
    private Recipe testRecipe2;
    private Recipe testRecipe3;

    private ArrayList<Article> al = new ArrayList<>();
    private ArrayList<Step>    sl = new ArrayList<>();

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
        ApplicationController.getInstance().initialize(mockContext, mockRecipeListManager, mockShoppingListManager, mockAvailableListManager, mockToCookListManager);
        testApplicationController =  ApplicationController.getInstance();
    }

    @Test
    public void addArticleToShoppingTest1()
    {

      testApplicationController.addArticleToShoppingList(testArticle1);
        Mockito.verify(mockShoppingListManager).addArticleIntelligent(testArticle1);
    }

    @Test
    public void addArticleToAvailableListTest1()
    {
      testApplicationController.addArticleToAvailableList(testArticle2);
        Mockito.verify(mockAvailableListManager).addArticleIntelligent(testArticle2);
    }

    /**
     * Test if Data access calls the correct method
     */
    @Test
    public void addRecipeTest1()
    {
      testApplicationController.addRecipe(testRecipe1);
        Mockito.verify(mockRecipeListManager).addItem(testRecipe1);
    }

    /**
     * Test if testApplicationController calls the ShoppingListManager.removeItem method
     * when removing an item
     */
    @Test
    public void removeItemFromShoppingListTest1()
    {
        int index = 42;
      testApplicationController.deleteArticleFromShoppingList(index);
        Mockito.verify(mockShoppingListManager).removeItem(Mockito.anyInt());
    }

    /**
     * Test if testApplicationController calls the ShoppingListManager.removeItem method
     * whit the correct parameter (index)
     */
    @Test
    public void removeItemFromShoppingListTest2()
    {
        int index = 42;
      testApplicationController.deleteArticleFromShoppingList(index);
        Mockito.verify(mockShoppingListManager).removeItem(index);
    }

    /**
     * Test if testApplicationController calls the ShoppingListManager.removeItem method
     * only once per call
     */
    @Test
    public void removeItemFromShoppingListTest3()
    {
      testApplicationController.deleteArticleFromShoppingList(1);
      testApplicationController.deleteArticleFromShoppingList(2);
      testApplicationController.deleteArticleFromShoppingList(3);
        Mockito.verify(mockShoppingListManager, times(3)).removeItem(Mockito.anyInt());
    }

    /**
     * Test if testApplicationController calls the AvailableListManager.removeItem method
     * when removing an item
     */
    @Test
    public void removeItemFromAvailableListTest1()
    {
        int index = 42;
      testApplicationController.deleteArticleFromAvailableList(index);
        Mockito.verify(mockAvailableListManager).removeItem(anyInt());
    }

    /**
     * Test if testApplicationController calls the AvailableListManager.removeItem method
     * whit the correct parameter (index)
     */
    @Test
    public void removeItemFromAvailableListTest2()
    {
        int index = 42;
      testApplicationController.deleteArticleFromAvailableList(index);
        Mockito.verify(mockAvailableListManager).removeItem(index);
    }

    /**
     * Test if testApplicationController calls the AvailableListManager.removeItem method
     * only once per call
     */
    @Test
    public void removeItemFromAvailableListTest3()
    {
      testApplicationController.deleteArticleFromAvailableList(1);
      testApplicationController.deleteArticleFromAvailableList(2);
      testApplicationController.deleteArticleFromAvailableList(3);
        Mockito.verify(mockAvailableListManager, times(3)).removeItem(Mockito.anyInt());
    }

    /**
     * Test if testApplicationController calls the RecipeListManager.removeItem method
     * when removing an item
     */
    @Test
    public void removeRecipeTest1()
    {
      testApplicationController.deleteRecipe(1);
        Mockito.verify(mockRecipeListManager).removeItem(anyInt());
    }

    /**
     * Test if testApplicationController calls the RecipeListManager.removeItem method
     * with the correct parameter
     */
    @Test
    public void removeRecipeTest2()
    {
      testApplicationController.deleteRecipe(1);
        Mockito.verify(mockRecipeListManager).removeItem(1);
    }

    /**
     * Test if testApplicationController calls the RecipeListManager.removeItem method
     * only once per call
     */
    @Test
    public void removeRecipeTest3()
    {
      testApplicationController.deleteRecipe(1);
      testApplicationController.deleteRecipe(2);
      testApplicationController.deleteRecipe(3);
        Mockito.verify(mockRecipeListManager, times(3)).removeItem(anyInt());
    }


    //....get item tests (with int/ index as parameter)..........

    /**
     * Test if testApplicationController calls the ShoppingListManager.getItem method
     * when removing an item
     */
    @Test
    public void getItemFromShoppingListTest1()
    {
        int index = 42;
      testApplicationController.getArticleFromShoppingList(index);
        Mockito.verify(mockShoppingListManager).getItem(Mockito.anyInt());
    }

    /**
     * Test if testApplicationController calls the ShoppingListManager.getItem method
     * whit the correct parameter (index)
     */
    @Test
    public void getItemFromShoppingListTest2()
    {
        int index = 42;
      testApplicationController.getArticleFromShoppingList(index);
        Mockito.verify(mockShoppingListManager).getItem(index);
    }

    /**
     * Test if testApplicationController calls the ShoppingListManager.getItem method
     * only once per call
     */
    @Test
    public void getItemFromShoppingListTest3()
    {
      testApplicationController.getArticleFromShoppingList(1);
      testApplicationController.getArticleFromShoppingList(2);
      testApplicationController.getArticleFromShoppingList(3);
        Mockito.verify(mockShoppingListManager, times(3)).getItem(Mockito.anyInt());
    }

    /**
     * Test actual return value
     */
    @Test
    public void getItemFromShoppingListTest4()
    {
        Mockito.when(mockShoppingListManager.getItem(1)).thenReturn(testArticle2);
        Article result = testApplicationController.getArticleFromShoppingList(1);
        Mockito.verify(mockShoppingListManager).getItem(1);
        assertTrue(result.getName().equals(testArticle2.getName()));
    }

    /**
     * Test if testApplicationController calls the AvailableListManager.getItem method
     * when removing an item
     */
    @Test
    public void getItemFromAvailableListTest1()
    {
        int index = 42;
      testApplicationController.getArticleFromAvailableList(index);
        Mockito.verify(mockAvailableListManager).getItem(anyInt());
    }

    /**
     * Test if testApplicationController calls the AvailableListManager.getItem method
     * whit the correct parameter (index)
     */
    @Test
    public void getItemFromAvailableListTest2()
    {
        int index = 42;
      testApplicationController.getArticleFromAvailableList(index);
        Mockito.verify(mockAvailableListManager).getItem(index);
    }

    /**
     * Test if testApplicationController calls the AvailableListManager.getItem method
     * only once per call
     */
    @Test
    public void getItemFromAvailableListTest3()
    {
      testApplicationController.getArticleFromAvailableList(1);
      testApplicationController.getArticleFromAvailableList(2);
      testApplicationController.getArticleFromAvailableList(3);
        Mockito.verify(mockAvailableListManager, times(3)).getItem(Mockito.anyInt());
    }

    /**
     * Test actual return value
     */
    @Test
    public void getItemFromAvailableListTest4()
    {
        Mockito.when(mockAvailableListManager.getItem(1)).thenReturn(testArticle2);
        Article result = testApplicationController.getArticleFromAvailableList(1);
        Mockito.verify(mockAvailableListManager).getItem(1);
        assertTrue(result.getName().equals(testArticle2.getName()));
    }

    /**
     * Test if testApplicationController calls the RecipeListManager.getItem method
     * when removing an item
     */
    @Test
    public void getRecipeTest1()
    {
      testApplicationController.getRecipe(1);
        Mockito.verify(mockRecipeListManager).getItem(anyInt());
    }

    /**
     * Test if testApplicationController calls the RecipeListManager.getItem method
     * with the correct parameter
     */
    @Test
    public void getRecipeTest2()
    {
      testApplicationController.getRecipe(1);
        Mockito.verify(mockRecipeListManager).getItem(1);
    }

    /**
     * Test if testApplicationController calls the RecipeListManager.getItem method
     * only once per call
     */
    @Test
    public void getRecipeTest3()
    {
      testApplicationController.getRecipe(1);
      testApplicationController.getRecipe(2);
      testApplicationController.getRecipe(3);
        Mockito.verify(mockRecipeListManager, times(3)).getItem(anyInt());
    }

    /**
     * Test actual return value
     */
    @Test
    public void getRecipeTest4()
    {
        Recipe testRecipe2 = new Recipe("Kartoffelsuppe", "Beschreibung", al, sl);

        Mockito.when(mockRecipeListManager.getItem(1)).thenReturn(testRecipe2);
        Recipe result = testApplicationController.getRecipe(1);
        Mockito.verify(mockRecipeListManager).getItem(1);
        assertTrue(result.getName().equals(testRecipe2.getName()));
    }

    //....get item tests (with String/ name as parameter)..........

    /**
     * Test if testApplicationController calls the ShoppingListManager.searchForArticle method
     * when removing an item
     */
    @Test
    public void getItemFromShoppingListWithNameTest1()
    {
      testApplicationController.getArticleFromShoppingList("Banane");
        Mockito.verify(mockShoppingListManager).searchForArticle(Mockito.anyString());
    }

    /**
     * Test if testApplicationController calls the ShoppingListManager.searchForArticle method
     * whit the correct parameter (name)
     */
    @Test
    public void getItemFromShoppingListWithNameTest2()
    {
        String name = "Bananae";
      testApplicationController.getArticleFromShoppingList(name);
        Mockito.verify(mockShoppingListManager).searchForArticle(name);
    }

    /**
     * Test if testApplicationController calls the ShoppingListManager.searchForArticle method
     * only once per call
     */
    @Test
    public void getItemFromShoppingListWithNameTest3()
    {
      testApplicationController.getArticleFromShoppingList("name1");
      testApplicationController.getArticleFromShoppingList("name2");
      testApplicationController.getArticleFromShoppingList("name3");
        Mockito.verify(mockShoppingListManager, times(3)).searchForArticle(Mockito.anyString());
    }

    /**
     * Test actual return value
     */
    @Test
    public void getItemFromShoppingListWithNameTest4()
    {
        Mockito.when(mockShoppingListManager.searchForArticle("name")).thenReturn(testArticle3);
        Article result = testApplicationController.getArticleFromShoppingList("name");
        Mockito.verify(mockShoppingListManager).searchForArticle("name");
        assertTrue(result.getName().equals(testArticle3.getName()));
    }

    /**
     * Test if testApplicationController calls the AvailableListManager.searchForArticle method
     * when removing an item
     */
    @Test
    public void getItemFromAvailableListWithNameTest1()
    {
      testApplicationController.getArticleFromAvailableList("Banane");
        Mockito.verify(mockAvailableListManager).searchForArticle(Mockito.anyString());
    }

    /**
     * Test if testApplicationController calls the AvailableListManager.searchForArticle method
     * whit the correct parameter (name)
     */
    @Test
    public void getItemFromAvailableListWithNameTest2()
    {
        String name = "Bananae";
      testApplicationController.getArticleFromAvailableList(name);
        Mockito.verify(mockAvailableListManager).searchForArticle(name);
    }

    /**
     * Test if testApplicationController calls the AvailableListManager.searchForArticle method
     * only once per call
     */
    @Test
    public void getItemFromAvailableListWithNameTest3()
    {
      testApplicationController.getArticleFromAvailableList("name1");
      testApplicationController.getArticleFromAvailableList("name2");
      testApplicationController.getArticleFromAvailableList("name3");
        Mockito.verify(mockAvailableListManager, times(3)).searchForArticle(Mockito.anyString());
    }

    /**
     * Test actual return value
     */
    @Test
    public void getItemFromAvailableListWithNameTest4()
    {
        Mockito.when(mockAvailableListManager.searchForArticle("name")).thenReturn(testArticle2);
        Article result = testApplicationController.getArticleFromAvailableList("name");
        Mockito.verify(mockAvailableListManager).searchForArticle("name");
        assertTrue(result.getName().equals(testArticle2.getName()));
    }

    /**
     * Test if testApplicationController calls the RecipeListManager.getItem method
     * when removing an item
     */
    @Test
    public void getRecipeWithNameTest1()
    {
      testApplicationController.getRecipe("Rezept");
        Mockito.verify(mockRecipeListManager).getItem(anyString());
    }

    /**
     * Test if testApplicationController calls the RecipeListManager.getItem method
     * with the correct parameter (string name)
     */
    @Test
    public void getRecipeWithNameTest2()
    {
      testApplicationController.getRecipe("Test1234");
        Mockito.verify(mockRecipeListManager).getItem("Test1234");
    }

    /**
     * Test if testApplicationController calls the RecipeListManager.getItem method
     * only once per call
     */
    @Test
    public void getRecipeWithNameTest3()
    {
      testApplicationController.getRecipe("123");
      testApplicationController.getRecipe("312");
      testApplicationController.getRecipe("123");
        Mockito.verify(mockRecipeListManager, times(3)).getItem(anyString());
    }

    /**
     * Test actual return value
     */
    @Test
    public void getRecipeWithNameTest4()
    {
        Recipe testRecipe2 = new Recipe("Kartoffelsuppe", "Beschreibung", al, sl);
        Mockito.when(mockRecipeListManager.getItem("name")).thenReturn(testRecipe2);
        Recipe result = testApplicationController.getRecipe("name");
        Mockito.verify(mockRecipeListManager).getItem("name");
        assertTrue(result.getName().equals(testRecipe2.getName()));
    }


    //....update item tests ..........

    /**
     * Test if testApplicationController calls the ShoppingListManager.updateArticle method
     * when updating an item
     */
    @Test
    public void updateItemFromShoppingListTest1()
    {
      testApplicationController.updateArticleFromShoppingList(1, testArticle2);
        Mockito.verify(mockShoppingListManager).updateArticle(Mockito.anyInt(), Mockito.any(testArticle1.getClass()));
    }

    /**
     * Test if testApplicationController calls the ShoppingListManager.updateArticle method
     * whit the correct parameter (index and newArticle)
     */
    @Test
    public void updateItemFromShoppingListTest2()
    {
      testApplicationController.updateArticleFromShoppingList(2, testArticle2);
        Mockito.verify(mockShoppingListManager).updateArticle(2, testArticle2);
    }

    /**
     * Test if testApplicationController calls the ShoppingListManager.updateArticle method
     * only once per call
     */
    @Test
    public void updateItemFromShoppingListTest3()
    {
      testApplicationController.updateArticleFromShoppingList(2, testArticle2);
      testApplicationController.updateArticleFromShoppingList(2, testArticle2);
      testApplicationController.updateArticleFromShoppingList(2, testArticle2);
        Mockito.verify(mockShoppingListManager, times(3)).updateArticle(Mockito.anyInt(), Mockito.any(testArticle1.getClass()));
    }

    /**
     * Test if testApplicationController calls the AvailableListManager.updateArticle method
     * when updating an item
     */
    @Test
    public void updateItemFromAvailableListTest1()
    {
      testApplicationController.updateArticleFromAvailableList(1, testArticle2);
        Mockito.verify(mockAvailableListManager).updateArticle(Mockito.anyInt(), Mockito.any(testArticle1.getClass()));
    }

    /**
     * Test if testApplicationController calls the AvailableListManager.updateArticle method
     * whit the correct parameter (index and new Article)
     */
    @Test
    public void updateItemFromAvailableListTest2()
    {
      testApplicationController.updateArticleFromAvailableList(2, testArticle2);
        Mockito.verify(mockAvailableListManager).updateArticle(2, testArticle2);
    }

    /**
     * Test if testApplicationController calls the AvailableListManager.updateArticle method
     * only once per call
     */
    @Test
    public void updateItemFromAvailableListTest3()
    {
      testApplicationController.updateArticleFromAvailableList(2, testArticle2);
      testApplicationController.updateArticleFromAvailableList(2, testArticle2);
        Mockito.verify(mockAvailableListManager, times(2)).updateArticle(Mockito.anyInt(), Mockito.any(testArticle1.getClass()));
    }

    /**
     * Test if testApplicationController calls the RecipeListManager.updateArticle method
     * when removing an item
     */
    @Test
    public void updateRecipeTest1()
    {
      testApplicationController.updateRecipe(1, testRecipe1);
        Mockito.verify(mockRecipeListManager).updateRecipe(anyInt(), any(Recipe.class));
    }

    /**
     * Test if testApplicationController calls the RecipeListManager.updateArticle method
     * with the correct parameter (index and newRecipe)
     */
    @Test
    public void updateRecipeTest2()
    {
      testApplicationController.updateRecipe(1, testRecipe1);
        Mockito.verify(mockRecipeListManager).updateRecipe(1, testRecipe1);
    }

    /**
     * Test if testApplicationController calls the RecipeListManager.updateArticle method
     * only once per call
     */
    @Test
    public void updateRecipeTest3()
    {
      testApplicationController.updateRecipe(1, testRecipe1);
      testApplicationController.updateRecipe(2, testRecipe1);
      testApplicationController.updateRecipe(3, testRecipe1);
        Mockito.verify(mockRecipeListManager, times(3)).updateRecipe(anyInt(), any(Recipe.class));
    }

    //....transfer articles between the two article lists tests ..........

    /**
     * Test if the correct methods are called
     */
    @Test
    public void fromShoppingToAvailableListTest1()
    {
        Mockito.when(mockShoppingListManager.getItem(3)).thenReturn(testArticle3);
        testApplicationController.transferArticleFromShoppingToAvailableList(3);

        Mockito.verify(mockShoppingListManager).getItem(anyInt());
        Mockito.verify(mockShoppingListManager).removeItem(anyInt());
        Mockito.verify(mockAvailableListManager).addArticleIntelligent(any(Article.class));
    }

    /**
     * Test if the correct parameter become  passed
     */
    @Test
    public void fromShoppingToAvailableListTest2()
    {
        Mockito.when(mockShoppingListManager.getItem(3)).thenReturn(testArticle3);
      testApplicationController.transferArticleFromShoppingToAvailableList(3);
        Mockito.verify(mockShoppingListManager).removeItem(3);
        Mockito.verify(mockShoppingListManager).getItem(3);
        Mockito.verify(mockAvailableListManager).addArticleIntelligent(testArticle3);
    }

    /**
     * Test if the correct methods are called
     */
    @Test
    public void fromAvailableToShoppingTest1()
    {
        Mockito.when(mockAvailableListManager.getItem(3)).thenReturn(testArticle3);
        testApplicationController.transferArticleFromAvailableToShoppingList(3);

        Mockito.verify(mockAvailableListManager).getItem(anyInt());
        Mockito.verify(mockAvailableListManager).removeItem(anyInt());
        Mockito.verify(mockShoppingListManager).addArticleIntelligent(any(Article.class));
    }

    /**
     * Test if the correct parameter become  passed
     */
    @Test
    public void fromAvailableToShoppingTest2()
    {
        Mockito.when(mockAvailableListManager.getItem(3)).thenReturn(testArticle3);
      testApplicationController.transferArticleFromAvailableToShoppingList(3);
        Mockito.verify(mockAvailableListManager).removeItem(3);
        Mockito.verify(mockAvailableListManager).getItem(3);
        Mockito.verify(mockShoppingListManager).addArticleIntelligent(testArticle3);
    }


    //....observable tests ..........

    private int counterOne   = 0;
    private int counterTwo   = 0;


    @Before
    public void resetCounters()
    {
        counterOne = 0;
        counterTwo = 0;
    }

    private class ObserverOne implements Observer
    {
        @Override
        public void onChange()
        {
            counterOne++;
        }
    }

    private class ObserverTwo implements Observer
    {
        @Override
        public void onChange()
        {
            counterTwo++;
        }
    }

    /**
     * Test if registerOnShoppingListChangeListener works
     * and if onShoppingListChange works
     */
    @Test
    public void onShoppingListChangeTest1()
    {
        ObserverOne bls1 = new ObserverOne();
      testApplicationController.registerOnShoppingListChangeListener(bls1);

      testApplicationController.transferArticleFromAvailableToShoppingList(3);
      testApplicationController.addArticleToShoppingList(testArticle3);

        assertEquals(counterOne, 2);
    }

    /**
     * Test with different 2 observers
     */
    @Test
    public void onShoppingListChangeTest2()
    {
        ObserverOne bls1 = new ObserverOne();
        ObserverTwo bls2 = new ObserverTwo();
      testApplicationController.registerOnShoppingListChangeListener(bls1);
      testApplicationController.registerOnShoppingListChangeListener(bls2);

      testApplicationController.transferArticleFromAvailableToShoppingList(3);
      testApplicationController.addArticleToShoppingList(testArticle3);

        assertEquals(counterOne, 2);
        assertEquals(counterTwo, 2);
    }

    /**
     * Test all methods where onShoppingListChange should be called
     */
    @Test
    public void onShoppingListChangeTest3()
    {
        ObserverOne bls1 = new ObserverOne();
      testApplicationController.registerOnShoppingListChangeListener(bls1);


      testApplicationController.transferArticleFromAvailableToShoppingList(3);
      testApplicationController.addArticleToShoppingList(testArticle3);
      testApplicationController.deleteArticleFromShoppingList(1);
      testApplicationController.updateArticleFromShoppingList(1, testArticle1);

        assertEquals(4, counterOne);
    }

    /**
     * Test unregisterOnShoppingListChangeListener
     */
    @Test
    public void onShoppingListChangeTest4()
    {
        ObserverOne bls1 = new ObserverOne();
      testApplicationController.registerOnShoppingListChangeListener(bls1);


      testApplicationController.transferArticleFromAvailableToShoppingList(3);
      testApplicationController.addArticleToShoppingList(testArticle3);
      testApplicationController.unregisterOnShoppingListChangeListener(bls1);

        //Shouldn't be counted anymore
      testApplicationController.deleteArticleFromShoppingList(1);

        assertEquals(2, counterOne);
    }

    /**
     * Test if registerOnAvailableListChangeListener works
     * and if onShoppingListChange works
     */
    @Test
    public void onAvailableListChangeTest1()
    {
        ObserverOne bls1 = new ObserverOne();
      testApplicationController.registerOnAvailableListChangeListener(bls1);

      testApplicationController.transferArticleFromShoppingToAvailableList(3);
      testApplicationController.addArticleToAvailableList(testArticle3);

        assertEquals(counterOne, 2);
    }

    /**
     * Test with different 2 observers
     */
    @Test
    public void onAvailableListChangeTest2()
    {
        ObserverOne bls1 = new ObserverOne();
        ObserverTwo bls2 = new ObserverTwo();
      testApplicationController.registerOnAvailableListChangeListener(bls1);
      testApplicationController.registerOnAvailableListChangeListener(bls2);

      testApplicationController.transferArticleFromShoppingToAvailableList(3);
      testApplicationController.addArticleToAvailableList(testArticle3);

        assertEquals(counterOne, 2);
        assertEquals(counterTwo, 2);
    }

    /**
     * Test all methods where onAvailableListChange should be called
     */
    @Test
    public void onAvailableListChangeTest3()
    {
        ObserverOne bls1 = new ObserverOne();
      testApplicationController.registerOnAvailableListChangeListener(bls1);


      testApplicationController.transferArticleFromShoppingToAvailableList(3);
      testApplicationController.addArticleToAvailableList(testArticle3);
      testApplicationController.deleteArticleFromAvailableList(1);
      testApplicationController.updateArticleFromAvailableList(1, testArticle1);

        assertEquals(4, counterOne);
    }

    /**
     * Test unregisterOnAvailableListChangeListener
     */
    @Test
    public void onAvailableListChangeTest4()
    {
        ObserverOne bls1 = new ObserverOne();
      testApplicationController.registerOnAvailableListChangeListener(bls1);


      testApplicationController.transferArticleFromShoppingToAvailableList(3);
      testApplicationController.addArticleToAvailableList(testArticle3);
      testApplicationController.unregisterOnAvailableListChangeListener(bls1);

        //Shouldn't be counted anymore
      testApplicationController.deleteArticleFromAvailableList(1);

        assertEquals(2, counterOne);
    }

    /**
     * Test if registerOnRecipeListChangeListener works
     * and if onShoppingListChange works
     */
    @Test
    public void onRecipeListChangeTest1()
    {
        ObserverOne bls1 = new ObserverOne();
      testApplicationController.registerOnRecipeListChangeListener(bls1);

      testApplicationController.addRecipe(testRecipe1);
      testApplicationController.addRecipe(testRecipe2);

        assertEquals(counterOne, 2);
    }

    /**
     * Test with different 2 observers
     */
    @Test
    public void onRecipeListChangeTest2()
    {
        ObserverOne bls1 = new ObserverOne();
        ObserverTwo bls2 = new ObserverTwo();
      testApplicationController.registerOnRecipeListChangeListener(bls1);
      testApplicationController.registerOnRecipeListChangeListener(bls2);

      testApplicationController.addRecipe(testRecipe1);
      testApplicationController.updateRecipe(1, testRecipe2);

        assertEquals(counterOne, 2);
        assertEquals(counterTwo, 2);
    }

    /**
     * Test all methods where onRecipeListChange should be called
     */
    @Test
    public void onRecipeListChangeTest3()
    {
        ObserverOne bls1 = new ObserverOne();
      testApplicationController.registerOnRecipeListChangeListener(bls1);

      testApplicationController.addRecipe(testRecipe1);
      testApplicationController.deleteRecipe(1);
      testApplicationController.updateRecipe(1, testRecipe1);

        assertEquals(3, counterOne);
    }

    /**
     * Test unregisterOnRecipeListChangeListener
     */
    @Test
    public void onRecipeListChangeTest4()
    {
        ObserverOne bls1 = new ObserverOne();
      testApplicationController.registerOnRecipeListChangeListener(bls1);


      testApplicationController.updateRecipe(1, testRecipe1);
      testApplicationController.addRecipe(testRecipe1);
      testApplicationController.unregisterOnRecipeListChangeListener(bls1);

        //Shouldn't be counted anymore
      testApplicationController.deleteRecipe(1);

        assertEquals(2, counterOne);
    }


    /**
     * Verify method calls
     */
    @Test
    public void recipeToShoppingListTest1()
    {
        when(mockRecipeListManager.getItem(1)).thenReturn(testRecipe1);
        when(mockAvailableListManager.getListOfNotAvailableArticles(testRecipe1.getArticles())).thenReturn(testRecipe1.getArticles());
        testApplicationController.recipeToShoppingList(1);
        Mockito.verify(mockAvailableListManager).getListOfNotAvailableArticles(testRecipe1.getArticles());
        Mockito.verify(mockShoppingListManager).addSeveralArticlesIntelligent(testRecipe1.getArticles());

    }

}