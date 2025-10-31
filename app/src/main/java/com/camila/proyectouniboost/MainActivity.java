package com.camila.proyectouniboost;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class MainActivity extends AppCompatActivity {

    ListView Lista;
    FirebaseFirestore db;

    String[][] datos = {
            {"Ingeniería de Software y Computación", "Coordinador Juan Pablo Diago ", "9 semestres", "9", "Educamos con calidad para formar Ingenieros líderes de las disciplinas de la Ingeniería de Software y computación, que, a través de su pensamiento crítico, sentido de emprendimiento y compromiso social y ambiental, son capaces de crear soluciones tecnológicas innovadoras a problemas reales del entorno. Nuestras propuestas académicas, de extensión e investigativas, fortalecen la construcción del conocimiento en el área de la tecnología."},
            {"Derecho", "Coordinadora Nayda Ordóñez Torres", "10 semestres", "7", "Derecho es un programa que forma abogados integrales, interdisciplinarios y éticos..."},
            {"Administración de Empresas", "Coordinador Martín José Valencia Gutierre", "9 semestres", "8", "La dirección empresarial es un pilar fundamental..."},
            {"Entrenamiento Deportivo", "Coordinador John Pablo Sandoval Paz", "9 semestres", "8", "La actual denominación del programa es Entrenamiento Deportivo..."},
            {"Licenciatura en Educación Infantil", "Coordinadora Ninfa Rosa Mejía Florez", "10 semestres", "9", "El objetivo general del programa es formar Licenciados en Educación Infantil..."},
            {"Contaduría Pública", "Coordinador Martín José Valencia Gutierrez", "10 semestres", "9", "Proyectar el programa de CONTADURÍA PUBLICA a nivel local y regional..."},
            {"Ingeniería Ambiental y de Saneamiento", "Coordinador Juan Pablo Prado", "10 semestres", "9", "El programa de Ingeniería Ambiental y de Saneamiento será reconocido..."},
            {"Finanzas y Negocios Internacionales", "Coordinadora Miryam Eugenia Peña Martinez", "9 semestres", "9", "Los ambientes empresariales cada vez más globalizados y competitivos..."},
            {"Ingeniería Civil", "Coordinador Juan Pablo Prado", "10 semestres", "9", "El ingeniero Civil de la Corporación Universitaria Autónoma del Cauca..."},
            {"Ingeniería Energética", "Coordinadora Yeny Lucía Erazo Hoyos", "10 semestres", "8", "En la actualidad, la Ingeniería Energética se constituye como la solución..."}
    };

    // MISMO tamaño que 'datos' (10)
    int[] datosImg = {
            R.drawable.ingenieria_software,
            R.drawable.derecho,
            R.drawable.administracion,
            R.drawable.entrenamiento_deportivo,
            R.drawable.licenciatura,
            R.drawable.contaduria,
            R.drawable.ambiental,
            R.drawable.finanzas,
            R.drawable.civil,
            R.drawable.energetica
    };

    // ---- UI refs
    private TextView btnUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();

        // ---- refs
        btnUsuario = findViewById(R.id.btnUsuario);

        // Enlaces
        TextView textPaginaweb = findViewById(R.id.textPaginaweb);
        textPaginaweb.setOnClickListener(v ->
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.uniautonoma.edu.co/")))
        );

        TextView linkText = findViewById(R.id.text_resdessociales);
        linkText.setOnClickListener(v ->
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/uniautonomadc?locale=es_LA")))
        );

        TextView tvIrASegunda = findViewById(R.id.textRegistro);
        tvIrASegunda.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, registro.class))
        );

        // Lista
        Lista = findViewById(R.id.lvLista);
        Lista.setAdapter(new Adaptador(this, datos, datosImg));
        Lista.setOnItemClickListener((parent, view, position, id) -> {
            Intent visorDetalles = new Intent(view.getContext(), Detallecarreras.class);
            visorDetalles.putExtra("TIT", datos[position][0]);
            visorDetalles.putExtra("DET", datos[position][4]);
            startActivity(visorDetalles);
        });

        // Configura comportamiento del botón de usuario
        configurarBotonUsuario();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Cada vez que volvemos a la pantalla, refrescamos el estado del botón
        actualizarEstadoBotonUsuario();
    }

    private void configurarBotonUsuario() {
        btnUsuario.setOnClickListener(v -> {
            String correoGuardado = getSharedPreferences("usuario", MODE_PRIVATE).getString("correo", null);
            if (correoGuardado == null) return;

            // Crea el popup card
            View popupView = getLayoutInflater().inflate(R.layout.menu_usuario, null);
            PopupWindow popup = new PopupWindow(
                    popupView,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    true
            );
            popup.setElevation(10);

            TextView nombre = popupView.findViewById(R.id.txtNombreUsuario);
            TextView correo = popupView.findViewById(R.id.txtCorreoUsuario);
            TextView inicial = popupView.findViewById(R.id.iconoInicial);
            Button btnCerrarSesion = popupView.findViewById(R.id.btnCerrarSesion);

            // Estado inicial
            nombre.setText("Cargando...");
            correo.setText(correoGuardado);
            inicial.setText("?");

            // Muestra anclado al botón
            popup.showAsDropDown(v, -250, 20, Gravity.END);

            // Consulta Firestore por el correo guardado
            db.collection("usuarios")
                    .whereEqualTo("correo", correoGuardado)
                    .get()
                    .addOnSuccessListener(qs -> {
                        if (!qs.isEmpty()) {
                            for (QueryDocumentSnapshot doc : qs) {
                                String nombreUsuario = doc.getString("nombre");
                                String correoUsuario = doc.getString("correo");
                                if (nombreUsuario != null && !nombreUsuario.isEmpty()) {
                                    nombre.setText(nombreUsuario);
                                    inicial.setText(String.valueOf(nombreUsuario.charAt(0)).toUpperCase());
                                } else {
                                    nombre.setText("Usuario");
                                    inicial.setText("?");
                                }
                                correo.setText(correoUsuario != null ? correoUsuario : correoGuardado);
                            }
                        } else {
                            nombre.setText("Usuario no encontrado");
                            inicial.setText("?");
                        }
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Error al cargar datos: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                    );

            // Cerrar sesión
            btnCerrarSesion.setOnClickListener(b -> {
                popup.dismiss();
                // Limpia preferencias
                getSharedPreferences("usuario", MODE_PRIVATE).edit().clear().apply();
                Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();

                // Volver/Quedar en la pantalla principal con el botón oculto
                Intent i = new Intent(MainActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            });
        });
    }

    private void actualizarEstadoBotonUsuario() {
        String correoGuardado = getSharedPreferences("usuario", MODE_PRIVATE).getString("correo", null);

        if (correoGuardado == null) {
            // No hay sesión -> ocultar botón
            btnUsuario.setVisibility(View.GONE);
            return;
        }

        // Sí hay sesión -> mostrar botón y cargar inicial
        btnUsuario.setVisibility(View.VISIBLE);
        btnUsuario.setText("?"); // valor provisional mientras llega Firestore

        db.collection("usuarios")
                .whereEqualTo("correo", correoGuardado)
                .limit(1)
                .get()
                .addOnSuccessListener(qs -> {
                    if (!qs.isEmpty()) {
                        String nombreUsuario = qs.getDocuments().get(0).getString("nombre");
                        if (nombreUsuario != null && !nombreUsuario.isEmpty()) {
                            btnUsuario.setText(String.valueOf(nombreUsuario.charAt(0)).toUpperCase());
                        } else {
                            btnUsuario.setText("U");
                        }
                    } else {
                        btnUsuario.setText("U");
                    }
                })
                .addOnFailureListener(e -> btnUsuario.setText("U"));
    }
}
