package com.example.iniciar_sesion.utils;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

public final class DialogUtils {
    public DialogUtils() {

    }

    /**
     * Clase reutilizable para acciones de riesgo, donde espera una confirmacion
     * para realizar un cambio. Se recibiran los siguientes parametros:
     * preguntar(String mensaje, Runnable accionPositiva, Context context)
     * */
    public static void confirmar(Context context, String mensaje, Runnable accionPositiva) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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

    public static void mostrarNotificacion(Context context, String titulo, String mensaje, Runnable metodo) {
        new AlertDialog.Builder(context)
                .setTitle(titulo)
                .setMessage(mensaje)
                .setPositiveButton("OK", (dialog, which) -> {
                    metodo.run();
                })
                .show();
    }

}
