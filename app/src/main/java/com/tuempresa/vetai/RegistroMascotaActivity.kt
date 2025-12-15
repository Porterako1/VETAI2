package com.tuempresa.vetai

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import com.tuempresa.vetai.ui.theme.AppDatabase
import com.tuempresa.vetai.ui.theme.entidades.Mascota
import kotlinx.coroutines.launch

class RegistroMascotaActivity : AppCompatActivity() {

    private lateinit var etNombre: TextInputEditText
    private lateinit var actvEspecie: AutoCompleteTextView
    private lateinit var etRaza: TextInputEditText
    private lateinit var etEdad: TextInputEditText
    private lateinit var etPeso: TextInputEditText
    private lateinit var etColor: TextInputEditText
    private lateinit var rgSexo: RadioGroup
    private lateinit var rbMacho: RadioButton
    private lateinit var rbHembra: RadioButton
    private lateinit var cbEsterilizado: CheckBox
    private lateinit var btnRegistrar: Button
    private lateinit var btnBack: ImageView

    private val especies = arrayOf(
        "Perro",
        "Gato",
        "Ave",
        "Conejo",
        "Hámster",
        "Tortuga",
        "Pez",
        "Otro"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_mascota)

        // Ocultar la barra de acción
        supportActionBar?.hide()

        // Inicializar vistas
        etNombre = findViewById(R.id.etNombre)
        actvEspecie = findViewById(R.id.actvEspecie)
        etRaza = findViewById(R.id.etRaza)
        etEdad = findViewById(R.id.etEdad)
        etPeso = findViewById(R.id.etPeso)
        etColor = findViewById(R.id.etColor)
        rgSexo = findViewById(R.id.rgSexo)
        rbMacho = findViewById(R.id.rbMacho)
        rbHembra = findViewById(R.id.rbHembra)
        cbEsterilizado = findViewById(R.id.cbEsterilizado)
        btnRegistrar = findViewById(R.id.btnRegistrar)
        btnBack = findViewById(R.id.btnBack)

        // Configurar AutoCompleteTextView de especies
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            especies
        )
        actvEspecie.setAdapter(adapter)

        // Botón volver
        btnBack.setOnClickListener {
            finish()
        }

        // Botón registrar
        btnRegistrar.setOnClickListener {
            guardarMascota()
        }
    }

    private fun guardarMascota() {
        val nombre = etNombre.text.toString().trim()
        val especie = actvEspecie.text.toString().trim()
        val raza = etRaza.text.toString().trim()
        val edadStr = etEdad.text.toString().trim()
        val pesoStr = etPeso.text.toString().trim()
        val color = etColor.text.toString().trim()

        // Obtener sexo seleccionado
        val sexo = when (rgSexo.checkedRadioButtonId) {
            R.id.rbMacho -> "Macho"
            R.id.rbHembra -> "Hembra"
            else -> "Macho"
        }

        // Obtener estado de esterilización
        val esterilizado = if (cbEsterilizado.isChecked) " - Esterilizado" else ""

        // Validar campos obligatorios
        when {
            nombre.isEmpty() -> {
                etNombre.error = "Ingresa el nombre de la mascota"
                etNombre.requestFocus()
                return
            }
            especie.isEmpty() -> {
                actvEspecie.error = "Selecciona una especie"
                actvEspecie.requestFocus()
                return
            }
        }

        // Convertir edad y peso
        val edad = edadStr.toIntOrNull() ?: 0
        val peso = pesoStr.toDoubleOrNull() ?: 0.0

        // Crear objeto Mascota (usando color como observaciones)
        val observaciones = if (color.isNotEmpty()) {
            "Color: $color$esterilizado"
        } else {
            esterilizado.trim()
        }

        val nuevaMascota = Mascota(
            nombre = nombre,
            especie = especie,
            raza = raza,
            edad = edad,
            peso = peso,
            sexo = sexo,
            observaciones = observaciones
        )

        // Guardar en base de datos
        lifecycleScope.launch {
            try {
                val db = AppDatabase.getDatabase(this@RegistroMascotaActivity)
                db.mascotaDao().insertarMascota(nuevaMascota)

                Toast.makeText(
                    this@RegistroMascotaActivity,
                    "¡Mascota registrada exitosamente!",
                    Toast.LENGTH_SHORT
                ).show()

                finish()
            } catch (e: Exception) {
                Toast.makeText(
                    this@RegistroMascotaActivity,
                    "Error al guardar: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}