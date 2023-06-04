package com.example.spendwisely.data.entidades

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
@Entity(tableName = "Cuentas")
data class Cuenta(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val nombre : String,
    val saldo : BigDecimal
) : Parcelable