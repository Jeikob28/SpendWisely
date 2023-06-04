package com.example.spendwisely.data.repositorios

import androidx.lifecycle.LiveData
import com.example.spendwisely.data.daos.CuentaDao
import com.example.spendwisely.data.entidades.Cuenta
import java.math.BigDecimal

class CuentaRepository(private val cuentaDao: CuentaDao) {

    val allCuentas: LiveData<List<Cuenta>> = cuentaDao.getAllCuentas()

    val sumSaldoCuentas : LiveData<BigDecimal> = cuentaDao.sumSaldoCuentas()

    suspend fun insert(cuenta: Cuenta) {
        cuentaDao.addCuenta(cuenta)
    }

    suspend fun update(cuenta: Cuenta) {
        cuentaDao.updateCuenta(cuenta)
    }

    suspend fun delete(cuenta: Cuenta) {
        cuentaDao.deleteCuenta(cuenta)
    }

    suspend fun restarSaldo(gasto: BigDecimal) {
        cuentaDao.restarSaldo(gasto)
    }

    suspend fun sumarSaldo(saldo: BigDecimal) {
        cuentaDao.sumarSaldo(saldo)
    }

}