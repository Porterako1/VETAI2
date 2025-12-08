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
import com.google.android.material.textfield.TextInputEditText
import com.tuempresa.vetai.ui.theme.Cita
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class NuevaCitaDialog : DialogFragment() {

    private lateinit var etFecha: TextInputEditText
    private lateinit var etHora: TextInputEditText
    private lateinit var actvMotivo: AutoCompleteTextView
    private lateinit var btnGuardar: Button
    private lateinit var btnCancelar: Button

    private var fechaSeleccionada: String = ""
    private var horaSeleccionada: String = ""

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
        btnGuardar = view.findViewById(R.id.btnGuardar)
        btnCancelar = view.findViewById(R.id.btnCancelar)

        // Configurar selector de fecha
        etFecha.setOnClickListener {
            mostrarSelectorFecha()
        }

        // Configurar selector de hora
        etHora.setOnClickListener {
            mostrarSelectorHora()
        }

        // Configurar dropdown de motivos
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, motivos)
        actvMotivo.setAdapter(adapter)

        // Configurar botones
        btnCancelar.setOnClickListener {
            dismiss()
        }

        btnGuardar.setOnClickListener {
            guardarCita()
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
                // Formatear fecha seleccionada
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                calendar.set(selectedYear, selectedMonth, selectedDay)
                fechaSeleccionada = dateFormat.format(calendar.time)
                etFecha.setText(fechaSeleccionada)
            },
            year, month, day
        )

        // No permitir fechas pasadas
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
                // Formatear hora seleccionada
                val horaFormateada = String.format("%02d:%02d", selectedHour, selectedMinute)

                // Validar horario de atención (9:00 AM - 6:00 PM)
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
            hour, minute, true // true para formato 24 horas
        )

        timePickerDialog.show()
    }

    private fun guardarCita() {
        val motivo = actvMotivo.text.toString()

        // Validar campos
        when {
            fechaSeleccionada.isEmpty() -> {
                Toast.makeText(requireContext(), "Selecciona una fecha", Toast.LENGTH_SHORT).show()
                return
            }
            horaSeleccionada.isEmpty() -> {
                Toast.makeText(requireContext(), "Selecciona una hora", Toast.LENGTH_SHORT).show()
                return
            }
            motivo.isEmpty() -> {
                Toast.makeText(requireContext(), "Selecciona un motivo", Toast.LENGTH_SHORT).show()
                return
            }
        }

        // Crear objeto Cita
        val nuevaCita = Cita(
            fecha = fechaSeleccionada,
            hora = horaSeleccionada,
            motivo = motivo
        )

        // Guardar en SharedPreferences
        CitasManager.agregarCita(requireContext(), nuevaCita)

        Toast.makeText(
            requireContext(),
            "¡Cita agendada exitosamente!",
            Toast.LENGTH_SHORT
        ).show()

        dismiss()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}