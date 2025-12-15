package com.tuempresa.vetai

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tuempresa.vetai.ui.theme.entidades.Mascota
import com.tuempresa.vetai.ui.theme.viewmodel.MascotaViewModel

class ListaMascotasActivity : ComponentActivity() {

    private val viewModel: MascotaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ListaMascotasContent(
                viewModel = viewModel,
                onNavigateToRegistro = {
                    startActivity(Intent(this, RegistroMascotaActivity::class.java))
                },
                onNavigateBack = { finish() }
            )
        }
    }
}

// Colores personalizados de tu app
private val PrimaryBlue = Color(0xFF3F7993) // Tu primary_blue exacto
private val AccentGreen = Color(0xFF8BC34A) // Tu accent_green exacto
private val BackgroundBlue = Color(0xFF3F7993) // Mismo azul del fondo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaMascotasContent(
    viewModel: MascotaViewModel,
    onNavigateToRegistro: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val mascotas by viewModel.mascotas.collectAsState(initial = emptyList())
    var mascotaAEliminar by remember { mutableStateOf<Mascota?>(null) }
    var mostrarDialogo by remember { mutableStateOf(false) }

    // Diálogo de confirmación
    if (mostrarDialogo && mascotaAEliminar != null) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            title = {
                Text(
                    "Eliminar Mascota",
                    fontWeight = FontWeight.Bold,
                    color = PrimaryBlue
                )
            },
            text = {
                Text("¿Estás seguro de que deseas eliminar a ${mascotaAEliminar?.nombre}?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        mascotaAEliminar?.let { viewModel.eliminarMascota(it) }
                        mostrarDialogo = false
                        mascotaAEliminar = null
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    )
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    mostrarDialogo = false
                    mascotaAEliminar = null
                }) {
                    Text("Cancelar", color = PrimaryBlue)
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Mis Mascotas",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PrimaryBlue
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToRegistro,
                containerColor = AccentGreen,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, "Agregar mascota")
            }
        },
        containerColor = BackgroundBlue
    ) { padding ->
        if (mascotas.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(BackgroundBlue),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    modifier = Modifier.size(120.dp),
                    tint = Color.White.copy(alpha = 0.3f)
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    "No hay mascotas registradas",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "Toca el botón + para agregar una",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackgroundBlue)
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(mascotas) { mascota ->
                    MascotaCard(
                        mascota = mascota,
                        onEliminar = {
                            mascotaAEliminar = mascota
                            mostrarDialogo = true
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MascotaCard(
    mascota: Mascota,
    onEliminar: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icono circular de corazón
            Surface(
                modifier = Modifier.size(56.dp),
                shape = MaterialTheme.shapes.medium,
                color = AccentGreen.copy(alpha = 0.2f)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        modifier = Modifier.size(32.dp),
                        tint = AccentGreen
                    )
                }
            }

            Spacer(Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = mascota.nombre,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryBlue
                )
                Text(
                    text = "${mascota.especie} • ${mascota.raza}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Text(
                    text = "${mascota.edad} años • ${mascota.peso} kg • ${mascota.sexo}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                if (mascota.observaciones.isNotBlank()) {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = mascota.observaciones,
                        style = MaterialTheme.typography.bodySmall,
                        color = PrimaryBlue.copy(alpha = 0.6f)
                    )
                }
            }

            // Botón de eliminar
            IconButton(
                onClick = onEliminar,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = Color.Red
                )
            }
        }
    }
}