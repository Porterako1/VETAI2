package com.tuempresa.vetai.ui.theme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {
    fun launch(block: suspend () -> Unit) {
        viewModelScope.launch { block() }
    }
}