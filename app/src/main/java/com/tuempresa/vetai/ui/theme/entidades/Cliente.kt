package com.tuempresa.vetai.ui.theme.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Cliente")
data class Cliente(
    @PrimaryKey(autoGenerate = true)
    val ID_Cliente: Int = 0,
    val Nombre: String,
    val Apellidos: String,
    val Telefono: String,
    val Usuario: String,
    val Contrasena: String
)
