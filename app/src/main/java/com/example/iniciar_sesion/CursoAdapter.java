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
        void onVer();
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
        holder.txtNombreC.setText(String.format(curso.getNombre()));
        holder.txtHorasC.setText(String.format("Horas: " + curso.getHoras()));
        holder.txtPrecioC.setText(String.format("Precio: " + curso.getPrecio()));
        holder.txtResponsableC.setText(String.format("Responsable: " + curso.getResponsable()));

        // Buttons
        holder.btnCurso.setOnClickListener(view -> {
            listener.onVer();
        });
    }

    @Override
    public int getItemCount() {
        return listaCursos.size();
    }

    public static class CursoViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombreC,txtPrecioC, txtResponsableC, txtHorasC;
        Button btnCurso;

        public CursoViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNombreC = itemView.findViewById(R.id.txtNombreC);
            txtPrecioC = itemView.findViewById(R.id.txtPrecioC);
            txtResponsableC = itemView.findViewById(R.id.txtResponsableC);
            txtHorasC = itemView.findViewById(R.id.txtHorasC);

            btnCurso = itemView.findViewById(R.id.btnCurso);
        }
    }

}
