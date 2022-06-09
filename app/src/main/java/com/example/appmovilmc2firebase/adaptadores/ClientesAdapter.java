package com.example.appmovilmc2firebase.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmovilmc2firebase.models.Client;

import java.util.List;

import appmovilmc2firebase.R;

public class ClientesAdapter extends RecyclerView.Adapter<ClientesAdapter.ClientesHolder> {
    private List<Client> mClientList;
    
    public ClientesAdapter(List<Client> clientList){
        this.mClientList = clientList;
    }

    public ClientesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_client, parent,false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemView.setLayoutParams(layoutParams);
        return new ClientesHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientesHolder holder, int position) {
        holder.mNombre2.setText(mClientList.get(position).getName().toString());
        holder.mIdfiscal2.setText(mClientList.get(position).getId_fiscal().toString());
        //holder.mUltimoacceso2.setText(mClientList.get(position).getLast_access().toString());

    }

    @Override
    public int getItemCount() {
        return mClientList.size();
    }

    public static class ClientesHolder extends RecyclerView.ViewHolder {
        public TextView mNombre, mNombre2, mIdfiscal, mIdfiscal2, mUltimoacceso, mUltimoacceso2;
        public ClientesHolder(View itemView) {
            super(itemView);
            mNombre = itemView.findViewById(R.id.nombrerazonsocialindice);
            mIdfiscal = itemView.findViewById(R.id.idfiscalindice);
            mUltimoacceso = itemView.findViewById(R.id.ultimoaccesoindice);
            mNombre2 = itemView.findViewById(R.id.nombrerazonsocialindice2);
            mIdfiscal2 = itemView.findViewById(R.id.idfiscalindice2);
            mUltimoacceso2 = itemView.findViewById(R.id.ultimoaccesoindice2);
        }
    }
}
