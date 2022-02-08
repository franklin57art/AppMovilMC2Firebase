package com.example.appmovilmc2firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class UserDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
    }

    //Editar datos del Usuario
    // Activity nuevo donde aparezcan los datos del usuario sin que se puedan editar a no ser que
    // se pulse un boton que se llame editar. Una vez se pulse ese boton se podrá modificar los datos del usuario.
    // tambien habrá un boton borrar para eliminar al usuario de la base de datos
        /*//Guardar Datos
        saveButton.setOnClickListener(view -> {
            Map<String, Object> usuarios = new HashMap<>();
            usuarios.put("provider", provider);
            usuarios.put("name", nameEditText.getText().toString());
            usuarios.put("surname", surnameEditText.getText().toString());
            usuarios.put("address", addresEditText.getText().toString());
            usuarios.put("phone", phoneEditText.getText().toString());

            db.collection("usuarios").document(email).set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d("Exito", "DocumentSnapshot successfully written!");
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("Error", "Error writing document", e);
                        }
                    });
        });

        //Borrar Datos
        deleteButton.setOnClickListener(view -> {

        });

        //Obtener Datos
        getButton.setOnClickListener(view -> {
            db.collection("usuarios").document(email).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    //addresEditText.setText();
                }
            });
        });*/
}