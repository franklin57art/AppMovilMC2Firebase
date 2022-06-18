package com.example.appmovilmc2firebase.ui.clientes;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appmovilmc2firebase.HomeActivity;
import com.example.appmovilmc2firebase.utils.GlobalInfo;
import com.example.appmovilmc2firebase.utils.PreferenceHelper;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import appmovilmc2firebase.R;

public class RegisterClientActivity extends AppCompatActivity {

    private static final String TAG = "RegisterClientActivity";

    private Button exit, aceptar;
    private TextInputEditText etname, etnombrecliente, etemail, ettelefono, etnombreempresa, etidfiscal;

    private PreferenceHelper preferenceHelper;
    private RequestQueue request;

    private int typeUser = 00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTitle("Nuevo Cliente");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_client);

        preferenceHelper = new PreferenceHelper(this);

        etname = findViewById(R.id.editTextRazonSocial);
        etnombrecliente = findViewById(R.id.editTextNombreCliente);
        etemail = findViewById(R.id.editTextEmailCliente);
        ettelefono = findViewById(R.id.editTextTelefonoCliente);
        etnombreempresa = findViewById(R.id.editTextNombreEmpresa);
        etidfiscal = findViewById(R.id.editTextIdFiscalEmpresa);

        exit = findViewById(R.id.buttonSalirRegisterClient);
        aceptar = findViewById(R.id.buttonAceptarRegisterClient);

        //Convierto la variable id_pt_user obtenida en el login y guardada con el shared preferences como String a Int.
        typeUser = Integer.parseInt(preferenceHelper.getType());

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

        final String nameclient = etnombrecliente.getText().toString();
        final String email = etemail.getText().toString();
        final String name = etname.getText().toString();
        final String telefono = ettelefono.getText().toString();
        final String nameempresa = etnombreempresa.getText().toString();
        final String idfiscal = etidfiscal.getText().toString();

        if (name.isEmpty() || email.isEmpty() || nameclient.isEmpty() || telefono.isEmpty() || nameempresa.isEmpty() || idfiscal.isEmpty()) {
            showAlertInformation(etnombrecliente, etemail, etname, ettelefono, etnombreempresa, etidfiscal, "El campo esta vacío. Asegúrese de rellenarlo correctamente");
        } else if (telefono.length() < 9) {
            showAlertInformationLengthPassword(ettelefono, "La longuitud del número de teléfono debe ser de al menos 9 dígitos.");
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, GlobalInfo.URL_REGISTER_CLIENT,
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
                    params.put("name", name);
                    params.put("nombre_cliente", nameclient);
                    params.put("email", email);
                    params.put("nombre_empresa", nameempresa);
                    params.put("telef1", telefono);
                    params.put("id_fiscal", idfiscal);
                    return params;
                }
            };
            request = Volley.newRequestQueue(RegisterClientActivity.this);
            request.add(stringRequest);
        }
    }

    private void parseData(String response) throws JSONException {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString("success").equals("true")) {
                Toast.makeText(RegisterClientActivity.this, "¡Cliente registrado correctamente!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegisterClientActivity.this, HomeActivity.class);
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

    private void showAlertInformation(TextInputEditText input, TextInputEditText inputt, TextInputEditText inputtt, TextInputEditText inputttt, TextInputEditText inputtttt, TextInputEditText inputttttt, String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        input.setError(s);
        input.requestFocus();
        inputt.setError(s);
        inputt.requestFocus();
        inputtt.setError(s);
        inputtt.requestFocus();
        inputttt.setError(s);
        inputttt.requestFocus();
        inputtttt.setError(s);
        inputtttt.requestFocus();
        inputttttt.setError(s);
        inputttttt.requestFocus();
        builder.create();
    }
}