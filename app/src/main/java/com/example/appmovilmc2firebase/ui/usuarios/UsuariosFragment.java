package com.example.appmovilmc2firebase.ui.usuarios;

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
import com.example.appmovilmc2firebase.databinding.FragmentClientBinding;
import com.example.appmovilmc2firebase.databinding.FragmentUsuariosBinding;
import com.example.appmovilmc2firebase.ui.alarmas.AlarmaViewModel;
import com.example.appmovilmc2firebase.ui.puntosDeMedida.PuntosmedidaViewModel;

public class UsuariosFragment extends Fragment {

    private FragmentClientBinding binding;
    private UsuariosViewModel usuariosViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @NonNull Bundle savedInstanceState) {

        usuariosViewModel = new ViewModelProvider(this).get(UsuariosViewModel.class);
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_usuarios, container, false);
        final TextView textView = root.findViewById(R.id.textViewUsuarios);
        usuariosViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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
