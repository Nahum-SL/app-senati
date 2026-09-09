package com.example.iniciar_sesion;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Registrar extends AppCompatActivity {

    EditText edtApellidosRE, edtNombresRE, edtTelefonoRE, edtDireccionRE,edtEmailRE;
    Button btnGuardar, btnCancelar;
    RequestQueue requestQueue;

    private final String URL = "http://10.0.2.2:3000/alumnos";

    /**
     * Carga los datos necesarios para el funcionamiento de la logica del activity
     * */
    private void loadUI() {
        edtApellidosRE = findViewById(R.id.edtApellidosRE);
        edtNombresRE = findViewById(R.id.edtNombresRE);
        edtTelefonoRE = findViewById(R.id.edtTelefonoRE);
        edtDireccionRE = findViewById(R.id.edtDireccionRE);
        edtEmailRE = findViewById(R.id.edtEmailRE);

        btnGuardar = findViewById(R.id.btnGuardar);
        btnCancelar = findViewById(R.id.btnCancelar);
    }

    /**
     * Se encarga de verificar que todos los campos esten llenos
     * antes de enviar los datos
     * */
    private boolean verificarVacios() {
        if (edtApellidosRE.getText().toString().isEmpty()) {
            edtApellidosRE.setError("Ingrese sus apellidos");
            edtApellidosRE.requestFocus();
            return false;
        }
        if (edtNombresRE.getText().toString().isEmpty()) {
            edtNombresRE.setError("Ingrese sus nombres");
            edtNombresRE.requestFocus();
            return false;
        }
        if (edtTelefonoRE.getText().toString().isEmpty()) {
            edtTelefonoRE.setError("Ingrese su telefono");
            edtTelefonoRE.requestFocus();
            return false;
        }
        if (edtDireccionRE.getText().toString().isEmpty()) {
            edtDireccionRE.setError("Ingrese su dirección");
            edtDireccionRE.requestFocus();
            return false;
        }
        if (edtEmailRE.getText().toString().isEmpty()) {
            edtEmailRE.setError("Ingrese su email");
            edtEmailRE.requestFocus();
            return false;
        }
        return true;
    }

    /**
     * Limpia las cajas de texto
     * */
    private void limpiar() {
        edtApellidosRE.setText(null);
        edtDireccionRE.setText(null);
        edtEmailRE.setText(null);
        edtNombresRE.setText(null);
        edtTelefonoRE.setText(null);
    }

    /**
     * Permite modificar las notificaciones que se mostraran despues de un evento
     * */
    private void notificar(String mensaje, Integer id) {
        Toast.makeText(getApplicationContext(), mensaje + " - ID: " + id, Toast.LENGTH_SHORT).show();
    }
    private void notificar(String mensaje) {
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar);

        this.loadUI();

        btnGuardar.setOnClickListener(view -> {
            if (verificarVacios()) {
                registrarAlumno();
            }
        });
        btnCancelar.setOnClickListener(view -> {
            limpiar();
        });
    }

    /**
     * Envia los datos ingresados del formulario a la base de datos
     * */
    private void registrarAlumno() {
        // Objeto conexion
        requestQueue = Volley.newRequestQueue(this);

        // Usamos la clase Alumno para asignar los datos
        Alumno alumno = new Alumno(
                edtApellidosRE.getText().toString(),
                edtNombresRE.getText().toString(),
                edtTelefonoRE.getText().toString(),
                edtDireccionRE.getText().toString(),
                edtEmailRE.getText().toString()
        );

        // Creamos un JSON que contendra los datos a enviar
        JSONObject jsonObject = new JSONObject();

        // Obtenemos los datos al JSON
        try {
            jsonObject.put("apellidos", alumno.getApellidos());
            jsonObject.put("nombres", alumno.getNombres());
            jsonObject.put("telefono", alumno.getTelefono());
            jsonObject.put("direccion", alumno.getDireccion());
            jsonObject.put("email", alumno.getEmail());
        } catch (JSONException e) {
            Log.e("Registrar", "Error creando un JSON", e);
            return;
        };

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                URL,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {

                            String mensaje = jsonObject.getString("message");
                            Integer id = jsonObject.getInt("id");

                            notificar(mensaje, id);
                            limpiar();

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        notificar("No se pudo guardar");
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }
}