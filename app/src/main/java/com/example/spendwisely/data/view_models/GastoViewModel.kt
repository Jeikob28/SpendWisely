package com.example.spendwisely.data.view_models

import android.app.Application
import androidx.lifecycle.*
import com.example.spendwisely.data.SWRoomDatabase
import com.example.spendwisely.data.entidades.Gasto
import com.example.spendwisely.data.repositorios.GastoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GastoViewModel(application: Application) : AndroidViewModel(application) {

    val allGastos : LiveData<List<Gasto>>
    private val repository : GastoRepository

    init {
        val gastoDao = SWRoomDatabase.getSWDatabase(application).gastoDao()
        repository = GastoRepository(gastoDao)
        allGastos = repository.allGastos
    }

    fun addGasto(gasto: Gasto) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(gasto)
        }
    }

    fun updateGasto(gasto: Gasto) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(gasto)
        }
    }

    fun deleteGasto(gasto: Gasto) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(gasto)
        }
    }

}