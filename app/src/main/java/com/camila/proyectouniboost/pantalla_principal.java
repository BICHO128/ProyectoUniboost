package com.camila.proyectouniboost;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.bumptech.glide.Glide;

import android.widget.ImageView;

public class pantalla_principal extends AppCompatActivity {
    private static final int SPLASH_TIME_OUT=4000;
    ImageView gifView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);



        gifView=findViewById(R.id.gifView);

        //IMAGEN IMPORTADA DE INTERNET

        Glide.with(this)

                .load("https://i.pinimg.com/originals/c3/13/aa/c313aa0c7b8174e5b023202d36b429bd.gif")
                .into(gifView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(pantalla_principal.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME_OUT);


    }
}