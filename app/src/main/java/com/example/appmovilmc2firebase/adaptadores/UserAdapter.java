package com.example.appmovilmc2firebase.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmovilmc2firebase.models.User;
import com.example.appmovilmc2firebase.ui.usuarios.RegisterUserActivity;

import java.util.ArrayList;

import appmovilmc2firebase.R;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UsuariosHolder> {

    private ArrayList<User> mUserList;

    private Context context;

    public Button mAddUserButton;

    public UserAdapter(ArrayList<User> userList) {
        this.mUserList = userList;
    }

    public UsuariosHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row_usuarios, parent, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemView.setLayoutParams(layoutParams);
        mAddUserButton = itemView.findViewById(R.id.buttonAddUser);
        context = parent.getContext();
        mAddUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RegisterUserActivity.class);
                context.startActivity(intent);
            }
        });
        return new UsuariosHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UsuariosHolder holder, int position) {
        holder.mName2.setText(mUserList.get(position).getName().toString());
        holder.mUserName2.setText(mUserList.get(position).getUsername().toString());
        holder.mType2.setText(mUserList.get(position).getType().toString());
        //holder.mUltimoAcceso2.setText(mUserList.get(position));
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public static class UsuariosHolder extends RecyclerView.ViewHolder {

        public TextView mName, mName2, mUserName, mUserName2, mType, mType2, mUltimoAcceso, mUltimoAcceso2;
        public SearchView mSearchView;

        public UsuariosHolder(View itemView) {
            super(itemView);
            mSearchView = itemView.findViewById(R.id.searchViewUser);
            mName = itemView.findViewById(R.id.nombreusuarioindice);
            mName2 = itemView.findViewById(R.id.nombreusuariodatos);
            mUserName = itemView.findViewById(R.id.usuarioindice);
            mUserName2 = itemView.findViewById(R.id.usuariodatos);
            mType = itemView.findViewById(R.id.tipousuarioindice);
            mType2 = itemView.findViewById(R.id.tipousuariodatos);
            mUltimoAcceso = itemView.findViewById(R.id.ultimoaccesousuarioindice);
            mUltimoAcceso2 = itemView.findViewById(R.id.ultimoaccesousuariodatos);
        }
    }
}
