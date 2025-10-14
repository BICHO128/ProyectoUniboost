package com.camila.proyectouniboost;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

public class Detallecarreras extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_carreras);


        ImageButton btnBack = findViewById(R.id.btnAtras);
        btnBack.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        TextView titulo = (TextView) findViewById(R.id.tbTituloDescripcion);
        TextView detalles = (TextView) findViewById(R.id.tvdescripcion);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if(b!=null) {
            titulo.setText(b.getString("TIT"));
            detalles.setText(b.getString("DET"));
    }
}
}