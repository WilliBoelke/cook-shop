package com.example.cookshop.view.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.cookshop.R;
import com.example.cookshop.model.UserPreferences;


public class FragmentSettings extends Fragment
{


    private View view;

    public FragmentSettings()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_settings, container, false);

        setupThemeSpinner();
        setupUnitSpinner();

        return view;
    }


    public void setupThemeSpinner()
    {
        Spinner themeSpinner = view.findViewById(R.id.theme_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.themes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        themeSpinner.setAdapter(adapter);
        themeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                switch (position)
                {
                    case 0:
                        //DarkMode
                        UserPreferences.getInstance().changeTheme(UserPreferences.DARK_MODE);
                        break;
                    case 1:
                        //LightMode
                        UserPreferences.getInstance().changeTheme(UserPreferences.LIGHT_MODE);
                        break;
                    case 2:
                        UserPreferences.getInstance().changeTheme(UserPreferences.LILAH_MODE);
                        break;
                    default:
                        //Default Darkmode
                        UserPreferences.getInstance().changeTheme(UserPreferences.DARK_MODE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });
    }


    public void setupUnitSpinner()
    {

    }
}