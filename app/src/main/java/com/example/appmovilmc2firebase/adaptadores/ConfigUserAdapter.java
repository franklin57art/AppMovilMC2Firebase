package com.example.appmovilmc2firebase.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appmovilmc2firebase.R;
import com.example.appmovilmc2firebase.models.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UsuariosHolder>{
    private List<User> mUserList;

    public UserAdapter(List<User> userList) {
        this.mUserList = userList;
    }

    @Override
    public UsuariosHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row_config, parent, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemView.setLayoutParams(layoutParams);

        return new UsuariosHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UsuariosHolder holder, int position) {
        holder.mUserName.setText(mUserList.get(position).getName().toString());
        holder.mUserUserName.setText(mUserList.get(position).getUsername().toString());
        holder.mUserEmail.setText(mUserList.get(position).getEmail().toString());
        holder.mUserTelefono.setText(mUserList.get(position).getTelefono().toString());
        holder.mUserType.setText(mUserList.get(position).getType().toString());
        holder.mUserPassword.setText(mUserList.get(position).getPassword().toString());
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public static class UsuariosHolder extends RecyclerView.ViewHolder {
        public TextView mTitle, mTitle2, mUserName2, mUserName, mUserUserName2, mUserUserName, mUserEmail2, mUserEmail, mUserPassword, mUserPassword2, mUserTelfono2, mUserTelefono, mUserType2, mUserType;
        public Button mUserButton;

        public UsuariosHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.tvTitle);
            mUserName2 = itemView.findViewById(R.id.tvName2);
            mUserName = itemView.findViewById(R.id.tvName);
            mUserUserName2 = itemView.findViewById(R.id.tvUserName2);
            mUserUserName = itemView.findViewById(R.id.tvUserName);
            mUserEmail2 = itemView.findViewById(R.id.tvEmail2);
            mUserEmail = itemView.findViewById(R.id.tvEmail);
            mUserTelfono2 = itemView.findViewById(R.id.tvTelefono2);
            mUserTelefono = itemView.findViewById(R.id.tvTelefono);
            mUserType2 = itemView.findViewById(R.id.tvTypeUser2);
            mUserType = itemView.findViewById(R.id.tvTypeUser);
            mTitle2 = itemView.findViewById(R.id.tvTitle2);
            mUserPassword2 = itemView.findViewById(R.id.tvPassword2);
            mUserPassword = itemView.findViewById(R.id.tvPassword);
            mUserButton = itemView.findViewById(R.id.button);
        itemView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mUserPassword.getVisibility() == View.INVISIBLE){
                    mUserPassword.setVisibility(View.VISIBLE);
                }else{
                    mUserPassword.setVisibility(View.INVISIBLE);
                }
            }
        });
        }
    }
}
