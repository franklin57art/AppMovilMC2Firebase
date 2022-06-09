package com.example.appmovilmc2firebase.ui.usuarios;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appmovilmc2firebase.GlobalInfo;
import com.example.appmovilmc2firebase.HomeActivity;
import com.example.appmovilmc2firebase.PreferenceHelper;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import appmovilmc2firebase.R;

public class RegisterUserActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "RegisterUserActivity";

    private ImageView info;
    private Button exit, aceptar;
    private TextInputEditText etusername, etemail, etnombre, etpassword;
    private Spinner spinner;
    private PreferenceHelper preferenceHelper;
    private RequestQueue request;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setTitle("Nuevo Usuario");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeruser);

        preferenceHelper = new PreferenceHelper(this);

        etusername = findViewById(R.id.editTextUserName);
        etemail = findViewById(R.id.editTextEmail);
        etnombre = findViewById(R.id.editTextNombreRegisterUser);
        etpassword = findViewById(R.id.editTextPassword);

        exit = findViewById(R.id.buttonSalirRegisterUser);
        aceptar = findViewById(R.id.buttonAceptarRegisterUser);

        info = findViewById(R.id.ivInfo);
        spinner = findViewById(R.id.spinnerRegisterUser);
        // Creo un array de string con los valores del spinner
        String[] array = getResources().getStringArray(R.array.type_array);
        // Creo un array de enteros que recorrera el array definido anteriormente y va a transformar los String en enteros
        Integer[] intArray = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            intArray[i] = Integer.parseInt(array[i]);
        }
        // Create an ArrayAdapter using the string array and a spinner layout
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, intArray);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo();
            }
        });

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerMe();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void registerMe() {

        final String username = etusername.getText().toString();
        final String email = etemail.getText().toString();
        final String name = etnombre.getText().toString();
        final String password = etpassword.getText().toString();
        final Integer spinnervalue = (Integer) spinner.getSelectedItem();

        if (username.isEmpty() || email.isEmpty() || name.isEmpty() || password.isEmpty()) {
            showAlertInformation(etusername, etemail, etnombre, etpassword, "El campo esta vacío. Asegúrese de rellenarlo correctamente");
        }else if(password.length() < 8){
            showAlertInformationLengthPassword(etpassword, "La longuitud de la contraseña dede de ser de al menos 8 caracteres");
        }else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, GlobalInfo.URL_REGISTER,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            request.getCache().clear();
                            Log.d(TAG, response);
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
                    params.put("username", username);
                    params.put("password", password);
                    params.put("email", email);
                    params.put("name", name);
                    params.put("type", spinnervalue.toString());
                    return params;
                }
            };
            request = Volley.newRequestQueue(RegisterUserActivity.this);
            request.add(stringRequest);
        }
    }

    private void parseData(String response) throws JSONException {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString("success").equals("true")) {
                Toast.makeText(RegisterUserActivity.this, "¡Usuario registrado correctamente!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegisterUserActivity.this, HomeActivity.class);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Integer i = (Integer) spinner.getSelectedItem();
        Log.d(TAG, i.toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("Se ha producido un error registrando al usuario");
        builder.setPositiveButton("Aceptar", null);
        builder.create();
        builder.show();
    }

    private void showError(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("Se ha producido un error al obtener respuesta del servidor");
        builder.setPositiveButton("Aceptar", null);
        builder.create();
        builder.show();
    }

    private void showAlertInformationLengthPassword(TextInputEditText input, String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        input.setError(s);
        input.requestFocus();
        builder.create();
    }

    private void showAlertInformation(TextInputEditText input, TextInputEditText inputt, TextInputEditText inputtt, TextInputEditText inputttt,String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        input.setError(s);
        input.requestFocus();
        inputt.setError(s);
        inputt.requestFocus();
        inputtt.setError(s);
        inputtt.requestFocus();
        inputttt.setError(s);
        inputttt.requestFocus();
        builder.create();
    }

    private void showInfo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Info");
        builder.setMessage("0->Virtual, 1->Técnico, 2->Comercial, 3->Directivo, 4->Administrativo, 5->Desarrollador, 6->Jefe de equipo");
        builder.setPositiveButton("Aceptar", null);
        builder.create();
        builder.show();
    }
}

