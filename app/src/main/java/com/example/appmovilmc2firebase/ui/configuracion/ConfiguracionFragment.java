package com.example.appmovilmc2firebase.ui.configuracion;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
import com.example.appmovilmc2firebase.PreferenceHelper;
import com.example.appmovilmc2firebase.R;
import com.example.appmovilmc2firebase.adaptadores.ConfigUserAdapter;
import com.example.appmovilmc2firebase.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*Fragment donde mostraremos nuestro RecyclerView con la lista de usuarios con los datos para la configuracion de sus parametros configurables.*/

public class ConfiguracionFragment extends Fragment {

    private static final String TAG = "ConfiguracionFragment";

    private RecyclerView mRecyclerView;
    private ArrayList<User> listaUsuers;

    private ProgressDialog progress;

    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    private PreferenceHelper preferenceHelper;

    private String authValue = "";

    private int idPtUser = 00;

    public ConfiguracionFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_configuracion, container, false);

        listaUsuers = new ArrayList<>();

        mRecyclerView = (RecyclerView) vista.findViewById(R.id.recyclerviewConfiguracion);
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

        progress = new ProgressDialog(getContext());
        progress.setMessage("Consultando...");
        progress.show();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, GlobalInfo.URL_USER, null, new Response.Listener<JSONObject>() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {
                User user = null;

                preferenceHelper = new PreferenceHelper(getActivity());

                JSONArray json = response.optJSONArray("result");

                String passwordDecrpyt = null;

                //Convierto la variable id_pt_user obtenida en el login y guardada con el shared preferences como String a Int.
                idPtUser = Integer.parseInt(preferenceHelper.getIdPtUser());

                try {
                    for (int i = 0; i < json.length(); i++) {
                        //Compruebo si hay algun valor id_pt_user dentro del json igual al valor del usuario logeado que hemos guardado con el shared preferences
                        if (json.getJSONObject(i).getInt("id_pt_user") == idPtUser) {
                            user = new User();
                            JSONObject jsonObject = null;
                            jsonObject = json.getJSONObject(i);
                            user.setName(jsonObject.optString("name"));
                            user.setUsername(jsonObject.optString("username"));
                            user.setEmail(jsonObject.optString("email"));
                            user.setPassword(jsonObject.optString("password"));
                            user.setType(jsonObject.optInt("type"));
                            user.setTelefono(jsonObject.optString("telefono"));
                            listaUsuers.add(user);
                        } else {
                            Log.e(TAG, "NO HAY COINCIDENCIA EN LOS ID_PT_USER");
                        }
                    }

                    progress.hide();
                    ConfigUserAdapter adapter = new ConfigUserAdapter(listaUsuers);
                    mRecyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "No se ha podido establecer conexion con el servidor " + response.toString(), Toast.LENGTH_LONG).show();
                    progress.hide();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "No se ha podido desencriptar la contraseña " + response.toString(), Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "No se puede conectar " + error.toString(), Toast.LENGTH_LONG).show();
                        System.out.println();
                        Log.d(TAG, "ERROR: " + error.toString());
                        progress.hide();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.recyclerviewConfiguracion);
    }
}
