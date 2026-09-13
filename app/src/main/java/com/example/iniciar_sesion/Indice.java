package com.example.iniciar_sesion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

public class Indice extends AppCompatActivity {

    Button btnListaSimple, btnListar, btnRegistrar, btnBuscar, btnAcercaDe;

    private void loadUI() {
        btnListar = findViewById(R.id.btnListar);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnAcercaDe = findViewById(R.id.btnAcercaDe);
        btnListaSimple = findViewById(R.id.btnListaSimple);
    }

    private void openActivity(Class<?> interfaz) {
        Intent i = new Intent(Indice.this, interfaz);
        startActivity(i);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Instala el Splash screen con la imagen predefinida desde AndroidManifest.xml
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_indice);

        this.loadUI();

        btnListaSimple.setOnClickListener(view -> {
            openActivity(ListaSimple.class);
        });
        btnListar.setOnClickListener(view -> {
            openActivity(Listar.class);
        });
        btnRegistrar.setOnClickListener(view -> {
            openActivity(Registrar.class);
        });
        btnBuscar.setOnClickListener(view -> {
            openActivity(Buscar.class);
        });
        btnAcercaDe.setOnClickListener(view -> {
            openActivity(AcercaDe.class);
        });

    }
}