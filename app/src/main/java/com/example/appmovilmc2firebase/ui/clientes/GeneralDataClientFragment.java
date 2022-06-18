package com.example.appmovilmc2firebase.ui.clientes;

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
import com.example.appmovilmc2firebase.adaptadores.GeneralDataClientesAdapter;
import com.example.appmovilmc2firebase.models.Client;
import com.example.appmovilmc2firebase.utils.GlobalInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import appmovilmc2firebase.R;

public class GeneralDataClientFragment extends Fragment {

    private static final String TAG = "ClientesGeneralDataFragment";

    private RecyclerView mRecyclerView;
    private ArrayList<Client> listaClients;

    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    private String authValue = "";

    private List<String> nameClientList;
    private String nameclient = "";


    public GeneralDataClientFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_general_data_clientes, container, false);

        listaClients = new ArrayList<>();
        nameClientList = new ArrayList<>();

        mRecyclerView = (RecyclerView) vista.findViewById(R.id.recyclerviewCallGeneralDataCliente);
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

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, GlobalInfo.URL_CLIENT, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Client cl = null;

                JSONArray json = response.optJSONArray("result");

                nameClientList.size();
                Log.e(TAG,nameClientList.toString());

                try {
                    for (int i = 0; i < json.length(); i++) {
                        cl = new Client();
                        JSONObject jsonObject = null;
                        jsonObject = json.getJSONObject(i);
                        cl.setName(jsonObject.optString("name"));
                        cl.setId_fiscal(jsonObject.optString("id_fiscal"));
                        cl.setNombre_empresa(jsonObject.optString("nombre_empresa"));
                        cl.setNombre_cliente(jsonObject.optString("nombre_cliente"));
                        cl.setId_tecnico(jsonObject.optInt("id_tecnico"));
                        listaClients.add(cl);
                    }
                    GeneralDataClientesAdapter adapter = new GeneralDataClientesAdapter(listaClients, this);
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
        mRecyclerView = view.findViewById(R.id.recyclerviewCallGeneralDataCliente);
    }

}