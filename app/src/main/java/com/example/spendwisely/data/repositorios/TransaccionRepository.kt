package com.example.spendwisely.data.repositorios

import androidx.lifecycle.LiveData
import com.example.spendwisely.data.daos.TransaccionDao
import com.example.spendwisely.data.entidades.Transaccion

class TransaccionRepository(private val transaccionDao: TransaccionDao) {

    val allTransacciones: LiveData<List<Transaccion>> = transaccionDao.getAllTransacciones()

    suspend fun insert(transaccion: Transaccion) {
        transaccionDao.addTransaccion(transaccion)
    }

    suspend fun update(transaccion: Transaccion) {
        transaccionDao.updateTransaccion(transaccion)
    }

    suspend fun delete(transaccion: Transaccion) {
        transaccionDao.deleteTransaccion(transaccion)
    }

}