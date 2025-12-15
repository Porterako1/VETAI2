package com.tuempresa.vetai.ui.theme.repositorios

import com.tuempresa.vetai.ui.theme.dao.MascotaDao
import com.tuempresa.vetai.ui.theme.entidades.Mascota
import kotlinx.coroutines.flow.Flow

class MascotaRepository(private val mascotaDao: MascotaDao) {

    val todasLasMascotas: Flow<List<Mascota>> = mascotaDao.obtenerTodasMascotas()

    suspend fun insertarMascota(mascota: Mascota): Long {
        return mascotaDao.insertarMascota(mascota)
    }

    suspend fun actualizarMascota(mascota: Mascota) {
        mascotaDao.actualizarMascota(mascota)
    }

    suspend fun eliminarMascota(mascota: Mascota) {
        mascotaDao.eliminarMascota(mascota)
    }

    suspend fun obtenerMascotaPorId(id: Int): Mascota? {
        return mascotaDao.obtenerMascotaPorId(id)
    }

    fun obtenerMascotasPorCliente(idCliente: Int): Flow<List<Mascota>> {
        return mascotaDao.obtenerMascotasPorCliente(idCliente)
    }
}