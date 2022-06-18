package com.example.appmovilmc2firebase.adaptadores;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.appmovilmc2firebase.models.PuntosDeMedida;
import com.example.appmovilmc2firebase.utils.PreferenceHelper;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import appmovilmc2firebase.R;

public class GeneralDataPuntosDeMedidaAdapter extends RecyclerView.Adapter<GeneralDataPuntosDeMedidaAdapter.GeneralDataPuntosDeMedidaHolder>{

    public Button mDeletePuntoMedidaButton;

    private ArrayList<PuntosDeMedida> mPuntoMedidaList;

    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    private Context context;

    private PreferenceHelper preferenceHelper;

    private int typeUser = 00;

    public GeneralDataPuntosDeMedidaAdapter(ArrayList<PuntosDeMedida> puntosDeMedidasList){
        this.mPuntoMedidaList = puntosDeMedidasList;
    }

    public GeneralDataPuntosDeMedidaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.general_data_puntosmedida,parent,false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemView.setLayoutParams(layoutParams);

        context = parent.getContext();

        preferenceHelper = new PreferenceHelper(context);
        //Convierto la variable id_pt_user obtenida en el login y guardada con el shared preferences como String a Int.
        typeUser = Integer.parseInt(preferenceHelper.getType());

        mDeletePuntoMedidaButton = itemView.findViewById(R.id.buttonGeneralDataClienteEliminarPuntoDeMedida);
        mDeletePuntoMedidaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeUser == 7) {
                    Toast.makeText(context,"En construcccion",Toast.LENGTH_LONG).show();
                    //deletePuntoDeMedida;
                } else {
                    Log.e("TAG", "EL TYPE DEL USUARIO NO ES DE TIPO PARTNER ADMINISTRADOR. Es un type tipo: " + typeUser);
                    showAlert();
                }
            }
        });

        return new GeneralDataPuntosDeMedidaHolder(itemView);
    }

    /*private void deletePuntoDeMedida() {



        String url = GlobalInfo.URL_DELETE_PUNTO + ;

        jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE,

    }*/

    public void onBindViewHolder(@NonNull GeneralDataPuntosDeMedidaHolder holder, int position){
        holder.mNombre.setText(mPuntoMedidaList.get(position).getName().toString());
        holder.mIdCliente.setText(mPuntoMedidaList.get(position).getId_psum_for_client().toString());
        holder.mDireccion.setText(mPuntoMedidaList.get(position).getAddress().toString());
        holder.mCp.setText(mPuntoMedidaList.get(position).getCod_postal().toString());
        holder.mCups.setText(mPuntoMedidaList.get(position).getCups().toString());
        holder.mCupsObras.setText(mPuntoMedidaList.get(position).getCups_obras().toString());
        holder.mAlquiler.setText(mPuntoMedidaList.get(position).getAlquiler_equipo().toString());
        holder.mConsumo.setText(mPuntoMedidaList.get(position).getEstimated_annual_consumption().toString());
        holder.mCliente.setText(mPuntoMedidaList.get(position).getId_client().toString());
        holder.mPoblacion.setText(mPuntoMedidaList.get(position).getId_poblacion().toString());
        holder.mComercializadora.setText(mPuntoMedidaList.get(position).getId_comerc().toString());
        holder.mEditText.setText(mPuntoMedidaList.get(position).getDescription().toString());
    }

    @Override
    public int getItemCount() {
        return mPuntoMedidaList.size();
    }

    public static class GeneralDataPuntosDeMedidaHolder extends RecyclerView.ViewHolder{
        public TextInputEditText mNombre, mIdCliente, mDireccion, mCp, mCups, mCupsObras, mAlquiler, mConsumo;
        public AutoCompleteTextView mCliente, mPoblacion, mComercializadora;
        public Spinner mPais, mTipoPunto, mTipoPlanta, mAlqEquipos, mAtrPotencia, mZonaTarifa, mRecargo;
        public EditText mEditText;

        public GeneralDataPuntosDeMedidaHolder(View itemView){
            super(itemView);
            mNombre = itemView.findViewById(R.id.tietGeneralDataPuntosDeMedidaNombre);
            mIdCliente = itemView.findViewById(R.id.tietGeneralDataPuntosDeMedidaIdCliente);
            mDireccion = itemView.findViewById(R.id.tietGeneralDataPuntosDeMedidaDireccion);
            mCp = itemView.findViewById(R.id.tietGeneralDataPuntosDeMedidaCp);
            mCups = itemView.findViewById(R.id.tietGeneralDataPuntosDeMedidaCups);
            mCupsObras = itemView.findViewById(R.id.tietGeneralDataPuntosDeMedidaCupsDeObras);
            mAlquiler = itemView.findViewById(R.id.tietGeneralDataPuntosDeMedidaAlquilerEquipos);
            mConsumo = itemView.findViewById(R.id.tietGeneralDataPuntosDeMedidaConsumoAnual);
            mCliente = itemView.findViewById(R.id.actvGeneralDataPuntosDeMedidaCliente);
            mPoblacion = itemView.findViewById(R.id.actvGeneralDataPuntosDeMedidaPoblacion);
            mComercializadora = itemView.findViewById(R.id.actvGeneralDataPuntosDeMedidaComercializadora);
            mPais = itemView.findViewById(R.id.spinnerGeneralDataPuntosDeMedidaPais);
            mTipoPunto = itemView.findViewById(R.id.spinnerTipoPuntoDeMedida);
            mTipoPlanta = itemView.findViewById(R.id.spinnerGeneralDataPuntosDeMedidaTipoDePlanta);
            mAlqEquipos = itemView.findViewById(R.id.spinnerGeneralDataPuntosDeMedidaModoAlq);
            mAtrPotencia = itemView.findViewById(R.id.spinnerGeneralDataPuntosDeMedidaModoATRPotencia);
            mZonaTarifa = itemView.findViewById(R.id.spinnerGeneralDataPuntosDeMedidaRecargo);
            mRecargo = itemView.findViewById(R.id.spinnerGeneralDataPuntosDeMedidaRecargo);
            mEditText =itemView.findViewById(R.id.editText);
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
