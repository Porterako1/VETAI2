package com.tuempresa.vetai.viewmodels

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tuempresa.vetai.R
import com.tuempresa.vetai.ui.theme.entidades.Mascota

class MascotasAdapter(
    private var mascotas: List<Mascota>,
    private val onEditarClick: (Mascota) -> Unit
) : RecyclerView.Adapter<MascotasAdapter.MascotaViewHolder>() {

    inner class MascotaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivMascotaIcon: ImageView = itemView.findViewById(R.id.ivMascotaIcon)
        val tvNombreMascota: TextView = itemView.findViewById(R.id.tvNombreMascota)
        val tvRazaEspecie: TextView = itemView.findViewById(R.id.tvRazaEspecie)
        val tvEdad: TextView = itemView.findViewById(R.id.tvEdad)
        val tvPeso: TextView = itemView.findViewById(R.id.tvPeso)
        val btnEditar: ImageView = itemView.findViewById(R.id.btnEditar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MascotaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mascota, parent, false)
        return MascotaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MascotaViewHolder, position: Int) {
        val mascota = mascotas[position]

        holder.tvNombreMascota.text = mascota.nombre
        holder.tvRazaEspecie.text = "${mascota.raza} • ${mascota.especie}"
        holder.tvEdad.text = if (mascota.edad == 1) "1 año" else "${mascota.edad} años"
        holder.tvPeso.text = "${mascota.peso} kg"

        // Cambiar icono según especie
        val iconoResId = when (mascota.especie.lowercase()) {
            "perro" -> R.drawable.ic_perro
            "gato" -> R.drawable.ic_perro // Cambiar por ic_gato si lo tienes
            else -> R.drawable.ic_perro
        }
        holder.ivMascotaIcon.setImageResource(iconoResId)

        // Click en editar
        holder.btnEditar.setOnClickListener {
            onEditarClick(mascota)
        }
    }

    override fun getItemCount(): Int = mascotas.size

    fun actualizarLista(nuevaLista: List<Mascota>) {
        mascotas = nuevaLista
        notifyDataSetChanged()
    }
}