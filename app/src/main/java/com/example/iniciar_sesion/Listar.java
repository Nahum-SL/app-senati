package com.example.iniciar_sesion;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Listar extends AppCompatActivity {
    RequestQueue requestQueue;
    RecyclerView recyclerAlumnos;
    ArrayList<Alumno> listaAlumnos;
    AlumnoAdapter alumnoAdapter;
    // Emulador Android Studio -> 10.0.2.2 - PC / (IPv4 - wifi) Con USB movil - http://localhost:3000/alumnos
    private final String URL = "http://10.0.2.2:3000/alumnos";

    /**
     * Carga los datos necesarios para el funcionamiento de la logica del activity
     * */
    private void loadUI() {
        recyclerAlumnos = findViewById(R.id.recyclerAlumnos);
        // Configurar RecyclerView
        recyclerAlumnos.setLayoutManager(new LinearLayoutManager(this));
        listaAlumnos = new ArrayList<>();
        alumnoAdapter = new AlumnoAdapter(listaAlumnos);
        recyclerAlumnos.setAdapter(alumnoAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listar);

        this.loadUI();
        obtenerDatosWS();
    }

    /**
     * Obtiene los datos que envia el WebService, desde la base de datos en MySQL
     * */
    private void obtenerDatosWS() {
        requestQueue = Volley.newRequestQueue(this);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                response -> {
                // Logs para verificar la entrada de datos
                Log.d("WS", "3. RESPUESTA RECIBIDA");
                Log.d("WS", "JSON: " + response.toString());

                renderizarAlumnos(response);
                },
                error -> {
                        // Logs para verificar el Error
                        Log.e("ErrorWS", "4. ERROR");
                        Log.e("ErrorWS", "Tipo" + error.getClass().getName());
                        Log.e("ErrorWS", "Mensaje: " + error.toString());

                        if (error.networkResponse != null) {
                            Log.e(
                            "ErrorWS",
                            "Código HTTP: "
                                + error.networkResponse.statusCode
                            );

                            if (error.networkResponse != null) {
                                String respuesta = new String(error.networkResponse.data);
                                Log.e("ErrorWS", "Respuesta servidor: " + respuesta);
                            }
                        }

                }
        );

        requestQueue.add(jsonObjectRequest);

        Log.d("WS", "5. Petición enviada de Volley");
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
            Log.e(
                "ErrorParseo",
                e.toString()
            );
        }
    }
}
