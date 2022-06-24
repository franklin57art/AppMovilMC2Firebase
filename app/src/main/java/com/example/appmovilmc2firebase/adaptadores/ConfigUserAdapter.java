package com.example.appmovilmc2firebase.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appmovilmc2firebase.HomeActivity;
import com.example.appmovilmc2firebase.models.User;
import com.example.appmovilmc2firebase.ui.configuracion.ChangePasswordConfigActivity;

import java.util.ArrayList;

import appmovilmc2firebase.R;

public class ConfigUserAdapter extends RecyclerView.Adapter<ConfigUserAdapter.ConfigUsuariosHolder> {

    private ArrayList<User> mUserList;

    private Context contextUser;

    private Button mSalir, mAceptar;

    public ConfigUserAdapter(ArrayList<User> userList) {
        this.mUserList = userList;
    }

    @Override
    public ConfigUsuariosHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemViewUser = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row_config_user, parent, false);
        RecyclerView.LayoutParams layoutParamsUser = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemViewUser.setLayoutParams(layoutParamsUser);
        contextUser = parent.getContext();

        mSalir = itemViewUser.findViewById(R.id.buttonSalirConfigUser);
        mSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contextUser, HomeActivity.class);
                contextUser.startActivity(intent);
            }
        });
        mAceptar = itemViewUser.findViewById(R.id.buttonChangePasswordConfigUser);
        mAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contextUser, ChangePasswordConfigActivity.class);
                contextUser.startActivity(intent);
            }
        });

        return new ConfigUsuariosHolder(itemViewUser);
    }

    @Override
    public void onBindViewHolder(ConfigUsuariosHolder holder, int position) {
        holder.mUserName.setText(mUserList.get(position).getName().toString());
        holder.mUserUserName.setText(mUserList.get(position).getUsername().toString());
        holder.mUserEmail.setText(mUserList.get(position).getEmail().toString());
        holder.mUserType.setText(mUserList.get(position).getType().toString());
        holder.mUserPassword.setText(mUserList.get(position).getPassword().toString());
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public static class ConfigUsuariosHolder extends RecyclerView.ViewHolder {
        public TextView mTitle3, mUserName2, mUserUserName2, mUserEmail2, mUserType2;
        public EditText mUserName, mUserUserName, mUserEmail, mUserType, mUserPassword;
        public Button mSalir, mAceptar;


        public ConfigUsuariosHolder(View itemView) {
            super(itemView);
            mTitle3 = itemView.findViewById(R.id.tvTitle3);
            mUserName2 = itemView.findViewById(R.id.tvName2);
            mUserName = itemView.findViewById(R.id.tvName);
            mUserUserName2 = itemView.findViewById(R.id.tvUserName2);
            mUserUserName = itemView.findViewById(R.id.tvUserName);
            mUserEmail2 = itemView.findViewById(R.id.tvEmail2);
            mUserEmail = itemView.findViewById(R.id.tvEmail);
            mUserType2 = itemView.findViewById(R.id.tvTypeUser2);
            mUserType = itemView.findViewById(R.id.tvTypeUser);
            mUserPassword = itemView.findViewById(R.id.tietPassword);
            mSalir = itemView.findViewById(R.id.buttonSalirConfigUser);
            mAceptar = itemView.findViewById(R.id.buttonChangePasswordConfigUser);
        }
    }
}
