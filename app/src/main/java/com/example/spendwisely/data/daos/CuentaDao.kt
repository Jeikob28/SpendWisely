package com.example.spendwisely.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.spendwisely.data.entidades.Cuenta
import java.math.BigDecimal

@Dao
interface CuentaDao {

    @Query("SELECT * FROM Cuentas")
    fun getAllCuentas(): LiveData<List<Cuenta>>

    @Query("SELECT SUM(saldo) FROM Cuentas")
    fun sumSaldoCuentas() : LiveData<BigDecimal>

    @Query("UPDATE Cuentas SET saldo = saldo - :gasto WHERE id = 1")
    suspend fun restarSaldo(gasto: BigDecimal)

    @Query("UPDATE Cuentas SET saldo = saldo + :saldo WHERE id = 1")
    suspend fun sumarSaldo(saldo: BigDecimal)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCuenta(cuenta: Cuenta)

    @Update
    suspend fun updateCuenta(cuenta: Cuenta)

    @Delete
    suspend fun deleteCuenta(cuenta: Cuenta)

}