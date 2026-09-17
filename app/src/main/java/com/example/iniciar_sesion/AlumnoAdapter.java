package com.example.iniciar_sesion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AlumnoAdapter extends RecyclerView.Adapter<AlumnoAdapter.AlumnoViewHolder> {
    private ArrayList<Alumno> listaAlumnos;
    private Context context;
    private OnActionListener listener;
    // Va a tener una interfaz
    public interface OnActionListener {
        // Definir los metodos
        void onVer();
    }
    public AlumnoAdapter(ArrayList<Alumno> listaAlumnos) {
        this.listaAlumnos = listaAlumnos;
    }
    public AlumnoAdapter(Context context, ArrayList<Alumno> listaAlumnos, OnActionListener listener) {
        this.context = context;
        this.listaAlumnos = listaAlumnos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AlumnoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alumno, parent, false);
        return new AlumnoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlumnoViewHolder holder, int position) {
        Alumno alumno = listaAlumnos.get(position);
        holder.txtNombre.setText(String.format(alumno.getApellidos() + " " + alumno.getNombres()));
        holder.txtTelefono.setText(String.format("Teléfono: " + alumno.getTelefono()));
        holder.txtEmail.setText(String.format("Email: " + alumno.getEmail()));

        // Buttons
        holder.btnUser.setOnClickListener(view -> {
            listener.onVer();
        });
    }

    @Override
    public int getItemCount() {
        return listaAlumnos.size();
    }

    public static class AlumnoViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre, txtTelefono, txtEmail;
        Button btnUser;

        public AlumnoViewHolder(@NonNull View itemView) {
            super(itemView);
            // Similar al loadUI
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtTelefono = itemView.findViewById(R.id.txtTelefono);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            btnUser = itemView.findViewById(R.id.btnUser);
        }
    }
}
