package cookshop.controller;

import com.example.cookshop.controller.applicationController.ApplicationController;
import com.example.cookshop.controller.viewController.RecipeController;
import com.example.cookshop.items.Article;
import com.example.cookshop.items.Category;
import com.example.cookshop.items.Recipe;
import com.example.cookshop.items.Step;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RecipeControllerTest
{

    private RecipeController testRecipeController;
    private ApplicationController mockApplicationController;


    private Article testArticle1;
    private Article testArticle2;
    private Article testArticle3;

    private final Step testStep1 = new Step("Schritt 1", "Beschreibung Schritt eins", 0, 1);
    private final Step testStep2 = new Step("Schritt 2", "Beschreibung Schritt zwei", 0, 2);
    private final Step testStep3 = new Step("Schritt 3", "Beschreibung Schritt drei", 0, 3);

    private final ArrayList<Article> al = new ArrayList<>();
    private final ArrayList<Step> sl = new ArrayList<>();
    private final String dateString = "09-1-2021 07:50:40";
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
    private Date date;


    @Before
    public void setUp() throws Exception
    {
        mockApplicationController = mock(ApplicationController.class);
        testRecipeController = new RecipeController(mockApplicationController);


        mockApplicationController = mock(ApplicationController.class);
        testRecipeController = new RecipeController(mockApplicationController);
        date = simpleDateFormat.parse(dateString);

        testArticle1 = new Article("Apfel", "Beschreibung Apfel", Category.FRUIT, 4, 1.0, date, date);
        testArticle2 = new Article("Birne", "Beschreibung Birne", Category.FRUIT, 3, 1.0, date, date);
        testArticle3 = new Article("Gurke", "Beschreibung Gurke", Category.VEGETABLE, 1, 13, date, date);

        al.add(testArticle1);
        al.add(testArticle2);
        al.add(testArticle3);
        sl.add(testStep1);
        sl.add(testStep2);
        sl.add(testStep3);

    }


    @Test
    public void checkUserInputNoName()
    {
        assertFalse(testRecipeController.checkUserInput("", "Beschreibung"));
    }

    @Test
    public void checkUserInputNoDescription()
    {
        assertFalse(testRecipeController.checkUserInput("Name", ""));
    }


    @Test
    public void checkUserInputNoInput()
    {
        assertFalse(testRecipeController.checkUserInput("", ""));
    }

    @Test
    public void checkUserInputBothThere()
    {
        assertTrue(testRecipeController.checkUserInput("Name", "Beschreibung"));
    }

    @Test
    public void generateRecipeFromInputTest()
    {
        Recipe testRecipe = testRecipeController.generateRecipeFromInput("testName", "testbeschreibung", al, sl);

        assertEquals("testName", testRecipe.getName());
        assertEquals("testbeschreibung", testRecipe.getDescription());
        assertEquals(al, testRecipe.getArticles());
        assertEquals(sl, testRecipe.getSteps());
    }

    Recipe testRecipe = new Recipe("Name", "Beschreibung", al, sl);


    @Test
    public void getRecipeTest()
    {
        mockApplicationController = mock(ApplicationController.class);
        when(mockApplicationController.getRecipe(anyInt())).thenReturn(testRecipe);
        testRecipeController = new RecipeController(mockApplicationController);

        testRecipeController.getRecipe(1);
        verify(mockApplicationController).getRecipe(1);
        testRecipeController.getRecipe(2);
        verify(mockApplicationController).getRecipe(2);
    }


    @Test
    public void addRecipe()
    {
        mockApplicationController = mock(ApplicationController.class);
        testRecipeController = new RecipeController(mockApplicationController);

        testRecipeController.addRecipe(testRecipe);
        verify(mockApplicationController).addRecipe(testRecipe);
    }

    @Test
    public void updateRecipe()
    {
        mockApplicationController = mock(ApplicationController.class);
        testRecipeController = new RecipeController(mockApplicationController);

        testRecipeController.updateRecipe(2, testRecipe);
        verify(mockApplicationController).updateRecipe(2, testRecipe);

        testRecipeController.updateRecipe(3, testRecipe);
        verify(mockApplicationController).updateRecipe(3, testRecipe);

    }


    @Test
    public void getRecipeByName()
    {
        mockApplicationController = mock(ApplicationController.class);
        testRecipeController = new RecipeController(mockApplicationController);

        testRecipeController.getRecipe("name");
        verify(mockApplicationController).getRecipe("name");


    }
}
