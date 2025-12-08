package com.tuempresa.vetai

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tuempresa.vetai.ui.theme.Cita

class CitasAdapter(
    private val citas: MutableList<Cita>,
    private val modo: String,
    private val onEliminarClick: (Cita) -> Unit
) : RecyclerView.Adapter<CitasAdapter.CitaViewHolder>() {

    inner class CitaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFecha: TextView = itemView.findViewById(R.id.tvFecha)
        val tvHora: TextView = itemView.findViewById(R.id.tvHora)
        val tvMotivo: TextView = itemView.findViewById(R.id.tvMotivo)
        val tvEstado: TextView = itemView.findViewById(R.id.tvEstado)
        val btnEliminar: ImageView = itemView.findViewById(R.id.btnEliminar)
        val viewIndicator: View = itemView.findViewById(R.id.viewIndicator)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cita, parent, false)
        return CitaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CitaViewHolder, position: Int) {
        val cita = citas[position]

        // Formatear y mostrar datos
        holder.tvFecha.text = formatearFecha(cita.fecha)
        holder.tvHora.text = cita.hora
        holder.tvMotivo.text = cita.motivo
        holder.tvEstado.text = cita.estado

        // Configurar colores según estado
        val context = holder.itemView.context
        when (cita.estado) {
            "Confirmada" -> {
                holder.viewIndicator.setBackgroundColor(
                    context.getColor(R.color.accent_green)
                )
                holder.tvEstado.setTextColor(
                    context.getColor(R.color.accent_green)
                )
            }
            "Cancelada" -> {
                holder.viewIndicator.setBackgroundColor(
                    context.getColor(android.R.color.holo_red_dark)
                )
                holder.tvEstado.setTextColor(
                    context.getColor(android.R.color.holo_red_dark)
                )
            }
        }

        // Configurar visibilidad del botón eliminar
        if (modo == "VER" && cita.estado == "Cancelada") {
            holder.btnEliminar.visibility = View.VISIBLE
        } else if (modo == "CANCELAR" && cita.estado == "Confirmada") {
            holder.btnEliminar.visibility = View.VISIBLE
        } else {
            holder.btnEliminar.visibility = View.GONE
        }

        // Click en botón eliminar
        holder.btnEliminar.setOnClickListener {
            onEliminarClick(cita)
        }
    }

    override fun getItemCount(): Int = citas.size

    private fun formatearFecha(fecha: String): String {
        // Convertir de dd/MM/yyyy a formato más legible
        try {
            val partes = fecha.split("/")
            if (partes.size == 3) {
                val dia = partes[0]
                val mes = when (partes[1]) {
                    "01" -> "Enero"
                    "02" -> "Febrero"
                    "03" -> "Marzo"
                    "04" -> "Abril"
                    "05" -> "Mayo"
                    "06" -> "Junio"
                    "07" -> "Julio"
                    "08" -> "Agosto"
                    "09" -> "Septiembre"
                    "10" -> "Octubre"
                    "11" -> "Noviembre"
                    "12" -> "Diciembre"
                    else -> partes[1]
                }
                val año = partes[2]
                return "$dia de $mes, $año"
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return fecha
    }
}