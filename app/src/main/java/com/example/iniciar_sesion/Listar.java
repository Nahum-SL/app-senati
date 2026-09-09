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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Listar extends AppCompatActivity {
    RequestQueue requestQueue;
    RecyclerView recyclerAlumnos;
    ArrayList<Alumno> listaAlumnos;
    AlumnoAdapter alumnoAdapter;
    // Emulador Android Studio -> 10.0.2.2 - PC / (IPv4 - wifi) Con USB movil
    private final String URL = "http://localhost:3000/alumnos";

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

    private void obtenerDatosWS() {
        requestQueue = Volley.newRequestQueue(this);


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        // Logs para verificar la entrada de datos
                        Log.d("WS", "3. RESPUESTA RECIBIDA");
                        Log.d("WS", "JSON: " + jsonArray.toString());

                        renderizarAlumnos(jsonArray);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
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
                }
        );

        requestQueue.add(jsonArrayRequest);

        Log.d("WS", "5. Petición enviada de Volley");
    }

    private void renderizarAlumnos(JSONArray jsonArray) {
        try {
            listaAlumnos.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                int id = jsonObject.getInt("id");

                String apellidos = jsonObject.getString("apellidos");
                String nombres = jsonObject.getString("nombres");
                String telefono = jsonObject.getString("telefono");
                String direccion = jsonObject.getString("direccion");
                String email = jsonObject.getString("email");

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
