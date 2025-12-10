package com.tuempresa.vetai.ui.theme.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tuempresa.vetai.ui.theme.repositorios.MascotaRepository
import com.tuempresa.vetai.ui.theme.viewmodels.MascotaViewModel

class MascotaViewModelFactory(
    private val repository: MascotaRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MascotaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MascotaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}