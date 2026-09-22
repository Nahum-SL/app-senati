package com.example.iniciar_sesion;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.iniciar_sesion.utils.ApiConfig;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListaSimple extends AppCompatActivity {

    ListView lstAlumnosSimple;
    RequestQueue requestQueue;
    String URL = ApiConfig.ALUMNOS;
    private void loadUI() {
        lstAlumnosSimple = findViewById(R.id.lsvAlumnosSimple);
    }

    private void obtenerDatosSimplesWS() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                response -> {
                    try {
                        String message = response.getString("message");
                        JSONArray data = response.getJSONArray("data");
                        renderizarAlumnosSimple(data);
                    } catch (Exception e) {

                    }
                },
                error -> {
                    Log.e("LISTAR", "Error", error);
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    private void renderizarAlumnosSimple(JSONArray jsonArray) {
        try {
            ArrayList<String> listaAlumnos = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                listaAlumnos.add(
                        jsonObject.getString("apellidos") + " " +
                                jsonObject.getString("nombres")
                );
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    R.layout.item_list,
                    R.id.txtAlumno,
                    listaAlumnos
            );

            lstAlumnosSimple.setAdapter(adapter);

        } catch (Exception e) {
            Log.e("LISTAR", "Error", e);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_simple);
        requestQueue = Volley.newRequestQueue(this);

        this.loadUI();
        obtenerDatosSimplesWS();
    }
}