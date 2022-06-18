package com.example.appmovilmc2firebase.adaptadores;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.appmovilmc2firebase.models.Client;
import com.example.appmovilmc2firebase.utils.PreferenceHelper;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.ArrayList;

import appmovilmc2firebase.R;

public class GeneralDataClientesAdapter extends RecyclerView.Adapter<GeneralDataClientesAdapter.GeneralDataClientesHolder> {

    public Button mDeleteClientButton;

    private ArrayList<Client> mClientList;

    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    private Context context;

    private PreferenceHelper preferenceHelper;

    private int typeUser = 00;


    public GeneralDataClientesAdapter(ArrayList<Client> clientList, Response.Listener<JSONObject> listener) {
        this.mClientList = clientList;
    }

    @NonNull
    @Override
    public GeneralDataClientesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.general_data_client, parent, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemView.setLayoutParams(layoutParams);

        context = parent.getContext();

        preferenceHelper = new PreferenceHelper(context);
        //Convierto la variable id_pt_user obtenida en el login y guardada con el shared preferences como String a Int.
        typeUser = Integer.parseInt(preferenceHelper.getType());

        mDeleteClientButton = itemView.findViewById(R.id.buttonGeneralDataClienteEliminarCliente);
        mDeleteClientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeUser == 7) {
                    Toast.makeText(context,"En construcccion",Toast.LENGTH_LONG).show();
                    //deleteClient();
                } else {
                    Log.e("TAG", "EL TYPE DEL USUARIO NO ES DE TIPO PARTNER ADMINISTRADOR. Es un type tipo: " + typeUser);
                    showAlert();
                }
            }
        });

        return new GeneralDataClientesHolder(itemView);
    }

    /*private void deleteClient() {



        String url = GlobalInfo.URL_DELETE_CLIENT + ;

        jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE,

    }*/

    public void onBindViewHolder(@NonNull GeneralDataClientesHolder holder, int position) {
        holder.mRazonSocial.setText(mClientList.get(position).getName().toString());
        holder.mIdFiscal.setText(mClientList.get(position).getId_fiscal().toString());
        holder.mNombreCorto.setText(mClientList.get(position).getNombre_empresa().toString());
        holder.mNombreCliente.setText(mClientList.get(position).getNombre_cliente().toString());
        holder.mTecnico.setText(mClientList.get(position).getId_tecnico().toString());
    }

    @Override
    public int getItemCount() {
        return mClientList.size();
    }

    public static class GeneralDataClientesHolder extends RecyclerView.ViewHolder {
        public TextInputEditText mRazonSocial, mIdFiscal, mNombreCorto, mNombreCliente;
        public AutoCompleteTextView mTecnico;

        public GeneralDataClientesHolder(View itemView) {
            super(itemView);
            mRazonSocial = itemView.findViewById(R.id.tietGeneralDataClienteRazonSocial);
            mIdFiscal = itemView.findViewById(R.id.tietGeneralDataClienteIdFiscal);
            mNombreCorto = itemView.findViewById(R.id.tietGeneralDataClienteNombreCorto);
            mNombreCliente = itemView.findViewById(R.id.tietGeneralDataClienteNombreDelCliente);
            mTecnico = itemView.findViewById(R.id.actvGeneralDataClienteTecnicosAsignados);
        }
    }

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Error");
        builder.setMessage("No es usted un usuario ADMMINISTRADOR. No tiene permiso de escritura");
        builder.setPositiveButton("Aceptar", null);
        builder.create();
        builder.show();
    }
}
