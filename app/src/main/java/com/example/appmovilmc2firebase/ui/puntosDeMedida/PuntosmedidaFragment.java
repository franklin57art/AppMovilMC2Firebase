package com.example.appmovilmc2firebase.ui.puntosDeMedida;

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
import com.example.appmovilmc2firebase.databinding.FragmentPuntosdemedidaBinding;
import com.example.appmovilmc2firebase.ui.alarmas.AlarmaViewModel;

public class PuntosmedidaFragment extends Fragment {

    private FragmentPuntosdemedidaBinding binding;
    private PuntosmedidaViewModel puntosmedidaViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @NonNull Bundle savedInstanceState) {

        puntosmedidaViewModel = new ViewModelProvider(this).get(PuntosmedidaViewModel.class);
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_puntosdemedida, container, false);
        final TextView textView = root.findViewById(R.id.textViewPuntosDeMedida);
        puntosmedidaViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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