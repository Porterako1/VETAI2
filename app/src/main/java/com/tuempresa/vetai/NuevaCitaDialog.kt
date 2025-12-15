package com.tuempresa.vetai

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import com.tuempresa.vetai.ui.theme.AppDatabase
import com.tuempresa.vetai.ui.theme.entidades.Citas
import com.tuempresa.vetai.ui.theme.entidades.Mascota
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class NuevaCitaDialog : DialogFragment() {

    private lateinit var etFecha: TextInputEditText
    private lateinit var etHora: TextInputEditText
    private lateinit var actvMotivo: AutoCompleteTextView
    private lateinit var actvMascota: AutoCompleteTextView
    private lateinit var btnGuardar: Button
    private lateinit var btnCancelar: Button

    private var fechaSeleccionada: String = ""
    private var horaSeleccionada: String = ""
    private var mascotas: List<Mascota> = emptyList()
    private var mascotaSeleccionada: Mascota? = null

    private val motivos = arrayOf(
        "Consulta General",
        "Vacunación",
        "Control de Rutina",
        "Emergencia",
        "Desparasitación",
        "Esterilización",
        "Análisis de Laboratorio",
        "Cirugía",
        "Revisión Dental",
        "Baño y Peluquería",
        "Otro"
    )

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_nueva_cita, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar vistas
        etFecha = view.findViewById(R.id.etFecha)
        etHora = view.findViewById(R.id.etHora)
        actvMotivo = view.findViewById(R.id.actvMotivo)
        actvMascota = view.findViewById(R.id.actvMascota)
        btnGuardar = view.findViewById(R.id.btnGuardar)
        btnCancelar = view.findViewById(R.id.btnCancelar)

        // Cargar mascotas
        cargarMascotas()

        // Configurar selector de fecha
        etFecha.setOnClickListener {
            mostrarSelectorFecha()
        }

        // Configurar selector de hora
        etHora.setOnClickListener {
            mostrarSelectorHora()
        }

        // Configurar dropdown de motivos
        val adapterMotivos = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            motivos
        )
        actvMotivo.setAdapter(adapterMotivos)

        // Configurar botones
        btnCancelar.setOnClickListener {
            dismiss()
        }

        btnGuardar.setOnClickListener {
            guardarCita()
        }
    }

    private fun cargarMascotas() {
        lifecycleScope.launch {
            try {
                val db = AppDatabase.getDatabase(requireContext())
                mascotas = db.mascotaDao().obtenerTodasMascotas().firstOrNull() ?: emptyList()

                if (mascotas.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Primero debes registrar una mascota",
                        Toast.LENGTH_LONG
                    ).show()
                    dismiss()
                    return@launch
                }

                // Configurar dropdown de mascotas
                val nombresMascotas = mascotas.map { "${it.nombre} (${it.especie})" }
                val adapterMascotas = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    nombresMascotas
                )
                actvMascota.setAdapter(adapterMascotas)

                // Listener para seleccionar mascota
                actvMascota.setOnItemClickListener { _, _, position, _ ->
                    mascotaSeleccionada = mascotas[position]
                }

            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    "Error al cargar mascotas: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun mostrarSelectorFecha() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                calendar.set(selectedYear, selectedMonth, selectedDay)
                fechaSeleccionada = dateFormat.format(calendar.time)
                etFecha.setText(fechaSeleccionada)
            },
            year, month, day
        )

        datePickerDialog.datePicker.minDate = calendar.timeInMillis
        datePickerDialog.show()
    }

    private fun mostrarSelectorHora() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, selectedHour, selectedMinute ->
                val horaFormateada = String.format("%02d:%02d", selectedHour, selectedMinute)

                if (selectedHour in 9..17) {
                    horaSeleccionada = horaFormateada
                    etHora.setText(horaFormateada)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Horario de atención: 9:00 AM - 6:00 PM",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            hour, minute, true
        )

        timePickerDialog.show()
    }

    private fun guardarCita() {
        val motivo = actvMotivo.text.toString()

        // LOGS DE DEPURACIÓN
        android.util.Log.d("CitaDebug", "=== INICIO GUARDAR CITA ===")
        android.util.Log.d("CitaDebug", "Fecha: '$fechaSeleccionada'")
        android.util.Log.d("CitaDebug", "Hora: '$horaSeleccionada'")
        android.util.Log.d("CitaDebug", "Motivo: '$motivo'")
        android.util.Log.d("CitaDebug", "Mascota seleccionada: ${mascotaSeleccionada?.nombre}")
        android.util.Log.d("CitaDebug", "ID Mascota: ${mascotaSeleccionada?.id}")

        when {
            fechaSeleccionada.isEmpty() -> {
                android.util.Log.e("CitaDebug", "ERROR: Fecha vacía")
                Toast.makeText(requireContext(), "Selecciona una fecha", Toast.LENGTH_SHORT).show()
                return
            }
            horaSeleccionada.isEmpty() -> {
                android.util.Log.e("CitaDebug", "ERROR: Hora vacía")
                Toast.makeText(requireContext(), "Selecciona una hora", Toast.LENGTH_SHORT).show()
                return
            }
            motivo.isEmpty() -> {
                android.util.Log.e("CitaDebug", "ERROR: Motivo vacío")
                Toast.makeText(requireContext(), "Selecciona un motivo", Toast.LENGTH_SHORT).show()
                return
            }
            mascotaSeleccionada == null -> {
                android.util.Log.e("CitaDebug", "ERROR: Mascota no seleccionada")
                Toast.makeText(requireContext(), "Selecciona una mascota", Toast.LENGTH_SHORT).show()
                return
            }
        }

        android.util.Log.d("CitaDebug", "Todas las validaciones pasadas, creando cita...")

        val nuevaCita = Citas(
            fecha = fechaSeleccionada,
            hora = horaSeleccionada,
            motivo = motivo,
            idMascota = mascotaSeleccionada!!.id,
            idVeterinario = null,
            estado = "Activa"
        )

        android.util.Log.d("CitaDebug", "Cita creada: $nuevaCita")
        android.util.Log.d("CitaDebug", "Llamando a CitasManager.agregarCita()...")

        CitasManager.agregarCita(requireContext(), nuevaCita) {
            android.util.Log.d("CitaDebug", "✅ Callback de éxito ejecutado")
            Toast.makeText(
                requireContext(),
                "¡Cita agendada exitosamente!",
                Toast.LENGTH_SHORT
            ).show()
            dismiss()
        }

        android.util.Log.d("CitaDebug", "=== FIN GUARDAR CITA ===")
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}