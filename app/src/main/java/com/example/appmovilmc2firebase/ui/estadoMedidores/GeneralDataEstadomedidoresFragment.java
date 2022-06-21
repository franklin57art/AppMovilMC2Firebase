package com.example.appmovilmc2firebase.ui.estadoMedidores;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appmovilmc2firebase.HomeActivity;
import com.example.appmovilmc2firebase.models.PuntosDeMedida;
import com.example.appmovilmc2firebase.utils.GlobalInfo;
import com.example.appmovilmc2firebase.utils.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import appmovilmc2firebase.R;

public class GeneralDataEstadomedidoresFragment extends Fragment {

    private static final String TAG = "EstadoDeMedidoresGeneralDataFragment";

    private Button editar, incidencia, volver;
    public TextView mNombreTitulo, mCups;

    private ArrayList<PuntosDeMedida> listaPuntosDeMedidas;

    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    private Context context;

    private PreferenceHelper preferenceHelper;

    private String authValue = "";

    private String nombrePuntoMedida = "";
    private Integer idConn = 00;
    private String cups = "";

    EstadomedidoresFragment estadomedidoresFragment;

    public GeneralDataEstadomedidoresFragment(){

    }

    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @NonNull Bundle savedInstanceState) {

        getActivity().setTitle("Datos contadores");

        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_general_data_estadomedidores, container, false);

        context = this.getContext();

        mNombreTitulo = vista.findViewById(R.id.tvGeneralDataEstadoMedidoresNombreTítulo);
        mCups = vista.findViewById(R.id.tvGeneralDataEstadoMedidoresCups);

        listaPuntosDeMedidas = new ArrayList<>();

        //Leo el valor del AUTH TOKEN KEY guardado al hacer el Login
        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("AUTHTOKENKEY", Context.MODE_PRIVATE);
        String authTokenValue = sharedPref.getString("AuthTokenKey", GlobalInfo.AUTH_TOKEN);
        authValue = authTokenValue;

        request = Volley.newRequestQueue(getContext());

        //Crear objeto bundler para recibir el objeto enviado por argumentos
        Bundle objetoPuntoDeMedida = getArguments();
        PuntosDeMedida puntosDeMedida = null;
        //Validar para verificar si existen argumento enviados para mostrar
        if(objetoPuntoDeMedida != null){
            puntosDeMedida = (PuntosDeMedida) objetoPuntoDeMedida.getSerializable("objetoPuntoDeMedida");
            //Establecer los datos en las vistas
            nombrePuntoMedida = puntosDeMedida.getName();
            mNombreTitulo.setText(nombrePuntoMedida);
        }

        /*editar = vista.findViewById(R.id.buttonGeneralDataEstadoMedidoresEditPuntoMedida);
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GeneralDataPuntosmedidaActivity.class);
                context.startActivity(intent);
                getActivity().finish();
            }
        });*/

        incidencia = vista.findViewById(R.id.buttonGeneralDataEstadoMedidoresIncidencia);
        incidencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IncidenciaEstadoMedidoresFragment fragment = new IncidenciaEstadoMedidoresFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.contenedor, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        volver = vista.findViewById(R.id.buttonAtrasGeneralDataEstadoMedidores);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HomeActivity.class);
                context.startActivity(intent);
            }
        });

        cargarDatos();
        return vista;
    }

    private void cargarDatos() {

        request = Volley.newRequestQueue(getContext());

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, GlobalInfo.URL_PUNTOS_DE_MEDIDA, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                PuntosDeMedida pdm = null;

                JSONArray json = response.optJSONArray("result");

                try {
                    for (int i = 0; i < json.length(); i++) {
                        if(nombrePuntoMedida.equals(json.getJSONObject(i).getString("name"))) {
                            pdm = new PuntosDeMedida();
                            JSONObject jsonObject = null;
                            jsonObject = json.getJSONObject(i);
                            pdm.setName(jsonObject.optString("name"));
                            pdm.setId_conn(jsonObject.optInt("id_conn"));
                            cups = pdm.setCups(jsonObject.optString("cups"));
                            idConn = pdm.setId_conn(jsonObject.optInt("id_conn"));
                            listaPuntosDeMedidas.add(pdm);
                        }else{
                            Log.d(TAG, "NO HAY COINCIDENCIA EN EL ATRIBUTO NAME. " + nombrePuntoMedida + " Y " + json.getJSONObject(i).getString("name"));
                        }
                        mCups.setText(cups);
                    }

                    //Enviamos datos al fragment de Incidencia
                    Bundle datosAEnviar = new Bundle();
                    datosAEnviar.putString("nombrePDM", nombrePuntoMedida);
                    datosAEnviar.putInt("idConnPDM", idConn);
                    getParentFragmentManager().setFragmentResult("key", datosAEnviar);

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

}