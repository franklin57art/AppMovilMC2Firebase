package com.example.appmovilmc2firebase.ui.clientes;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import appmovilmc2firebase.R;

public class GeneralDataClientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTitle("Datos Cliente");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_data_client);
        //Icono para volver atras.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

