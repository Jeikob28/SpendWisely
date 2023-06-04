package com.example.spendwisely.data.view_models

import android.app.Application
import androidx.lifecycle.*
import com.example.spendwisely.data.SWRoomDatabase
import com.example.spendwisely.data.entidades.Transaccion
import com.example.spendwisely.data.repositorios.TransaccionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransaccionViewModel(application: Application) : AndroidViewModel(application) {

    val allTransacciones : LiveData<List<Transaccion>>
    private val repository : TransaccionRepository

    init {
        val transaccionDao = SWRoomDatabase.getSWDatabase(application).transaccionDao()
        repository = TransaccionRepository(transaccionDao)
        allTransacciones = repository.allTransacciones
    }

    fun addGasto(transaccion: Transaccion) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(transaccion)
        }
    }

    fun updateGasto(transaccion: Transaccion) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(transaccion)
        }
    }

    fun deleteGasto(transaccion: Transaccion) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(transaccion)
        }
    }

}