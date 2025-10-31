package com.camila.proyectouniboost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class registro extends AppCompatActivity {

    // UI
    private EditText edtNombre, edtCorreo, edtCelular;
    private Button btnRegistrate, btnIniciarSesion;
    private ImageButton btnBack;

    // Firebase
    private FirebaseFirestore db;

    // Estados de UI
    private static final String KEY_MODE = "ui_mode";
    private static final int MODE_HOME = 0;      // Pantalla base con dos botones
    private static final int MODE_REGISTER = 1;  // Card de registro (3 campos)
    private static final int MODE_LOGIN = 2;     // Card de login (solo correo)
    private int currentMode = MODE_HOME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();

        // refs
        edtNombre = findViewById(R.id.edtNombre);
        edtCorreo = findViewById(R.id.edtCorreo);
        edtCelular = findViewById(R.id.edtCelular);
        btnRegistrate = findViewById(R.id.btnRegistrate);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        btnBack = findViewById(R.id.btnAtras);

        // Restaurar modo si hay
        if (savedInstanceState != null) {
            currentMode = savedInstanceState.getInt(KEY_MODE, MODE_HOME);
        }
        applyMode(currentMode);

        // Flecha atrás: navega dentro de la misma pantalla
        btnBack.setOnClickListener(v -> {
            if (currentMode == MODE_HOME) {
                // Estando en HOME, sí permitimos ir hacia atrás (a la pantalla principal)
                getOnBackPressedDispatcher().onBackPressed(); // o finish();
            } else {
                // En REGISTER o LOGIN, retrocede al HOME interno (sin salir de esta Activity)
                handleBackWithinScreen();
            }
        });


        // Botón REGÍSTRATE
        btnRegistrate.setOnClickListener(v -> {
            if (currentMode == MODE_HOME) {
                switchToRegister();
            } else if (currentMode == MODE_REGISTER) {
                registrarUsuario();
            }
        });

        // Botón INICIAR SESIÓN
        btnIniciarSesion.setOnClickListener(v -> {
            if (currentMode == MODE_HOME) {
                switchToLogin();
            } else if (currentMode == MODE_LOGIN) {
                iniciarSesion();
            } else if (currentMode == MODE_REGISTER) {
                // Si estuviera en registro y se toca "Iniciar sesión" (si decides mostrarlo),
                // te llevo al login. Por ahora en MODE_REGISTER lo ocultamos.
                switchToLogin();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Botón físico atrás: primero vuelve al HOME de esta pantalla
        if (currentMode != MODE_HOME) {
            handleBackWithinScreen();
        } else {
            super.onBackPressed();
        }
    }

    private void handleBackWithinScreen() {
        // Volver siempre al estado base de ESTA pantalla
        switchToHome();
    }

    private void switchToHome() {
        currentMode = MODE_HOME;
        applyMode(currentMode);
    }

    private void switchToRegister() {
        currentMode = MODE_REGISTER;
        applyMode(currentMode);
    }

    private void switchToLogin() {
        currentMode = MODE_LOGIN;
        applyMode(currentMode);
    }

    private void applyMode(int mode) {
        // Limpia errores previos
        edtNombre.setError(null);
        edtCorreo.setError(null);
        edtCelular.setError(null);

        if (mode == MODE_HOME) {
            // Oculta campos, muestra ambos botones con textos base
            edtNombre.setVisibility(View.GONE);
            edtCorreo.setVisibility(View.GONE);
            edtCelular.setVisibility(View.GONE);

            btnRegistrate.setVisibility(View.VISIBLE);
            btnRegistrate.setText("REGÍSTRATE");

            btnIniciarSesion.setVisibility(View.VISIBLE);
            btnIniciarSesion.setText("INICIAR SESIÓN");

        } else if (mode == MODE_REGISTER) {
            // Muestra 3 campos, oculta botón iniciar sesión
            edtNombre.setVisibility(View.VISIBLE);
            edtCorreo.setVisibility(View.VISIBLE);
            edtCelular.setVisibility(View.VISIBLE);

            btnRegistrate.setVisibility(View.VISIBLE);
            btnRegistrate.setText("CONFIRMAR REGISTRO");

            // Aquí ocultamos “Iniciar sesión” para evitar confusiones
            btnIniciarSesion.setVisibility(View.GONE);

        } else if (mode == MODE_LOGIN) {
            // Solo correo visible, botón REGÍSTRATE oculto
            edtNombre.setVisibility(View.GONE);
            edtCelular.setVisibility(View.GONE);
            edtCorreo.setVisibility(View.VISIBLE);

            btnRegistrate.setVisibility(View.GONE); // <- que no aparezca en login
            btnIniciarSesion.setVisibility(View.VISIBLE);
            btnIniciarSesion.setText("INICIAR SESIÓN");
        }
    }

    private void registrarUsuario() {
        String nombre = edtNombre.getText().toString().trim();
        String correo = edtCorreo.getText().toString().trim();
        String celular = edtCelular.getText().toString().trim();

        if (nombre.isEmpty()) { edtNombre.setError("Ingresa tu nombre"); edtNombre.requestFocus(); return; }
        if (correo.isEmpty()) { edtCorreo.setError("Ingresa tu correo"); edtCorreo.requestFocus(); return; }
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) { edtCorreo.setError("Correo inválido"); edtCorreo.requestFocus(); return; }
        if (celular.isEmpty()) { edtCelular.setError("Ingresa tu celular"); edtCelular.requestFocus(); return; }

        // (Opcional) validar que no exista ya
        db.collection("usuarios")
                .whereEqualTo("correo", correo)
                .limit(1)
                .get()
                .addOnSuccessListener(qs -> {
                    if (!qs.isEmpty()) {
                        Toast.makeText(this, "Ya existe un usuario con ese correo", Toast.LENGTH_SHORT).show();
                    } else {
                        Map<String, Object> usuario = new HashMap<>();
                        usuario.put("nombre", nombre);
                        usuario.put("correo", correo);
                        usuario.put("celular", celular);
                        usuario.put("createdAt", System.currentTimeMillis());

                        db.collection("usuarios")
                                .add(usuario)
                                .addOnSuccessListener(ref -> {
                                    Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();

                                    // Limpia campos y vuelve al HOME de ESTA pantalla (no salir)
                                    edtNombre.setText("");
                                    edtCorreo.setText("");
                                    edtCelular.setText("");
                                    switchToHome(); // <-- nos “deja” en la pantalla de registro
                                })
                                .addOnFailureListener(e ->
                                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                                );
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error al validar correo: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

    private void iniciarSesion() {
        String correo = edtCorreo.getText().toString().trim();

        if (correo.isEmpty()) { edtCorreo.setError("Ingresa tu correo"); edtCorreo.requestFocus(); return; }
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) { edtCorreo.setError("Correo inválido"); edtCorreo.requestFocus(); return; }

        db.collection("usuarios")
                .whereEqualTo("correo", correo)
                .limit(1)
                .get()
                .addOnSuccessListener(snapshots -> {
                    if (!snapshots.isEmpty()) {
                        // Opcional: traer nombre por conveniencia
                        String nombre = null;
                        for (QueryDocumentSnapshot doc : snapshots) {
                            nombre = doc.getString("nombre");
                            break;
                        }

                        getSharedPreferences("usuario", MODE_PRIVATE)
                                .edit()
                                .putString("correo", correo)
                                .apply();

                        Toast.makeText(this, "¡Bienvenido" + (nombre != null ? ", " + nombre : "") + "!", Toast.LENGTH_SHORT).show();

                        // Llevar a pantalla principal de tu app
                        // Cambia a la que uses realmente (MainActivity / pantalla_principal)
                        startActivity(new android.content.Intent(this, pantalla_principal.class));
                        finish();

                    } else {
                        Toast.makeText(this, "Correo no registrado", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error al iniciar sesión: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_MODE, currentMode);
    }
}
