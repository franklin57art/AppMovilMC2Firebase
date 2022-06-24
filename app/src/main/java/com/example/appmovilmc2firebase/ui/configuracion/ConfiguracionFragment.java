package com.example.appmovilmc2firebase.ui.configuracion;


import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.example.appmovilmc2firebase.adaptadores.ConfigUserAdapter;
import com.example.appmovilmc2firebase.adaptadores.ConfigUserAdapterPartner;
import com.example.appmovilmc2firebase.models.Partner;
import com.example.appmovilmc2firebase.models.User;
import com.example.appmovilmc2firebase.utils.GlobalInfo;
import com.example.appmovilmc2firebase.utils.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import appmovilmc2firebase.R;

/*Fragment donde mostraremos nuestro RecyclerView con la lista de usuarios con los datos para la configuracion de sus parametros configurables.*/

public class ConfiguracionFragment extends Fragment {

    private static final String TAG = "ConfiguracionFragment";

    private RecyclerView mRecyclerViewUser, mRecyclerViewPartner;
    private ArrayList<User> listaUsuers;
    private ArrayList<Partner> listaPartners;

    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest, jsonObjectRequestPartner;

    private PreferenceHelper preferenceHelper;

    private String authValue = "";

    private Integer idPtUser = 00;
    private Integer idPartner = 00;

    public ConfiguracionFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_configuracion, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listaUsuers = new ArrayList<>();
        listaPartners = new ArrayList<>();

        mRecyclerViewPartner = (RecyclerView) view.findViewById(R.id.recyclerviewConfiguracionPartner);
        mRecyclerViewPartner.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerViewPartner.setHasFixedSize(true);

        mRecyclerViewUser = (RecyclerView) view.findViewById(R.id.recyclerviewConfiguracionUser);
        mRecyclerViewUser.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerViewUser.setHasFixedSize(true);

        //Leo el valor del AUTH TOKEN KEY guardado al hacer el Login
        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("AUTHTOKENKEY", Context.MODE_PRIVATE);
        String authTokenValue = sharedPref.getString("AuthTokenKey", GlobalInfo.AUTH_TOKEN);
        authValue = authTokenValue;

        request = Volley.newRequestQueue(getContext());

        cargarWebServicePartner();
        cargarWebServiceUser();
    }

    private void cargarWebServicePartner() {

        jsonObjectRequestPartner = new JsonObjectRequest(Request.Method.GET, GlobalInfo.URL_PARTNER, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Partner partner = null;

                preferenceHelper = new PreferenceHelper(getActivity());

                JSONArray json = response.optJSONArray("result");

                //Convierto la variable id_partner obtenida en el login y guardada con el shared preferences como String a Int.
                idPartner = Integer.parseInt(preferenceHelper.getIdPartner());

                try {
                    for (int i = 0; i < json.length(); i++) {
                        //Compruebo si hay algun valor id_partner dentro del json igual al valor del usuario logeado que hemos guardado con el shared preferences
                        if (idPartner.equals(json.getJSONObject(i).getInt("id_partner"))) {
                            partner = new Partner();
                            JSONObject jsonObject = null;
                            jsonObject = json.getJSONObject(i);
                            partner.setRazon_social(jsonObject.optString("razon_social"));
                            partner.setName(jsonObject.optString("name"));
                            listaPartners.add(partner);
                        } else {
                            Log.e(TAG, "NO HAY COINCIDENCIA EN EL ATRIBUTO ID_PARTNER. " + idPartner + ", " + json.getJSONObject(i).getInt("id_partner"));
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    showError(e.toString());
                }

                ConfigUserAdapterPartner adapter = new ConfigUserAdapterPartner(listaPartners);
                mRecyclerViewPartner.setAdapter(adapter);
            }
        },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showError(error.toString());
                        Log.e(TAG, "ERROR: " + error.toString());
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
        request.add(jsonObjectRequestPartner);

    }

    private void cargarWebServiceUser() {

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, GlobalInfo.URL_USER, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                User user = null;

                preferenceHelper = new PreferenceHelper(getActivity());

                JSONArray json = response.optJSONArray("result");

                //Convierto la variable id_pt_user obtenida en el login y guardada con el shared preferences como String a Int.
                idPtUser = Integer.parseInt(preferenceHelper.getIdPtUser());

                try {
                    for (int i = 0; i < json.length(); i++) {
                        //Compruebo si hay algun valor id_pt_user en la respuesta del json que sea igual al valor del usuario logeado que hemos guardado con el shared preferences
                        if (json.getJSONObject(i).getInt("id_pt_user") == idPtUser) {
                            Log.e(TAG, String.valueOf(json.getJSONObject(i).getInt("id_pt_user")));
                            user = new User();
                            JSONObject jsonObject = null;
                            jsonObject = json.getJSONObject(i);
                            user.setName(jsonObject.optString("name"));
                            user.setUsername(jsonObject.optString("username"));
                            user.setEmail(jsonObject.optString("email"));
                            //Guardo en una variable la contraseña del usuario logeado que viene en la respuesta.
                            //String contrasenia = json.getJSONObject(i).getString("password");
                            //System.out.println("CONTRASEÑA: "+contrasenia);
                            //Desencripto la contraseña guardada en la variable con el mismo metodo usado para encriptar en php
                            //String decrypt = Java_AES_Cipher.decrypt(Java_AES_Cipher.CIPHER_KEY,contrasenia);
                            //System.out.println("DESENCRIPTAR CONTRASEÑA: "+decrypt);
                            //Guardo en la contraseña desencriptada en el nuevo usuario vacio.
                            user.setPassword(jsonObject.optString("password"));
                            user.setType(jsonObject.optInt("type"));
                            listaUsuers.add(user);
                        } else {
                            Log.e(TAG, "NO HAY COINCIDENCIA EN EL ATRIBUTO ID_PT_USER. " + idPtUser + ", " + json.getJSONObject(i).getInt("id_pt_user"));
                        }
                    }

                    ConfigUserAdapter adapter = new ConfigUserAdapter(listaUsuers);
                    mRecyclerViewUser.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    showError(e.toString());
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showError(error.toString());
                        Log.e(TAG, "ERROR: " + error.toString());
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Error");
        builder.setMessage("Se ha producido un error al obtener respuesta del servidor. "+s);
        builder.setPositiveButton("Aceptar", null);
        builder.create();
        builder.show();
    }
}
