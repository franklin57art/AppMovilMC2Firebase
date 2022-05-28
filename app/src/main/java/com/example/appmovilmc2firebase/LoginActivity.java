package com.example.appmovilmc2firebase;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private TextInputEditText emailEditText, passwordEditText;
    private TextView olvidarContraseniaTextView;
    private Button logInButton;

    private PreferenceHelper preferenceHelper;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private RequestQueue rQueue;

    private ProgressDialog pdDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        //Splash
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setTitle("Login");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //Preferencias compartidas para el primer inicio de sesion. Esto lo hacemos para el mesaje de bienvenida
        sharedPreferences = getSharedPreferences("PRIMER_ACCESO", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putBoolean("primer_acceso",true).commit();

        //preferenceHelper.
        preferenceHelper = new PreferenceHelper(this);

        emailEditText = findViewById(R.id.emailTextInputEditTextAl);
        passwordEditText = findViewById(R.id.passwordTextInputEditTextAl);

        olvidarContraseniaTextView = findViewById(R.id.passwordForgotTextViewAl);

        logInButton = findViewById(R.id.loginButtonAl);

        pdDialog = new ProgressDialog(LoginActivity.this);

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailEditText.setText("aarestizabal@correo.com");
                passwordEditText.setText("123456");

                final String memail = emailEditText.getText().toString().trim();
                final String mpassword = passwordEditText.getText().toString().trim();

                if (memail.isEmpty()) {
                    showError(emailEditText, "Email de usuario vacio. Es necesario introducir un email válido");
                } else if (mpassword.isEmpty()) {
                    showError(passwordEditText, "Contraseña vacia.");
                } else {
                    login(memail, mpassword);
                    saveAuthKey();
                }
            }
        });

        olvidarContraseniaTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Aún no esta disponible esta opcion", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        findViewById(R.id.loginLayout).setVisibility(View.VISIBLE);
    }

    public void login(String email, String password) {

        pdDialog.setTitle("Autenticacion");
        pdDialog.setMessage("Espere mientras comprobamos los datos introducidos");
        pdDialog.setCanceledOnTouchOutside(false);
        pdDialog.show();
        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GlobalInfo.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.e("ENTRAMOS", response);
                rQueue.getCache().clear();
                parseData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                showError(emailEditText,error.toString());
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
                data.put("password", password);
                return data;
            }
        };
        rQueue = Volley.newRequestQueue(LoginActivity.this);
        rQueue.add(stringRequest);
        }

    private void parseData(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("success").equals("true")) {
                saveInfo(response);
                Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }else{
                showAlert();
            }
        } catch (JSONException e) {
            pdDialog.dismiss();
            e.printStackTrace();
        }
    }

    //clase para guardar datos del usuario que accede a la app (en este caso guardo email de usuario y nombre)
    //estos datos se mostran en el activity home modificando los atributos de su nav_bar
    private void saveInfo(String response) {
        preferenceHelper.putIsLogin(true);
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("success").equals("true")) {
                pdDialog.dismiss();
                JSONArray dataArray = jsonObject.getJSONArray("result");
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject dataobj = dataArray.getJSONObject(i);
                    preferenceHelper.putName(dataobj.getString("name"));
                    preferenceHelper.putEmail(dataobj.getString("email"));
                    preferenceHelper.putIdPtUser(dataobj.getString("id_pt_user"));
                    preferenceHelper.putType(dataobj.getString("type"));
                }
            }
        } catch (JSONException e) {
            pdDialog.dismiss();
            e.printStackTrace();
        }
    }

    private void saveAuthKey(){
        //Preferencias compartidas para pasar el valor del parametro del header authorization.
        sharedPreferences = this.getSharedPreferences("AUTHTOKENKEY",Context.MODE_PRIVATE);
        editor.putString("AuthTokenKey", GlobalInfo.AUTH_TOKEN);
        editor.commit();
    }

    private void showAlert() {
        pdDialog.dismiss();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("Se ha producido un error autenticando al usuario");
        builder.setPositiveButton("Aceptar", null);
        builder.create();
        builder.show();
    }

    private void showError(TextInputEditText input, String s) {
        pdDialog.dismiss();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("Se ha producido un error al obtener respuesta del servidor");
        builder.setPositiveButton("Aceptar", null);
        input.setError(s);
        input.requestFocus();
        builder.create();
        builder.show();
    }

}
