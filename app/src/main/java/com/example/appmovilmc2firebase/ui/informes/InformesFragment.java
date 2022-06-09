package com.example.appmovilmc2firebase.ui.informes;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmovilmc2firebase.GlobalInfo;

import appmovilmc2firebase.R;

public class InformesFragment extends Fragment {

    private static final String TAG = "InformesFragment";

    private RecyclerView mRecyclerView;
    private ProgressDialog pDialog;
    private String authValue = "";

    public InformesFragment(){}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @NonNull Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_informes, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerviewInformes);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setHasFixedSize(true);

        //Leo el valor del AUTH TOKEN KEY guardado al hacer el Login
        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("AUTHTOKENKEY", Context.MODE_PRIVATE);
        String authTokenValue = sharedPref.getString("AuthTokenKey", GlobalInfo.AUTH_TOKEN);
        authValue = authTokenValue;



        return view;
    }


}
