package cookshop.items;

import com.example.cookshop.items.Article;
import com.example.cookshop.items.Category;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class ArticleTest
{


    Article testArticle1;
    Article testArticle2;
    Article testArticle3;


    @Before
    public void setUp() throws Exception {
        testArticle1 = new Article("Apfel", "4 Äpfel", Category.FRUIT, 4, 1.0);
        testArticle2 = new Article("Birne", "3 Birnen", Category.FRUIT, 3, 1.0);
        testArticle3 = new Article("Gurke", "Eine Gurke", Category.VEGETABLE, 1, 13);
    }


    @Test
    public void getNameTest1() {
        assertTrue(testArticle1.getName().equals("Apfel") &&
                testArticle2.getName().equals("Birne") &&
                testArticle3.getName().equals("Gurke"));
    }

    @Test
    public void getDescriptionTest1() {
        assertTrue(testArticle1.getDescription().equals("4 Äpfel") &&
                testArticle2.getDescription().equals("3 Birnen") &&
                testArticle3.getDescription().equals("Eine Gurke"));
    }

    @Test
    public void getAmountTest1() {
        assertTrue(testArticle1.getAmount() == 4 &&
                testArticle2.getAmount() == 3 &&
                testArticle3.getAmount() == 1);
    }

    @Test
    public void getWeightTest1() {
        assertTrue(testArticle1.getWeight() == 1 &&
                testArticle2.getWeight() == 1.0 &&
                testArticle3.getWeight() == 13);
    }

    @Test
    public void getCategoryTest1() {
        assertTrue(testArticle1.getCategory() == Category.FRUIT &&
                testArticle2.getCategory() == Category.FRUIT &&
                testArticle3.getCategory() == Category.VEGETABLE);
    }

    @Test
    public void getCategoryToStringTest1() {
        assertTrue(testArticle1.getCategory().toString().equals("Fruit") &&
                testArticle2.getCategory().toString().equals("Fruit") &&
                testArticle3.getCategory().toString().equals("Vegetable"));
    }

    @Test
    public void setNameTest1() {
        assertTrue(testArticle1.getName().equals("Apfel") &&
                testArticle2.getName().equals("Birne") &&
                testArticle3.getName().equals("Gurke"));

        testArticle1.setName("Olive");
        testArticle2.setName("Banane");

        assertTrue(testArticle1.getName().equals("Olive") &&
                testArticle2.getName().equals("Banane") &&
                testArticle3.getName().equals("Gurke"));

    }

    @Test
    public void setNameTest2() {
        assertTrue(testArticle1.getName().equals("Apfel") &&
                testArticle2.getName().equals("Birne"));

        testArticle1.setName("    Olive     ");
        testArticle2.setName("       Banane");

        assertTrue(testArticle1.getName().equals("Olive") &&
                testArticle2.getName().equals("Banane"));

    }

    @Test
    public void setDescriptionTest1() {
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
    public void setDescriptionTest2() {
        assertTrue(testArticle1.getDescription().equals("4 Äpfel"));

        testArticle1.setDescription("    Test beschreibung        ");

        assertTrue(testArticle1.getDescription().equals("Test beschreibung"));
    }

    @Test
    public void setWeightTest1() {
        assertTrue(testArticle1.getWeight() == 1);

        testArticle1.setWeight(12.5);

        assertTrue(testArticle1.getWeight() == 12.5);
    }

    @Test
    public void setWeightTest2() {
        assertTrue(testArticle1.getWeight() == 1);

        testArticle1.setWeight(Double.MAX_VALUE);

        assertTrue(testArticle1.getWeight() == Double.MAX_VALUE);
    }

    @Test
    public void setWeightTest3() {
        assertTrue(testArticle1.getWeight() == 1);

        testArticle1.setWeight(Double.MAX_VALUE + 1);

        assertTrue(testArticle1.getWeight() == Double.MAX_VALUE);
    }

    @Test
    public void setWeightTest4() {
        assertTrue(testArticle1.getWeight() == 1);

        testArticle1.setWeight(-123.4);

        assertTrue(testArticle1.getWeight() == 0);
    }

    @Test
    public void setAmountTest1() {
        assertTrue(testArticle1.getAmount() == 4);

        testArticle1.setAmount(13);

        assertTrue(testArticle1.getAmount() == 13);
    }

    @Test
    public void setAmountTest2() {
        assertTrue(testArticle1.getAmount() == 4);

        testArticle1.setAmount(Integer.MAX_VALUE);

        assertTrue(testArticle1.getAmount() == Integer.MAX_VALUE);
    }

    @Test
    public void setAmountTest3() {
        assertTrue(testArticle1.getAmount() == 4);

        testArticle1.setAmount(Integer.MAX_VALUE + 1);

        assertTrue(testArticle1.getAmount() == 0);
    }

    @Test
    public void setAmountTest4() {
        assertTrue(testArticle1.getAmount() == 4);

        testArticle1.setAmount(Integer.MAX_VALUE + 1);


        assertTrue(testArticle1.getAmount() == 0);
    }

    @Test
    public void setCategoryTest1() {
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
    public void compareToTest1() {
        assertTrue(testArticle1.compareTo(testArticle2) == 1 &&
                testArticle2.compareTo(testArticle3) == 1);
    }

    @Test
    public void compareToTest2() {
        assertTrue(testArticle3.compareTo(testArticle2) == -1 &&
                testArticle2.compareTo(testArticle1) == -1);
    }

    @Test
    public void compareToTest3() {
        String name = "name";
        testArticle1.setName(name);
        testArticle2.setName(name);
        testArticle3.setName(name);

        assertTrue(testArticle3.compareTo(testArticle2) == 0 &&
                testArticle2.compareTo(testArticle1) == 0);
    }

    @Test
    public void getMementoPatterTest1() {
        assertTrue(testArticle1.getMementoPattern().equals("Apfel||4 Äpfel||1.0||4||Fruit||"));
        assertTrue(testArticle2.getMementoPattern().equals("Birne||3 Birnen||1.0||3||Fruit||"));
    }

    @Test
    public void setObjectFromMementoPatternTest1() {
        testArticle1.setObjectFromMementoPattern("Melone||Wassermelone||1||2||Fruit||");
        assertTrue(testArticle1.getName().equals("Melone") &&
                testArticle1.getDescription().equals("Wassermelone") &&
                testArticle1.getWeight() == 1.0 &&
                testArticle1.getAmount() == 2 &&
                testArticle1.getCategory().equals(Category.FRUIT));

    }

    @Test
    public void setObjectFromMementoPatternTest2() {
        testArticle1.setObjectFromMementoPattern("   Melone||    Wassermelone    || 1   ||   2||Fruit||");
        assertTrue(testArticle1.getName().equals("Melone") &&
                testArticle1.getDescription().equals("Wassermelone") &&
                testArticle1.getWeight() == 1.0 &&
                testArticle1.getAmount() == 2 &&
                testArticle1.getCategory().equals(Category.FRUIT));
    }

    @Test
    public void setObjectFromMementoPatternTest3() {
        testArticle1 = new Article("   Melone||    Wassermelone    || 1   ||   2||Fruit||");
        assertTrue(testArticle1.getName().equals("Melone") &&
                testArticle1.getDescription().equals("Wassermelone") &&
                testArticle1.getWeight() == 1.0 &&
                testArticle1.getAmount() == 2 &&
                testArticle1.getCategory().equals(Category.FRUIT));
    }

    @Test
    public void setObjectFromMementoPatternTest4() {
        testArticle1 = new Article("   Melone||    Wassermelone    || 1   ||   2||Fruit||");
        assertTrue(testArticle1.getCategory().equals(Category.FRUIT));
    }

    @Test
    public void setObjectFromMementoPatternTest5() {
        testArticle1 = new Article("   Melone||    Wassermelone    || 1   ||   2||Vegetable||");
        assertTrue(testArticle1.getCategory().equals(Category.VEGETABLE));
    }

    @Test
    public void setObjectFromMementoPatternTest6() {
        testArticle1 = new Article("   Melone||    Wassermelone    || 1   ||   2||Meat||");
        assertTrue(testArticle1.getCategory().equals(Category.MEAT));
    }

    @Test
    public void setObjectFromMementoPatternTest7() {
        testArticle1 = new Article("   Melone||    Wassermelone    || 1   ||   2||Drink||");
        assertTrue(testArticle1.getCategory().equals(Category.DRINK));
    }

    @Test
    public void setObjectFromMementoPatternTest8() {
        testArticle1 = new Article("   Melone||    Wassermelone    || 1   ||   2||Others||");
        assertTrue(testArticle1.getCategory().equals(Category.OTHERS));
    }

    @Test
    public void setObjectFromMementoPatternTest9() {
        testArticle1 = new Article("   Melone||    Wassermelone    || 1   ||   2|| ||");
        assertTrue(testArticle1.getCategory().equals(Category.OTHERS));
    }

    @Test
    public void copyArticleTest1() {
        Article copiedArticle = testArticle1.clone();

        assertTrue(copiedArticle.getName().equals(testArticle1.getName()));
        assertTrue(copiedArticle.getDescription().equals(testArticle1.getDescription()));
        assertTrue(copiedArticle.getWeight() == testArticle1.getWeight());
        assertTrue(copiedArticle.getAmount() == testArticle1.getAmount());
        assertTrue(copiedArticle.getCategory().equals(testArticle1.getCategory()));
    }
}