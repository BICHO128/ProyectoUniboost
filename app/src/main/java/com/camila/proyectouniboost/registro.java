package com.camila.proyectouniboost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class registro extends AppCompatActivity {

    EditText edtNombre, edtCorreo, edtCelular;
    Button btnRegistrate, btnIniciarSesion;
    FirebaseFirestore db;

    boolean modoRegistro = false; // false = inicio sesión, true = registro

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();

        edtNombre = findViewById(R.id.edtNombre);
        edtCorreo = findViewById(R.id.edtCorreo);
        edtCelular = findViewById(R.id.edtCelular);
        btnRegistrate = findViewById(R.id.btnRegistrate);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);

        ImageButton btnBack = findViewById(R.id.btnAtras);
        btnBack.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        // --- Modo Registro ---
        btnRegistrate.setOnClickListener(v -> {
            if (!modoRegistro) {
                // Primera vez: mostrar campos de registro
                modoRegistro = true;
                edtNombre.setVisibility(View.VISIBLE);
                edtCorreo.setVisibility(View.VISIBLE);
                edtCelular.setVisibility(View.VISIBLE);
                btnIniciarSesion.setVisibility(View.GONE);
                btnRegistrate.setText("CONFIRMAR REGISTRO");
            } else {
                // Confirmar registro
                registrarUsuario();
            }
        });

        // --- Modo Inicio Sesión ---
        btnIniciarSesion.setOnClickListener(v -> {
            if (modoRegistro) {
                // Si venía de modo registro, volver al modo normal
                modoRegistro = false;
                edtNombre.setVisibility(View.GONE);
                edtCelular.setVisibility(View.GONE);
                btnRegistrate.setVisibility(View.VISIBLE);
                btnIniciarSesion.setText("INICIAR SESIÓN");
            } else {
                // Mostrar solo campo de correo si no está visible
                if (edtCorreo.getVisibility() == View.GONE) {
                    edtCorreo.setVisibility(View.VISIBLE);
                } else {
                    iniciarSesion();
                }
            }
        });
    }

    private void registrarUsuario() {
        String nombre = edtNombre.getText().toString().trim();
        String correo = edtCorreo.getText().toString().trim();
        String celular = edtCelular.getText().toString().trim();

        if (nombre.isEmpty() || correo.isEmpty() || celular.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            edtCorreo.setError("Correo inválido");
            return;
        }

        Map<String, Object> usuario = new HashMap<>();
        usuario.put("nombre", nombre);
        usuario.put("correo", correo);
        usuario.put("celular", celular);
        usuario.put("createdAt", System.currentTimeMillis());

        db.collection("usuarios")
                .add(usuario)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void iniciarSesion() {
        String correo = edtCorreo.getText().toString().trim();

        if (correo.isEmpty()) {
            edtCorreo.setError("Ingresa tu correo");
            return;
        }

        db.collection("usuarios")
                .whereEqualTo("correo", correo)
                .get()
                .addOnSuccessListener(snapshots -> {
                    if (!snapshots.isEmpty()) {
                        Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                        getSharedPreferences("usuario", MODE_PRIVATE)
                                .edit()
                                .putString("correo", correo)
                                .apply();

                        startActivity(new Intent(this, pantalla_principal.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Correo no registrado", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error al iniciar sesión: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
