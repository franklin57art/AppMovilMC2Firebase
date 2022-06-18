package com.example.appmovilmc2firebase.ui.puntosDeMedida;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appmovilmc2firebase.HomeActivity;
import com.example.appmovilmc2firebase.models.Client;
import com.example.appmovilmc2firebase.utils.GlobalInfo;
import com.example.appmovilmc2firebase.utils.PreferenceHelper;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import appmovilmc2firebase.R;

public class RegisterPuntosmedidaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static final String TAG = "RegisterPuntosDeMedidaActivity";

    private Button exit, aceptar;
    private TextInputEditText etname, etcups;
    private ImageView info;
    private Spinner spinnerTipo, spinnerCliente;

    private PreferenceHelper preferenceHelper;
    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;
    private ArrayList<Integer> listaIdClients;

    private String authValue = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTitle("Nuevo Punto de Medida");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_puntosmedida);

        listaIdClients = new ArrayList<Integer>();

        //Leo el valor del AUTH TOKEN KEY guardado al hacer el Login
        SharedPreferences sharedPref = RegisterPuntosmedidaActivity.this.getSharedPreferences("AUTHTOKENKEY", Context.MODE_PRIVATE);
        String authTokenValue = sharedPref.getString("AuthTokenKey", GlobalInfo.AUTH_TOKEN);
        authValue = authTokenValue;

        preferenceHelper = new PreferenceHelper(this);

        etname = findViewById(R.id.editTextNombrePuntoDeMedida);
        etcups = findViewById(R.id.editTextCupsPuntoDeMedida);

        exit = findViewById(R.id.buttonSalirRegisterPuntoDeMedida);
        aceptar = findViewById(R.id.buttonAceptarRegisterPuntoDeMedida);

        info = findViewById(R.id.ivInfoTipoPuntoDeMedida);
        spinnerTipo = findViewById(R.id.spinnerTipoPuntoDeMedida);
        // Creo un array de string con los valores del spinner
        String[] arrayTipos = getResources().getStringArray(R.array.type_array_punto_de_medida);
        // Creo un array de enteros que recorrera el array definido anteriormente y va a transformar los String en enteros
        Integer[] intArrayTipos = new Integer[arrayTipos.length];
        for (int i = 0; i < arrayTipos.length; i++) {
            intArrayTipos[i] = Integer.parseInt(arrayTipos[i]);
        }
        // Create an ArrayAdapter using the string array and a spinner layout
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, intArrayTipos);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerTipo.setAdapter(adapter);

        request = Volley.newRequestQueue(this);

        spinnerCliente = findViewById(R.id.spinnerClientePuntoDeMedida);
        cargarDatosSpinner();

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

    public void cargarDatosSpinner() {

        request = Volley.newRequestQueue(this);

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, GlobalInfo.URL_CLIENT_SPINNER, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Client cl = null;
                Integer id = null;

                JSONArray json = response.optJSONArray("result");

                Log.e(TAG, json.toString());
                try {
                    for (int i = 0; i < json.length(); i++) {
                        cl = new Client();
                        JSONObject jsonObject = null;
                        jsonObject = json.getJSONObject(i);
                        id = cl.setId_client(jsonObject.optInt("id_client"));
                        listaIdClients.add(id);

                    }
                    System.out.println(listaIdClients);

                    // Creo un array de enteros que recorrera el array definido anteriormente
                    Integer[] intArrayClientes = new Integer[listaIdClients.size()];
                    for (int i = 0; i < listaIdClients.size(); i++) {
                        intArrayClientes[i] = listaIdClients.get(i);
                        System.out.println("ARRAYCLIENTES: "+i+". " + intArrayClientes[i]);
                    }

                    System.out.println("LONGUITUD ARRAYCLIENTES: "+intArrayClientes.length);
                    // Create an ArrayAdapter using the string array and a spinner layout
                    ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(RegisterPuntosmedidaActivity.this, android.R.layout.simple_spinner_item, intArrayClientes);
                    // Specify the layout to use when the list of choices appears
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerCliente.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(RegisterPuntosmedidaActivity.this, "No se ha podido establecer conexion con el servidor " + e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterPuntosmedidaActivity.this, "No se puede conectar " + error.toString(), Toast.LENGTH_LONG).show();
                        System.out.println();
                        Log.d(TAG, "ERROR: " + error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "*/*");
                headers.put("Authorization", authValue);
                return headers;
            }
        };
        request.add(jsonObjectRequest);
    }

    private void registerMe() {

        final String name = etname.getText().toString();
        final String cups = etcups.getText().toString();
        final Integer spinnerTipoValue = (Integer) spinnerTipo.getSelectedItem();
        final Integer spinnerClienteValue = (Integer) spinnerCliente.getSelectedItem();

        if (name.isEmpty() || cups.isEmpty()) {
            showAlertInformation(etname, etcups, "El campo esta vacío. Asegúrese de rellenarlo correctamente");
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, GlobalInfo.URL_REGISTER_PUNTO_DE_MEDIDA,
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
                    params.put("cups", cups);
                    params.put("name", name);
                    params.put("type", spinnerTipoValue.toString());
                    params.put("id_client", spinnerClienteValue.toString());
                    return params;
                }
            };
            request = Volley.newRequestQueue(RegisterPuntosmedidaActivity.this);
            request.add(stringRequest);
        }
    }

    private void parseData(String response) throws JSONException {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString("success").equals("true")) {
                Toast.makeText(RegisterPuntosmedidaActivity.this, "¡Punto de medida registrado correctamente!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegisterPuntosmedidaActivity.this, HomeActivity.class);
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


    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Integer i = (Integer) spinnerTipo.getSelectedItem();
        Log.d(TAG, i.toString());
        Integer ii = (Integer) spinnerCliente.getSelectedItem();
        Log.d(TAG, ii.toString());
    }


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

    private void showAlertInformation(TextInputEditText input, TextInputEditText inputt, String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        input.setError(s);
        input.requestFocus();
        inputt.setError(s);
        inputt.requestFocus();
        builder.create();
    }

    private void showInfo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Info");
        builder.setMessage("1->Suministro eléctrico, 2->Fotovoltaica, 3->Suministro de gas, 4->Analizador de redes eléctricas, 5->Estación meteorológica");
        builder.setPositiveButton("Aceptar", null);
        builder.create();
        builder.show();
    }
}