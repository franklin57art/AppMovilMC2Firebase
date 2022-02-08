package com.example.appmovilmc2firebase;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseFirestore db;
    ProgressDialog mLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeMC2);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        mLoadingBar=new ProgressDialog(RegisterActivity.this);

        setup();
    }

    private void setup() {
        Toolbar tool = findViewById(R.id.toolbarRegister);
        tool.setTitle("Registro");

        EditText emailEditText = findViewById(R.id.editTextEmailAddress);
        EditText passwordEditText = findViewById(R.id.editTextPassword);
        EditText telefonoEditText = findViewById(R.id.editTextPhone);
        EditText addressEditText = findViewById(R.id.editTextPostalAddress);
        EditText nombreEditText = findViewById(R.id.editTextName);
        EditText apellidosEditText = findViewById(R.id.editTextSurname);

        Button registro = findViewById(R.id.registerButton);
        Button cancelar = findViewById(R.id.cancelButton);

        registro.setOnClickListener(view -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String phone = telefonoEditText.getText().toString();
            if (email.isEmpty() || !email.contains("@")) {
                showError(emailEditText, "Email introducido no válido");
            } else if (password.isEmpty() || password.length()<6) {
                showError(passwordEditText, "La contraseña debe tener al menos 6 caracteres");
            } else if (phone.isEmpty() || phone.length()!=9) {
                showError(passwordEditText, "El numero de telefono debe tener 9 digitos");
            } else if (addressEditText.getText().toString().isEmpty()) {
                showError(addressEditText, "Es necesario poner una direccion postal");
            } else if (nombreEditText.getText().toString().isEmpty()) {
                showError(nombreEditText, "Es necesario poner un nombre");
            } else if (apellidosEditText.getText().toString().isEmpty()) {
                showError(apellidosEditText, "Es necesario poner apellidos");
            } else {
                mLoadingBar.setTitle("Registro");
                mLoadingBar.setMessage("Espere mientras registramos sus datos en el sistema");
                mLoadingBar.setCanceledOnTouchOutside(false);
                mLoadingBar.show();
                mFirebaseAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mLoadingBar.dismiss();
                            Map<String, Object> usuarios = new HashMap<>();
                            usuarios.put("nombre", nombreEditText.getText().toString());
                            usuarios.put("apellidos", apellidosEditText.getText().toString());
                            usuarios.put("dirección", addressEditText.getText().toString());
                            usuarios.put("teléfono", telefonoEditText.getText().toString());
                            db.collection("usuarios").document(emailEditText.getText().toString()).set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(RegisterActivity.this, "Usuario registrado con éxito", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(RegisterActivity.this, AuthActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(i);
                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("Error", "Error writing database", e);
                                        }
                                    });
                        } else {
                            Toast.makeText(RegisterActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        cancelar.setOnClickListener(view -> {
            mLoadingBar.setTitle("Saliendo...");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();
            Intent i = new Intent(this, AuthActivity.class);
            startActivity(i);
        });
    }

    private void showError(EditText input, String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        input.setError(s);
        input.requestFocus();
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}