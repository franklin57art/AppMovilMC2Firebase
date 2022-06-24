package com.example.appmovilmc2firebase.adaptadores;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmovilmc2firebase.models.PuntosDeMedida;
import com.example.appmovilmc2firebase.ui.puntosDeMedida.RegisterPuntosmedidaActivity;
import com.example.appmovilmc2firebase.utils.PreferenceHelper;

import java.util.ArrayList;

import appmovilmc2firebase.R;

public class PuntosDeMedidaAdapter extends RecyclerView.Adapter<PuntosDeMedidaAdapter.PuntosDeMedidaHolder> implements View.OnClickListener{

    private LayoutInflater inflater;
    private ArrayList<PuntosDeMedida> mPuntosDeMedidaList;

    private Context context;

    private Button mAddPuntoMedidaButton;

    private PreferenceHelper preferenceHelper;

    private int typeUser = 00;

    //listener
    private View.OnClickListener listener;

    public PuntosDeMedidaAdapter(Context context, ArrayList<PuntosDeMedida> puntosDeMedidaList) {
        this.inflater = LayoutInflater.from(context);
        this.mPuntosDeMedidaList = puntosDeMedidaList;
    }

    @NonNull
    @Override
    public PuntosDeMedidaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_row_puntosdemedida, parent, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemView.setLayoutParams(layoutParams);

        itemView.setOnClickListener(this);

        context = parent.getContext();

        preferenceHelper = new PreferenceHelper(context);
        //Convierto la variable id_pt_user obtenida en el login y guardada con el shared preferences como String a Int.
        typeUser = Integer.parseInt(preferenceHelper.getType());

        mAddPuntoMedidaButton = itemView.findViewById(R.id.buttonAddPuntoDeMedida);
        mAddPuntoMedidaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeUser == 7) {
                    Intent intent = new Intent(context, RegisterPuntosmedidaActivity.class);
                    context.startActivity(intent);
                } else {
                    Log.e("TAG", "EL TYPE DEL USUARIO NO ES DE TIPO PARTNER ADMINISTRADOR. Es un type tipo: " + typeUser);
                    showAlert();
                }
            }
        });

        return new PuntosDeMedidaHolder(itemView);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull PuntosDeMedidaHolder holder, int position) {
        holder.mNombre2.setText(mPuntosDeMedidaList.get(position).getName().toString());
        holder.mCups2.setText(mPuntosDeMedidaList.get(position).getCups().toString());
        holder.mCliente2.setText(mPuntosDeMedidaList.get(position).getId_client().toString());
        //holder.mMonotorizacion2.setText(mPuntosDeMedidaList.get(position).getHas_monit().toString());

    }

    @Override
    public int getItemCount() {
        return mPuntosDeMedidaList.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null){
            listener.onClick(v);
        }
    }

    public static class PuntosDeMedidaHolder extends RecyclerView.ViewHolder {
        public TextView mNombre, mNombre2, mCups, mCups2, mCliente, mCliente2, mMonotorizacion, mMonotorizacion2;
        public ImageView mImageView;

        public PuntosDeMedidaHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.ivPuntosDeMedida);
            mNombre = itemView.findViewById(R.id.nombreindice);
            mCups = itemView.findViewById(R.id.cupsindice);
            mCliente = itemView.findViewById(R.id.clienteindice);
            mMonotorizacion = itemView.findViewById(R.id.monitorizacionindice);
            mNombre2 = itemView.findViewById(R.id.nombreindice2);
            mCups2 = itemView.findViewById(R.id.cupsindice2);
            mCliente2 = itemView.findViewById(R.id.clienteindice2);
            mMonotorizacion2 = itemView.findViewById(R.id.monitorizacionindice2);
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
