package com.example.appmovilmc2firebase.ui.puntosDeMedida;

import android.app.Activity;
import android.app.AlertDialog;
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
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appmovilmc2firebase.adaptadores.PuntosDeMedidaAdapter;
import com.example.appmovilmc2firebase.interfaces.iComunicaFragments;
import com.example.appmovilmc2firebase.models.PuntosDeMedida;
import com.example.appmovilmc2firebase.utils.GlobalInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import appmovilmc2firebase.R;

public class PuntosmedidaFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "PuntosMedidaFragment";

    private PuntosDeMedidaAdapter puntoDeMedidaAdapter;
    private RecyclerView mRecyclerView;

    private ArrayList<PuntosDeMedida> listaPuntosDeMedida;

    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    private String authValue = "";

    private int typeUser = 00;

    private Integer idclient = 00;
    private Integer idclientjson = 00;

    private ArrayList<Integer> idClientList;

    //referencia para comunicar fragments
    Activity activity;
    iComunicaFragments interfaceComunicaFragments;

    public PuntosmedidaFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                idClientList = result.getIntegerArrayList("id_client");
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @NonNull Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_puntosdemedida, container, false);

        listaPuntosDeMedida = new ArrayList<>();
        idClientList = new ArrayList<>();

        mRecyclerView = (RecyclerView) vista.findViewById(R.id.recyclerviewPsum);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.hasFixedSize();

        //Leo el valor del AUTH TOKEN KEY guardado al hacer el Login
        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("AUTHTOKENKEY", Context.MODE_PRIVATE);
        String authTokenValue = sharedPref.getString("AuthTokenKey", GlobalInfo.AUTH_TOKEN);
        authValue = authTokenValue;

        request = Volley.newRequestQueue(getContext());

        cargarDatos();

        return vista;
    }

    //Con este metodo hago la conexion con el web service
    public void cargarDatos() {

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, GlobalInfo.URL_PUNTOS_DE_MEDIDA, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                PuntosDeMedida pdm = null;

                JSONArray json = response.optJSONArray("result");

                try {
                    for (int a = 0; a < idClientList.size(); a++) {
                        for (int i = 0; i < json.length(); i++) {
                            idclient = idClientList.get(a);
                            Log.e(TAG, String.valueOf(idclient));
                            idclientjson = json.getJSONObject(i).getInt("id_client");
                            Log.e(TAG, String.valueOf(idclientjson));
                            try {
                                if (idclient.equals(idclientjson)) {
                                    pdm = new PuntosDeMedida();
                                    JSONObject jsonObject = null;
                                    jsonObject = json.getJSONObject(i);
                                    pdm.setName(jsonObject.optString("name"));
                                    pdm.setCups(jsonObject.optString("cups"));
                                    pdm.setId_client(jsonObject.optInt("id_client"));
                                    //pdm.setHas_monit(jsonObject.optInt("has_monit"));
                                    listaPuntosDeMedida.add(pdm);
                                } else {
                                    Log.d(TAG, "NO HAY COINCIDENCIA EN EL ATRIBUTO ID_CLIENT. " + idclient + " Y " + idclientjson);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                showError(e.toString());
                            }
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    showError(e.toString());
                }

                mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                mRecyclerView.setHasFixedSize(true);
                puntoDeMedidaAdapter = new PuntosDeMedidaAdapter(getContext(), listaPuntosDeMedida);
                mRecyclerView.setAdapter(puntoDeMedidaAdapter);
                mRecyclerView.setClickable(true);
                puntoDeMedidaAdapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = listaPuntosDeMedida.get(mRecyclerView.getChildAdapterPosition(v)).getName();
                        //Toast.makeText(getContext(), "Name del elemento seleccionado: " + name.toString(), Toast.LENGTH_LONG).show();
                        interfaceComunicaFragments.enviarPuntoDeMedida(listaPuntosDeMedida.get(mRecyclerView.getChildAdapterPosition(v))); //aqui envio to do el objeto
                    }
                });
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
        }

        ;
        request.add(jsonObjectRequest);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.activity = (Activity) context;
            interfaceComunicaFragments = (iComunicaFragments) this.activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.recyclerviewPsum);
    }

    private void showError(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Error");
        builder.setMessage("Se ha producido un error al obtener respuesta del servidor. " + s);
        builder.setPositiveButton("Aceptar", null);
        builder.create();
        builder.show();
    }
}