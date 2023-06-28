package com.example.temas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatDelegate;

public class MainActivity extends AppCompatActivity {

    Spinner themeSpinner;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        themeSpinner = findViewById(R.id.spinner_theme);
        sharedPreferences = getApplication().getSharedPreferences("ThemePrefs", MODE_PRIVATE);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.theme_names, android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        themeSpinner.setAdapter(adapter);

        themeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedTheme = parent.getItemAtPosition(position).toString();
                int themeValue = getThemeValue(selectedTheme);

                AppCompatDelegate.setDefaultNightMode(themeValue);
                saveThemePreference(themeValue);
                //recreate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        int savedTheme = sharedPreferences.getInt("theme", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        AppCompatDelegate.setDefaultNightMode(savedTheme);
    }

    private int getThemeValue(String selectedTheme) {
        switch (selectedTheme) {
            case "Claro":
                return AppCompatDelegate.MODE_NIGHT_NO;
            case "Oscuro":
                return AppCompatDelegate.MODE_NIGHT_YES;
            case "CiberpunkTheme":
                return R.style.CiberpunkTheme;
            case "NeonTheme":
                return R.style.NeonTheme;
            default:
                return AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
        }
    }

    private void saveThemePreference(int themeValue) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("theme", themeValue);
        editor.apply();
    }
}
