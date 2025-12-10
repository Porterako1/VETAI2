package com.tuempresa.vetai.ui.theme.repositorios

import androidx.lifecycle.LiveData
import com.tuempresa.vetai.ui.theme.AppDatabase
import com.tuempresa.vetai.ui.theme.entidades.Mascota

class MascotaRepository(private val db: AppDatabase) {

    private val mascotaDao = db.mascotaDao()

    suspend fun insertar(mascota: Mascota): Long {
        return mascotaDao.insertar(mascota)
    }

    suspend fun actualizar(mascota: Mascota) {
        mascotaDao.actualizar(mascota)
    }

    suspend fun eliminar(mascota: Mascota) {
        mascotaDao.eliminar(mascota)
    }

    fun obtenerMascotasPorCliente(clienteId: Int): LiveData<List<Mascota>> {
        return mascotaDao.obtenerMascotasPorCliente(clienteId)
    }

    fun obtenerMascotaPorId(id: Int): LiveData<Mascota> {
        return mascotaDao.obtenerMascotaPorId(id)
    }

    fun obtenerTodasLasMascotas(): LiveData<List<Mascota>> {
        return mascotaDao.obtenerTodasLasMascotas()
    }
}