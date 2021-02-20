package com.example.cookshop.model;

import android.content.SharedPreferences;

/**
 * This class is used to get all necessary values
 * (for example from the shared preferences) and
 * make them available in the whole app
 * <p>
 * Examples: users request id
 *
 * @author WilliBoelke
 */
public class UserPreferences
{

    //------------Static Variables------------

    /**
     * SharedPreferences name
     */
    public static final String SETTINGS_SHARED_PREFERENCES = "CookAndShopSettings";

    /**
     * Key to get the DarkMode boolean from the SharedPreferences
     */

    public static final String THEME_KEY = "theme";
    public static final String DARK_MODE = "dark_mode";
    public static final String LIGHT_MODE = "light_mode";
    public static final String LILAH_MODE = "lilah_mode";

    private static UserPreferences instance;
    private String theme;
    private SharedPreferences prefs;


    public static UserPreferences getInstance()
    {
        if (instance == null)
        {
            instance = new UserPreferences();
        }
        return instance;
    }


    public void init(SharedPreferences prefs)
    {
        this.prefs = prefs;
        theme = prefs.getString(THEME_KEY, DARK_MODE);
    }


    public String getTheme()
    {
        return theme;
    }


    public void changeTheme(String theme)
    {
        this.theme = theme;
        prefs.edit().putString(THEME_KEY, theme).commit();
    }
}