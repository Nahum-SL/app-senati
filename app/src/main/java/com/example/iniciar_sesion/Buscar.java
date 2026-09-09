package com.example.iniciar_sesion;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Buscar extends AppCompatActivity {

    EditText edtBuscarId, edtApellidos, edtNombres, edtTelefono, edtDireccion, edtEmail;
    Button btnBuscar, btnEliminar, btnActualizar, btnReiniciar;
    RequestQueue requestQueue;
    private void loadUI() {
        edtBuscarId = findViewById(R.id.edtBuscarId);
        edtApellidos = findViewById(R.id.edtApellidos);
        edtNombres = findViewById(R.id.edtNombres);
        edtTelefono = findViewById(R.id.edtTelefono);
        edtDireccion = findViewById(R.id.edtDireccion);
        edtEmail = findViewById(R.id.edtEmail);

        btnBuscar = findViewById(R.id.btnBuscar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnReiniciar = findViewById(R.id.btnReiniciar);
    }
    private final String URL = "http://10.0.2.2:3000/alumnos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_buscar);
        requestQueue = Volley.newRequestQueue(this);

        this.loadUI();

        btnBuscar.setOnClickListener(v -> {
            buscarDatosWS();
        });

        btnActualizar.setOnClickListener(v -> {
            mostrarPregunta("¿Estas seguro de guardar los cambios?", this::actualizarDatosWS);
        });

        // this:: --> Sintaxis de referencia de metodos
        btnEliminar.setOnClickListener(v -> {
            mostrarPregunta("¿Estas seguro de eliminar el alumno?", this::eliminarDatosWS);
        });

        btnReiniciar.setOnClickListener(v -> {
            mostrarPregunta("¿Desea limpiar los campos?", this::limpiar);
        });
    }

    private void mostrarPregunta(String mensaje, Runnable accionPositiva) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Buscar.this);
        builder.setTitle("Confirmación");
        builder.setMessage(mensaje);

        // Boton de respuesta
        // Positivo
        builder.setPositiveButton("Si", (dialog, which) -> {
            Log.i("Respuesta", "Afirmativo");
            if (accionPositiva != null) {
                accionPositiva.run();
            }
        });

        //Negativo
        builder.setNegativeButton("No", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void limpiar() {
        edtBuscarId.setText(null);
        edtApellidos.setText(null);
        edtDireccion.setText(null);
        edtEmail.setText(null);
        edtNombres.setText(null);
        edtTelefono.setText(null);
    }
    private void buscarDatosWS() {

        // Obtener el ID
        String id = edtBuscarId.getText().toString().trim();

        // Validar que exista la ID antes de una accion
        if (id.isEmpty()) {
            edtBuscarId.setError("Ingrese un ID");
            edtBuscarId.requestFocus();
            return;
        }

        // Construir la URL apartir de el ID
        String urlBuscar = URL + "/" + id;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                urlBuscar,
                null,
                response -> {
                    try {

                        JSONObject data = response.getJSONObject("data");

                        Alumno alumno = new Alumno(
                                data.getInt("id"),
                                data.getString("apellidos"),
                                data.getString("nombres"),
                                data.getString("telefono"),
                                data.getString("direccion"),
                                data.getString("email")
                        );

                        edtApellidos.setText(alumno.getApellidos());
                        edtNombres.setText(alumno.getNombres());
                        edtTelefono.setText(alumno.getTelefono());
                        edtDireccion.setText(alumno.getDireccion());
                        edtEmail.setText(alumno.getEmail());

                    } catch (JSONException e) {
                        Log.e("BUSCAR", "Error procesando alumno", e);
                    }

                    Log.d("BUSCAR", "Respuesta: " + response.toString());
                },
                error -> {
                    error.printStackTrace();
                }
        );

        requestQueue.add(request);
    };

    private void actualizarDatosWS() {

        // Obtener el ID
        String id = edtBuscarId.getText().toString().trim();

        // Validar que exista la ID antes de una accion
        if (id.isEmpty()) {
            edtBuscarId.setError("Ingrese un ID");
            edtBuscarId.requestFocus();
            return;
        }
        // Configurar la URL
        String urlBuscar = URL + "/" + id;

        // Crear el Objeto JSON con los nuevos datos
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("apellidos", edtApellidos.getText().toString());
            jsonObject.put("nombres", edtNombres.getText().toString());
            jsonObject.put("telefono", edtTelefono.getText().toString());
            jsonObject.put("direccion", edtDireccion.getText().toString());
            jsonObject.put("email", edtEmail.getText().toString());

        } catch (JSONException e) {
            Log.e("ACTUALIZADO", "Error creando el JSON", e);
        }

        // Crear la petición
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                urlBuscar,
                jsonObject,
                response -> {
                    try {
                        String mensaje = response.getString("message");

                        new AlertDialog.Builder(Buscar.this)
                                .setTitle("Actualización")
                                .setMessage(mensaje)
                                .setPositiveButton("OK", (dialog, which) -> {
                                    limpiar();
                                })
                                .show();

                    } catch (JSONException e) {
                        Log.e("ACTUALIZAR", "Error procesando respuesta", e);
                    }
                },
                error -> {
                    Log.e("ACTUALIZAR", "Error Volley", error);
                }
        );

        requestQueue.add((request));
    }

    private void eliminarDatosWS() {

        // Obtener el ID
        String id = edtBuscarId.getText().toString().trim();

        // Validar que exista la ID antes de una accion
        if (id.isEmpty()) {
            edtBuscarId.setError("Ingrese un ID");
            edtBuscarId.requestFocus();
            return;
        }

        // Configurar la URL
        String urlBuscar = URL + "/" + id;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.DELETE,
                urlBuscar,
                null,
                response -> {
                    try {
                        String mensaje = response.getString("message");

                        new AlertDialog.Builder(Buscar.this)
                                .setTitle("Eliminación")
                                .setMessage(mensaje)
                                .setPositiveButton("OK", (dialog, which) -> {
                                    limpiar();
                                }).show();
                    } catch (JSONException e) {
                        Log.e("ELIMINAR", "Error procesando respuesta", e);
                    }

                },
                error -> {
                    error.printStackTrace();
                }
        );

        requestQueue.add(request);
    }

}