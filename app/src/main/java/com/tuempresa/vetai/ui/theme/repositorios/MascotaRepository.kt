package com.tuempresa.vetai.ui.theme.repositorios

import com.tuempresa.vetai.ui.theme.AppDatabase
import com.tuempresa.vetai.ui.theme.entidades.Mascota

class MascotaRepository (private val db: AppDatabase){
    suspend fun obtenerMascotasDeCliente(idCliente: Int) = db.mascotaDao().obtenerMascotasDeCliente(idCliente)

    suspend fun insertar(mascota: Mascota) = db.mascotaDao().insertar(mascota)

    suspend fun actualizar(mascota: Mascota) = db.mascotaDao().actualizar(mascota)

    suspend fun eliminar(mascota: Mascota) = db.mascotaDao().eliminar(mascota)
}