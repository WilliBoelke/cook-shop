package cookshop.model;


import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import com.example.cookshop.R;
import com.example.cookshop.model.database.DatabaseNamingContract;
import com.example.cookshop.view.main.MainActivity;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


public class ArticleListTests
{

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
            public void areTheViewsDisplayed()
    {
        activityTestRule.getActivity().getApplicationContext().deleteDatabase(DatabaseNamingContract.DATABASE_NAME);
        onView(withId(R.id.add_item_fab)).perform(click());

        onView(withId(R.id.content_card_name)).check(matches(isDisplayed()));
        onView(withId(R.id.content_card_description)).check(matches(isDisplayed()));
        onView(withId(R.id.content_card_amount)).check(matches(isDisplayed()));
        onView(withId(R.id.content_card_weight)).check(matches(isDisplayed()));

        onView(withId(R.id.name_edittext)).check(matches(isDisplayed()));
        onView(withId(R.id.description_edittext)).check(matches(isDisplayed()));
        onView(withId(R.id.amount_edittext)).check(matches(isDisplayed()));
        onView(withId(R.id.weight_edittext)).check(matches(isDisplayed()));

        onView(withId(R.id.amount_seekbar)).check(matches(isDisplayed()));
        onView(withId(R.id.weight_seekbar)).check(matches(isDisplayed()));

        onView(withId(R.id.add_article_fab)).check(matches(isDisplayed()));
    }


    @Test
    public void addArticleToShoppingList()
    {
        activityTestRule.getActivity().getApplicationContext().deleteDatabase(DatabaseNamingContract.DATABASE_NAME);
        // Adding Article
        onView(withId(R.id.add_item_fab)).perform(click());
        onView(withId(R.id.name_edittext)).perform(typeText("testArticle"));
        onView(withId(R.id.description_edittext)).perform(typeText("test article description"));
        Espresso.pressBack();// HideKeyboard
        onView(withId(R.id.amount_edittext)).perform(typeText("2"));
        Espresso.pressBack();// HideKeyboard
        onView(withId(R.id.weight_edittext)).perform(typeText("3"));
        Espresso.pressBack();// HideKeyboard
        onView(withId(R.id.add_article_fab)).perform(click());

        //back in shoppingListFragment
        onView(withId(R.id.article_recycler)).check(matches(isDisplayed()));
        onView(withId(R.id.add_item_fab)).check(matches(isDisplayed()));

        //Recycler item Test
        onView(withId(R.id.article_recycler_constraint)).check(matches(isDisplayed()));
        onView(withId(R.id.article_name_textview)).check(matches(isDisplayed()));
        onView(withId(R.id.article_amount_textview)).check(matches(isDisplayed()));
        onView(withId(R.id.article_weight_textview)).check(matches(isDisplayed()));

        //Delete Article
        onView(withId(R.id.article_recycler_constraint)).perform(swipeRight());


        //Should be cone
        onView(withId(R.id.article_recycler_constraint)).check(doesNotExist());
        onView(withId(R.id.article_name_textview)).check(doesNotExist());
        onView(withId(R.id.article_amount_textview)).check(doesNotExist());
        onView(withId(R.id.article_weight_textview)).check(doesNotExist());


    }


    @Test
    public void addArticleAndSwipeLeft()
    {
        activityTestRule.getActivity().getApplicationContext().deleteDatabase(DatabaseNamingContract.DATABASE_NAME);
        // Adding Article
        onView(withId(R.id.add_item_fab)).perform(click());
        onView(withId(R.id.name_edittext)).perform(typeText("testArticle"));
        onView(withId(R.id.description_edittext)).perform(typeText("test article description"));
        Espresso.pressBack();// HideKeyboard
        onView(withId(R.id.amount_edittext)).perform(typeText("2"));
        Espresso.pressBack();// HideKeyboard
        onView(withId(R.id.weight_edittext)).perform(typeText("3"));
        Espresso.pressBack();// HideKeyboard
        onView(withId(R.id.add_article_fab)).perform(click());

        //back in shoppingListFragment
        onView(withId(R.id.article_recycler)).check(matches(isDisplayed()));
        onView(withId(R.id.add_item_fab)).check(matches(isDisplayed()));

        //Recycler item Test
        onView(withId(R.id.article_recycler_constraint)).check(matches(isDisplayed()));
        onView(withId(R.id.article_name_textview)).check(matches(isDisplayed()));
        onView(withId(R.id.article_amount_textview)).check(matches(isDisplayed()));
        onView(withId(R.id.article_weight_textview)).check(matches(isDisplayed()));

        //Delete Article
        onView(withId(R.id.article_recycler_constraint)).perform(swipeLeft());


        //Should be gone
        onView(withId(R.id.article_recycler_constraint)).check(doesNotExist());
        onView(withId(R.id.article_name_textview)).check(doesNotExist());
        onView(withId(R.id.article_amount_textview)).check(doesNotExist());
        onView(withId(R.id.article_weight_textview)).check(doesNotExist());


        //Go to AvailableList
        onView(withId(R.id.nav_available)).perform(click());

        //Recycler item Test
        onView(withId(R.id.article_recycler_constraint)).check(matches(isDisplayed()));
        onView(withId(R.id.article_name_textview)).check(matches(isDisplayed()));
        onView(withId(R.id.article_amount_textview)).check(matches(isDisplayed()));
        onView(withId(R.id.article_weight_textview)).check(matches(isDisplayed()));


        //Delete Article
        onView(withId(R.id.article_recycler_constraint)).perform(swipeLeft());

        //Should be gone
        onView(withId(R.id.article_recycler_constraint)).check(doesNotExist());
        onView(withId(R.id.article_name_textview)).check(doesNotExist());
        onView(withId(R.id.article_amount_textview)).check(doesNotExist());
        onView(withId(R.id.article_weight_textview)).check(doesNotExist());

        //Go to AvailableList
        onView(withId(R.id.nav_shopping)).perform(click());

        //Recycler item Test
        onView(withId(R.id.article_recycler_constraint)).check(matches(isDisplayed()));
        onView(withId(R.id.article_name_textview)).check(matches(isDisplayed()));
        onView(withId(R.id.article_amount_textview)).check(matches(isDisplayed()));
        onView(withId(R.id.article_weight_textview)).check(matches(isDisplayed()));

    }


    @Test
    public void addSimpleArticle()
    {
        activityTestRule.getActivity().getApplicationContext().deleteDatabase(DatabaseNamingContract.DATABASE_NAME);

        //Go to AvailableList
        onView(withId(R.id.nav_available)).perform(click());

        // Adding Article
        onView(withId(R.id.add_item_fab)).perform(click());
        onView(withId(R.id.name_edittext)).perform(typeText("testArticle"));
        onView(withId(R.id.description_edittext)).perform(typeText("test article description"));
        Espresso.pressBack();// HideKeyboard
        onView(withId(R.id.amount_edittext)).perform(typeText("2"));
        Espresso.pressBack();// HideKeyboard
        onView(withId(R.id.weight_edittext)).perform(typeText("3"));
        Espresso.pressBack();// HideKeyboard
        onView(withId(R.id.add_article_fab)).perform(click());

        //back in shoppingListFragment
        onView(withId(R.id.article_recycler)).check(matches(isDisplayed()));
        onView(withId(R.id.add_item_fab)).check(matches(isDisplayed()));

        //Recycler item Test
        onView(withId(R.id.article_recycler_constraint)).check(matches(isDisplayed()));
        onView(withId(R.id.article_name_textview)).check(matches(isDisplayed()));
        onView(withId(R.id.article_amount_textview)).check(matches(isDisplayed()));
        onView(withId(R.id.article_weight_textview)).check(matches(isDisplayed()));

        //Delete Article
        onView(withId(R.id.article_recycler_constraint)).perform(swipeRight());

        //Should be cone
        onView(withId(R.id.article_recycler_constraint)).check(doesNotExist());
        onView(withId(R.id.article_name_textview)).check(doesNotExist());
        onView(withId(R.id.article_amount_textview)).check(doesNotExist());
        onView(withId(R.id.article_weight_textview)).check(doesNotExist());
    }


    @Test
    public void articleViewer()
    {
        activityTestRule.getActivity().getApplicationContext().deleteDatabase(DatabaseNamingContract.DATABASE_NAME);

        //Go to AvailableList
        onView(withId(R.id.nav_available)).perform(click());

        // Adding Article
        onView(withId(R.id.add_item_fab)).perform(click());
        onView(withId(R.id.name_edittext)).perform(typeText("testArticle"));
        onView(withId(R.id.description_edittext)).perform(typeText("test article description"));
        Espresso.pressBack();// HideKeyboard
        onView(withId(R.id.amount_edittext)).perform(typeText("2"));
        Espresso.pressBack();// HideKeyboard
        onView(withId(R.id.weight_edittext)).perform(typeText("3"));
        Espresso.pressBack();// HideKeyboard
        onView(withId(R.id.add_article_fab)).perform(click());

        //back in shoppingListFragment
        onView(withId(R.id.article_recycler)).check(matches(isDisplayed()));
        onView(withId(R.id.add_item_fab)).check(matches(isDisplayed()));

        //Recycler item Test
        onView(withId(R.id.article_recycler_constraint)).check(matches(isDisplayed()));
        onView(withId(R.id.article_name_textview)).check(matches(isDisplayed()));
        onView(withId(R.id.article_amount_textview)).check(matches(isDisplayed()));
        onView(withId(R.id.article_weight_textview)).check(matches(isDisplayed()));

        //Click on Recycler item
        onView(withId(R.id.article_recycler_constraint)).perform(click());


        //Arrticle viewer in view
        onView(withId(R.id.description_textview)).check(matches(withText("test article description")));
        onView(withId(R.id.amount_textview)).check(matches(withText("2")));
        onView(withId(R.id.weight_textview)).check(matches(withText("3.0")));

    }


    @Test
    public void editArticle()
    {
        activityTestRule.getActivity().getApplicationContext().deleteDatabase(DatabaseNamingContract.DATABASE_NAME);

        // Adding Article
        onView(withId(R.id.add_item_fab)).perform(click());
        onView(withId(R.id.name_edittext)).perform(typeText("testArticle"));
        onView(withId(R.id.description_edittext)).perform(typeText("test article description"));
        Espresso.pressBack();// HideKeyboard
        onView(withId(R.id.amount_edittext)).perform(typeText("2"));
        Espresso.pressBack();// HideKeyboard
        onView(withId(R.id.weight_edittext)).perform(typeText("3"));
        Espresso.pressBack();// HideKeyboard
        onView(withId(R.id.add_article_fab)).perform(click());

        //back in shoppingListFragment
        onView(withId(R.id.article_recycler)).check(matches(isDisplayed()));
        onView(withId(R.id.add_item_fab)).check(matches(isDisplayed()));

        //Recycler item Test
        onView(withId(R.id.article_recycler_constraint)).check(matches(isDisplayed()));
        onView(withId(R.id.article_name_textview)).check(matches(isDisplayed()));
        onView(withId(R.id.article_amount_textview)).check(matches(isDisplayed()));
        onView(withId(R.id.article_weight_textview)).check(matches(isDisplayed()));

       //Click Article
        onView(withId(R.id.article_recycler_constraint)).perform(click());

        //Article Viewer  in view


        //Click edit Button
        onView(withId(R.id.edit_article_fab)).perform(click());

        //Edit Article in view
        onView(withId(R.id.content_card_name)).check(matches(isDisplayed()));
        onView(withId(R.id.content_card_description)).check(matches(isDisplayed()));
        onView(withId(R.id.content_card_amount)).check(matches(isDisplayed()));
        onView(withId(R.id.content_card_weight)).check(matches(isDisplayed()));
        onView(withId(R.id.name_edittext)).check(matches(isDisplayed()));
        onView(withId(R.id.description_edittext)).check(matches(isDisplayed()));
        onView(withId(R.id.amount_edittext)).check(matches(isDisplayed()));
        onView(withId(R.id.weight_edittext)).check(matches(isDisplayed()));


        //Editing Article
        onView(withId(R.id.name_edittext)).perform(replaceText("bearbeited"));
        onView(withId(R.id.description_edittext)).perform(replaceText("neue beschreibung"));
        onView(withId(R.id.amount_edittext)).perform(replaceText("3"));
        onView(withId(R.id.weight_edittext)).perform(typeText("4"));
        Espresso.pressBack();// HideKeyboard
        onView(withId(R.id.add_article_fab)).perform(click());

        //Article viewer in view
        onView(withId(R.id.description_textview)).check(matches(withText("neue beschreibung")));
        onView(withId(R.id.amount_textview)).check(matches(withText("3")));
        onView(withId(R.id.weight_textview)).check(matches(withText("34.0")));

        Espresso.pressBack();// Go back to list fragment

        //Recycler item Test
        onView(withId(R.id.article_recycler_constraint)).check(matches(isDisplayed()));
        onView(withId(R.id.article_name_textview)).check(matches(withText("bearbeited")));
        onView(withId(R.id.article_amount_textview)).check(matches(isDisplayed()));
        onView(withId(R.id.article_weight_textview)).check(matches(isDisplayed()));
    }




}
