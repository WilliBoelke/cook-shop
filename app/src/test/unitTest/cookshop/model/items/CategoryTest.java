package cookshop.model.items;

import com.example.cookshop.items.Category;

import org.junit.Test;



import static org.junit.Assert.*;

public class CategoryTest
{
    @Test
    public void categoryToStringTest()
    {
        assertTrue(Category.FRUIT.toString().equals("Fruit"));
        assertTrue(Category.VEGETABLE.toString().equals("Vegetable"));
        assertTrue(Category.MEAT.toString().equals("Meat"));
        assertTrue(Category.DRINK.toString().equals("Drink"));
        assertTrue(Category.OTHERS.toString().equals("Others"));

    }

}