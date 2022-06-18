package com.example.appmovilmc2firebase.ui.estadoMedidores;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
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
import com.example.appmovilmc2firebase.adaptadores.EstadoDeMedidoresAdapter;
import com.example.appmovilmc2firebase.models.PuntosDeMedida;
import com.example.appmovilmc2firebase.utils.GlobalInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import appmovilmc2firebase.R;

;

public class EstadomedidoresFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "EstadoDeMedidoresFragment";

    private EstadoDeMedidoresAdapter estadoDeMedidoresAdapter;
    private RecyclerView mRecyclerView;

    private ArrayList<PuntosDeMedida> listaPuntosDeMedidas;

    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    private String authValue = "";

    private ArrayList<Integer> idClientList;
    private Integer idclient = 00;
    private Integer idclientjson = 00;

    public TableRow mDataEstadoMedidoresTable;

    public EstadomedidoresFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                idClientList = result.getIntegerArrayList("id_client");
                Log.e(TAG, idClientList.toString());
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @NonNull Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_estadomedidores, container, false);

        listaPuntosDeMedidas = new ArrayList<>();
        idClientList = new ArrayList<>();

        mRecyclerView = (RecyclerView) vista.findViewById(R.id.recyclerviewEstadoDeMedidores);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setHasFixedSize(true);

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
                                    listaPuntosDeMedidas.add(pdm);
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
                estadoDeMedidoresAdapter = new EstadoDeMedidoresAdapter(getContext(), listaPuntosDeMedidas);
                mRecyclerView.setAdapter(estadoDeMedidoresAdapter);
                mRecyclerView.setClickable(true);
                estadoDeMedidoresAdapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = listaPuntosDeMedidas.get(mRecyclerView.getChildAdapterPosition(v)).getName();
                        Toast.makeText(getContext(), "ID del elemento seleccionado: " + name.toString(), Toast.LENGTH_LONG).show();
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
        };
        request.add(jsonObjectRequest);
    }

    @Override
    public void onClick(View v) {


    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.recyclerviewEstadoDeMedidores);
    }

    private void showError(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Error");
        builder.setMessage("Se ha producido un error al obtener respuesta del servidor. " + s);
        builder.setPositiveButton("Aceptar", null);
        builder.create();
        builder.show();
    }

    private void showErrorID(String s, String ss) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Error");
        builder.setMessage("NO HAY COINCIDENCIA EN EL ATRIBUTO ID_CLIENT. " + s + ", " + ss);
        builder.setPositiveButton("Aceptar", null);
        builder.create();
        builder.show();
    }

}