package com.example.appmovilmc2firebase;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.appmovilmc2firebase.interfaces.iComunicaFragments;
import com.example.appmovilmc2firebase.models.Client;
import com.example.appmovilmc2firebase.models.PuntosDeMedida;
import com.example.appmovilmc2firebase.ui.clientes.GeneralDataClientFragment;
import com.example.appmovilmc2firebase.ui.estadoMedidores.GeneralDataEstadomedidoresFragment;
import com.example.appmovilmc2firebase.ui.puntosDeMedida.GeneralDataPuntosmedidaFragment;
import com.example.appmovilmc2firebase.utils.PreferenceHelper;
import com.google.android.material.navigation.NavigationView;

import appmovilmc2firebase.R;

public class HomeActivity extends AppCompatActivity implements iComunicaFragments {

    private AppBarConfiguration mAppBarConfiguration;
    private ProgressDialog pdDialog;

    private TextView tVNameNavHeader, tvEmailNavHeader;

    private PreferenceHelper preferenceHelper;

    private String authValue = "";

    //variable del fragment
    GeneralDataEstadomedidoresFragment generalDataEstadomedidoresFragment;
    GeneralDataClientFragment generalDataClientFragment;
    GeneralDataPuntosmedidaFragment generalDataPuntosDeMedidaFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Home");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Leo los datos del primer inicio
        SharedPreferences sp = getSharedPreferences("PRIMER_ACCESO", Context.MODE_PRIVATE);
        boolean primerAcceso = sp.getBoolean("primer_acceso", true);

        preferenceHelper = new PreferenceHelper(this);

        pdDialog = new ProgressDialog(HomeActivity.this);

        if (primerAcceso) {
            showWelcome();
            primerAcceso = false;
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("primer_acceso", primerAcceso).apply();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_clientes, R.id.nav_puntosdemedida, R.id.nav_estadomedidores, R.id.nav_usuarios, R.id.nav_configuracion)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                navigationView.removeOnLayoutChangeListener(this);

                tVNameNavHeader = navigationView.findViewById(R.id.nameTextView_navHeader);
                tvEmailNavHeader = navigationView.findViewById(R.id.emailtextView_navHeader);

                tVNameNavHeader.setText(preferenceHelper.getName());
                tvEmailNavHeader.setText(preferenceHelper.getEmail());
            }
        });

    }

    public void showWelcome() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Bienvenido a EmeceCuadrado");
        builder.setMessage("Bienvenido " + preferenceHelper.getName());
        builder.setPositiveButton("Aceptar", null);
        builder.create();
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        ((MenuInflater) inflater).inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            /*case R.id.visual_edit:
                ajustes();
                return true;*/
            case R.id.disconnect:
                desconectar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void desconectar() {
        //Borrado de datos
        preferenceHelper.putIsLogin(false);
        pdDialog.setTitle("Saliendo...");
        pdDialog.setCanceledOnTouchOutside(false);
        pdDialog.show();
        Intent i = new Intent(this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        HomeActivity.this.finish();
    }

    //Abrira un nuevo acitvity para editar los datos del Perfil. Parecido al de registar usuario
    private void ajustes() {
        //hacer un fragment que cambie el modo de la aplicacion entre claro y oscuro. El tamaño de la letra. Y tenga 4-5 estilos de letra.
        Intent i = new Intent(this, AjustesActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void enviarEstadoDeMedidores(PuntosDeMedida pdm) {
        //Aqui se realiza toda la logica del envio de datos desde el fragment Estado de medidores al general data estado de medidores
        generalDataEstadomedidoresFragment = new GeneralDataEstadomedidoresFragment();
        //objeto bundle para pasar la informacion
        Bundle envioDatos = new Bundle();
        //eviamos el objeto que esta llegando con el serializable
        envioDatos.putSerializable("objetoPuntoDeMedida", pdm);
        generalDataEstadomedidoresFragment.setArguments(envioDatos);
        //abrir Fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contenedor, generalDataEstadomedidoresFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Override
    public void enviarCliente(Client cl) {
        //Aqui se realiza toda la logica del envio de datos desde el fragment Clientes al general data estado de medidores
        generalDataClientFragment = new GeneralDataClientFragment();
        //objeto bundle para pasar la informacion
        Bundle envioDatos = new Bundle();
        //eviamos el objeto que esta llegando con el serializable
        envioDatos.putSerializable("objetoCliente", cl);
        generalDataClientFragment.setArguments(envioDatos);
        //abrir Fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contenedor, generalDataClientFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Override
    public void enviarPuntoDeMedida(PuntosDeMedida pdm) {
        //Aqui se realiza toda la logica del envio de datos desde el fragment Estado de medidores al general data estado de medidores
        generalDataPuntosDeMedidaFragment = new GeneralDataPuntosmedidaFragment();
        //objeto bundle para pasar la informacion
        Bundle envioDatos = new Bundle();
        //eviamos el objeto que esta llegando con el serializable
        envioDatos.putSerializable("objetoPuntoDeMedida", pdm);
        generalDataPuntosDeMedidaFragment.setArguments(envioDatos);
        //abrir Fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contenedor, generalDataPuntosDeMedidaFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    /*public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Desea salir de Emece2App?")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.show();
        }
        return super.onKeyDown(keyCode, event);
    }*/

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
