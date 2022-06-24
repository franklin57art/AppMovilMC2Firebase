package com.example.appmovilmc2firebase.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmovilmc2firebase.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import appmovilmc2firebase.R;

public class UserDataAdapter extends RecyclerView.Adapter<UserDataAdapter.UserViewHolder> {

    ArrayList<User> listaUser;
    ArrayList<User> listaOriginal;

    public UserDataAdapter(ArrayList<User> listaUser) {
        this.listaUser = listaUser;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaUser);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_usuarios, null, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.viewNombre.setText(listaUser.get(position).getName());
        holder.viewUsername.setText(listaUser.get(position).getUsername());
        holder.viewType.setText(String.valueOf(listaUser.get(position).getType()));
    }

    public void filtrado(final String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            listaUser.clear();
            listaUser.addAll(listaOriginal);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<User> collecion = listaUser.stream()
                        .filter(i -> i.getName().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                listaUser.clear();
                listaUser.addAll(collecion);
            } else {
                for (User c : listaOriginal) {
                    if (c.getName().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        listaUser.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaUser.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        TextView viewNombre, viewUsername, viewType;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            viewNombre = itemView.findViewById(R.id.nombreUserData);
            viewUsername = itemView.findViewById(R.id.usernameUserData);
            viewType = itemView.findViewById(R.id.TipoUserData);

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, VerActivity.class);
                    intent.putExtra("ID", listaUser.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });*/
        }
    }
}
