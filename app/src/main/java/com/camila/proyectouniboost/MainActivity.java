package com.camila.proyectouniboost;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.net.Uri;
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
            {"Ingeniería de Software y Computación", "Coordinador Juan Pablo Diago ", "Ingeniería de Software y Computación", "9", "Educamos con calidad para formar Ingenieros líderes de las disciplinas de la Ingeniería de Software y computación, que, a través de su pensamiento crítico, sentido de emprendimiento y compromiso social y ambiental, son capaces de crear soluciones tecnológicas innovadoras a problemas reales del entorno. Nuestras propuestas académicas, de extensión e investigativas, fortalecen la construcción del conocimiento en el área de la tecnología."},
            {"Derecho", "Coordinadora Nayda Ordóñez Torres", "10 semestre", "7", "Derecho es un programa que forma abogados integrales, interdisciplinarios y éticos con altas capacidades para el ejercicio de la profesión, mediante la preparación para el conocimiento, la investigación, la interpretación y la argumentación dentro del mundo jurídico. Lo anterior con el fin de lograr la realización de la Justicia como bien fundamental de una sociedad, aportando así al mejoramiento de las relaciones humanas."},
            {"Administración de Empresas", "Coordinador Martín José Valencia Gutierre  ","9 semestres", "8", "La dirección empresarial es un pilar fundamental en el éxito de cualquier organización en el mundo contemporáneo. A través de la innovación, la optimización de los recursos y la maximización de las inversiones corporativas, los administradores de empresas enfrentan los desafíos e identifican las oportunidades en el mundo globalizado."},
            {"Entrenamiento Deportivo", "Coordinador John Pablo Sandoval Paz ", "9 semestres", "La actual denominación del programa es Entrenamiento Deportivo, el nivel de formación del programa es universitario de pregrado, y el título que otorga es Profesional en Entrenamiento Deportivo, es importante destacar que se soporta la denominación teniendo en cuenta la Ley del Entrenador Deportivo (2020), que busca “dignificar la profesión de entrenador deportivo, proteger a los deportistas y garantizar la formación, capacitación y actualización de los entrenadores deportivos”, por tanto permite seguir ofertando una oportunidad de profesionalización de las personas que ejercen la labor deportiva como entrenadores, conllevando a su cualificación como profesionales."},
            {"Licenciatura en Educación Infantil", " Coordinadora Ninfa Rosa Mejía Florez ", "10 semestres", "9", "El objetivo general del programa es formar Licenciados en Educación Infantil, con formación pedagógica incluyente desde una confluencia transdisciplinar y multicultural de saberes, que definen un perfil asociado al desarrollo de competencias  en diversos contextos de los ámbitos local, nacional e internacional, lo que garantiza la proyección de profesionales competitivos, innovadores, emprendedores, con sensibilidad social y espíritu investigativo, capacitados para generar procesos de transformación de las diversas realidades asociadas a la primera infancia."},
            {"Contaduría Pública", "Coordinador Martín José Valencia Gutierrez ", "10 semestres", "9", "Proyectar el programa de CONTADURÍA PUBLICA a nivel local y regional para brindar instrumentos que permitan la optimización del conocimiento financiero y contable basado en la experiencia, al igual que contribuir a la formación integral del individuo mediante el desarrollo o perfeccionamiento de sus facultades intelectuales, afectivas y psicomotrices, buscando con lo anterior una verdadera alternativa educativa."},
            {"Ingeniería Ambiental y de Saneamiento", "Coordinador Juan Pablo Prado ", "10 semestres", "9", "El programa de Ingeniería Ambiental y de Saneamiento será reconocido en la región Pacífico colombiana, por la alta calidad de sus procesos académicos y de formación de profesionales con compromiso y responsabilidad ética, social y ambiental, frente a los retos del desarrollo sostenible, aunando esfuerzos desde el su campo de acción, para lograr un gran impacto desde la docencia, investigación y la proyección social."},
            {"Finanzas y Negocios Internacionales", "Coordinadora Miryam Eugenia Peña Martinez  ", " 9 semestres", "9", "Los ambientes empresariales cada vez más globalizados y competitivos demandan la formación de personas con la capacidad de reconocer oportunidades en entornos sociales, económicos, culturales y políticos diversos. Nuestros estudiantes potencian sus conocimientos y construyen habilidades en la gestión financiera, mercados internacionales, análisis de riesgos y logística internacional, a través de casos reales y proyectos prácticos que los prepararán para desafíos empresariales globales."},
            {"Ingeniería Civil", "Coordinador Juan Pablo Prado ", "10 semestres", "9", "El ingeniero Civil de la Corporación Universitaria Autónoma del Cauca, será un profesional integral e innovador, con amplia visión gerencial para planificar y gestionar proyectos de ingeniería civil, generador de oportunidades de emprendimiento y con capacidad para liderar proyectos con un enfoque en el desarrollo sostenible de la región y el país."},
            {"Ingeniería Energética", "Coordinadora Yeny Lucía Erazo Hoyos ", "10 semestres", "8", "En la actualidad, la Ingeniería Energética se constituye como la solución más apropiada para afrontar  la crisis ambiental  que se genera por la producción del dióxido de carbono,  uno de los gases de efecto invernadero que impactan el cambio climático a nivel mundial, gas que en su mayoría es producido en la actualidad por el uso desmedido de sistemas de generación y de consumo de energías convencionales y muy contaminantes,  poco eficientes y de poca calidad,  bajo este principio, la formación de los Ingenieros Energéticos se presenta como el motor para la generación de soluciones en materia energética que respondan a los retos nacionales y mundiales de la que se conoce como la transformación energética(cobertura, infraestructura, energías limpias, industria 4.0, eficiencia y disminución de consumo energético) que se va a vivir en las próximas décadas y que será factor clave para la mejorar la calidad de vida del entorno. De esta manera, el panorama laboral para nuestros futuros profesionales se presenta ampliamente favorable en el panorama local nacional y mundial."},
    };
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
            R.drawable.energetica,
            R.drawable.logo2
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();

        // === Botones de enlaces ===
        TextView textPaginaweb = findViewById(R.id.textPaginaweb);
        textPaginaweb.setOnClickListener(v -> {
            String url = "https://www.uniautonoma.edu.co/";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        });

        TextView linkText = findViewById(R.id.text_resdessociales);
        linkText.setOnClickListener(v -> {
            String url = "https://www.facebook.com/uniautonomadc?locale=es_LA";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        });

        // === Enlace al registro ===
        TextView tvIrASegunda = findViewById(R.id.textRegistro);
        tvIrASegunda.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, registro.class));
        });

        // === Lista de carreras ===
        Lista = findViewById(R.id.lvLista);
        Lista.setAdapter(new Adaptador(this, datos, datosImg));
        Lista.setOnItemClickListener((parent, view, position, id) -> {
            Intent visorDetalles = new Intent(view.getContext(), Detallecarreras.class);
            visorDetalles.putExtra("TIT", datos[position][0]);
            visorDetalles.putExtra("DET", datos[position][4]);
            startActivity(visorDetalles);
        });

        // === Botón de perfil ===
        TextView btnUsuario = findViewById(R.id.btnUsuario);

        btnUsuario.setOnClickListener(v -> {
            // Recuperar correo guardado del usuario logueado
            String correoGuardado = getSharedPreferences("usuario", MODE_PRIVATE)
                    .getString("correo", null);

            if (correoGuardado == null) {
                Toast.makeText(this, "No hay usuario activo. Inicia sesión.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, registro.class));
                finish();
                return;
            }

            // Crear vista del popup
            View popupView = getLayoutInflater().inflate(R.layout.menu_usuario, null);
            PopupWindow popup = new PopupWindow(popupView,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    true);
            popup.setElevation(10);

            TextView nombre = popupView.findViewById(R.id.txtNombreUsuario);
            TextView correo = popupView.findViewById(R.id.txtCorreoUsuario);
            TextView inicial = popupView.findViewById(R.id.iconoInicial);
            Button btnCerrarSesion = popupView.findViewById(R.id.btnCerrarSesion);

            // Mostrar popup (inicialmente con carga)
            nombre.setText("Cargando...");
            correo.setText(correoGuardado);
            inicial.setText("?");

            popup.showAsDropDown(v, -250, 20, Gravity.END);

            // Consultar Firestore según el correo guardado
            db.collection("usuarios")
                    .whereEqualTo("correo", correoGuardado)
                    .get()
                    .addOnSuccessListener(querySnapshot -> {
                        if (!querySnapshot.isEmpty()) {
                            for (QueryDocumentSnapshot doc : querySnapshot) {
                                String nombreUsuario = doc.getString("nombre");
                                String correoUsuario = doc.getString("correo");

                                nombre.setText(nombreUsuario.toUpperCase());
                                correo.setText(correoUsuario);
                                inicial.setText(String.valueOf(nombreUsuario.charAt(0)).toUpperCase());
                            }
                        } else {
                            nombre.setText("Usuario no encontrado");
                            inicial.setText("?");
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Error al cargar datos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });

            // === Cerrar sesión ===
            btnCerrarSesion.setOnClickListener(b -> {
                popup.dismiss();
                // Borrar SharedPreferences
                getSharedPreferences("usuario", MODE_PRIVATE).edit().clear().apply();

                Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, registro.class);
                startActivity(intent);
                finish();
            });
        });
    }
}