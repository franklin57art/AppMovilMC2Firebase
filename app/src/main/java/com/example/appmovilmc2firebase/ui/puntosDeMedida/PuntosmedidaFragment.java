package com.example.appmovilmc2firebase.ui.puntosDeMedida;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmovilmc2firebase.R;
import com.example.appmovilmc2firebase.adaptadores.AdapterPsum;
import com.example.appmovilmc2firebase.databinding.FragmentPuntosdemedidaBinding;
import com.example.appmovilmc2firebase.io.ApiAdapter;
import com.example.appmovilmc2firebase.io.response.PsumResponse;
import com.example.appmovilmc2firebase.models.Psum;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PuntosmedidaFragment extends Fragment {

    private FragmentPuntosdemedidaBinding binding;
    private PuntosmedidaViewModel puntosmedidaViewModel;
    private RecyclerView mRecyclerView;
    private List<Psum> psumArrayList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @NonNull Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_puntosdemedida, container, false);
        //Creo una instancia de la clase view model
        puntosmedidaViewModel = new ViewModelProvider(this).get(PuntosmedidaViewModel.class);
        //Declaro una variable de tipo textview que se encuentra dentro del fragment anteriormente inflado y usaremos esta variable para cambiar este text view
        final TextView textView = root.findViewById(R.id.textViewPuntosDeMedida);
        puntosmedidaViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
            super.onViewCreated(view, savedInstanceState);

        //Creo el recyclerView
        mRecyclerView = view.findViewById(R.id.my_recycler_view_psum);
        //Linea para mejorar rendimiento. Se usa si sabemos que el contenido no afectara al tamaño del recyclerview
        mRecyclerView.setHasFixedSize(true);
        //Nuestro recycleview usara un linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //retrofit
        Call<PsumResponse> call = ApiAdapter.getInstance().getApi().getPsums();

        call.enqueue(new Callback<PsumResponse>() {
            @Override
            public void onResponse(Call<PsumResponse> call, Response<PsumResponse> response) {

                if(response.isSuccessful()){
                    psumArrayList=response.body().getSuministros();
                    mRecyclerView.setAdapter(new AdapterPsum(getActivity(),psumArrayList));
                    Log.d("En respuesta de los Suministros", "Tamaño de los Suministros => " + psumArrayList.size());
                }else{
                    Toast.makeText(getActivity(), response.body().getError(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<PsumResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}