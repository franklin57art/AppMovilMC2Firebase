package com.example.appmovilmc2firebase.ui.clientes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

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
import com.example.appmovilmc2firebase.models.PuntosDeMedida;
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

public class GeneralDataClientFragment extends Fragment {

    private static final String TAG = "ClientesGeneralDataFragment";

    private Button mDeleteClientButton, volver, update;
    private TextInputEditText mRazonSocial, mIdFiscal, mNombreCorto, mNombreCliente;
    private AutoCompleteTextView mTecnico;
    private ListView mNombrePdm;

    private ArrayList<String> listaNamePdm;

    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    private Context context;

    private PreferenceHelper preferenceHelper;

    private int typeUser = 00;

    private String authValue = "";

    private String nameCl = "";
    private Integer idclient = 00;
    private String nameCliente = "";
    private String nameEmpresa = "";
    private Integer idTecnico = 00;
    private String namePdm = "";

    ClienteFragment clienteFragment;

    public GeneralDataClientFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {

        getActivity().setTitle("Datos clientes");

        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_general_data_client, container, false);

        context = this.getContext();

        preferenceHelper = new PreferenceHelper(context);
        //Convierto la variable id_pt_user obtenida en el login y guardada con el shared preferences como String a Int.
        typeUser = Integer.parseInt(preferenceHelper.getType());

        mRazonSocial = vista.findViewById(R.id.tietGeneralDataClienteRazonSocial);
        mIdFiscal = vista.findViewById(R.id.tietGeneralDataClienteIdFiscal);
        mNombreCorto = vista.findViewById(R.id.tietGeneralDataClienteNombreCorto);
        mNombreCliente = vista.findViewById(R.id.tietGeneralDataClienteNombreDelCliente);
        mTecnico = vista.findViewById(R.id.actvGeneralDataClienteTecnicosAsignados);
        mNombrePdm = vista.findViewById(R.id.lvTableDatosGeneralDataClienteNombrePuntosDeSuministro);

        listaNamePdm = new ArrayList<String>();

        //Leo el valor del AUTH TOKEN KEY guardado al hacer el Login
        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("AUTHTOKENKEY", Context.MODE_PRIVATE);
        String authTokenValue = sharedPref.getString("AuthTokenKey", GlobalInfo.AUTH_TOKEN);
        authValue = authTokenValue;

        request = Volley.newRequestQueue(getContext());

        //Crear objeto bundler para recibir el objetoenviado por argumentos
        Bundle objetoClient = getArguments();
        Client client = null;
        //Validar para verificar si existen argumento enviados para mostrar
        if (objetoClient != null) {
            client = (Client) objetoClient.getSerializable("objetoCliente");
            //Establecer los datos en las vistas
            idclient = client.getId_client();
            nameCl = client.getName();
            mRazonSocial.setText(client.getName());
            mIdFiscal.setText(client.getId_fiscal());
            Log.e(TAG, String.valueOf(idclient));
        }

        mDeleteClientButton = vista.findViewById(R.id.buttonGeneralDataClienteEliminarCliente);
        mDeleteClientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeUser == 7) {
                    deleteClient();
                } else {
                    Log.e("TAG", "EL TYPE DEL USUARIO NO ES DE TIPO PARTNER ADMINISTRADOR. Es un type tipo: " + typeUser);
                    showAlert();
                }
            }
        });

        volver = vista.findViewById(R.id.buttonAtrasGeneralDataClient);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });

        update = vista.findViewById(R.id.buttonActualizarGeneralDataCliente);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeUser == 7) {
                    updateData();
                } else {
                    Log.e("TAG", "EL TYPE DEL USUARIO NO ES DE TIPO PARTNER ADMINISTRADOR. Es un type tipo: " + typeUser);
                    showAlert();
                }
            }
        });

        cargarDatos();
        cargarNombresPdm();

        return vista;
    }

    private void updateData() {
        final String rsCliente = mRazonSocial.getText().toString();
        final String idFiscal = mIdFiscal.getText().toString();
        final String nombreCorto = mNombreCorto.getText().toString();
        final String nombreCliente = mNombreCliente.getText().toString();
        final String tecnico = mTecnico.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, GlobalInfo.URL_UPDATE_CLIENT + String.valueOf(idclient),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        request.getCache().clear();
                        Log.d(TAG, response);
                        try {
                            parseDataUpdate(response);
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
                params.put("name", rsCliente);
                params.put("nombre_cliente", nombreCliente);
                params.put("nombre_empresa", nombreCorto);
                params.put("id_tecnico", tecnico);
                params.put("id_fiscal", idFiscal);
                return params;
            }
        };
        request = Volley.newRequestQueue(getActivity());
        request.add(stringRequest);
    }

    private void parseDataUpdate(String response) throws JSONException {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString("success").equals("true")) {
                Toast.makeText(getActivity(), "¡Cliente actualizado con éxito!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
            } else {
                showError(response);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            showAlertt();
        }
    }

    private void deleteClient() {

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, GlobalInfo.URL_DELETE_CLIENT + String.valueOf(idclient),
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
                params.put("token", "no");
                params.put("except", "id_client");
                params.put("nameID", "id_client");
                params.put("id", String.valueOf(idclient));
                return params;
            }
        };
        request = Volley.newRequestQueue(getActivity());
        request.add(stringRequest);
    }

    private void parseData(String response) throws JSONException {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString("success").equals("true")) {
                Toast.makeText(getActivity(), "¡Cliente eliminado con éxito!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
            } else {
                showError(response);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            showAlertt();
        }
    }

    private void cargarDatos() {

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, GlobalInfo.URL_CLIENT, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Client cl = null;

                JSONArray json = response.optJSONArray("result");

                try {
                    for (int i = 0; i < json.length(); i++) {
                        if (nameCl.equals(json.getJSONObject(i).getString("name"))) {
                            cl = new Client();
                            JSONObject jsonObject = null;
                            jsonObject = json.getJSONObject(i);
                            nameCliente = cl.setNombre_cliente(jsonObject.optString("nombre_cliente"));
                            nameEmpresa = cl.setNombre_empresa(jsonObject.optString("nombre_empresa"));
                            idTecnico = cl.setId_tecnico(jsonObject.optInt("id_tecnico"));
                        } else {
                            Log.e(TAG, "NO HAY COINCIDENCIA EN EL ATRIBUTO NAME. " + nameCl + ", " + json.getJSONObject(i).getString("name"));
                        }
                        mNombreCorto.setText(nameCliente);
                        mNombreCliente.setText(nameEmpresa);
                        mTecnico.setText(String.valueOf(idTecnico));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    showError(e.toString());
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "No se puede conectar " + error.toString(), Toast.LENGTH_LONG).show();
                        System.out.println();
                        Log.d(TAG, "ERROR: " + error.toString());
                    }
                }
        ) {
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

    private void cargarNombresPdm() {

        request = Volley.newRequestQueue(getContext());

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, GlobalInfo.URL_PUNTOS_DE_MEDIDA, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                PuntosDeMedida pdm = null;

                JSONArray json = response.optJSONArray("result");

                try {
                    for (int i = 0; i < json.length(); i++) {
                        if (idclient.equals(json.getJSONObject(i).getInt("id_client"))) {
                            pdm = new PuntosDeMedida();
                            JSONObject jsonObject = null;
                            jsonObject = json.getJSONObject(i);
                            namePdm = pdm.setName(jsonObject.optString("name"));
                            listaNamePdm.add(namePdm);
                        } else {
                            Log.d(TAG, "NO HAY COINCIDENCIA EN EL ATRIBUTO NAME. " + idclient + " Y " + json.getJSONObject(i).getInt("id_client"));
                        }
                    }
                    Log.d(TAG, "Lista nombre pdm: " + listaNamePdm);

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listaNamePdm);
                    mNombrePdm.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "No se ha podido establecer conexion con el servidor " + response.toString(), Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "No se puede conectar " + error.toString(), Toast.LENGTH_LONG).show();
                        System.out.println();
                        Log.d(TAG, "ERROR: " + error.toString());
                    }
                }
        ) {
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


    private void showError(String s) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        builder.setTitle("Error");
        builder.setMessage("Se ha producido un error al obtener respuesta del servidor. " + s);
        builder.setPositiveButton("Aceptar", null);
        builder.create();
        builder.show();
    }

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Error");
        builder.setMessage("No es usted un usuario ADMMINISTRADOR. No tiene permiso de escritura y/o borrado");
        builder.setPositiveButton("Aceptar", null);
        builder.create();
        builder.show();
    }

    private void showAlertt() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        builder.setTitle("Error");
        builder.setMessage("Se ha producido un error eliminando al cliente. No es posible eliminar un Cliente con Puntos de Suministros asociados. Elimine o desvincule dichos puntos del Cliente. ");
        builder.setPositiveButton("Aceptar", null);
        builder.create();
        builder.show();
    }

}
