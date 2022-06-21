package com.example.appmovilmc2firebase.ui.estadoMedidores;

import android.app.AlertDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appmovilmc2firebase.HomeActivity;
import com.example.appmovilmc2firebase.utils.GlobalInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import appmovilmc2firebase.R;

public class IncidenciaEstadoMedidoresFragment extends Fragment {

    private static final String TAG = "IncidenciaEstadoDeMedidoresActivity";

    private Button exit, aceptar;
    private TextView nombrePM;
    private ImageView info;
    private Spinner spinnerTipo;
    private EditText descripccionIncidencia;

    private RequestQueue request;

    private String authValue = "";

    private int idconn = 00;
    private String namepdm = "";

    GeneralDataEstadomedidoresFragment generalDataEstadomedidoresFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Recibimos datos desde el fragment GeneralDataEstadomedidores
        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                namepdm = result.getString("nombrePDM");
                nombrePM.setText(namepdm);
                idconn = result.getInt("idConnPDM");
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {
        getActivity().setTitle("Nueva Incidencia");

        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_incidencia_estado_medidores, container, false);

        //Leo el valor del AUTH TOKEN KEY guardado al hacer el Login
        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("AUTHTOKENKEY", Context.MODE_PRIVATE);
        String authTokenValue = sharedPref.getString("AuthTokenKey", GlobalInfo.AUTH_TOKEN);
        authValue = authTokenValue;

        request = Volley.newRequestQueue(getContext());

        exit = vista.findViewById(R.id.buttonSalirIncidenciaEstadoMedidores);
        aceptar = vista.findViewById(R.id.buttonEnviarIncidenciaEstadoMedidores);

        nombrePM = vista.findViewById(R.id.tvIncidenciaEstadoMedidoresNombrePuntoDeMedida);

        info = vista.findViewById(R.id.ivInfoIncidenciaEstadoMedidores);

        descripccionIncidencia = vista.findViewById(R.id.etIncidenciaEstadoMedidores);

        spinnerTipo = vista.findViewById(R.id.spinnerIncidenciaEstadoMedidoresTipoIncidencia);
        // Creo un array de string con los valores del spinner
        String[] arrayTipos = getResources().getStringArray(R.array.type_array_tipo_incidencia_punto_de_medida);
        // Creo un array de enteros que recorrera el array definido anteriormente y va a transformar los String en enteros
        Integer[] intArrayTipos = new Integer[arrayTipos.length];
        for (int i = 0; i < arrayTipos.length; i++) {
            intArrayTipos[i] = Integer.parseInt(arrayTipos[i]);
        }
        // Create an ArrayAdapter using the string array and a spinner layout
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_spinner_item, intArrayTipos);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerTipo.setAdapter(adapter);

        request = Volley.newRequestQueue(getContext());

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo();
            }
        });

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarIncidencia();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });
        return vista;
    }

    public void enviarIncidencia() {
        final String detallesIncidencia = descripccionIncidencia.getText().toString();
        final Integer spinnerTipoValue = (Integer) spinnerTipo.getSelectedItem();

        if (detallesIncidencia.isEmpty()) {
            showAlertInformation(descripccionIncidencia, "El campo esta vacío. Asegúrese de rellenarlo correctamente");
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.PUT, GlobalInfo.URL_REGISTER_INCIDENCIA_PUNTO_DE_MEDIDA + String.valueOf(idconn),
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
                    params.put("notes", detallesIncidencia);
                    //params.put("type", spinnerTipoValue.toString());
                    return params;
                }
            };
            request = Volley.newRequestQueue(getActivity());
            request.add(stringRequest);
        }
    }

    private void parseData(String response) throws JSONException {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString("success").equals("true")) {
                Toast.makeText(getActivity(), "¡Incidencia registrada correctamente!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
            } else {
                showAlert();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Integer i = (Integer) spinnerTipo.getSelectedItem();
        Log.d(TAG, i.toString());
    }


    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Error");
        builder.setMessage("Se ha producido un error registrando la incidencia");
        builder.setPositiveButton("Aceptar", null);
        builder.create();
        builder.show();
    }

    private void showError(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Error");
        builder.setMessage("Se ha producido un error al obtener respuesta del servidor");
        builder.setPositiveButton("Aceptar", null);
        builder.create();
        builder.show();
    }

    private void showAlertInformation(EditText input, String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        input.setError(s);
        input.requestFocus();
        builder.create();
    }

    private void showInfo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Info");
        builder.setMessage("1->Fallo sw comunicación, 2->Datos medidos erróneos, 3->Nuevo suministro telemedido, 4->Cambio datos comunicación, 5->Solicitud forzado de descarga, 6->Otros");
        builder.setPositiveButton("Aceptar", null);
        builder.create();
        builder.show();
    }


}