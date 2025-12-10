package com.tuempresa.vetai

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.tuempresa.vetai.ui.theme.factory.MascotaViewModelFactory
import com.tuempresa.vetai.ui.theme.AppDatabase
import com.tuempresa.vetai.ui.theme.repositorios.MascotaRepository
import com.tuempresa.vetai.ui.theme.entidades.Mascota
import com.tuempresa.vetai.ui.theme.viewmodels.MascotaViewModel
import java.text.SimpleDateFormat
import java.util.*

class RegistroMascotaActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageView
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

    private lateinit var mascotaViewModel: MascotaViewModel
    private var clienteId: Int = 1

    private val especies = arrayOf("Perro", "Gato", "Ave", "Conejo", "Hámster", "Otro")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_mascota)

        supportActionBar?.hide()

        // Inicializar ViewModel
        val db = AppDatabase.getDatabase(this)
        val repo = MascotaRepository(db)
        val factory = MascotaViewModelFactory(repo)
        mascotaViewModel = ViewModelProvider(this, factory).get(MascotaViewModel::class.java)

        inicializarVistas()
        configurarDropdownEspecies()
        configurarEventos()
    }

    private fun inicializarVistas() {
        btnBack = findViewById(R.id.btnBack)
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
    }

    private fun configurarDropdownEspecies() {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            especies
        )
        actvEspecie.setAdapter(adapter)
    }

    private fun configurarEventos() {
        btnBack.setOnClickListener { finish() }
        btnRegistrar.setOnClickListener { registrarMascota() }
    }

    private fun registrarMascota() {
        // Obtener valores de los campos
        val nombre = etNombre.text.toString().trim()
        val especie = actvEspecie.text.toString().trim()
        val raza = etRaza.text.toString().trim()
        val edadStr = etEdad.text.toString().trim()
        val pesoStr = etPeso.text.toString().trim()
        val color = etColor.text.toString().trim()
        val sexo = if (rbMacho.isChecked) "Macho" else "Hembra"
        val esterilizado = cbEsterilizado.isChecked

        // Validar campos obligatorios
        if (!validarCampos(nombre, especie, raza, edadStr, pesoStr, color)) {
            return
        }

        // Convertir edad y peso a números
        val edad = edadStr.toIntOrNull()
        val peso = pesoStr.toDoubleOrNull()

        // Validar valores numéricos
        if (!validarValoresNumericos(edad, peso)) {
            return
        }

        // Crear objeto Mascota
        val nuevaMascota = Mascota(
            id = 0,
            nombre = nombre,
            raza = raza,
            edad = edad!!,
            peso = peso!!,
            color = color,
            sexo = sexo,
            especie = especie,
            esterilizado = esterilizado,
            clienteId = clienteId,

        )

        // Usar el nuevo método con callback
        mascotaViewModel.insertar(nuevaMascota, clienteId) { success ->
            runOnUiThread {
                if (success) {
                    Toast.makeText(
                        this,
                        "¡Mascota registrada exitosamente! 🐾",
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        "Error al registrar la mascota",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    // AÑADIR ESTOS MÉTODOS FALTANTES:
    private fun validarCampos(
        nombre: String,
        especie: String,
        raza: String,
        edadStr: String,
        pesoStr: String,
        color: String
    ): Boolean {
        when {
            nombre.isEmpty() -> {
                etNombre.error = "Ingresa el nombre de tu mascota"
                etNombre.requestFocus()
                return false
            }
            especie.isEmpty() -> {
                Toast.makeText(
                    this,
                    "Selecciona una especie",
                    Toast.LENGTH_SHORT
                ).show()
                actvEspecie.requestFocus()
                return false
            }
            raza.isEmpty() -> {
                etRaza.error = "Ingresa la raza"
                etRaza.requestFocus()
                return false
            }
            edadStr.isEmpty() -> {
                etEdad.error = "Ingresa la edad"
                etEdad.requestFocus()
                return false
            }
            pesoStr.isEmpty() -> {
                etPeso.error = "Ingresa el peso"
                etPeso.requestFocus()
                return false
            }
            color.isEmpty() -> {
                etColor.error = "Ingresa el color"
                etColor.requestFocus()
                return false
            }
        }
        return true
    }

    private fun validarValoresNumericos(edad: Int?, peso: Double?): Boolean {
        when {
            edad == null || edad < 0 -> {
                etEdad.error = "Edad inválida"
                etEdad.requestFocus()
                Toast.makeText(
                    this,
                    "La edad debe ser un número positivo",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
            edad > 30 -> {
                etEdad.error = "Edad muy alta"
                etEdad.requestFocus()
                Toast.makeText(
                    this,
                    "Por favor verifica la edad ingresada",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
            peso == null || peso <= 0 -> {
                etPeso.error = "Peso inválido"
                etPeso.requestFocus()
                Toast.makeText(
                    this,
                    "El peso debe ser un número mayor a 0",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
            peso > 200 -> {
                etPeso.error = "Peso muy alto"
                etPeso.requestFocus()
                Toast.makeText(
                    this,
                    "Por favor verifica el peso ingresado",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
        }
        return true
    }

    private fun obtenerFechaActual(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return sdf.format(Date())
    }

    override fun onDestroy() {
        super.onDestroy()
        // Limpiar recursos si es necesario
    }
}