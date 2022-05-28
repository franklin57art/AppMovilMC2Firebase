package com.example.appmovilmc2firebase.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmovilmc2firebase.R;
import com.example.appmovilmc2firebase.models.PuntosDeMedida;

import java.util.List;

public class PuntosDeMedidaAdapter extends RecyclerView.Adapter<PuntosDeMedidaAdapter.PuntosDeMedidaHolder> {
    private List<PuntosDeMedida> mPuntosDeMedidaList;

    public PuntosDeMedidaAdapter(List<PuntosDeMedida> puntosDeMedidaList){
        this.mPuntosDeMedidaList = puntosDeMedidaList;
    }

    @NonNull
    @Override
    public PuntosDeMedidaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_puntosdemedida, parent,false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemView.setLayoutParams(layoutParams);
        return new PuntosDeMedidaHolder(itemView);
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

    public static class PuntosDeMedidaHolder extends RecyclerView.ViewHolder {
        public TextView mEspacio, mEspacio2, mNombre, mNombre2, mCups, mCups2, mCliente, mCliente2, mMonotorizacion, mMonotorizacion2;
        public PuntosDeMedidaHolder(View itemView) {
            super(itemView);
            mEspacio = itemView.findViewById(R.id.espacioindice);
            mNombre = itemView.findViewById(R.id.nombreindice);
            mCups = itemView.findViewById(R.id.cupsindice);
            mCliente = itemView.findViewById(R.id.clienteindice);
            mMonotorizacion = itemView.findViewById(R.id.monitorizacionindice);
            mEspacio2 = itemView.findViewById(R.id.espacioindice2);
            mNombre2 = itemView.findViewById(R.id.nombreindice2);
            mCups2 = itemView.findViewById(R.id.cupsindice2);
            mCliente2 = itemView.findViewById(R.id.clienteindice2);
            mMonotorizacion2 = itemView.findViewById(R.id.monitorizacionindice2);
        }
    }
}
