package com.example.appmovilmc2firebase.adaptadores;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmovilmc2firebase.interfaces.iComunicaFragments;
import com.example.appmovilmc2firebase.models.Client;
import com.example.appmovilmc2firebase.ui.clientes.RegisterClientActivity;
import com.example.appmovilmc2firebase.utils.PreferenceHelper;

import java.util.ArrayList;
import java.util.List;

import appmovilmc2firebase.R;

public class ClientesAdapter extends RecyclerView.Adapter<ClientesAdapter.ClientesHolder> implements View.OnClickListener{

    private static final String TAG = "ClientesAdapter";

    public Button mAddClientButton;
    public TableRow mDataClientTable;

    private LayoutInflater inflater;
    private ArrayList<Client> mClientList;

    private Context context;

    private PreferenceHelper preferenceHelper;

    private int typeUser = 00;

    private String celda = "";
    private Client cl = null;
    private List<String> celdaList;

    //listener
    private View.OnClickListener listener;

    //referencias para comunicar con fragment
    Activity activity;
    iComunicaFragments interfaceComunicaFragments;

    public ClientesAdapter(Context context, ArrayList<Client> clientList) {
        this.inflater = LayoutInflater.from(context);
        this.mClientList = clientList;
    }

    public ClientesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_row_client, parent, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemView.setLayoutParams(layoutParams);

        itemView.setOnClickListener(this);

        context = parent.getContext();

        preferenceHelper = new PreferenceHelper(context);
        //Convierto la variable id_pt_user obtenida en el login y guardada con el shared preferences como String a Int.
        typeUser = Integer.parseInt(preferenceHelper.getType());

        mAddClientButton = itemView.findViewById(R.id.buttonAddClient);
        mAddClientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeUser == 7) {
                    Intent intent = new Intent(context, RegisterClientActivity.class);
                    context.startActivity(intent);
                } else {
                    Log.e("TAG", "EL TYPE DEL USUARIO NO ES DE TIPO PARTNER ADMINISTRADOR. Es un type tipo: " + typeUser);
                    showAlert();
                }
            }
        });

        celdaList = new ArrayList<>();

        mDataClientTable = itemView.findViewById(R.id.tableRowDatosClientes);
        mDataClientTable.setClickable(true);
        mDataClientTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataClientTable = (TableRow) v;
                cl = new Client();

                for (int i = 0; i < mClientList.size(); i++) {
                    TextView cell = (TextView) mDataClientTable.getChildAt(i);
                    celda = cell.getText().toString();
                    Log.e(TAG, celda);
                    celdaList.add(celda);
                }

                Log.e(TAG, celdaList.toString());

                Intent intent = new Intent(context, GeneralDataClientesAdapter.class);
                //Paso datos de este Activity al Activity GDClientesAdapter
                intent.putExtra("valorCelda", (Parcelable) celdaList);

                //Paso datos de este Activity al FragmentGDClientFragment

                context.startActivity(intent);
            }
        });


        return new ClientesHolder(itemView);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ClientesHolder holder, int position) {
        holder.mNombre2.setText(mClientList.get(position).getName().toString());
        holder.mIdfiscal2.setText(mClientList.get(position).getId_fiscal().toString());
        //holder.mUltimoacceso2.setText(mClientList.get(position).getLast_access().toString());

    }

    @Override
    public int getItemCount() {
        return mClientList.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null){
            listener.onClick(v);
        }
    }

    public static class ClientesHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mNombre, mNombre2, mIdfiscal, mIdfiscal2, mUltimoacceso, mUltimoacceso2;
        public SearchView mSearchView;

        public ClientesHolder(View itemView) {
            super(itemView);
            mSearchView = itemView.findViewById(R.id.searchViewClient);
            mNombre = itemView.findViewById(R.id.nombrerazonsocialindice);
            mIdfiscal = itemView.findViewById(R.id.idfiscalindice);
            mUltimoacceso = itemView.findViewById(R.id.ultimoaccesoindice);
            mNombre2 = itemView.findViewById(R.id.nombrerazonsocialindice2);
            mIdfiscal2 = itemView.findViewById(R.id.idfiscalindice2);
            mUltimoacceso2 = itemView.findViewById(R.id.ultimoaccesoindice2);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Error");
        builder.setMessage("No es usted un usuario ADMMINISTRADOR. No tiene permiso de escritura");
        builder.setPositiveButton("Aceptar", null);
        builder.create();
        builder.show();
    }
}
