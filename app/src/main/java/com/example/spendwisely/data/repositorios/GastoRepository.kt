package com.example.spendwisely.data.repositorios

import androidx.lifecycle.LiveData
import com.example.spendwisely.data.daos.GastoDao
import com.example.spendwisely.data.entidades.Gasto

class GastoRepository(private val gastoDao: GastoDao) {

    val allGastos: LiveData<List<Gasto>> = gastoDao.getAllGastos()

    suspend fun insert(gasto: Gasto) {
        gastoDao.addGasto(gasto)
    }

    suspend fun update(gasto: Gasto) {
        gastoDao.updateGasto(gasto)
    }

    suspend fun delete(gasto: Gasto) {
        gastoDao.deleteGasto(gasto)
    }

}