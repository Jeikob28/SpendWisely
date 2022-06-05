package com.example.spendwisely.data.repositorios

import androidx.lifecycle.LiveData
import com.example.spendwisely.data.daos.CuentaDao
import com.example.spendwisely.data.entidades.Cuenta

class CuentaRepository(private val cuentaDao: CuentaDao) {

    val allCuentas: LiveData<List<Cuenta>> = cuentaDao.getAllCuentas()

    val sumSaldoCuentas : LiveData<Double> = cuentaDao.sumSaldoCuentas()

    suspend fun insert(cuenta: Cuenta) {
        cuentaDao.addCuenta(cuenta)
    }

    suspend fun update(cuenta: Cuenta) {
        cuentaDao.updateCuenta(cuenta)
    }

    suspend fun delete(cuenta: Cuenta) {
        cuentaDao.deleteCuenta(cuenta)
    }

    suspend fun restarSaldo(gasto: Double) {
        cuentaDao.restarSaldo(gasto)
    }

    suspend fun sumarSaldo(saldo: Double) {
        cuentaDao.sumarSaldo(saldo)
    }

}