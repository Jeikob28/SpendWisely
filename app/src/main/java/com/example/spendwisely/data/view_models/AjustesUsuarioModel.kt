package com.example.spendwisely.data.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.spendwisely.data.SWRoomDatabase
import com.example.spendwisely.data.entidades.AjustesUsuario
import com.example.spendwisely.data.repositorios.AjustesUsuarioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AjustesUsuarioModel(application: Application) : AndroidViewModel(application) {

    val unicoAjustesUsuario : LiveData<AjustesUsuario>
    private val repository : AjustesUsuarioRepository

    init {
        val ajustesUsuarioDao = SWRoomDatabase.getSWDatabase(application).ajustesUsuarioDao()
        repository = AjustesUsuarioRepository(ajustesUsuarioDao)
        unicoAjustesUsuario = repository.unicoAjustesUsuario
    }

    fun updateAjustesUsuario(ajustesUsuario: AjustesUsuario) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(ajustesUsuario)
        }
    }

}