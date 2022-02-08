package com.example.appmovilmc2firebase.ui.estadoMedidores;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.appmovilmc2firebase.R;
import com.example.appmovilmc2firebase.databinding.FragmentEstadomedidoresBinding;
import com.example.appmovilmc2firebase.ui.alarmas.AlarmaViewModel;

public class EstadomedidoresFragment extends Fragment {

    private FragmentEstadomedidoresBinding binding;
    private EstadomedidoresViewModel estadomedidoresViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @NonNull Bundle savedInstanceState) {

        estadomedidoresViewModel = new ViewModelProvider(this).get(EstadomedidoresViewModel.class);
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_estadomedidores, container, false);
        final TextView textView = root.findViewById(R.id.textViewEstadoMedidores);
        estadomedidoresViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}