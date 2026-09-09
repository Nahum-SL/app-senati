package com.example.iniciar_sesion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Indice extends AppCompatActivity {

    Button btnListar, btnRegistrar, btnBuscar, btnAcercaDe;

    private void loadUI() {
        btnListar = findViewById(R.id.btnListar);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnAcercaDe = findViewById(R.id.btnAcercaDe);
    }

    private void openActivity(Class interfaz) {
        Intent i = new Intent(getApplicationContext(), interfaz);
        startActivity(i);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_indice);

        this.loadUI();

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