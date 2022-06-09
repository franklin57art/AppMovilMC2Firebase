package com.example.appmovilmc2firebase.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appmovilmc2firebase.models.Partner;

import java.util.List;

import appmovilmc2firebase.R;

public class ConfigUserAdapterPartner extends RecyclerView.Adapter<ConfigUserAdapterPartner.ConfigUsuariosHolderPartner> {

    private List<Partner> mPartnerList;

    private Context contextPartner;

    public ConfigUserAdapterPartner(List<Partner> partnerList) {
        this.mPartnerList = partnerList;
    }

    @Override
    public ConfigUsuariosHolderPartner onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemViewPartner = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row_config_partner, parent, false);
        RecyclerView.LayoutParams layoutParamsPartner = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemViewPartner.setLayoutParams(layoutParamsPartner);
        contextPartner = parent.getContext();

        return new ConfigUsuariosHolderPartner(itemViewPartner);
    }

    @Override
    public void onBindViewHolder(ConfigUsuariosHolderPartner holder, int position) {
        holder.mUserRazonSocial.setText(mPartnerList.get(position).getRazon_social().toString());
        holder.mUserNamePartner.setText(mPartnerList.get(position).getName().toString());
    }

    @Override
    public int getItemCount() {
        return mPartnerList.size();
    }

    public static class ConfigUsuariosHolderPartner extends RecyclerView.ViewHolder {
        public TextView mTitle, mUserRazonSocial2, mUserNamePartner2;
        public EditText mUserRazonSocial, mUserNamePartner;


        public ConfigUsuariosHolderPartner(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.tvTitle);
            mUserRazonSocial2 = itemView.findViewById(R.id.tvRazonSocial2);
            mUserRazonSocial = itemView.findViewById(R.id.tvRazonSocial);
            mUserNamePartner2 = itemView.findViewById(R.id.tvNamePartner2);
            mUserNamePartner = itemView.findViewById(R.id.tvNamePartner);
        }
    }
}
