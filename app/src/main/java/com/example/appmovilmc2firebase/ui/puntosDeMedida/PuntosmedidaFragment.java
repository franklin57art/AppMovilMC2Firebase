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
import com.example.appmovilmc2firebase.io.ApiService;
import com.example.appmovilmc2firebase.models.Psum;
import com.example.appmovilmc2firebase.models.PsumAllowedPsums;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PuntosmedidaFragment extends Fragment {

    private  static final String TAG = "Puntos Medida Fragment";

    private FragmentPuntosdemedidaBinding binding;
    private PuntosmedidaViewModel puntosmedidaViewModel;
    private RecyclerView mRecyclerView;
    private ArrayList<Psum> psumList;

    private static final String BASE_URL = "https://partners.emececuadrado.com/api/";

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

        // Creamos un interceptor y le indicamos el log level a usar
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(HttpLoggingInterceptor.Level.BODY);
        // Asociamos el interceptor a las peticiones
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(logging).build();

        GsonBuilder gson = new GsonBuilder();
        gson.setLenient();

        //retrofit
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(httpClient).addConverterFactory(GsonConverterFactory.create(gson.create())).build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<PsumAllowedPsums> call = apiService.getPsums();

        call.enqueue(new Callback<PsumAllowedPsums>() {

            @Override
            public void onResponse(Call<PsumAllowedPsums> call, Response<PsumAllowedPsums> response) {
                Log.d(TAG, "En respuesta: Servidor respondiendo: " + response.toString());
                Log.d(TAG, "En respuesta: Información recibida: " + response.body().toString());
                if(response.isSuccessful()){
                    psumList=response.body().getAllowed_psums();
                    mRecyclerView.setAdapter(new AdapterPsum(getActivity(),psumList));
                    Log.e(TAG, "Tamaño de los Suministros => " + psumList.size());
                }else{
                    Toast.makeText(getActivity(), response.body().toString(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<PsumAllowedPsums> call, Throwable t) {
                Log.e(TAG, "ERROR en la respuesta de la API: " + t.getMessage());
                Toast.makeText(getActivity(), "Algo salio mal", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}