package com.tuempresa.vetai

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tuempresa.vetai.ui.theme.entidades.Citas

class CitasAdapter(
    private val citas: MutableList<Citas>,
    private val modo: String, // "VER" o "CANCELAR"
    private val onEliminarClick: (Citas) -> Unit
) : RecyclerView.Adapter<CitasAdapter.CitaViewHolder>() {

    class CitaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvFecha: TextView = view.findViewById(R.id.tvFechaCita)
        val tvHora: TextView = view.findViewById(R.id.tvHoraCita)
        val tvMotivo: TextView = view.findViewById(R.id.tvMotivoCita)
        val tvEstado: TextView = view.findViewById(R.id.tvEstadoCita)
        val btnEliminar: ImageView = view.findViewById(R.id.btnEliminarCita)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cita, parent, false)
        return CitaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CitaViewHolder, position: Int) {
        val cita = citas[position]

        holder.tvFecha.text = cita.fecha
        holder.tvHora.text = cita.hora
        holder.tvMotivo.text = cita.motivo
        holder.tvEstado.text = cita.estado

        // Configurar color según el estado
        when (cita.estado) {
            "Activa" -> {
                holder.tvEstado.setTextColor(
                    holder.itemView.context.getColor(R.color.accent_green)
                )
            }
            "Cancelada" -> {
                holder.tvEstado.setTextColor(
                    holder.itemView.context.getColor(android.R.color.holo_red_dark)
                )
            }
            else -> {
                holder.tvEstado.setTextColor(
                    holder.itemView.context.getColor(R.color.gray)
                )
            }
        }

        // Mostrar/ocultar botón de eliminar según el modo
        if (modo == "CANCELAR") {
            holder.btnEliminar.visibility = View.VISIBLE
            holder.btnEliminar.setOnClickListener {
                onEliminarClick(cita)
            }
        } else {
            holder.btnEliminar.visibility = View.VISIBLE
            holder.btnEliminar.setOnClickListener {
                onEliminarClick(cita)
            }
        }
    }

    override fun getItemCount() = citas.size
}