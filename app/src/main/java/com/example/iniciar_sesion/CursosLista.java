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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CursosLista extends AppCompatActivity implements CursoAdapter.OnActionListener{

    RequestQueue requestQueue;
    RecyclerView recyclerCursos;
    ArrayList<Curso> listaCursos;
    CursoAdapter cursoAdapter;

    private final String URL = "http://10.0.2.2:3000/cursos";

    private void loadUI() {
        recyclerCursos = findViewById(R.id.recyclerCursos);
        // confiuracion
        recyclerCursos.setLayoutManager(new LinearLayoutManager(this));
        listaCursos = new ArrayList<>();
        cursoAdapter = new CursoAdapter(this, listaCursos, this);
        recyclerCursos.setAdapter(cursoAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cursos_lista);
        requestQueue = Volley.newRequestQueue(this);

        this.loadUI();
        obtenerDatosWS();


    }

    private void obtenerDatosWS() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                response -> {
                    // Logs para verificar la entrada de datos
                    Log.d("WS", "RESPUESTA RECIBIDA");
                    Log.d("WS", "JSON: " + response.toString());

                    renderizarCursos(response);
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
    private void renderizarCursos(JSONObject jsonObject) {
        try {
            // Obtener el array
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            listaCursos.clear();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject cursoJSON = jsonArray.getJSONObject(i);

                int id = cursoJSON.getInt("id");

                String nombre = cursoJSON.getString("nombre");
                int horas = cursoJSON.getInt("horas");
                double precio = cursoJSON.getDouble("precio");
                String responsable = cursoJSON.getString("responsable");

                Curso curso = new Curso(
                        id,
                        nombre,
                        horas,
                        precio,
                        responsable
                );
                listaCursos.add(curso);
            }

            cursoAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            Log.e("ErrorParseo", e.toString());
        }
    }

    @Override
    public void onVer() {
        Toast.makeText(this, "Hola", Toast.LENGTH_SHORT).show();
    }
}