package cookshop.model;

import android.content.Context;

import androidx.test.filters.SmallTest;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.cookshop.items.Article;
import com.example.cookshop.items.Category;
import com.example.cookshop.items.Recipe;
import com.example.cookshop.items.Step;
import com.example.cookshop.model.database.DatabaseHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@SmallTest
public class DatabaseHelperTest
{
    private Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

    private DatabaseHelper database;

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

        database = new DatabaseHelper(context);
    }

    @After
    public void tearDown()
    {
        database.close();
    }

    /**
     * Test if 3 different recipes will be added to the Database
     */
    @Test
    public void addRecipesToDatabase()
    {
        database.reset();
        assertEquals(0, database.retrieveAllRecipes().size());
        database.insertRecipe(testRecipe1);
        database.insertRecipe(testRecipe2);
        database.insertRecipe(testRecipe3);
        assertEquals(3, database.retrieveAllRecipes().size());
    }

    /**
     * Recipes with the same name shouldn't be added to the database
     * (unique constraint)
     */
    @Test
    public void addRecipesToDatabaseTestSameName()
    {
        database.reset();
        assertEquals(0, database.retrieveAllRecipes().size());
        database.insertRecipe(testRecipe1);
        database.insertRecipe(testRecipe1);
        assertEquals(1, database.retrieveAllRecipes().size());
    }

    /**
     * Verify that the recipes are saved correctly
     */
    @Test
    public void verifySavedRecipeNameTest()
    {
        database.reset();
        assertEquals(0, database.retrieveAllRecipes().size());
        database.insertRecipe(testRecipe1);

        Recipe recipe = database.retrieveAllRecipes().get(0);
        assertEquals("Spaghetti", recipe.getName());
    }

    /**
     * Verify that the recipes are saved correctly
     */
    @Test
    public void verifySavedRecipeDescriptionTest()
    {
        database.reset();
        assertEquals(0, database.retrieveAllRecipes().size());
        database.insertRecipe(testRecipe1);

        Recipe recipe = database.retrieveAllRecipes().get(0);
        assertEquals("Mit blognese", recipe.getDescription());
    }

    /**
     * Verify that the recipes are saved correctly
     */
    @Test
    public void verifySavedRecipeAmountOfStepsTest()
    {
        database.reset();
        assertEquals(0, database.retrieveAllRecipes().size());
        database.insertRecipe(testRecipe1);

        int amountOfArticles = database.retrieveAllRecipes().get(0).getArticles().size();
        assertEquals(3, amountOfArticles);
    }

    /**
     * Verify that the recipes are saved correctly
     */
    @Test
    public void verifySavedRecipeAmountOfIngredientsTest()
    {
        database.reset();
        assertEquals(0, database.retrieveAllRecipes().size());
        database.insertRecipe(testRecipe1);

        int amountOfSteps = database.retrieveAllRecipes().get(0).getSteps().size();
        assertEquals(3, amountOfSteps);
    }

    /**
     * Test if 3 different articles will be added to the Database
     */
    @Test
    public void saveArticleToDatabase()
    {
        database.reset();
        assertEquals(0, database.retrieveAllArticlesFrom("BelongingOne").size());
        database.insertArticle("BelongingOne", testArticle1);
        database.insertArticle("BelongingOne", testArticle2);
        database.insertArticle("BelongingOne", testArticle3);
        assertEquals(3, database.retrieveAllArticlesFrom("BelongingOne").size());
    }

    /**
     * Articles with the same name can be stored in the database
     */
    @Test
    public void addArticlesToDatabaseTest2()
    {
        database.reset();
        assertEquals(0, database.retrieveAllArticlesFrom("BelongingOne").size());
        database.insertArticle("BelongingOne", testArticle1);
        database.insertArticle("BelongingOne", testArticle1);
        assertEquals(2, database.retrieveAllArticlesFrom("BelongingOne").size());
    }

    /**
     * Verify that the article was saved correctly
     */
    @Test
    public void verifySavedArticleNameTest()
    {
        database.reset();
        assertEquals(0, database.retrieveAllArticlesFrom("BelongingOne").size());
        database.insertArticle("BelongingOne", testArticle1);

        Article article = database.retrieveAllArticlesFrom("BelongingOne").get(0);
        assertEquals("Apfel", article.getName());
    }

    /**
     * Verify that the article was saved correctly
     */
    @Test
    public void verifySavedArticleDescription()
    {
        database.reset();
        assertEquals(0, database.retrieveAllArticlesFrom("BelongingOne").size());
        database.insertArticle("BelongingOne", testArticle1);

        Article article = database.retrieveAllArticlesFrom("BelongingOne").get(0);
        assertEquals("4 Äpfel", article.getDescription());
    }

    /**
     * Verify that the article was saved correctly
     */
    @Test
    public void verifySavedArticleAmountTest()
    {
        database.reset();
        assertEquals(0, database.retrieveAllArticlesFrom("BelongingOne").size());
        database.insertArticle("BelongingOne", testArticle1);

        Article article = database.retrieveAllArticlesFrom("BelongingOne").get(0);
        assertEquals(4, article.getAmount());
    }

    /**
     * Verify that the article was saved correctly
     */
    @Test
    public void verifySavedArticleWeightTest()
    {
        database.reset();
        assertEquals(0, database.retrieveAllArticlesFrom("BelongingOne").size());
        database.insertArticle("BelongingOne", testArticle1);

        Article article = database.retrieveAllArticlesFrom("BelongingOne").get(0);
        assertTrue(1.0 == article.getWeight());
    }

    /**
     * Verify that the article was saved correctly
     */
    @Test
    public void verifySavedArticleCategoryTest()
    {
        database.reset();
        assertEquals(0, database.retrieveAllArticlesFrom("BelongingOne").size());
        database.insertArticle("BelongingOne", testArticle1);

        Article article = database.retrieveAllArticlesFrom("BelongingOne").get(0);
        assertTrue(Category.FRUIT == article.getCategory());
    }


    /**
     * Verify that a recipe will be deleted
     */
    @Test
    public void deleteRecipeTest1()
    {
        database.reset();
        assertEquals(0, database.retrieveAllRecipes().size());
        database.insertRecipe(testRecipe1);
        assertEquals(1, database.retrieveAllRecipes().size());
        database.deleteRecipe(testRecipe1.getName());
        assertEquals(0, database.retrieveAllRecipes().size());
    }


    /**
     * Verify that the correct recipe will be deleted
     */
    @Test
    public void deleteRecipeTest2()
    {
        database.reset();
        assertEquals(0, database.retrieveAllRecipes().size());

        database.insertRecipe(testRecipe1);
        database.insertRecipe(testRecipe2);
        database.insertRecipe(testRecipe3);

        database.deleteRecipe(testRecipe2.getName());

        assertEquals(2, database.retrieveAllRecipes().size());
        assertEquals(testRecipe1.getName(), (database.retrieveAllRecipes().get(0).getName()));
        assertEquals(testRecipe3.getName(), (database.retrieveAllRecipes().get(1).getName()));
    }

    /**
     * Test if a can be updated
     */
    @Test
    public void updateRecipeTest1()
    {
        database.reset();
        assertEquals(0, database.retrieveAllRecipes().size());;

        database.insertRecipe(testRecipe1);
        assertEquals(testRecipe1.getName(), (database.retrieveAllRecipes().get(0).getName()));

        assertEquals(1, database.retrieveAllRecipes().size());

        database.updateRecipe(testRecipe1.getName(), testRecipe2);
        assertEquals(testRecipe2.getName(), (database.retrieveAllRecipes().get(0).getName()));

        assertEquals(1, database.retrieveAllRecipes().size());
    }


    /**
     * Verify that a article will be deleted
     */
    @Test
    public void deleteArticleTest1()
    {
        database.reset();
        assertEquals(0, database.retrieveAllArticlesFrom("BelongingOne").size());
        database.insertArticle("BelongingOne", testArticle1);
        assertEquals(1, database.retrieveAllArticlesFrom("BelongingOne").size());
        database.deleteArticle("BelongingOne", testArticle1.getName());
        assertEquals(0, database.retrieveAllRecipes().size());
    }


    /**
     * Verify that the correct article will be deleted
     */
    @Test
    public void deleteArticleTest2()
    {
        database.reset();
        assertEquals(0, database.retrieveAllRecipes().size());

        database.insertArticle("BelongingOne", testArticle1);
        database.insertArticle("BelongingOne", testArticle2);
        database.insertArticle("BelongingOne", testArticle3);

        database.deleteArticle(testArticle2.getName(), "BelongingOne");
        assertEquals(2, database.retrieveAllArticlesFrom("BelongingOne").size());

        assertEquals(testArticle1.getName(), (database.retrieveAllArticlesFrom("BelongingOne").get(0).getName()));
        assertEquals(testArticle3.getName(), (database.retrieveAllArticlesFrom("BelongingOne").get(1).getName()));
    }



    @Test
    public void updateArticleTest1()
    {
        database.reset();
        assertEquals(0, database.retrieveAllArticlesFrom("BelongingOne").size());;

        database.insertArticle("BelongingOne", testArticle1);
        assertEquals(testArticle1.getName(), (database.retrieveAllArticlesFrom("BelongingOne").get(0).getName()));

        assertEquals(1, database.retrieveAllArticlesFrom("BelongingOne").size());

        database.updateArticle(testArticle1.getName(), "BelongingOne", testArticle2);
        assertEquals(testArticle2.getName(), (database.retrieveAllArticlesFrom("BelongingOne").get(0).getName()));

        assertEquals(1, database.retrieveAllArticlesFrom("BelongingOne").size());
    }




}
