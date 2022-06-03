package com.example.spendwisely.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.spendwisely.data.entidades.Gasto

@Dao
interface GastoDao {

    @Query("SELECT * FROM gastos")
    fun getAllGastos(): LiveData<List<Gasto>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addGasto(gasto: Gasto)

    @Update
    suspend fun updateGasto(gasto: Gasto)

    @Delete
    suspend fun deleteGasto(gasto: Gasto)

}