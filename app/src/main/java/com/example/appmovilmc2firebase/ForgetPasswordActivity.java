package com.example.appmovilmc2firebase;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

import appmovilmc2firebase.R;

public class ForgetPasswordActivity extends AppCompatActivity {

    private static final String TAG = "ForgetPasswordActivity";

    private Button exit, aceptar;
    private TextInputEditText etemail;

    private RequestQueue request;

    private static int tiempoTranscurrir = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTitle("Recuperar Contraseña");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        etemail = findViewById(R.id.tietEmailForgetPassword);

        exit = findViewById(R.id.buttonSalirForgetPassword);
        aceptar = findViewById(R.id.buttonAceptarForgetPassword);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = etemail.getText().toString().trim();
                if (email.isEmpty()) {
                    showErrorInputText(etemail, "Email de usuario vacio. Es necesario introducir un email válido");
                } else {
                    showAlertInformation(email);
                    sendPasswordForMail(email);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(ForgetPasswordActivity.this,LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            handler.removeCallbacks(null);
                        }
                    }, tiempoTranscurrir );//define el tiempo.
                }
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void sendPasswordForMail(String email) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GlobalInfo.URL_SENDPASSWORD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                request.getCache().clear();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                showError(etemail,error.toString());
            }
        }) {

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Accept", "*/*");
                headers.put("Authorization", GlobalInfo.AUTH_TOKEN);
                return headers;
            }

            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<String, String>();
                data.put("email", email);
                return data;
            }
        };
        request = Volley.newRequestQueue(ForgetPasswordActivity.this);
        request.add(stringRequest);
    }

    private void showErrorInputText(TextInputEditText input, String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        input.setError(s);
        input.requestFocus();
        builder.create();
    }

    private void showAlertInformation(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Aviso");
        builder.setMessage("Dirección de correo electrónico introducida correctamente. Le hemos enviado un correo a la siguiente dirección: " + s);
        builder.setPositiveButton("Aceptar", null);
        builder.create();
        builder.show();
    }

    private void showError(TextInputEditText input, String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("Se ha producido un error al obtener respuesta del servidor." + s);
        builder.setPositiveButton("Aceptar", null);
        input.setError(s);
        input.requestFocus();
        builder.create();
        builder.show();
    }
}