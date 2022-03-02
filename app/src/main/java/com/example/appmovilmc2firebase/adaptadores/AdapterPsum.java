package com.example.appmovilmc2firebase.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmovilmc2firebase.R;
import com.example.appmovilmc2firebase.models.Psum;

import java.util.List;

public class AdapterPsum extends RecyclerView.Adapter<AdapterPsum.ViewHolder> implements View.OnClickListener{

    LayoutInflater inflater;
    List<Psum> listsuministros;
    Context context;

    //listener
    private View.OnClickListener listener;

    public AdapterPsum(Context context, List<Psum> listsuministros){
        //this.inflater = LayoutInflater.from(context);
        this.listsuministros = listsuministros;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterPsum.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.from(context).inflate(R.layout.lista_psum, parent, false);
        //view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    public void setOnclickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPsum.ViewHolder holder, int position) {
        int id = listsuministros.get(position).getId();
        String nombre = listsuministros.get(position).getName();
        String cups = listsuministros.get(position).getCups();
        int idpsumforclient = listsuministros.get(position).getIdPsumForClient();
        holder.id.setId(id);
        holder.nombre.setText(nombre);
        holder.cups.setText(cups);
        holder.idpsumforclient.setId(idpsumforclient);
    }


    @Override
    public int getItemCount() {
        return listsuministros.size();
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView id, nombre, cups, idpsumforclient;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id_suministros);
            nombre = itemView.findViewById(R.id.nombre_suministros);
            cups = itemView.findViewById(R.id.cup_suministros);
            idpsumforclient = itemView.findViewById(R.id.idpsum_suministros);
        }
    }
}
