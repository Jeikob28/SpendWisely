package com.example.spendwisely.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.spendwisely.data.entidades.Cuenta

@Dao
interface CuentaDao {

    @Query("SELECT * FROM cuentas")
    fun getAllCuentas(): LiveData<List<Cuenta>>

    @Query("SELECT SUM(saldo) FROM cuentas")
    fun sumSaldoCuentas() : LiveData<Double>

    @Query("UPDATE cuentas SET saldo = saldo - :gasto WHERE id = 1")
    suspend fun restarSaldo(gasto: Double)

    @Query("UPDATE cuentas SET saldo = saldo + :saldo WHERE id = 1")
    suspend fun sumarSaldo(saldo: Double)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCuenta(cuenta: Cuenta)

    @Update
    suspend fun updateCuenta(cuenta: Cuenta)

    @Delete
    suspend fun deleteCuenta(cuenta: Cuenta)

}