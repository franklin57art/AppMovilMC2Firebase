package com.example.appmovilmc2firebase.ui.usuarios;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appmovilmc2firebase.R;
import com.example.appmovilmc2firebase.adaptadores.UserAdapter;
import com.example.appmovilmc2firebase.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/*Fragment donde mostraremos nuestro RecyclerView con la lista de usuarios con los datos para la configuracion de sus parametros configurables.*/

public class ConfiguracionFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    private static final String TAG = "UsuariosFragment";

    RecyclerView mRecyclerView;
    ArrayList<User> listaUsuers;

    ProgressDialog progress;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    public UsuariosFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_usuarios, container, false);

        listaUsuers = new ArrayList<>();

        mRecyclerView = (RecyclerView) vista.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setHasFixedSize(true);

        request = Volley.newRequestQueue(getContext());

        cargarWebService();

        return vista;
    }

    //Con este metodo hago la conexion con el web service
    private void cargarWebService() {

        progress = new ProgressDialog(getContext());
        progress.setMessage("Consultando...");
        progress.show();

        String url = "http://192.168.1.62/pt__users?select=*";

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "No se puede conectar " + error.toString(), Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d(TAG, "ERROR: "+ error.toString());
        progress.hide();
    }

    @Override
    public void onResponse(JSONObject response) {
        User user = null;

        JSONArray json = response.optJSONArray("result");

        try {
            for (int i = 0; i < json.length(); i++) {
                user = new User();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);
                user.setName(jsonObject.optString("name"));
                user.setUsername(jsonObject.optString("username"));
                user.setEmail(jsonObject.optString("email"));
                user.setPassword(jsonObject.optString("password"));
                user.setBirthday(jsonObject.optString("birthday"));
                user.setTelefono(jsonObject.optInt("telefono"));
                listaUsuers.add(user);
            }
            progress.hide();
            UserAdapter adapter = new UserAdapter(listaUsuers);
            mRecyclerView.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "No se ha podido establecer conexion con el servidor " + response.toString(), Toast.LENGTH_LONG).show();
            progress.hide();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.recyclerview);
    }
}
