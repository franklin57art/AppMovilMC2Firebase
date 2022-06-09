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
import com.example.appmovilmc2firebase.GlobalInfo;
import com.example.appmovilmc2firebase.PreferenceHelper;
import com.example.appmovilmc2firebase.adaptadores.ConfigUserAdapter;
import com.example.appmovilmc2firebase.adaptadores.ConfigUserAdapterPartner;
import com.example.appmovilmc2firebase.models.Partner;
import com.example.appmovilmc2firebase.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

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

    private int idPtUser = 00;
    private int idPartner = 00;

    public ConfiguracionFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_configuracion, container, false);

        listaUsuers = new ArrayList<>();
        listaPartners = new ArrayList<>();

        mRecyclerViewPartner = (RecyclerView) vista.findViewById(R.id.recyclerviewConfiguracionPartner);
        mRecyclerViewPartner.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerViewPartner.setHasFixedSize(true);

        mRecyclerViewUser = (RecyclerView) vista.findViewById(R.id.recyclerviewConfiguracionUser);
        mRecyclerViewUser.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerViewUser.setHasFixedSize(true);

        //Leo el valor del AUTH TOKEN KEY guardado al hacer el Login
        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("AUTHTOKENKEY", Context.MODE_PRIVATE);
        String authTokenValue = sharedPref.getString("AuthTokenKey", GlobalInfo.AUTH_TOKEN);
        authValue = authTokenValue;

        request = Volley.newRequestQueue(getContext());

        cargarWebServicePartner();
        cargarWebServiceUser();

        return vista;
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
                        if (json.getJSONObject(i).getInt("id_partner") == idPartner) {
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

                    ConfigUserAdapterPartner adapter = new ConfigUserAdapterPartner(listaPartners);
                    mRecyclerViewPartner.setAdapter(adapter);

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
                            String decrypt = decrypt(jsonObject.optString("password"), "oW%c76+jb2");
                            user.setPassword(decrypt);
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerViewUser = view.findViewById(R.id.recyclerviewConfiguracionUser);
        mRecyclerViewPartner = view.findViewById(R.id.recyclerviewConfiguracionPartner);
    }

    private static String CIPHER_NAME = "AES/CBC/PKCS5PADDING";
    private static int CIPHER_KEY_LEN = 16; //128 bits

    /**
     * Encrypt data using AES Cipher (CBC) with 128 bit key
     *
     *
     * @param key  - key to use should be 16 bytes long (128 bits)
     * @param iv - initialization vector
     * @param data - data to encrypt
     * @return encryptedData data in base64 encoding with iv attached at end after a :
     */
    public static String encrypt(String key, String iv, String data) {
        try {
            if (key.length() < ConfiguracionFragment.CIPHER_KEY_LEN) {
                int numPad = ConfiguracionFragment.CIPHER_KEY_LEN - key.length();

                for(int i = 0; i < numPad; i++){
                    key += "0"; //0 pad to len 16 bytes
                }

            } else if (key.length() > ConfiguracionFragment.CIPHER_KEY_LEN) {
                key = key.substring(0, CIPHER_KEY_LEN); //truncate to 16 bytes
            }


            IvParameterSpec initVector = new IvParameterSpec(iv.getBytes("ISO-8859-1"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("ISO-8859-1"), "AES");

            Cipher cipher = Cipher.getInstance(ConfiguracionFragment.CIPHER_NAME);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, initVector);

            byte[] encryptedData = cipher.doFinal((data.getBytes()));

            String base64_EncryptedData = new String(Base64.getEncoder().encodeToString(encryptedData));
            String base64_IV = new String(Base64.getEncoder().encodeToString(iv.getBytes("ISO-8859-1")));

            return base64_EncryptedData + ":" + base64_IV;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * Decrypt data using AES Cipher (CBC) with 128 bit key
     *
     * @param key - key to use should be 16 bytes long (128 bits)
     * @param data - encrypted data with iv at the end separate by :
     * @return decrypted data string
     */

    public static String decrypt(String key, String data) {
        try {

            if (key.length() < ConfiguracionFragment.CIPHER_KEY_LEN) {
                int numPad = ConfiguracionFragment.CIPHER_KEY_LEN - key.length();

                for(int i = 0; i < numPad; i++){
                    key += "0"; //0 pad to len 16 bytes
                }

            } else if (key.length() > ConfiguracionFragment.CIPHER_KEY_LEN) {
                key = key.substring(0, CIPHER_KEY_LEN); //truncate to 16 bytes
            }

            String[] parts = data.split(":");

            IvParameterSpec iv = new IvParameterSpec(Base64.getDecoder().decode(parts[1]));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("ISO-8859-1"), "AES");

            Cipher cipher = Cipher.getInstance(ConfiguracionFragment.CIPHER_NAME);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] decodedEncryptedData = Base64.getDecoder().decode(parts[0]);

            byte[] original = cipher.doFinal(decodedEncryptedData);

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
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
