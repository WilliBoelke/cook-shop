package cookshop.model.items;

import com.example.cookshop.items.Article;
import com.example.cookshop.items.Category;
import com.example.cookshop.items.Recipe;
import com.example.cookshop.items.Step;

import org.junit.Before;
import org.junit.Test;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

public class RecipeTest
{
    Recipe testRecipe1;
    Recipe testRecipe2;
    Recipe testRecipe3;
    Article testArticle1;
    Article testArticle2;
    Article testArticle3;
    Article testArticle4;
    Article testArticle5;
    Article testArticle6;

    Step testStep1 = new Step("Schritt 1", "Beschreibung Schritt eins", 0, 1);
    Step testStep2 = new Step("Schritt 2", "Beschreibung Schritt zwei", 0, 2);
    Step testStep3 = new Step("Schritt 3", "Beschreibung Schritt drei", 0, 3);
    Step testStep4 = new Step("Schritt 4", "Beschreibung Schritt vier", 0, 4);
    Step testStep5 = new Step("Schritt 5", "Beschreibung Schritt fünf", 0, 5);

    ArrayList<Article> al = new ArrayList<>();
    ArrayList<Step> sl = new ArrayList<>();
    private String dateString = "09-1-2021 07:50:40";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
    private Date date;


    @Before
    public void setUp() throws Exception
    {
        date = simpleDateFormat.parse(dateString);

         testArticle1 = new Article("Apfel", "Beschreibung Apfel", Category.FRUIT, 4, 1.0 , date,  date);
         testArticle2 = new Article("Birne", "Beschreibung Birne", Category.FRUIT, 3, 1.0, date, date);
         testArticle3 = new Article("Gurke", "Beschreibung Gurke", Category.VEGETABLE, 1, 13, date, date);
         testArticle4 = new Article("Mehl","Beschreibung Mehl",  Category.OTHERS, 12, 13, date, date);
         testArticle5 = new Article("Melone", "Beschreibung Melone",  Category.FRUIT, 12, 2.13, date, date);
         testArticle6 = new Article("Mais", "Beschreibung Mais", Category.VEGETABLE, 21, 1.2, date, date);

        al.add(testArticle1);
        al.add(testArticle2);
        al.add(testArticle3);
        sl.add(testStep1);
        sl.add(testStep2);
        sl.add(testStep3);
        testRecipe1 = new Recipe("Spaghetti", "Mit blognese", al, sl);
        testRecipe2 = new Recipe("Kartoffelsuppe", "Beschreibung", al,sl);
        testRecipe3 = new Recipe("Rezept", "Beschreibung", al,sl);
    }

    @Test
    public void getNameTest1() {
        assertTrue(testRecipe1.getName().equals("Spaghetti") &&
                testRecipe2.getName().equals("Kartoffelsuppe") &&
                testRecipe3.getName().equals("Rezept"));
    }

    @Test
    public void getDescriptionTest1() {
        assertTrue(testRecipe1.getDescription().equals("Mit blognese") &&
                testRecipe2.getDescription().equals("Beschreibung") &&
                testRecipe3.getDescription().equals("Beschreibung"));
    }

    @Test
    public void getArticleListTest1() {
        assertTrue(testRecipe1.getArticles().equals(al));
    }

    @Test
    public void setNameTest1() {
        assertTrue(testRecipe1.getName().equals("Spaghetti"));

        testRecipe1.setName("new name");

        assertTrue(testRecipe1.getName().equals("new name"));
    }

    @Test
    public void setDescriptionTest1()
    {
        assertTrue(testRecipe1.getDescription().equals("Mit blognese"));

        testRecipe1.setDescription("new description    ");

        assertTrue(testRecipe1.getDescription().equals("new description"));
    }

    @Test
    public void setArticleListTest1()
    {
        ArrayList<Article>  nal = new ArrayList<>();
        nal.add(testArticle4);
        nal.add(testArticle5);
        nal.add(testArticle6);

        assertTrue(testRecipe1.getArticles().equals(al));

        testRecipe1.setArticles(nal);

        assertTrue(testRecipe1.getArticles().equals(nal));
    }

    @Test
    public void setStepListTest1()
    {
        ArrayList<Step>  nsl = new ArrayList<>();
        nsl.add(testStep4);
        nsl.add(testStep5);
        nsl.add(testStep2);

        assertTrue(testRecipe1.getSteps().equals(sl));

        testRecipe1.setSteps(nsl);

        assertTrue(testRecipe1.getSteps().equals(nsl));
    }

    @Test
    public void compareToTest1()
    {
        assertTrue(testRecipe2.compareTo(testRecipe3) == 1 &&
                testRecipe3.compareTo(testRecipe1) ==  1);
    }

    @Test
    public void compareToTest2()
    {
        assertTrue(testRecipe1.compareTo(testRecipe3) ==  - 1 &&
                testRecipe3.compareTo(testRecipe2) ==   - 1);
    }

    @Test
    public void compareToTest3()
    {
        String name = "name";
        testRecipe1.setName(name);
        testRecipe2.setName(name);
        testRecipe3.setName(name);
        assertTrue(testRecipe1.compareTo(testRecipe3) ==  0 &&
                testRecipe3.compareTo(testRecipe2) ==   0);
    }

    @Test
    public void getMementoPatternTest1()
    {
        System.out.println(testRecipe1.getMementoPattern());
        System.out.println(testRecipe2.getMementoPattern());
        System.out.println(testRecipe3.getMementoPattern());
        assertTrue(testRecipe1.getMementoPattern().equals("Spaghetti++Mit blognese++Apfel||Beschreibung Apfel||1.0||4||"+ dateString + "||" + dateString + "||Fruit||++Birne||Beschreibung Birne||1.0||3||"+ dateString + "||" + dateString + "||Fruit||++Gurke||Beschreibung Gurke||13.0||1||"+ dateString + "||" + dateString + "||Vegetable||++"));
        assertTrue(testRecipe2.getMementoPattern().equals("Kartoffelsuppe++Beschreibung++Apfel||Beschreibung Apfel||1.0||4||"+ dateString + "||" + dateString + "||Fruit||++Birne||Beschreibung Birne||1.0||3||"+ dateString + "||" + dateString + "||Fruit||++Gurke||Beschreibung Gurke||13.0||1||"+ dateString + "||" + dateString + "||Vegetable||++"));
        assertTrue(testRecipe3.getMementoPattern().equals("Rezept++Beschreibung++Apfel||Beschreibung Apfel||1.0||4||"+ dateString + "||" + dateString + "||Fruit||++Birne||Beschreibung Birne||1.0||3||"+ dateString + "||" + dateString + "||Fruit||++Gurke||Beschreibung Gurke||13.0||1||"+ dateString + "||" + dateString + "||Vegetable||++"));
    }

    @Test
    public void setObjectFromMementoPatternTest1()
    {
        testRecipe1.setObjectFromMementoPattern("Spaghetti++Mit blognese++Apfel||Beschreibung Apfel||1.0||4||"+ dateString + "||" + dateString + "||Fruit||++Birne||Beschreibung Birne||1.0||3||"+ dateString + "||" + dateString + "||Fruit||++Gurke||Beschreibung Gurke||13.0||1||"+ dateString + "||" + dateString + "||Vegetable||++");
        assertTrue(testRecipe1.getName().equals("Spaghetti"));
        assertTrue(testRecipe1.getDescription().equals("Mit blognese"));
    }

    @Test
    public void setObjectFromMementoPatternTest2()
    {
        testRecipe1.setObjectFromMementoPattern("Test      ++memento Beschreibung    ++Apfel    ||4 Äpfel||1.0||4||"+ dateString + "||" + dateString + "||Fruit||++Birne||3 Birnen||1.0||3||"+ dateString + "||" + dateString + "||Fruit||++Gurke||Beschreibung||13.0||1||"+ dateString + "||" + dateString + "||Vegetable||++");
        assertTrue(testRecipe1.getName().equals("Test"));
        assertTrue(testRecipe1.getDescription().equals("memento Beschreibung"));
    }

    @Test
    public void setObjectFromMementoPatternTest3()
    {
        testRecipe1.setObjectFromMementoPattern("Test      ++memento Beschreibung    ++Apfel    ||4 Äpfel||1.0||4||"+ dateString + "||" + dateString + "||Fruit||++Birne||3 Birnen||1.0||3||"+ dateString + "||" + dateString + "||Fruit||++Gurke||Beschreibung||13.0||1||"+ dateString + "||" + dateString + "||Vegetable||++");
        assertTrue(testRecipe1.getName().equals("Test"));
        assertTrue(testRecipe1.getDescription().equals("memento Beschreibung"));
    }

    @Test
    public void setObjectFromMementoPatternTest4()
    {
        testRecipe3.setName(testRecipe1.getName());
        testRecipe3.setDescription(testRecipe1.getDescription());
        testRecipe3.setArticles(testRecipe1.getArticles());
        assertTrue(testRecipe1.getMementoPattern().equals(testRecipe3.getMementoPattern()));

        System.out.println(testRecipe1.getMementoPattern());
        String mementoPattern = testRecipe1.getMementoPattern();
        testRecipe2.setObjectFromMementoPattern(mementoPattern);
        assertTrue(testRecipe1.getMementoPattern().equals(testRecipe2.getMementoPattern()));
    }

    @Test
    public void serializationTest1() throws IOException, ClassNotFoundException
    {
        byte[] bytes = testRecipe1.serialize();
        Recipe recipe = Recipe.deserialize(bytes);

        assertEquals(testRecipe1.getName(), recipe.getName());
        assertEquals(testRecipe1.getDescription(), recipe.getDescription());
        assertTrue(testRecipe1.getSteps().size() == recipe.getSteps().size());
        assertTrue(testRecipe1.getArticles().size() ==  recipe.getArticles().size());
    }
}