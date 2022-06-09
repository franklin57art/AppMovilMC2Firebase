package com.example.appmovilmc2firebase.ui.usuarios;

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
import com.example.appmovilmc2firebase.GlobalInfo;
import com.example.appmovilmc2firebase.PreferenceHelper;
import com.example.appmovilmc2firebase.adaptadores.UserAdapter;
import com.example.appmovilmc2firebase.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import appmovilmc2firebase.R;

public class UsuariosFragment extends Fragment {

    private static final String TAG = "UsuariosFragment";

    private RecyclerView mRecyclerView;
    private ArrayList<User> listaUsers;

    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    private PreferenceHelper preferenceHelper;

    private String authValue = "";

    private int typeUser = 00;

    public UsuariosFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @NonNull Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_usuarios, container, false);

        listaUsers = new ArrayList<>();

        mRecyclerView = (RecyclerView) vista.findViewById(R.id.recyclerviewUsuarios);
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

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, GlobalInfo.URL_USER, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                User user = null;

                preferenceHelper = new PreferenceHelper(getActivity());

                JSONArray json = response.optJSONArray("result");

                //Convierto la variable id_pt_user obtenida en el login y guardada con el shared preferences como String a Int.
                typeUser = Integer.parseInt(preferenceHelper.getType());

                try {
                    if (typeUser == 7) {
                        for (int i = 0; i < json.length(); i++) {
                            //Compruebo si hay algun valor type dentro del json igual al valor de un usuario de tipo Administrador. En este caso ese calor es 7
                            user = new User();
                            JSONObject jsonObject = null;
                            jsonObject = json.getJSONObject(i);
                            user.setName(jsonObject.optString("name"));
                            user.setUsername(jsonObject.optString("username"));
                            user.setType(jsonObject.optInt("type"));
                            listaUsers.add(user);
                        }
                        UserAdapter adapter = new UserAdapter(listaUsers);
                        mRecyclerView.setAdapter(adapter);
                    } else {
                        Log.e(TAG, "EL TYPE DEL USUARIO NO ES DE TIPO PARTNER ADMINISTRADOR. Es un type tipo: " + typeUser);
                        showAlert();
                    }
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
        mRecyclerView = view.findViewById(R.id.recyclerviewUsuarios);
    }

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Error");
        builder.setMessage("No es usted un usuario ADMMINISTRADOR");
        builder.setPositiveButton("Aceptar", null);
        builder.create();
        builder.show();
    }
}
