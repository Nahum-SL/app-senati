package com.example.iniciar_sesion;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.iniciar_sesion.helpers.ApiConfig;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

// Esta clase tiene herencia | contrato AlumnoAdapter.onActionListener
public class Listar extends AppCompatActivity implements AlumnoAdapter.OnActionListener{
    RequestQueue requestQueue;
    RecyclerView recyclerAlumnos;
    ArrayList<Alumno> listaAlumnos;
    AlumnoAdapter alumnoAdapter;

    String URL = ApiConfig.ALUMNOS;

    /**
     * Carga los datos necesarios para el funcionamiento de la logica del activity
     * */
    private void loadUI() {
        recyclerAlumnos = findViewById(R.id.recyclerAlumnos);

        // Configurar RecyclerView
        recyclerAlumnos.setLayoutManager(new LinearLayoutManager(this));
        listaAlumnos = new ArrayList<>();
        // Constructor 2
        alumnoAdapter = new AlumnoAdapter(this, listaAlumnos, this);
        recyclerAlumnos.setAdapter(alumnoAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listar);
        requestQueue = Volley.newRequestQueue(this);

        this.loadUI();
        obtenerDatosWS();
    }


    /**
     * Obtiene los datos que envia el WebService, desde la base de datos en MySQL
     * */
    private void obtenerDatosWS() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                response -> {
                // Logs para verificar la entrada de datos
                Log.d("WS", "RESPUESTA RECIBIDA");
                Log.d("WS", "JSON: " + response.toString());

                renderizarAlumnos(response);
                },
                error -> {
                        // Logs para verificar el Error
                        Log.e("ErrorWS", "ERROR");
                        Log.e("ErrorWS", "Tipo" + error.getClass().getName());
                        Log.e("ErrorWS", "Mensaje: " + error.toString());

                        if (error.networkResponse != null) {Log.e("ErrorWS", "Código HTTP: " + error.networkResponse.statusCode);
                            if (error.networkResponse != null) {
                                String respuesta = new String(error.networkResponse.data);
                                Log.e("ErrorWS", "Respuesta servidor: " + respuesta);
                            }
                        }

                }
        );

        requestQueue.add(jsonObjectRequest);

        Log.d("WS", "Petición enviada de Volley");
    }

    /**
     * Permite renderizar los registros antes de cargarlos a la UI
     * */
    private void renderizarAlumnos(JSONObject jsonObject) {
        try {
            // Obtener el array
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            listaAlumnos.clear();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject alumnoJSON = jsonArray.getJSONObject(i);

                int id = alumnoJSON.getInt("id");

                String apellidos = alumnoJSON.getString("apellidos");
                String nombres = alumnoJSON.getString("nombres");
                String telefono = alumnoJSON.getString("telefono");
                String direccion = alumnoJSON.getString("direccion");
                String email = alumnoJSON.getString("email");

                Alumno alumno = new Alumno(
                        id,
                        apellidos,
                        nombres,
                        telefono,
                        direccion,
                        email
                );
                listaAlumnos.add(alumno);
            }

            alumnoAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            Log.e("ErrorParseo", e.toString());
        }
    }

    @Override
    public void onVer() {
        Toast.makeText(this, "Hola", Toast.LENGTH_SHORT).show();
    }
}
