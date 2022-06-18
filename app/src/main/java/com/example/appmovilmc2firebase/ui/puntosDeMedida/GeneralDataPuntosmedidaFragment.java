package com.example.appmovilmc2firebase.ui.puntosDeMedida;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appmovilmc2firebase.adaptadores.GeneralDataPuntosDeMedidaAdapter;
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

public class GeneralDataPuntosmedidaFragment extends Fragment {

    private static final String TAG = "PuntosDeMedidaGeneralDataFragment";

    private RecyclerView mRecyclerView;
    private Spinner spinnerPais, spinnerTipoPunto, spinnerTipoPlanta, spinnerModAlq, spinnerAtrPotencia, spinnerZonaTari, spinnerRecargo;

    private ArrayList<PuntosDeMedida> listaPuntosDeMedida;

    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    private PreferenceHelper preferenceHelper;

    private String authValue = "";

    public GeneralDataPuntosmedidaFragment(){

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @NonNull ViewGroup container, @NonNull Bundle savedInstanceState){
        View vista = inflater.inflate(R.layout.fragment_general_data_puntosdemedida, container, false);

        listaPuntosDeMedida = new ArrayList<>();

        mRecyclerView = (RecyclerView) vista.findViewById(R.id.recyclerviewCallGeneralDataPuntosDeMedida);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setHasFixedSize(true);

        //Leo el valor del AUTH TOKEN KEY guardado al hacer el Login
        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("AUTHTOKENKEY", Context.MODE_PRIVATE);
        String authTokenValue = sharedPref.getString("AuthTokenKey", GlobalInfo.AUTH_TOKEN);
        authValue = authTokenValue;

        request = Volley.newRequestQueue(getContext());

        cargarDatos();

        return vista;

    }

    private void cargarDatos() {
        request = Volley.newRequestQueue(getContext());

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, GlobalInfo.URL_PUNTOS_DE_MEDIDA, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                PuntosDeMedida pdm = null;

                preferenceHelper = new PreferenceHelper(getActivity());

                JSONArray json = response.optJSONArray("result");

                try {
                    for (int i = 0; i < json.length(); i++) {
                        pdm = new PuntosDeMedida();
                        JSONObject jsonObject = null;
                        jsonObject = json.getJSONObject(i);
                        pdm.setName(jsonObject.optString("name"));
                        pdm.setId_client(jsonObject.optInt("id_client"));
                        pdm.setId_psum_for_client(jsonObject.optString("id_psum_for_client"));
                        pdm.setAddress(jsonObject.optString("address"));
                        pdm.setId_poblacion(jsonObject.optInt("id_poblacion"));
                        pdm.setCod_postal(jsonObject.optString("cod_postal"));
                        pdm.setCups(jsonObject.optString("cups"));
                        pdm.setCups_obras(jsonObject.optString("cups_obras"));
                        pdm.setId_comerc(jsonObject.optInt("id_comerc"));
                        pdm.setAlquiler_equipo(jsonObject.optInt("alquiler_equipo"));
                        pdm.setEstimated_annual_consumption(jsonObject.optInt("estimated_annual_consumption"));
                        pdm.setDescription(jsonObject.optString("description"));
                        listaPuntosDeMedida.add(pdm);
                    }

                    GeneralDataPuntosDeMedidaAdapter adapter = new GeneralDataPuntosDeMedidaAdapter(listaPuntosDeMedida);
                    mRecyclerView.setAdapter(adapter);
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

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.recyclerviewCallGeneralDataPuntosDeMedida);
    }
}
