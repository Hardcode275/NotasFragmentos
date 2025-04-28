package com.example.notasfragmentos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotasAdapter(
    private var notas: List<Nota>,
    private val onClick: (Nota) -> Unit
) : RecyclerView.Adapter<NotasAdapter.NotaViewHolder>() {

    class NotaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titulo: TextView = view.findViewById(R.id.textTitulo)
        val contenido: TextView = view.findViewById(R.id.textContenido)
        val fecha: TextView = view.findViewById(R.id.textFecha)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_nota, parent, false)
        return NotaViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        val nota = notas[position]
        holder.titulo.text = nota.titulo
        holder.contenido.text = nota.contenido
        holder.fecha.text = nota.fecha

        holder.itemView.setOnClickListener {
            onClick(nota)
        }
    }

    override fun getItemCount(): Int = notas.size

    // 🔥 Método para actualizar las notas
    fun actualizarLista(nuevasNotas: List<Nota>) {
        notas = nuevasNotas
        notifyDataSetChanged()
    }
}



