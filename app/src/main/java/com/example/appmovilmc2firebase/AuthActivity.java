package com.example.appmovilmc2firebase;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class AuthActivity extends AppCompatActivity {

    private int GOOGLE_SIGN_IN = 100;

    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mFirebaseAuth;
    ProgressDialog mLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Splash
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.AppThemeMC2);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        //Analytics Event
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        //bundle.putString("message", "Integracion de Firebase completa");
        mFirebaseAnalytics.logEvent("InitScreen", bundle);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mLoadingBar = new ProgressDialog(AuthActivity.this);

        //Setup
        setup();
        session();
    }

    @Override
    protected void onStart() {
        super.onStart();
        findViewById(R.id.authLayout).setVisibility(View.VISIBLE);
    }

    private void session() {
        SharedPreferences prefs = (SharedPreferences) getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
        String email = prefs.getString("email", null);
        String provider = prefs.getString("provider", null);

        if (email != null && provider != null) {
            findViewById(R.id.authLayout).setVisibility(View.INVISIBLE);
            showHome(email, ProviderType.valueOf(provider));
        } else {

        }

    }

    private void setup() {
        setTitle("Autenticación");

        EditText emailEditText = findViewById(R.id.emailEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        TextView registroTextView = findViewById(R.id.registerTextView);
        TextView olvidarContraseniaTextView = findViewById(R.id.passwordForgotTextView);

        Button logInButton = findViewById(R.id.loginButton);
        Button googleButton = findViewById(R.id.loginButtonGoogle);

        /*emailEditText.setText("farestiz@alumnos.unex.es");
        passwordEditText.setText("123456");*/

        /*Log.d("Valor email: ",emailEditText.getText().toString());
        Log.d("Valor password: ",passwordEditText.getText().toString());*/

        logInButton.setOnClickListener(view -> {
            if (TextUtils.isEmpty(emailEditText.getText().toString())) {
                showError(emailEditText, "Email de usuario vacio. Es necesario introducir un email registrado");
            } else if (TextUtils.isEmpty(passwordEditText.getText().toString())) {
                showError(passwordEditText, "Contraseña vacia");
            } else {
                mLoadingBar.setTitle("Autenticacion");
                mLoadingBar.setMessage("Espere mientras comprobamos los datos introducidos");
                mLoadingBar.setCanceledOnTouchOutside(false);
                mLoadingBar.show();
                mFirebaseAuth.signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            showHome(task.getResult().getUser().getEmail(), ProviderType.BASIC);
                            mLoadingBar.dismiss();
                        } else {
                            showAlert();
                        }
                    }
                });
            }
        });

        googleButton.setOnClickListener(view -> {
            //Configuracion

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            mGoogleSignInClient.signOut();

            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
        });

        registroTextView.setOnClickListener(view -> {
            Intent i = new Intent(this, RegisterActivity.class);
            startActivity(i);
        });

        olvidarContraseniaTextView.setOnClickListener(view -> {
            Toast.makeText(this, "Aún no esta disponible esta opcion", Toast.LENGTH_LONG).show();
        });
    }

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("Se ha producido un error autenticando al usuario");
        builder.setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showError(EditText input, String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setPositiveButton("Aceptar", null);
        input.setError(s);
        input.requestFocus();
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showHome(String email, ProviderType provider) {
        Intent i = new Intent(this, HomeActivity.class);
        i.putExtra("email", email);
        i.putExtra("provider", provider.name());
        startActivity(i);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                    Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
                    FirebaseAuth.getInstance().signInWithCredential(credential);
                    if (task.isSuccessful()) {
                        showHome(account.getEmail(), ProviderType.GOOGLE);
                        mLoadingBar.dismiss();
                    } else {
                        showAlert();
                    }
                }
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                showAlert();
            }
        }
    }


}