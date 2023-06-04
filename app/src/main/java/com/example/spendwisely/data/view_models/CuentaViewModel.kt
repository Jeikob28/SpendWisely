package com.example.spendwisely.data.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.spendwisely.data.SWRoomDatabase
import com.example.spendwisely.data.entidades.Cuenta
import com.example.spendwisely.data.repositorios.CuentaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.BigDecimal

class CuentaViewModel(application: Application) : AndroidViewModel(application) {

    val allCuentas : LiveData<List<Cuenta>>
    val sumCuentas : LiveData<BigDecimal>
    private val repository : CuentaRepository

    init {
        val cuentaDao = SWRoomDatabase.getSWDatabase(application).cuentaDao()
        repository = CuentaRepository(cuentaDao)
        allCuentas = repository.allCuentas
        sumCuentas = repository.sumSaldoCuentas
    }

    fun addCuenta(cuenta: Cuenta) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(cuenta)
        }
    }

    fun updateCuenta(cuenta: Cuenta) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(cuenta)
        }
    }

    fun deleteCuenta(cuenta: Cuenta) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(cuenta)
        }
    }

    fun restarSaldo(gasto: BigDecimal) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.restarSaldo(gasto)
        }
    }

    fun sumarSaldo(saldo: BigDecimal) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.sumarSaldo(saldo)
        }
    }

}