package cookshop;

import android.widget.TextView;

import com.example.cookshop.controller.viewController.ArticleController;
import com.example.cookshop.items.Article;
import com.example.cookshop.items.Category;

import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ArticleControllerTest
{

    ArticleController testArticleController;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;


    @Before
    public void setup()
    {
        testArticleController = new ArticleController();
        textView1 = mock(TextView.class);
        textView2 = mock(TextView.class);
        textView3 = mock(TextView.class);
        textView4 = mock(TextView.class);
        textView5 = mock(TextView.class);
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

        Article result =  testArticleController.generateArticleFromInput(textView1, textView2, Category.FRUIT, textView3, textView4);

        assertEquals("aName", result.getName());
        assertEquals("Description", result.getDescription());
        assertTrue(13 ==  result.getWeight());
        assertTrue(12 ==  result.getAmount());
        assertEquals(Category.FRUIT, result.getCategory());
    }


    @Test
    public void generateArticleWithDate()
    {
        when(textView1.getText()).thenReturn("aName");
        when(textView2.getText()).thenReturn("Description");
        when(textView3.getText()).thenReturn("13");
        when(textView4.getText()).thenReturn("12");

        Article result =  testArticleController.generateArticleFromInput(textView1, textView2, Category.FRUIT, textView3, textView4);

        assertEquals("aName", result.getName());
        assertEquals("Description", result.getDescription());
        assertTrue(13 ==  result.getWeight());
        assertTrue(12 ==  result.getAmount());
        assertEquals(Category.FRUIT, result.getCategory());
    }
}