package com.example.appmovilmc2firebase.ui.puntosDeMedida;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appmovilmc2firebase.HomeActivity;
import com.example.appmovilmc2firebase.models.PuntosDeMedida;
import com.example.appmovilmc2firebase.utils.GlobalInfo;
import com.example.appmovilmc2firebase.utils.PreferenceHelper;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import appmovilmc2firebase.R;

public class GeneralDataPuntosmedidaFragment extends Fragment {

    private static final String TAG = "PuntosDeMedidaGeneralDataFragment";

    public Button mDeletePuntoMedidaButton, volver, actualizar;
    public TextInputEditText mNombre, mIdCliente, mDireccion, mCp, mCups, mCupsObras, mAlquiler, mConsumo;
    public AutoCompleteTextView mCliente, mPoblacion, mComercializadora;
    public EditText mEditText;
    private Spinner spinnerPais, spinnerTipoPunto, spinnerTipoPlanta, spinnerModAlq, spinnerAtrPotencia, spinnerZonaTari, spinnerRecargo;
    private ImageView ivSpPais, ivSpTipoPunto, ivSpTipoPlanta, ivSpModAlq, ivSpAtrPot, ivSpZonaTari, ivSpRecargo;

    private ArrayList<PuntosDeMedida> listaPuntosDeMedida;

    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    private Context context;

    private PreferenceHelper preferenceHelper;

    private int typeUser = 00;

    private String authValue = "";

    private String nombrePdm = "";
    private String namepdm = "";
    private Integer idConn = 00;

    private String nombrePuntoMedida = "";
    private Integer idClient = 00;
    private String idPsumClient = "";
    private String address = "";
    private Integer poblacion = 00;
    private String cp = "";
    private String cups = "";
    private String cupsObras = "";
    private Integer idComerc = 00;
    private Float alquilerEquipo = 0.0f;
    private Integer consumoAnual = 00;
    private String description = "";

    public GeneralDataPuntosmedidaFragment() {

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {

        getActivity().setTitle("Datos Punto de medida");

        View vista = inflater.inflate(R.layout.fragment_general_data_puntosmedida, container, false);

        context = this.getContext();

        preferenceHelper = new PreferenceHelper(context);
        //Convierto la variable id_pt_user obtenida en el login y guardada con el shared preferences como String a Int.
        typeUser = Integer.parseInt(preferenceHelper.getType());

        mNombre = vista.findViewById(R.id.tietGeneralDataPuntosDeMedidaNombre);
        mIdCliente = vista.findViewById(R.id.tietGeneralDataPuntosDeMedidaIdCliente);
        mDireccion = vista.findViewById(R.id.tietGeneralDataPuntosDeMedidaDireccion);
        mCp = vista.findViewById(R.id.tietGeneralDataPuntosDeMedidaCp);
        mCups = vista.findViewById(R.id.tietGeneralDataPuntosDeMedidaCups);
        mCupsObras = vista.findViewById(R.id.tietGeneralDataPuntosDeMedidaCupsDeObras);
        mAlquiler = vista.findViewById(R.id.tietGeneralDataPuntosDeMedidaAlquilerEquipos);
        mConsumo = vista.findViewById(R.id.tietGeneralDataPuntosDeMedidaConsumoAnual);
        mCliente = vista.findViewById(R.id.actvGeneralDataPuntosDeMedidaCliente);
        mPoblacion = vista.findViewById(R.id.actvGeneralDataPuntosDeMedidaPoblacion);
        mComercializadora = vista.findViewById(R.id.actvGeneralDataPuntosDeMedidaComercializadora);
        mEditText = vista.findViewById(R.id.editText);

        spinnerPais = vista.findViewById(R.id.spinnerGeneralDataPuntosDeMedidaPais);
        spinnerTipoPunto = vista.findViewById(R.id.spinnerGeneralDataPuntosDeMedidaTipoDePunto);
        spinnerTipoPlanta = vista.findViewById(R.id.spinnerGeneralDataPuntosDeMedidaTipoDePlanta);
        spinnerModAlq = vista.findViewById(R.id.spinnerGeneralDataPuntosDeMedidaModoAlq);
        spinnerAtrPotencia = vista.findViewById(R.id.spinnerGeneralDataPuntosDeMedidaModoATRPotencia);
        spinnerZonaTari = vista.findViewById(R.id.spinnerGeneralDataPuntosDeMedidaZonaTarifaria);
        spinnerRecargo = vista.findViewById(R.id.spinnerGeneralDataPuntosDeMedidaRecargo);

        ivSpPais = vista.findViewById(R.id.ivInfoSpinnerFGDPMPais);
        ivSpTipoPunto = vista.findViewById(R.id.ivInfoSpinnerFGDPMTipoPunto);
        ivSpTipoPlanta = vista.findViewById(R.id.ivInfoSpinnerFGDPMTipoPlanta);
        ivSpModAlq = vista.findViewById(R.id.ivInfoSpinnerFGDPMAlquEquipos);
        ivSpAtrPot = vista.findViewById(R.id.ivInfoSpinnerFGDPMATRPotencia);
        ivSpZonaTari = vista.findViewById(R.id.ivInfoSpinnerFGDPMZonaTarifaria);
        ivSpRecargo = vista.findViewById(R.id.ivInfoSpinnerFGDPMRecargo);

        listaPuntosDeMedida = new ArrayList<>();

        //Leo el valor del AUTH TOKEN KEY guardado al hacer el Login
        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("AUTHTOKENKEY", Context.MODE_PRIVATE);
        String authTokenValue = sharedPref.getString("AuthTokenKey", GlobalInfo.AUTH_TOKEN);
        authValue = authTokenValue;

        request = Volley.newRequestQueue(getContext());

        //Crear objeto bundler para recibir el objetoenviado por argumentos
        Bundle objetoPuntoDeMedida = getArguments();
        PuntosDeMedida puntosDeMedida = null;
        //Validar para verificar si existen argumento enviados para mostrar
        if (objetoPuntoDeMedida != null) {
            puntosDeMedida = (PuntosDeMedida) objetoPuntoDeMedida.getSerializable("objetoPuntoDeMedida");
            //Establecer los datos en las vistas
            nombrePdm = puntosDeMedida.getName();
        }

        ivSpPais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "0. España, 1. Portugal";
                showInfo(s);
            }
        });

        ivSpTipoPunto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String s = "0. Suministro eléctrico, 1. Fotovoltaica, 2. Suministro de gas, 3. Analizador de redes eléctricas, 4. Estación meteorológica";
                showInfo(s);
            }
        });

        ivSpTipoPlanta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "0. Suministro general, 1. Suministro uso industrial, 2. Suministro de riego , 3. Suministro de puerto no privado";
                showInfo(s);
            }
        });

        ivSpModAlq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "1. Mensual, 2. Diario";
                showInfo(s);
            }
        });

        ivSpAtrPot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "1. Mensual, 2. Diario";
                showInfo(s);
            }
        });

        ivSpZonaTari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "1. Península, 2. Baleares, 3. Canarias, 4. Ceuta, 5. Melilla";
                showInfo(s);
            }
        });

        ivSpRecargo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "0. Si 1. No";
                showInfo(s);
            }
        });

        mDeletePuntoMedidaButton = vista.findViewById(R.id.buttonGeneralDataEliminarPuntoDeMedida);
        mDeletePuntoMedidaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeUser == 7) {
                    deletePuntoDeMedida();
                } else {
                    Log.e("TAG", "EL TYPE DEL USUARIO NO ES DE TIPO PARTNER ADMINISTRADOR. Es un type tipo: " + typeUser);
                    showAlert();
                }
            }
        });

        volver = vista.findViewById(R.id.buttonAtrasGeneralDataPuntosDeMedida);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HomeActivity.class);
                context.startActivity(intent);
            }
        });

        actualizar = vista.findViewById(R.id.buttonActualizarGeneralDataPuntosDeMedida);
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeUser == 7) {
                    updateData();
                } else {
                    Log.e("TAG", "EL TYPE DEL USUARIO NO ES DE TIPO PARTNER ADMINISTRADOR. Es un type tipo: " + typeUser);
                    showAlert();
                }
            }
        });

        cargarDatos();

        return vista;

    }

    private void cargarDatosSpinners() {
        // Creo los array de string con los valores del spinner
        String[] arrayPais = getResources().getStringArray(R.array.type_array_punto_de_medida_pais);
        String[] arrayTipoPunto = getResources().getStringArray(R.array.type_array_punto_de_medida_tipo_punto);
        String[] arrayTipoPlanta = getResources().getStringArray(R.array.type_array_punto_de_medida_tipo_planta);
        String[] arrayModAlq = getResources().getStringArray(R.array.type_array_punto_de_medida_mod_alq);
        String[] arrayAtrPot = getResources().getStringArray(R.array.type_array_punto_de_medida_mod_atr_pot);
        String[] arrayZonaTari = getResources().getStringArray(R.array.type_array_punto_de_medida_tar_zone);
        String[] arrayRecargo = getResources().getStringArray(R.array.type_array_punto_de_medida_recargar);

        // Creo un array de enteros que recorrera el array definido anteriormente y va a transformar los String en enteros
        Integer[] intArrayPais = new Integer[arrayPais.length];
        for (int i = 0; i < arrayPais.length; i++) {
            intArrayPais[i] = Integer.parseInt(arrayPais[i]);
        }
        Integer[] intArrayTipoPunto = new Integer[arrayTipoPunto.length];
        for (int i = 0; i < arrayTipoPunto.length; i++) {
            intArrayTipoPunto[i] = Integer.parseInt(arrayTipoPunto[i]);
        }
        Integer[] intArrayTipoPlanta = new Integer[arrayTipoPlanta.length];
        for (int i = 0; i < arrayTipoPlanta.length; i++) {
            intArrayTipoPlanta[i] = Integer.parseInt(arrayTipoPlanta[i]);
        }
        // Creo un array de enteros que recorrera el array definido anteriormente y va a transformar los String en enteros
        Integer[] intArrayModAlq = new Integer[arrayModAlq.length];
        for (int i = 0; i < arrayModAlq.length; i++) {
            intArrayModAlq[i] = Integer.parseInt(arrayModAlq[i]);
        }
        Integer[] intArrayAtrPot = new Integer[arrayAtrPot.length];
        for (int i = 0; i < arrayAtrPot.length; i++) {
            intArrayAtrPot[i] = Integer.parseInt(arrayAtrPot[i]);
        }
        Integer[] intArrayZonaTari = new Integer[arrayZonaTari.length];
        for (int i = 0; i < arrayZonaTari.length; i++) {
            intArrayZonaTari[i] = Integer.parseInt(arrayZonaTari[i]);
        }
        Integer[] intArrayRecargo = new Integer[arrayRecargo.length];
        for (int i = 0; i < arrayRecargo.length; i++) {
            intArrayRecargo[i] = Integer.parseInt(arrayRecargo[i]);
        }

        // Create an ArrayAdapter using the string array and a spinner layout
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this.getActivity(), android.R.layout.simple_spinner_item, intArrayPais);
        ArrayAdapter<Integer> adapter2 = new ArrayAdapter<Integer>(this.getActivity(), android.R.layout.simple_spinner_item, intArrayTipoPunto);
        ArrayAdapter<Integer> adapter3 = new ArrayAdapter<Integer>(this.getActivity(), android.R.layout.simple_spinner_item, intArrayTipoPlanta);
        ArrayAdapter<Integer> adapter4 = new ArrayAdapter<Integer>(this.getActivity(), android.R.layout.simple_spinner_item, intArrayModAlq);
        ArrayAdapter<Integer> adapter5 = new ArrayAdapter<Integer>(this.getActivity(), android.R.layout.simple_spinner_item, intArrayAtrPot);
        ArrayAdapter<Integer> adapter6 = new ArrayAdapter<Integer>(this.getActivity(), android.R.layout.simple_spinner_item, intArrayZonaTari);
        ArrayAdapter<Integer> adapter7 = new ArrayAdapter<Integer>(this.getActivity(), android.R.layout.simple_spinner_item, intArrayRecargo);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinnerPais.setAdapter(adapter);
        spinnerTipoPunto.setAdapter(adapter2);
        spinnerTipoPlanta.setAdapter(adapter3);
        spinnerModAlq.setAdapter(adapter4);
        spinnerAtrPotencia.setAdapter(adapter5);
        spinnerZonaTari.setAdapter(adapter6);
        spinnerRecargo.setAdapter(adapter7);

    }

    private void deletePuntoDeMedida() {

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, GlobalInfo.URL_DELETE_PUNTO_DE_MEDIDA + String.valueOf(idConn),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        request.getCache().clear();
                        Log.d(TAG, response);
                        try {
                            parseData(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showError(error.toString());
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Accept", "*/*");
                headers.put("Authorization", GlobalInfo.AUTH_TOKEN);
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", "no");
                params.put("except", "id_conn");
                params.put("nameID", "id_conn");
                params.put("id", String.valueOf(idConn));
                return params;
            }
        };
        request = Volley.newRequestQueue(getActivity());
        request.add(stringRequest);
    }

    private void parseData(String response) throws JSONException {
        try {
                JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString("success").equals("true")) {
                Toast.makeText(getActivity(), "¡Punto de Medida eliminado con éxito!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
            } else {
                showAlertt();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void cargarDatos() {

        request = Volley.newRequestQueue(getContext());

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, GlobalInfo.URL_PUNTOS_DE_MEDIDA, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                PuntosDeMedida pdm = null;
                Integer idPais = null;
                Integer type = null;
                Integer typePlant = null;
                Integer modAlqEq = null;
                Integer modAtrPot = null;
                Integer tarZone = null;
                Integer rechargeTemp = null;

                JSONArray json = response.optJSONArray("result");
                System.out.println(json);

                try {
                    for (int i = 0; i < json.length(); i++) {
                        if (nombrePdm.equals(json.getJSONObject(i).getString("name")) || namepdm.equals(json.getJSONObject(i).getString("name"))) {
                            pdm = new PuntosDeMedida();
                            JSONObject jsonObject = null;
                            jsonObject = json.getJSONObject(i);
                            idConn = pdm.setId_conn(jsonObject.getInt("id_conn"));
                            nombrePuntoMedida = pdm.setName(jsonObject.optString("name"));
                            idClient = pdm.setId_client(jsonObject.optInt("id_client"));
                            idPsumClient = pdm.setId_psum_for_client(jsonObject.optString("id_psum_for_client"));
                            address = pdm.setAddress(jsonObject.optString("address"));
                            poblacion = pdm.setId_poblacion(jsonObject.optInt("id_poblacion"));
                            cp = pdm.setCod_postal(jsonObject.optString("cod_postal"));
                            cups = pdm.setCups(jsonObject.optString("cups"));
                            cupsObras = pdm.setCups_obras(jsonObject.optString("cups_obras"));
                            idComerc = pdm.setId_comerc(jsonObject.optInt("id_comerc"));
                            alquilerEquipo = pdm.setAlquiler_equipo(jsonObject.optInt("alquiler_equipo"));
                            consumoAnual = pdm.setEstimated_annual_consumption(jsonObject.optInt("estimated_annual_consumption"));
                            description = pdm.setDescription(jsonObject.optString("description"));
                            idPais = pdm.setId_country(jsonObject.optInt("id_country"));
                            type = pdm.setType(jsonObject.optInt("type"));
                            typePlant = pdm.setType_plant(jsonObject.optInt("type_plant"));
                            modAlqEq = pdm.setMod_alq_eq(jsonObject.optInt("modo_alq_eq"));
                            modAtrPot = pdm.setModo_atr_potencia(jsonObject.optInt("modo_atr_potencia"));
                            tarZone = pdm.setTar_zone(jsonObject.optInt("tar_zone"));
                            rechargeTemp = pdm.setRecharge_temp(jsonObject.optInt("recharge_temp"));
                            listaPuntosDeMedida.add(pdm);

                        } else {
                            Log.d(TAG, "NO HAY COINCIDENCIA EN EL ATRIBUTO NAME. " + nombrePuntoMedida + " Y " + json.getJSONObject(i).getString("name"));
                        }
                        mNombre.setText(nombrePuntoMedida);
                        mCliente.setText(String.valueOf(idClient));
                        mIdCliente.setText(idPsumClient);
                        mDireccion.setText(address);
                        mPoblacion.setText(String.valueOf(poblacion));
                        mCp.setText(cp);
                        mCups.setText(cups);
                        mCupsObras.setText(cupsObras);
                        mComercializadora.setText(String.valueOf(idComerc));
                        mAlquiler.setText(String.valueOf(alquilerEquipo));
                        mConsumo.setText(String.valueOf(consumoAnual));
                        mDireccion.setText(description);

                        Integer[] intArrayPais = new Integer[idPais];
                        intArrayPais[0] = idPais;
                        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, intArrayPais);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerPais.setAdapter(adapter);

                        Integer[] intArrayTipo = new Integer[type];
                        if (intArrayTipo.length > 0) {
                            intArrayTipo[0] = type;
                            ArrayAdapter<Integer> adapter2 = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, intArrayTipo);
                            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerTipoPunto.setAdapter(adapter2);
                        }

                            Integer[] intArrayTipoPlanta = new Integer[typePlant];
                        if (intArrayTipoPlanta.length > 0) {
                            intArrayTipoPlanta[0] = typePlant;
                            ArrayAdapter<Integer> adapter3 = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, intArrayTipoPlanta);
                            adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerTipoPlanta.setAdapter(adapter3);
                        }

                        Integer[] intArrayModAlqEq = new Integer[modAlqEq];
                        intArrayModAlqEq [0] = modAlqEq;
                        ArrayAdapter<Integer> adapter4 = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, intArrayModAlqEq);
                        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerModAlq.setAdapter(adapter4);

                        Integer[] intArrayAtrPot = new Integer[modAtrPot];
                        intArrayAtrPot [0] = modAtrPot;
                        ArrayAdapter<Integer> adapter5= new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, intArrayAtrPot);
                        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerAtrPotencia.setAdapter(adapter5);

                        Integer[] intArrayTarZone = new Integer[tarZone];
                        intArrayTarZone [0] = tarZone;
                        ArrayAdapter<Integer> adapter6 = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, intArrayTarZone);
                        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerZonaTari.setAdapter(adapter6);

                        Integer[] intArrayReTemp = new Integer[rechargeTemp];
                        intArrayReTemp [0] = rechargeTemp;
                        ArrayAdapter<Integer> adapter7 = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, intArrayReTemp);
                        adapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerRecargo.setAdapter(adapter7);
                    }

                    cargarDatosSpinners();

                } catch (JSONException e) {
                    e.printStackTrace();
                    showError(e.toString());
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

    private void updateData() {
        final Integer spinnerPaisValue = (Integer) spinnerPais.getSelectedItem();
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Integer i = (Integer) spinnerPais.getSelectedItem();
        Log.d(TAG, i.toString());
        /*Integer ii = (Integer) spinnerCliente.getSelectedItem();
        Log.d(TAG, ii.toString());*/
    }


    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void showError(String s) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        builder.setTitle("Error");
        builder.setMessage("Se ha producido un error al obtener respuesta del servidor. " + s);
        builder.setPositiveButton("Aceptar", null);
        builder.create();
        builder.show();
    }

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Error");
        builder.setMessage("No es usted un usuario ADMMINISTRADOR. No tiene permiso de escritura y/o borrado");
        builder.setPositiveButton("Aceptar", null);
        builder.create();
        builder.show();
    }

    private void showAlertt() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        builder.setTitle("Error");
        builder.setMessage("Se ha producido un error eliminando el Punto de Medida.");
        builder.setPositiveButton("Aceptar", null);
        builder.create();
        builder.show();
    }

    private void showInfo(String s) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        builder.setTitle("Info");
        builder.setMessage(s);
        builder.setPositiveButton("Aceptar", null);
        builder.create();
        builder.show();
    }

}
