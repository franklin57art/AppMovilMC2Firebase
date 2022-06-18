package com.example.appmovilmc2firebase.ui.configuracion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appmovilmc2firebase.utils.GlobalInfo;
import com.example.appmovilmc2firebase.HomeActivity;
import com.example.appmovilmc2firebase.utils.PreferenceHelper;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import appmovilmc2firebase.R;

public class ChangePasswordConfigActivity extends AppCompatActivity {

    private static final String TAG = "ChangePasswordConfigActivity";

    private Button exit, aceptar;
    private TextInputEditText etpasswordNueva, etpasswordNuevaRepetida;

    private PreferenceHelper preferenceHelper;

    private JsonObjectRequest jsonObjectRequest;
    private RequestQueue request;

    private String authValue = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTitle("Cambiar contraseña");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_config);

        preferenceHelper = new PreferenceHelper(this);

        etpasswordNueva = findViewById(R.id.tietPasswordNew);
        etpasswordNuevaRepetida = findViewById(R.id.tietRepeatPassword);

        exit = findViewById(R.id.buttonVolverChangePasswordConfig);
        aceptar = findViewById(R.id.buttonAceptarChangePasswordConfig);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Leo el valor del AUTH TOKEN KEY guardado al hacer el Login
        SharedPreferences sharedPref = this.getSharedPreferences("AUTHTOKENKEY", Context.MODE_PRIVATE);
        String authTokenValue = sharedPref.getString("AuthTokenKey", GlobalInfo.AUTH_TOKEN);
        authValue = authTokenValue;

        request = Volley.newRequestQueue(this);
    }

    private void changePassword() {


        final String newpassword = etpasswordNueva.getText().toString().trim();
        final String repeatpassword = etpasswordNuevaRepetida.getText().toString().trim();

        //Convierto la variable id_pt_user obtenida en el login y guardada con el shared preferences como String a Int.
        Log.e("ID PT USER: ", preferenceHelper.getIdPtUser());

        if (newpassword.isEmpty() || repeatpassword.isEmpty()) {
            Log.e("NEW PASSWORD VAcio: ", newpassword);
            Log.e("NEW PASSWORD REPEAT VAcio: ", repeatpassword);
            showErrorInputText(etpasswordNueva, etpasswordNuevaRepetida, "Uno o los dos campos de las contraseñas están vacíos");
        } else if ((newpassword.length() < 8) || (repeatpassword.length() < 8)) {
            Log.e("NEW PASSWORD length: ", String.valueOf(newpassword.length()));
            Log.e("NEW PASSWORD REPEAT length: ", String.valueOf(repeatpassword.length()));
            showErrorInputText(etpasswordNueva, etpasswordNuevaRepetida, "La longuitud de la contraseña dede de ser de al menos 8 caracteres");
        } else if (!newpassword.equals(repeatpassword)) {
            Log.e("NEW PASSWORD No Igual: ", newpassword);
            Log.e("NEW PASSWORD REPEAT No Igual: ", repeatpassword);
            showErrorInputText(etpasswordNueva, etpasswordNuevaRepetida, "Las contraseñas no coinciden entre ellas");
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, GlobalInfo.URL_CHANGEPASSWORD,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            request.getCache().clear();
                            Log.e(TAG, response);
                            try {
                                parseData(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            showError(error.toString());
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Accept", "*/*");
                    headers.put("Authorization", GlobalInfo.AUTH_TOKEN);
                    return headers;
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("password", repeatpassword);
                    params.put("id_pt_user", preferenceHelper.getIdPtUser());
                    return params;
                }
            };
            request = Volley.newRequestQueue(ChangePasswordConfigActivity.this);
            request.add(stringRequest);
        }

    }

    private void parseData(String response) throws JSONException {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString("success").equals("true")) {
                Toast.makeText(ChangePasswordConfigActivity.this, "¡Contraseña modificada correctamente!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ChangePasswordConfigActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                this.finish();
            } else {
                showAlert();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showAlert() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("Se ha producido un error al cambiar la contraseña.");
        builder.setPositiveButton("Aceptar", null);
        builder.create();
        builder.show();
    }

    private void showError(String s) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("Se ha producido un error al obtener respuesta del servidor.");
        builder.setPositiveButton("Aceptar", null);
        builder.create();
        builder.show();
    }

    private void showErrorInputText(TextInputEditText input, TextInputEditText inputt, String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        input.setError(s);
        inputt.setError(s);
        input.requestFocus();
        inputt.requestFocus();
        builder.create();
    }


}