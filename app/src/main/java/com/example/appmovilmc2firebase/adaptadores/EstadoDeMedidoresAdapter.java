package com.example.appmovilmc2firebase.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmovilmc2firebase.models.PuntosDeMedida;

import java.util.ArrayList;

import appmovilmc2firebase.R;

public class EstadoDeMedidoresAdapter extends RecyclerView.Adapter<EstadoDeMedidoresAdapter.EstadoDeMedidoresHolder> implements View.OnClickListener{

    private LayoutInflater inflater;
    private ArrayList<PuntosDeMedida> mEstadoMedidoresList;

    private Context context;

    //listener
    private View.OnClickListener listener;

    public EstadoDeMedidoresAdapter(Context context, ArrayList<PuntosDeMedida> estadoMedidoresList){
        this.inflater = LayoutInflater.from(context);
        this.mEstadoMedidoresList = estadoMedidoresList;
    }

    @NonNull
    @Override
    public EstadoDeMedidoresHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_row_estadodemedidores, parent, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemView.setLayoutParams(layoutParams);

        itemView.setOnClickListener(this);

        context = parent.getContext();

        return new EstadoDeMedidoresHolder(itemView);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull EstadoDeMedidoresHolder holder, int position) {
        holder.mNombreDatos.setText(mEstadoMedidoresList.get(position).getName().toString());
    }

    public int getItemCount(){
        return mEstadoMedidoresList.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null){
            listener.onClick(v);
        }
    }

    public class EstadoDeMedidoresHolder extends RecyclerView.ViewHolder{
        public TextView mNombreTitulo, mNombreDatos, mDatosTitulo, mDatosDatos, mDiscontinuidadTitulo, mDiscontinuidadDatos, mUltimos7DiasTitulo, mUltimos7DiasDatos;

        public EstadoDeMedidoresHolder(View itemView){
            super(itemView);
            mNombreTitulo = itemView.findViewById(R.id.nombreEstadoDeMedidores);
            mNombreDatos = itemView.findViewById(R.id.nombreEstadoDeMedidoresDatos);
            mDatosTitulo = itemView.findViewById(R.id.datosEstadoDeMedidores);
            mDatosDatos = itemView.findViewById(R.id.datosEstadoDeMedidoresDatos);
            mDiscontinuidadTitulo = itemView.findViewById(R.id.discontinuidadEstadoDeMedidores);
            mDiscontinuidadDatos = itemView.findViewById(R.id.discontinuidadEstadoDeMedidoresDatos);
            mUltimos7DiasTitulo = itemView.findViewById(R.id.ultimos7diasEstadoDeMedidores);
            mUltimos7DiasDatos = itemView.findViewById(R.id.ultimos7diasEstadoDeMedidoresDatos);
        }
    }
}
