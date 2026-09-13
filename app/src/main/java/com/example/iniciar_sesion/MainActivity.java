package com.example.iniciar_sesion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;


public class MainActivity extends AppCompatActivity {

    EditText edtUsuario, edtPassword;
    Button btnLogin;

    private void loadUI() {
        edtUsuario = findViewById(R.id.edtUsuario);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
    }

    private void openActivity(Class interfaz) {
        Intent i = new Intent(getApplicationContext(), interfaz);
        startActivity(i);
    }


    private boolean verificarVacio() {
        if (edtUsuario.getText().toString().isEmpty()) {
            edtUsuario.setError("Ingrese un usuario");
            edtUsuario.requestFocus();
            return false;
        }

        if (edtPassword.getText().toString().isEmpty()) {
            edtPassword.setError("Ingrese una contraseña");
            edtPassword.requestFocus();
            return false;
        }

        return true;
    }

    private void verificarAdmin() {
        String passAdmin = "123456", usuAdmin = "Admin";

        String usuario = edtUsuario.getText().toString();
        String password = edtPassword.getText().toString();

        if (usuAdmin.equals(usuario) && passAdmin.equals(password)) {
            openActivity(Indice.class);
        } else {
            Toast.makeText(getApplicationContext(), "Credenciales invalidas", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        this.loadUI();

        btnLogin.setOnClickListener(view ->  {
            if (verificarVacio()) {
                verificarAdmin();
            }
        });
    }
}