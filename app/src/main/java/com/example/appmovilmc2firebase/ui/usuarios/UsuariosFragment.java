package com.example.appmovilmc2firebase.ui.usuarios;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
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
import com.example.appmovilmc2firebase.adaptadores.UserDataAdapter;
import com.example.appmovilmc2firebase.models.User;
import com.example.appmovilmc2firebase.utils.GlobalInfo;
import com.example.appmovilmc2firebase.utils.PreferenceHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import appmovilmc2firebase.R;

public class UsuariosFragment extends Fragment implements SearchView.OnQueryTextListener {

    private static final String TAG = "UsuariosFragment";

    private SearchView mSearchView;
    private FloatingActionButton mAddUserButton;
    private RecyclerView mRecyclerView;
    private UserDataAdapter adapter;

    private ArrayList<User> listaUsers;

    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    private PreferenceHelper preferenceHelper;

    private String authValue = "";

    private int typeUser = 00;

    public UsuariosFragment() {

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_usuarios,  container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSearchView = view.findViewById(R.id.txtBuscar);
        mRecyclerView = view.findViewById(R.id.item_row_usuarios);
        mAddUserButton = view.findViewById(R.id.favNuevo);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        listaUsers = new ArrayList<>();

        preferenceHelper = new PreferenceHelper(getContext());
        //Convierto la variable id_pt_user obtenida en el login y guardada con el shared preferences como String a Int.
        typeUser = Integer.parseInt(preferenceHelper.getType());

        //Leo el valor del AUTH TOKEN KEY guardado al hacer el Login
        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("AUTHTOKENKEY", Context.MODE_PRIVATE);
        String authTokenValue = sharedPref.getString("AuthTokenKey", GlobalInfo.AUTH_TOKEN);
        authValue = authTokenValue;

        request = Volley.newRequestQueue(getContext());

        cargarWebService();

        mAddUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RegisterUserActivity.class);
                startActivity(intent);
            }
        });

        mSearchView.setOnQueryTextListener(this);
    }

    //Con este metodo hago la conexion con el web service
    private void cargarWebService() {

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, GlobalInfo.URL_USER, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                User user = null;

                preferenceHelper = new PreferenceHelper(getContext());

                JSONArray json = response.optJSONArray("result");

                //Guardo en una variable de tipo entero el valor de la variable type de usuario obtenida al hacer login con este y guardada con el shared preferences. Como es un String la convierto a Int.
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
                            String.valueOf(user.setType(jsonObject.optInt("type")));
                            listaUsers.add(user);
                        }

                        adapter = new UserDataAdapter(listaUsers);
                        mRecyclerView.setAdapter(adapter);

                    } else {
                        Log.e(TAG, "EL TYPE DEL USUARIO NO ES DE TIPO PARTNER ADMINISTRADOR. Es un type tipo: " + typeUser);
                        showAlert();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "No se ha podido establecer conexion con el servidor " + response.toString(), Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "No se puede conectar " + error.toString(), Toast.LENGTH_LONG).show();
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

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Error");
        builder.setMessage("No es usted un usuario ADMMINISTRADOR. No tiene permiso de escritura");
        builder.setPositiveButton("Aceptar", null);
        builder.create();
        builder.show();
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filtrado(newText);
        return false;
    }
}
