package com.example.spendwisely.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.spendwisely.data.entidades.Cuenta

@Dao
interface CuentaDao {

    @Query("SELECT * FROM cuentas")
    fun getAllCuentas(): LiveData<List<Cuenta>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCuenta(cuenta: Cuenta)

    @Update
    suspend fun updateCuenta(cuenta: Cuenta)

    @Delete
    suspend fun deleteCuenta(cuenta: Cuenta)

}