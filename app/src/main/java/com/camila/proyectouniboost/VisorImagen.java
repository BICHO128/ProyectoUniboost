package com.camila.proyectouniboost;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class VisorImagen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visor_imagen);

        ImageButton btnBack = findViewById(R.id.btnAtras);
        btnBack.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        ImageView ivImagenCompleta = findViewById(R.id.ivImagenCompleta);

        ivImagenCompleta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VisorImagen.this, "", Toast.LENGTH_SHORT).show();

                Intent ir = new Intent(VisorImagen.this, carrera_ingsotfware.class);
                startActivity(ir);
            }
        });


        }
    }
