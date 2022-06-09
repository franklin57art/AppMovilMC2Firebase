package com.example.appmovilmc2firebase.ui.puntosDeMedida;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.appmovilmc2firebase.GlobalInfo;
import com.example.appmovilmc2firebase.adaptadores.PuntosDeMedidaAdapter;
import com.example.appmovilmc2firebase.models.PuntosDeMedida;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import appmovilmc2firebase.R;

public class PuntosmedidaFragment extends Fragment {

    private static final String TAG = "PuntosMedidaFragment";

    private RecyclerView mRecyclerView;
    ArrayList<PuntosDeMedida> listaPuntosDeMedida;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    private String authValue = "";

    public PuntosmedidaFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @NonNull Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_puntosdemedida, container, false);

        listaPuntosDeMedida = new ArrayList<>();

        mRecyclerView = (RecyclerView) vista.findViewById(R.id.recyclerviewPsum);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.hasFixedSize();

        //Leo el valor del AUTH TOKEN KEY guardado al hacer el Login
        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("AUTHTOKENKEY", Context.MODE_PRIVATE);
        String authTokenValue = sharedPref.getString("AuthTokenKey", GlobalInfo.AUTH_TOKEN);
        authValue = authTokenValue;

        request = Volley.newRequestQueue(getContext());

        cargarWebService();

        return vista;
    }

    //Con este metodo hago la conexion con el web service
    private void cargarWebService() {

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, GlobalInfo.URL_PUNTOS_DE_MEDIDA, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                PuntosDeMedida pdm = null;
                JSONArray json = response.optJSONArray("result");
                try {
                    for (int i = 0; i < json.length(); i++) {
                        pdm = new PuntosDeMedida();
                        JSONObject jsonObject = null;
                        jsonObject = json.getJSONObject(i);
                        pdm.setName(jsonObject.optString("name"));
                        pdm.setCups(jsonObject.optString("cups"));
                        pdm.setId_client(jsonObject.optInt("id_client"));
                        //pdm.setHas_monit(jsonObject.optInt("has_monit"));
                        listaPuntosDeMedida.add(pdm);
                    }

                    PuntosDeMedidaAdapter adapter = new PuntosDeMedidaAdapter(listaPuntosDeMedida);
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
        mRecyclerView = view.findViewById(R.id.recyclerviewPsum);
    }
}