package cookshop.model;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import com.example.cookshop.R;
import com.example.cookshop.model.database.DatabaseNamingContract;
import com.example.cookshop.view.main.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class RecipeTests
{

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Before
    public void setup()
    {
        activityTestRule.getActivity().getApplicationContext().deleteDatabase(DatabaseNamingContract.DATABASE_NAME);
    }

    @After
    public void teardown()
    {
        activityTestRule.getActivity().getApplicationContext().deleteDatabase(DatabaseNamingContract.DATABASE_NAME);
    }



    @Test
    public void areTheViewsDisplayed()
    {

        //Go to recipeList
        onView(withId(R.id.nav_recipes)).perform(click());

        // Go to AddRecipe
        onView(withId(R.id.add_item_fab)).perform(click());

        //CheckViews

        onView(withId(R.id.content_card_name)).check(matches(isDisplayed()));
        onView(withId(R.id.name_edittext)).check(matches(isDisplayed()));
        onView(withId(R.id.content_card_description)).check(matches(isDisplayed()));
        onView(withId(R.id.description_edittext)).check(matches(isDisplayed()));
        onView(withId(R.id.content_card_articles)).check(matches(isDisplayed()));
        onView(withId(R.id.scroll_view)).perform(swipeUp());
        onView(withId(R.id.new_article_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.articles_listview)).check(matches(isDisplayed()));
        onView(withId(R.id.content_card_steps)).check(matches(isDisplayed()));
        onView(withId(R.id.new_step_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.steps_listview)).check(matches(isDisplayed()));

    }


    @Test
    public void addARecipe()
    {
        //Go to recipeList
        onView(withId(R.id.nav_recipes)).perform(click());

        // Go to AddRecipe
        onView(withId(R.id.add_item_fab)).perform(click());

        //Add only A name
        onView(withId(R.id.name_edittext)).perform(typeText("TestRecipe"));
        Espresso.pressBack();// HideKeyboard

        onView(withId(R.id.fab)).perform(click());

        //No description we should still be in the add recipe activity
        onView(withId(R.id.content_card_name)).check(matches(isDisplayed()));
        onView(withId(R.id.name_edittext)).check(matches(isDisplayed()));
        onView(withId(R.id.content_card_description)).check(matches(isDisplayed()));
        onView(withId(R.id.description_edittext)).check(matches(isDisplayed()));
        onView(withId(R.id.content_card_articles)).check(matches(isDisplayed()));
        onView(withId(R.id.scroll_view)).perform(swipeUp());
        onView(withId(R.id.new_article_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.articles_listview)).check(matches(isDisplayed()));
        onView(withId(R.id.content_card_steps)).check(matches(isDisplayed()));
        onView(withId(R.id.new_step_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.steps_listview)).check(matches(isDisplayed()));

    }


    @Test
    public void addARecipeNoDescription()
    {
        //Go to recipeList
        onView(withId(R.id.nav_recipes)).perform(click());

        // Go to AddRecipe
        onView(withId(R.id.add_item_fab)).perform(click());

        //Add only A name
        onView(withId(R.id.description_edittext)).perform(typeText("TestRecipe"));
        Espresso.pressBack();// HideKeyboard

        onView(withId(R.id.fab)).perform(click());

        //No description we should still be in the add recipe activity
        onView(withId(R.id.content_card_name)).check(matches(isDisplayed()));
        onView(withId(R.id.name_edittext)).check(matches(isDisplayed()));
        onView(withId(R.id.content_card_description)).check(matches(isDisplayed()));
        onView(withId(R.id.description_edittext)).check(matches(isDisplayed()));
        onView(withId(R.id.content_card_articles)).check(matches(isDisplayed()));
        onView(withId(R.id.scroll_view)).perform(swipeUp());
        onView(withId(R.id.new_article_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.articles_listview)).check(matches(isDisplayed()));
        onView(withId(R.id.content_card_steps)).check(matches(isDisplayed()));
        onView(withId(R.id.new_step_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.steps_listview)).check(matches(isDisplayed()));

    }


    @Test
    public void addSimpleRecipe()
    {
        //Go to recipeList
        onView(withId(R.id.nav_recipes)).perform(click());

        onView(withId(R.id.recipe_recycler_constraint)).check(doesNotExist());
        onView(withId(R.id.recipe_description_textview)).check(doesNotExist());
        onView(withId(R.id.recipe_name_textview)).check(doesNotExist());

        // Go to AddRecipe
        onView(withId(R.id.add_item_fab)).perform(click());

        //Add only A name
        onView(withId(R.id.description_edittext)).perform(typeText("TestRecipeDescription"));
        onView(withId(R.id.name_edittext)).perform(typeText("TestRecipe"));
        Espresso.pressBack();// HideKeyboard

        onView(withId(R.id.fab)).perform(click());

        //Recycler Item

        onView(withId(R.id.recipe_recycler_constraint)).check(matches(isDisplayed()));
        onView(withId(R.id.recipe_description_textview)).check(matches(isDisplayed()));
        onView(withId(R.id.recipe_name_textview)).check(matches(isDisplayed()));
    }


    @Test
    public void addSimpleRecipeCheckRecyclerItem()
    {
        //Go to recipeList
        onView(withId(R.id.nav_recipes)).perform(click());

        // Go to AddRecipe
        onView(withId(R.id.add_item_fab)).perform(click());

        //Add only A name
        onView(withId(R.id.description_edittext)).perform(typeText("TestRecipeDescription"));
        onView(withId(R.id.name_edittext)).perform(typeText("TestRecipe"));
        Espresso.pressBack();// HideKeyboard

        onView(withId(R.id.fab)).perform(click());

        //Recycler Item

        onView(withId(R.id.recipe_description_textview)).check(matches(withText("TestRecipeDescription")));
        onView(withId(R.id.recipe_name_textview)).check(matches(withText("TestRecipe")));
    }


    @Test
    public void recipeViewer()
    {

        //Go to recipeList
        onView(withId(R.id.nav_recipes)).perform(click());

        // Go to AddRecipe
        onView(withId(R.id.add_item_fab)).perform(click());

        //Add only A name
        onView(withId(R.id.description_edittext)).perform(typeText("TestRecipeDescription"));
        onView(withId(R.id.name_edittext)).perform(typeText("TestRecipe"));
        Espresso.pressBack();// HideKeyboard

        onView(withId(R.id.fab)).perform(click());

        //Open Recipe Viewer
        onView(withId(R.id.recipe_recycler_constraint)).perform(click());

        //Recipe Viewer views
        onView(withId(R.id.app_bar)).check(matches(isDisplayed()));
        onView(withId(R.id.toolbar_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.content_card_description)).check(matches(isDisplayed()));
        onView(withId(R.id.description_textview)).check(matches(isDisplayed()));
        onView(withId(R.id.content_card_steps)).check(matches(isDisplayed()));
        onView(withId(R.id.steps_viewpager)).check(matches(isDisplayed()));
        onView(withId(R.id.content_card_articles)).check(matches(isDisplayed()));
        onView(withId(R.id.articles_viewpager)).check(matches(isDisplayed()));
        onView(withId(R.id.textView)).check(matches(isDisplayed()));
        onView(withId(R.id.textView3)).check(matches(isDisplayed()));

        //Not there Steps and Articles
        onView(withId(R.id.fragment_article_name_textview)).check(doesNotExist());
        onView(withId(R.id.frame_layout_step_fragment)).check(doesNotExist());

        //Description TextView is set
        onView(withId(R.id.description_textview)).check(matches(withText("TestRecipeDescription")));
    }


    @Test
    public void recipeWithArticles()
    {
        //Go to recipeList
        onView(withId(R.id.nav_recipes)).perform(click());

        // Go to AddRecipe
        onView(withId(R.id.add_item_fab)).perform(click());

        //Add only A name
        onView(withId(R.id.description_edittext)).perform(typeText("TestRecipeDescription"));
        onView(withId(R.id.name_edittext)).perform(typeText("TestRecipe"));
        Espresso.pressBack();// HideKeyboard

        //addArticle
        addArticleToRecipe("Article1");
        addArticleToRecipe("Article2");
        addArticleToRecipe("Article3");

        onView(withId(R.id.fab)).perform(click());

        //Open Recipe Viewer
        onView(withId(R.id.recipe_recycler_constraint)).perform(click());

        //ScrollingDown
        onView(withId(R.id.content_card_description)).perform(swipeUp());
        onView(withId(R.id.content_card_description)).perform(swipeUp());
        onView(withId(R.id.content_card_description)).perform(swipeUp());
        onView(withId(R.id.content_card_description)).perform(swipeUp());
        onView(withId(R.id.content_card_description)).perform(swipeUp());

        //Articles are there now
        onView(withText("Article1")).check(matches(isDisplayed()));
        onView(withText("Article1")).perform(swipeLeft());
        onView(withText("Article2")).check(matches(isDisplayed()));
        onView(withText("Article2")).perform(swipeLeft());
        onView(withId(R.id.frame_layout_step_fragment)).check(doesNotExist());
    }


    @Test
    public void recipeWithSteps()
    {
        //Go to recipeList
        onView(withId(R.id.nav_recipes)).perform(click());

        // Go to AddRecipe
        onView(withId(R.id.add_item_fab)).perform(click());

        //Add only A name
        onView(withId(R.id.description_edittext)).perform(typeText("TestRecipeDescription"));
        onView(withId(R.id.name_edittext)).perform(typeText("TestRecipe"));
        Espresso.pressBack();// HideKeyboard

        //addArticle
        addStepsToRecipe("testStep1");
        addStepsToRecipe("testStep2");
        addStepsToRecipe("testStep3");

        onView(withId(R.id.fab)).perform(click());

        //Open Recipe Viewer
        onView(withId(R.id.recipe_recycler_constraint)).perform(click());

        //ScrollingDown
        onView(withId(R.id.content_card_description)).perform(swipeUp());
        onView(withId(R.id.content_card_description)).perform(swipeUp());
        onView(withId(R.id.content_card_description)).perform(swipeUp());
        onView(withId(R.id.content_card_description)).perform(swipeUp());
        onView(withId(R.id.content_card_description)).perform(swipeUp());

        //Articles are there now
        onView(withText("testStep1")).check(matches(isDisplayed()));
        onView(withText("testStep1")).perform(swipeLeft());
        onView(withText("testStep2")).check(matches(isDisplayed()));
        onView(withText("testStep2")).perform(swipeLeft());
        onView(withText("testStep3")).check(matches(isDisplayed()));
        onView(withId(R.id.frameLayout)).check(doesNotExist());
    }


    @Test
    public void  fullRecipe()
    {
        //Go to recipeList
        onView(withId(R.id.nav_recipes)).perform(click());

        // Go to AddRecipe
        onView(withId(R.id.add_item_fab)).perform(click());

        //Add only A name
        onView(withId(R.id.description_edittext)).perform(typeText("TestRecipeDescription"));
        onView(withId(R.id.name_edittext)).perform(typeText("TestRecipe"));
        Espresso.pressBack();// HideKeyboard

        //addArticle
        addArticleToRecipe("Article1");
        addArticleToRecipe("Article2");
        addArticleToRecipe("Article3");
        //addArticle
        addStepsToRecipe("testStep1");
        addStepsToRecipe("testStep2");
        addStepsToRecipe("testStep3");

        onView(withId(R.id.fab)).perform(click());

        //Open Recipe Viewer
        onView(withId(R.id.recipe_recycler_constraint)).perform(click());

        //ScrollingDown
        onView(withId(R.id.content_card_description)).perform(swipeUp());
        onView(withId(R.id.content_card_description)).perform(swipeUp());
        onView(withId(R.id.content_card_description)).perform(swipeUp());
        onView(withId(R.id.content_card_description)).perform(swipeUp());
        onView(withId(R.id.content_card_description)).perform(swipeUp());

        //Articles are there now
        onView(withText("testStep1")).check(matches(isDisplayed()));
        onView(withText("testStep1")).perform(swipeLeft());
        onView(withText("testStep2")).check(matches(isDisplayed()));
        onView(withText("testStep2")).perform(swipeLeft());
        onView(withText("testStep3")).check(matches(isDisplayed()));

        //Articles are there now
        onView(withText("Article1")).check(matches(isDisplayed()));
        onView(withText("Article1")).perform(swipeLeft());
        onView(withText("Article2")).check(matches(isDisplayed()));
        onView(withText("Article2")).perform(swipeLeft());
        onView(withText("Article3")).check(matches(isDisplayed()));

    }




    @Test
    public void recipeToToCookList()
    {
        //Go to recipeList
        onView(withId(R.id.nav_recipes)).perform(click());

        // Go to AddRecipe
        onView(withId(R.id.add_item_fab)).perform(click());

        //Add only A name
        onView(withId(R.id.description_edittext)).perform(typeText("TestRecipeDescription"));
        onView(withId(R.id.name_edittext)).perform(typeText("TestRecipe"));
        Espresso.pressBack();// HideKeyboard

        //addArticle
        addArticleToRecipe("Article1");
        addArticleToRecipe("Article2");
        addArticleToRecipe("Article3");

        onView(withId(R.id.fab)).perform(click());

        //ToCookList
        onView(withId(R.id.recipe_recycler_constraint)).perform(swipeLeft());

        //Go to recipeList
        onView(withId(R.id.nav_shopping)).perform(click());
    }






    private void addArticleToRecipe(String name)
    {

        onView(withId(R.id.new_article_btn)).perform(click());
        onView(withId(R.id.name_edittext)).perform(typeText(name));
        onView(withId(R.id.description_edittext)).perform(typeText(name +" description"));
        Espresso.pressBack();// HideKeyboard
        onView(withId(R.id.amount_edittext)).perform(typeText("2"));
        Espresso.pressBack();// HideKeyboard
        onView(withId(R.id.weight_edittext)).perform(typeText("3"));
        Espresso.pressBack();// HideKeyboard
        onView(withId(R.id.add_article_fab)).perform(click());
    }


    private void addStepsToRecipe(String name)
    {

        onView(withId(R.id.new_step_btn)).perform(click());
        onView(withId(R.id.name_edittext)).perform(typeText(name));
        onView(withId(R.id.description_edittext)).perform(typeText(name +" description"));
        Espresso.pressBack();// HideKeyboard
        onView(withId(R.id.add_step_fab)).perform(click());
    }


}
