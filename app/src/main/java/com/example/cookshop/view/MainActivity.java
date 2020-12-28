package com.example.cookshop.view;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.cookshop.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

public class MainActivity extends AppCompatActivity
{
    //------------Static Variables------------


    //------------Instance Variables------------

    private  final String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d(TAG, "onCreate: Called");
        super.onCreate(savedInstanceState);

        //Set the Abb theme (true = dark, false = light)
        setTheme(true);
        //Setting the FragmentFactory
        getSupportFragmentManager().setFragmentFactory(new FragmentFactory());

        setContentView(R.layout.activity_main);
       // BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        //NavigationUI.setupWithNavController(navView, navController);


        this.setupBottomNav();
    }


    //------------Setup Views------------

    private void setTheme(boolean theme)
    {
        //Setting the app heme and contentView
        Log.d(TAG, "onCreate : setting app theme...");
        if (theme)
        {
            setTheme(R.style.DarkTheme);
            Log.d(TAG, "onCreate :  app theme DARK");
        }
        else
        {
            setTheme(R.style.LightTheme);
            Log.d(TAG, "onCreate :  app theme LIGHT");
        }
    }
    private void setupBottomNav()
    {
        // setting up the BottomNavigationView with Listener
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_view);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    private void onMainFabClickListener(View view)
    {

    }



    //------------Bottom Navigation ------------

    /**
     * Bottom Nav Listener
     */
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener()
    {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            Class selectedFragment = null;
            // switch ... case to select the right Fragment to start
            switch (item.getItemId())
            {
                case R.id.nav_to_cook:
                    Log.d(TAG, "onNavigationItemSelected: replacing current fragment with fragment toCook");
                    selectedFragment = FragmentShoppingList.class;
                    break;
                case R.id.nav_recommended:
                    Log.d(TAG, "onNavigationItemSelected: replacing current fragment with fragment recommended");
                    selectedFragment = FragmentRecommendedList.class;
                    break;
                case R.id.nav_recipes:
                    Log.d(TAG, "onNavigationItemSelected: replacing current fragment with fragment recipes");
                    selectedFragment = FragmentRecipeList.class;
                    break;
                case R.id.nav_available:
                    Log.d(TAG, "onNavigationItemSelected: replacing current fragment with fragment available");
                    selectedFragment = FragmentAvailableList.class;
                    break;
                case R.id.nav_shopping:
                    Log.d(TAG, "onNavigationItemSelected: replacing current fragment with fragment shopping");
                    selectedFragment = FragmentShoppingList.class;
                    break;
                default:
                    selectedFragment = FragmentShoppingList.class;
                    return false;
            }

            // giving the FragmentManager the container and the fragment which should be loaded into view
            // ... also commit
            try
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment, new Bundle()).addToBackStack(null).commit();
            }
            catch (NullPointerException e)
            {
                Log.e(TAG, "onNavigationItemSelected: Fragment was null ", e);
            }
            // return true to tell that everything did go right
            return true;
        }
    };


}