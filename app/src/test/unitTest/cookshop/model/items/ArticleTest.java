package cookshop.model.items;

import com.example.cookshop.items.Article;
import com.example.cookshop.items.Category;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertTrue;


public class ArticleTest
{


    private Article testArticle1;
    private Article testArticle2;
    private Article testArticle3;
    private final String dateString = "09-1-2021 07:50:40";
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
    private Date date;


    @Before
    public void setUp() throws Exception
    {
        date = simpleDateFormat.parse(dateString);
        testArticle1 = new Article("Apfel", "4 Äpfel", Category.FRUIT, 4, 1.0, date, date);
        testArticle2 = new Article("Birne", "3 Birnen", Category.FRUIT, 3, 1.0, date, date);
        testArticle3 = new Article("Gurke", "Eine Gurke", Category.VEGETABLE, 1, 13, date, date);
    }


    //....Getter..........


    @Test
    public void getNameTest()
    {
        assertTrue(testArticle1.getName().equals("Apfel") &&
                testArticle2.getName().equals("Birne") &&
                testArticle3.getName().equals("Gurke"));
    }

    @Test
    public void getDescriptionTest()
    {
        assertTrue(testArticle1.getDescription().equals("4 Äpfel") &&
                testArticle2.getDescription().equals("3 Birnen") &&
                testArticle3.getDescription().equals("Eine Gurke"));
    }

    @Test
    public void getAmountTest()
    {
        assertTrue(testArticle1.getAmount() == 4 &&
                testArticle2.getAmount() == 3 &&
                testArticle3.getAmount() == 1);
    }

    @Test
    public void getWeightTest()
    {
        assertTrue(testArticle1.getWeight() == 1 &&
                testArticle2.getWeight() == 1.0 &&
                testArticle3.getWeight() == 13);
    }

    @Test
    public void getCategoryTest()
    {
        assertTrue(testArticle1.getCategory() == Category.FRUIT &&
                testArticle2.getCategory() == Category.FRUIT &&
                testArticle3.getCategory() == Category.VEGETABLE);
    }

    @Test
    public void getDateOfCreationTest()
    {
        assertTrue(testArticle1.getDateOfCreation() == date &&
                testArticle2.getDateOfCreation() == date &&
                testArticle3.getDateOfCreation() == date);
    }

    @Test
    public void getDateOfUpdateTest()
    {
        assertTrue(testArticle1.getDateOfUpdate() == date &&
                testArticle2.getDateOfUpdate() == date &&
                testArticle3.getDateOfUpdate() == date);
    }


    @Test
    public void getCategoryToStringTest1()
    {
        assertTrue(testArticle1.getCategory().toString().equals("Fruit") &&
                testArticle2.getCategory().toString().equals("Fruit") &&
                testArticle3.getCategory().toString().equals("Vegetable"));
    }


    //....Getter..........

    @Test
    public void setNameTest1()
    {
        assertTrue(testArticle1.getName().equals("Apfel") &&
                testArticle2.getName().equals("Birne") &&
                testArticle3.getName().equals("Gurke"));

        testArticle1.setName("Olive");
        testArticle2.setName("Banane");

        assertTrue(testArticle1.getName().equals("Olive") &&
                testArticle2.getName().equals("Banane") &&
                testArticle3.getName().equals("Gurke"));

    }

    /**
     * Testing if the name will be trimmed
     */
    @Test
    public void setNameTest2()
    {
        assertTrue(testArticle1.getName().equals("Apfel") &&
                testArticle2.getName().equals("Birne"));

        testArticle1.setName("    Olive     ");
        testArticle2.setName("       Banane");

        assertTrue(testArticle1.getName().equals("Olive") &&
                testArticle2.getName().equals("Banane"));

    }

    @Test
    public void setDescriptionTest1()
    {
        assertTrue(testArticle1.getDescription().equals("4 Äpfel") &&
                testArticle2.getDescription().equals("3 Birnen") &&
                testArticle3.getDescription().equals("Eine Gurke"));

        testArticle1.setDescription("Test beschreibung");
        testArticle3.setDescription("Banane");

        assertTrue(testArticle1.getDescription().equals("Test beschreibung") &&
                testArticle2.getDescription().equals("3 Birnen") &&
                testArticle3.getDescription().equals("Banane"));

    }

    @Test
    public void setDescriptionTest2()
    {
        assertTrue(testArticle1.getDescription().equals("4 Äpfel"));

        testArticle1.setDescription("    Test beschreibung        ");

        assertTrue(testArticle1.getDescription().equals("Test beschreibung"));
    }

    @Test
    public void setWeightTest1()
    {
        assertTrue(testArticle1.getWeight() == 1);

        testArticle1.setWeight(12.5);

        assertTrue(testArticle1.getWeight() == 12.5);
    }

    @Test
    public void setWeightTest2()
    {
        assertTrue(testArticle1.getWeight() == 1);

        testArticle1.setWeight(Double.MAX_VALUE);

        assertTrue(testArticle1.getWeight() == Double.MAX_VALUE);
    }

    @Test
    public void setWeightTest3()
    {
        assertTrue(testArticle1.getWeight() == 1);

        testArticle1.setWeight(Double.MAX_VALUE + 1);

        assertTrue(testArticle1.getWeight() == Double.MAX_VALUE);
    }

    @Test
    public void setWeightTest4()
    {
        assertTrue(testArticle1.getWeight() == 1);

        testArticle1.setWeight(-123.4);

        assertTrue(testArticle1.getWeight() == 0);
    }

    @Test
    public void setAmountTest1()
    {
        assertTrue(testArticle1.getAmount() == 4);

        testArticle1.setAmount(13);

        assertTrue(testArticle1.getAmount() == 13);
    }

    @Test
    public void setAmountTest2()
    {
        assertTrue(testArticle1.getAmount() == 4);

        testArticle1.setAmount(Integer.MAX_VALUE);

        assertTrue(testArticle1.getAmount() == Integer.MAX_VALUE);
    }

    @Test
    public void setAmountTest3()
    {
        assertTrue(testArticle1.getAmount() == 4);

        testArticle1.setAmount(Integer.MAX_VALUE + 1);

        assertTrue(testArticle1.getAmount() == 0);
    }

    @Test
    public void setAmountTest4()
    {
        assertTrue(testArticle1.getAmount() == 4);

        testArticle1.setAmount(Integer.MAX_VALUE + 1);

        assertTrue(testArticle1.getAmount() == 0);
    }

    @Test
    public void setCategoryTest1()
    {
        assertTrue(testArticle1.getCategory() == Category.FRUIT &&
                testArticle2.getCategory() == Category.FRUIT &&
                testArticle3.getCategory() == Category.VEGETABLE);

        testArticle1.setCategory(Category.OTHERS);
        testArticle2.setCategory(Category.MEAT);
        testArticle3.setCategory(Category.DRINK);

        assertTrue(testArticle1.getCategory() == Category.OTHERS &&
                testArticle2.getCategory() == Category.MEAT &&
                testArticle3.getCategory() == Category.DRINK);
    }

    @Test
    public void compareToTestGreater()
    {
        assertTrue(testArticle1.compareTo(testArticle2) == 1 &&
                testArticle2.compareTo(testArticle3) == 1);
    }

    @Test
    public void compareToTestSmaller()
    {
        assertTrue(testArticle3.compareTo(testArticle2) == -1 &&
                testArticle2.compareTo(testArticle1) == -1);
    }

    @Test
    public void compareToTestEqual()
    {
        String name = "name";
        testArticle1.setName(name);
        testArticle2.setName(name);
        testArticle3.setName(name);

        assertTrue(testArticle3.compareTo(testArticle2) == 0 &&
                testArticle2.compareTo(testArticle1) == 0);
    }


    @Test
    public void getMementoPatterTest1()
    {
        System.out.println(testArticle1.getMementoPattern());
        assertTrue(testArticle1.getMementoPattern().equals("Apfel||4 Äpfel||1.0||4||" + dateString + "||" + dateString + "||Fruit||"));
        assertTrue(testArticle2.getMementoPattern().equals("Birne||3 Birnen||1.0||3||" + dateString + "||" + dateString + "||Fruit||"));
    }

    @Test
    public void setObjectFromMementoPatternTest()
    {
        testArticle1.setObjectFromMementoPattern("Melone||Wassermelone||1||2||" + dateString + "||" + dateString + "||Fruit||");
        assertTrue(testArticle1.getName().equals("Melone") &&
                testArticle1.getDescription().equals("Wassermelone") &&
                testArticle1.getWeight() == 1.0 &&
                testArticle1.getAmount() == 2 &&
                testArticle1.getCategory().equals(Category.FRUIT));

    }

    @Test
    public void setObjectFromMementoPatternTrim()
    {
        testArticle1.setObjectFromMementoPattern("   Melone||    Wassermelone    || 1   ||   2||" + dateString + "||" + dateString + "||Fruit||");
        assertTrue(testArticle1.getName().equals("Melone") &&
                testArticle1.getDescription().equals("Wassermelone") &&
                testArticle1.getWeight() == 1.0 &&
                testArticle1.getAmount() == 2 &&
                testArticle1.getCategory().equals(Category.FRUIT));
    }

    @Test
    public void setObjectFromMementoPatternTestCategoryFruit()
    {
        testArticle1 = new Article("   Melone||    Wassermelone    || 1   ||   2||" + dateString + "||" + dateString + "||Fruit||");
        assertTrue(testArticle1.getCategory().equals(Category.FRUIT));
    }

    @Test
    public void setObjectFromMementoPatternTestCategoryVegetable()
    {
        testArticle1 = new Article("   Melone||    Wassermelone    || 1   ||   2||" + dateString + "||" + dateString + "||Vegetable||");
        assertTrue(testArticle1.getCategory().equals(Category.VEGETABLE));
    }

    @Test
    public void setObjectFromMementoPatternTestCategoryMeat()
    {
        testArticle1 = new Article("   Melone||    Wassermelone    || 1   ||   2||" + dateString + "||" + dateString + "||Meat||");
        assertTrue(testArticle1.getCategory().equals(Category.MEAT));
    }

    @Test
    public void setObjectFromMementoPatternTestCategoryDrink()
    {
        testArticle1 = new Article("   Melone||    Wassermelone    || 1   ||   2||" + dateString + "||" + dateString + "||Drink||");
        assertTrue(testArticle1.getCategory().equals(Category.DRINK));
    }

    @Test
    public void setObjectFromMementoPatternTestCategoryOthers()
    {
        testArticle1 = new Article("   Melone||    Wassermelone    || 1   ||   2||" + dateString + "||" + dateString + "||Others||");
        assertTrue(testArticle1.getCategory().equals(Category.OTHERS));
    }

    @Test
    public void setObjectFromMementoPatternTest9()
    {
        testArticle1 = new Article("   Melone||    Wassermelone    || 1   ||   2||" + dateString + "||" + dateString + "||   ||");
        assertTrue(testArticle1.getCategory().equals(Category.OTHERS));
    }

    @Test
    public void copyArticleTest1()
    {
        Article copiedArticle = testArticle1.clone();

        assertTrue(copiedArticle.getName().equals(testArticle1.getName()));
        assertTrue(copiedArticle.getDescription().equals(testArticle1.getDescription()));
        assertTrue(copiedArticle.getWeight() == testArticle1.getWeight());
        assertTrue(copiedArticle.getAmount() == testArticle1.getAmount());
        assertTrue(copiedArticle.getCategory().equals(testArticle1.getCategory()));
    }
}