package cookshop.controller;

import android.widget.TextView;

import com.example.cookshop.controller.viewController.ArticleController;
import com.example.cookshop.items.Article;
import com.example.cookshop.items.Category;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ArticleControllerTest
{

    private ArticleController testArticleController;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;


    @Before
    public void setup()
    {
        testArticleController = new ArticleController();
        textView1 = mock(TextView.class);
        textView2 = mock(TextView.class);
        textView3 = mock(TextView.class);
        textView4 = mock(TextView.class);
    }


    @Test
    public void checkUserInputWithEmptyName()
    {
        when(textView1.getText()).thenReturn("");

        assertEquals(2, testArticleController.checkUserInput(textView1));
    }


    @Test
    public void checkUserInputWithName()
    {
        when(textView1.getText()).thenReturn("aName");

        assertEquals(1, testArticleController.checkUserInput(textView1));
    }


    @Test
    public void generateArticle()
    {
        when(textView1.getText()).thenReturn("aName");
        when(textView2.getText()).thenReturn("Description");
        when(textView3.getText()).thenReturn("13");
        when(textView4.getText()).thenReturn("12");

        Article result = testArticleController.generateArticleFromInput(textView1, textView2, Category.FRUIT, textView3, textView4);

        assertEquals("aName", result.getName());
        assertEquals("Description", result.getDescription());
        assertTrue(13 == result.getWeight());
        assertTrue(12 == result.getAmount());
        assertEquals(Category.FRUIT, result.getCategory());
    }


    @Test
    public void generateArticleWithDate()
    {
        when(textView1.getText()).thenReturn("aName");
        when(textView2.getText()).thenReturn("Description");
        when(textView3.getText()).thenReturn("13");
        when(textView4.getText()).thenReturn("12");

        Date date = Calendar.getInstance().getTime();
        Article result = testArticleController.generateArticleFromInput(textView1, textView2, Category.FRUIT, textView3, textView4, date);

        assertEquals("aName", result.getName());
        assertEquals("Description", result.getDescription());
        assertTrue(13 == result.getWeight());
        assertTrue(12 == result.getAmount());
        assertEquals(Category.FRUIT, result.getCategory());
        assertEquals(date, result.getDateOfCreation());
        assertNotEquals(date, result.getDateOfUpdate());
        assertTrue(result.getDateOfUpdate().after(date));
    }
}