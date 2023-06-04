package com.example.spendwisely.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.spendwisely.data.entidades.Transaccion

@Dao
interface TransaccionDao {

    @Query("SELECT * FROM Transacciones")
    fun getAllTransacciones(): LiveData<List<Transaccion>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTransaccion(transaccion: Transaccion)

    @Update
    suspend fun updateTransaccion(transaccion: Transaccion)

    @Delete
    suspend fun deleteTransaccion(transaccion: Transaccion)

}