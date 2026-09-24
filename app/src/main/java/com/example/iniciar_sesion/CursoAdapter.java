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

public class CursoAdapter extends RecyclerView.Adapter<CursoAdapter.CursoViewHolder> {
    private ArrayList<Curso> listaCursos;
    private Context context;
    private OnActionListener listener;
    // Va a tener una interfaz
    public interface OnActionListener {
        // Definir los metodos

        // El Adapter no debería hacer la petición HTTP.
        // Se necesita el pasar el ID asi que se le asigna el parametro
        void onVer(int id);
        void onBorrar(int id);
    }

    public CursoAdapter(Context context, ArrayList<Curso> listaCursos, OnActionListener listener) {
        this.listaCursos = listaCursos;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CursoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_curso, parent, false);
        return new CursoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CursoViewHolder holder, int position) {
        Curso curso = listaCursos.get(position);
        holder.txtNombreC.setText(curso.getNombre());
        holder.txtResponsableC.setText(String.format("Responsable: " + curso.getResponsable()));
        holder.txtHorasC.setText(String.format("Horas: " + curso.getHoras() + " " + "Precio: " + curso.getPrecio()));

        // Buttons
        holder.btnCursoVer.setOnClickListener(view -> {
            listener.onVer(curso.getId());
        });

        // Buttons
        holder.btnCursoBorrar.setOnClickListener(view -> {
            listener.onBorrar(curso.getId());
        });
    }

    @Override
    public int getItemCount() {
        return listaCursos.size();
    }

    public static class CursoViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombreC, txtResponsableC, txtHorasC;
        Button btnCursoVer, btnCursoBorrar;

        public CursoViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNombreC = itemView.findViewById(R.id.txtNombreC);
            txtResponsableC = itemView.findViewById(R.id.txtResponsableC);
            txtHorasC = itemView.findViewById(R.id.txtHorasC);

            btnCursoVer = itemView.findViewById(R.id.btnCursoVer);
            btnCursoBorrar = itemView.findViewById(R.id.btnCursoBorrar);
        }
    }

}
