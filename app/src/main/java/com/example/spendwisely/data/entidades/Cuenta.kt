package com.example.spendwisely.data.entidades

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "cuentas")
data class Cuenta(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val nombre : String,
    val saldo : Double
) : Parcelable
