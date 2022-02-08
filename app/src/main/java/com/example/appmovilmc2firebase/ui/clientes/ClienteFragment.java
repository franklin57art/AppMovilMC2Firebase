package com.example.appmovilmc2firebase.ui.clientes;

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

public class ClienteFragment extends Fragment {

    private FragmentClientBinding binding;
    private ClienteViewModel clienteViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {
        clienteViewModel = new ViewModelProvider(this).get(ClienteViewModel.class);
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_client, container, false);
        final TextView textView = root.findViewById(R.id.textViewCliente);
        clienteViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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