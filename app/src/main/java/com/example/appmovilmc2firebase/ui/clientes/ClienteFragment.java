package com.example.appmovilmc2firebase.ui.clientes;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appmovilmc2firebase.adaptadores.ClientesAdapter;
import com.example.appmovilmc2firebase.interfaces.iComunicaFragments;
import com.example.appmovilmc2firebase.models.Client;
import com.example.appmovilmc2firebase.utils.GlobalInfo;
import com.example.appmovilmc2firebase.utils.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import appmovilmc2firebase.R;

public class ClienteFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "ClienteFragment";

    private ClientesAdapter clientesAdapter;
    private RecyclerView mRecyclerView;

    private ArrayList<Client> listaClients;

    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    private PreferenceHelper preferenceHelper;
    private String authValue = "";

    private int typeUser = 00;

    private Integer idPartner = 00;
    private Integer idClient = 00;

    private ArrayList<Integer> idClientList;

    //referencia para comunicar fragments
    Activity activity;
    iComunicaFragments interfaceComunicaFragments;

    public ClienteFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_client, container, false);

        listaClients = new ArrayList<>();
        idClientList = new ArrayList<>();

        mRecyclerView = vista.findViewById(R.id.recyclerviewClient);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setHasFixedSize(true);

        preferenceHelper = new PreferenceHelper(this.getActivity());
        //Convierto la variable id_pt_user obtenida en el login y guardada con el shared preferences como String a Int.
        typeUser = Integer.parseInt(preferenceHelper.getType());

        //Leo el valor del AUTH TOKEN KEY guardado al hacer el Login
        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("AUTHTOKENKEY", Context.MODE_PRIVATE);
        String authTokenValue = sharedPref.getString("AuthTokenKey", GlobalInfo.AUTH_TOKEN);
        authValue = authTokenValue;

        request = Volley.newRequestQueue(getContext());

        cargarDatos();

        return vista;
    }

    //Con este metodo hago la conexion con el web service
    private void cargarDatos() {

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, GlobalInfo.URL_CLIENT, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Client cl = null;

                preferenceHelper = new PreferenceHelper(getActivity());

                JSONArray json = response.optJSONArray("result");

                idPartner = Integer.parseInt(preferenceHelper.getIdPartner());

                try {
                    for (int i = 0; i < json.length(); i++) {
                        if (idPartner.equals(json.getJSONObject(i).getInt("id_partner"))) {
                            cl = new Client();
                            JSONObject jsonObject = null;
                            jsonObject = json.getJSONObject(i);
                            cl.setName(jsonObject.optString("name"));
                            cl.setId_fiscal(jsonObject.optString("id_fiscal"));
                            //cl.setLast_access(jsonObject.optInt("last_access"));
                            idClient = cl.setId_client(jsonObject.optInt("id_client"));
                            listaClients.add(cl);
                            idClientList.add(idClient);
                        } else {
                            Log.e(TAG, "NO HAY COINCIDENCIA EN EL ATRIBUTO ID_PARTNER. " + idPartner + ", " + json.getJSONObject(i).getInt("id_partner"));
                        }
                    }

                    for (int i = 0; i < idClientList.size(); i++) {
                        Log.e(TAG, String.valueOf(idClientList));
                    }

                    //Paso una lista con los id_clientes que tiene el partner de este Fragment a los Fragment EstadoDeMedidoresFragment y a PuntosDeMedidaFragment
                    //Crear bundle, que son los datos que pasaremos
                    Bundle datosAEnviar = new Bundle();
                    // Aquí pongo todos los datos que quiera en formato clave, valor
                    datosAEnviar.putIntegerArrayList("id_client", idClientList);
                    getParentFragmentManager().setFragmentResult("key", datosAEnviar);
                    Log.e(TAG, datosAEnviar.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                    showError(e.toString());
                }


                mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                mRecyclerView.setHasFixedSize(true);
                clientesAdapter = new ClientesAdapter(getContext(),listaClients);
                mRecyclerView.setAdapter(clientesAdapter);
                mRecyclerView.setClickable(true);
                clientesAdapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = listaClients.get(mRecyclerView.getChildAdapterPosition(v)).getName();
                        //Toast.makeText(getContext(), "Name del elemento seleccionado: " + name.toString(), Toast.LENGTH_LONG).show();
                        interfaceComunicaFragments.enviarCliente(listaClients.get(mRecyclerView.getChildAdapterPosition(v))); //aqui envio to do el objeto
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
        mRecyclerView = view.findViewById(R.id.recyclerviewClient);
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