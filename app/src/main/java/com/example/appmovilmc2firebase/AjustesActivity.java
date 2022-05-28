package com.example.appmovilmc2firebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toolbar;

import com.google.android.material.appbar.MaterialToolbar;

public class AjustesActivity extends AppCompatActivity {

    private ConstraintLayout back;
    private Switch change_theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Ajustes");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);
        //Icono para volver atras.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        back = findViewById(R.id.AjustesActivity);
        change_theme = findViewById(R.id.switch1);

        change_theme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (change_theme.isChecked()){
                    updateTheme("DARK", "#df000000");
                } else {
                    updateTheme("DEFAULT", "#ffffff");
                }
            }
        });

        loadTheme();

    }

    private void updateTheme(String key, String c1) {
        SharedPreferences savePreferences = getSharedPreferences("config_theme", MODE_PRIVATE);
        SharedPreferences.Editor ObjEditor = savePreferences.edit();
        ObjEditor.putString("theme", key);
        ObjEditor.commit();

        back.setBackgroundColor(Color.parseColor(c1));
    }

    public void loadTheme(){
        SharedPreferences loadPreferences = getSharedPreferences("config_theme", MODE_PRIVATE);
        String actualTheme = loadPreferences.getString("theme", "DEFAULT");

        if(actualTheme.equals("DEFAULT")){
            updateTheme("DEFAULT", "#ffffff");
        }else if(actualTheme.equals("DARK")){
            updateTheme("DARK", "#df000000");
            change_theme.setChecked(true);
        }
    }
}