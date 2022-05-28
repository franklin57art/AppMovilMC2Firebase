package com.example.appmovilmc2firebase.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appmovilmc2firebase.R;
import com.example.appmovilmc2firebase.models.User;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class ConfigUserAdapter extends RecyclerView.Adapter<ConfigUserAdapter.ConfigUsuariosHolder> {
    private List<User> mUserList;

    public ConfigUserAdapter(List<User> userList) {
        this.mUserList = userList;
    }

    @Override
    public ConfigUsuariosHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row_config, parent, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemView.setLayoutParams(layoutParams);

        return new ConfigUsuariosHolder(itemView);
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
        public TextView mTitle, mTitle2, mTitle3, mUserRazonSocial2, mUserNamePartner2, mUserName2, mUserUserName2, mUserEmail2, mUserType2;
        public EditText mUserRazonSocial, mUserNamePartner, mUserName, mUserUserName, mUserEmail, mUserType;
        public TextInputEditText mUserPassword,mUserPasswordNew, mUserPasswordNewRepeat;

        public ConfigUsuariosHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.tvTitle);
            mUserRazonSocial2 = itemView.findViewById(R.id.tvRazonSocial2);
            mUserRazonSocial = itemView.findViewById(R.id.tvRazonSocial);
            mUserNamePartner2 = itemView.findViewById(R.id.tvNamePartner2);
            mUserNamePartner = itemView.findViewById(R.id.tvNamePartner);
            mTitle3 = itemView.findViewById(R.id.tvTitle3);
            mUserName2 = itemView.findViewById(R.id.tvName2);
            mUserName = itemView.findViewById(R.id.tvName);
            mUserUserName2 = itemView.findViewById(R.id.tvUserName2);
            mUserUserName = itemView.findViewById(R.id.tvUserName);
            mUserEmail2 = itemView.findViewById(R.id.tvEmail2);
            mUserEmail = itemView.findViewById(R.id.tvEmail);
            mUserType2 = itemView.findViewById(R.id.tvTypeUser2);
            mUserType = itemView.findViewById(R.id.tvTypeUser);
            mTitle2 = itemView.findViewById(R.id.tvTitle2);

            mUserPassword = itemView.findViewById(R.id.tietPassword);
            mUserPasswordNew = itemView.findViewById(R.id.tietPasswordNew);
            mUserPasswordNewRepeat = itemView.findViewById(R.id.tietRepeatPassword);

        }
    }
}
