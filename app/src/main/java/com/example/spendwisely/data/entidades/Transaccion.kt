package com.example.spendwisely.data.entidades

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal
import java.util.*

@Parcelize
@Entity(tableName = "Transacciones")
data class Transaccion(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val cantidad : BigDecimal,
    val fecha : Date,
    val nota : String,
    val categoria : Int,
    val esGasto : Boolean,
    val esIngreso : Boolean,
    val cuenta : Int
) : Parcelable