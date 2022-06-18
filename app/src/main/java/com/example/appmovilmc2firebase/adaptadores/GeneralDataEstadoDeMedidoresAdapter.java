package com.example.appmovilmc2firebase.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmovilmc2firebase.models.PuntosDeMedida;
import com.example.appmovilmc2firebase.ui.puntosDeMedida.GeneralDataPuntosmedidaActivity;

import java.util.ArrayList;

import appmovilmc2firebase.R;

public class GeneralDataEstadoDeMedidoresAdapter extends RecyclerView.Adapter<GeneralDataEstadoDeMedidoresAdapter.GeneralDataEstadoDeMedidoresHolder> {

    private ArrayList<PuntosDeMedida> mEstadoMedidoresList;

    private Context context;

    private Button editar, incidencia;

    public GeneralDataEstadoDeMedidoresAdapter(ArrayList<PuntosDeMedida> mEstadoMedidoresList) {
        this.mEstadoMedidoresList = mEstadoMedidoresList;
    }

    @NonNull
    public GeneralDataEstadoDeMedidoresHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.general_data_estadomedidores, parent, false);

        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemView.setLayoutParams(layoutParams);

        context = parent.getContext();

        editar = itemView.findViewById(R.id.buttonGeneralDataEstadoMedidoresEditPuntoMedida);
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GeneralDataPuntosmedidaActivity.class);
                context.startActivity(intent);
            }
        });

        incidencia = itemView.findViewById(R.id.buttonGeneralDataEstadoMedidoresIncidencia);
        incidencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "En construcción...", Toast.LENGTH_LONG).show();
            }
        });

        return new GeneralDataEstadoDeMedidoresHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull GeneralDataEstadoDeMedidoresHolder holder, int position) {
        holder.mNombreTitulo.setText(mEstadoMedidoresList.get(position).getName().toString());
        holder.mCups.setText(mEstadoMedidoresList.get(position).getCups().toString());
    }

    @Override
    public int getItemCount() {
        return mEstadoMedidoresList.size();
    }

    public class GeneralDataEstadoDeMedidoresHolder extends RecyclerView.ViewHolder {
        public TextView mNombreTitulo, mCups;

        public GeneralDataEstadoDeMedidoresHolder(View itemView) {
            super(itemView);
            mNombreTitulo = itemView.findViewById(R.id.tvGeneralDataEstadoMedidoresNombreTítulo);
            mCups = itemView.findViewById(R.id.tvGeneralDataEstadoMedidoresCups);
        }
    }
}

