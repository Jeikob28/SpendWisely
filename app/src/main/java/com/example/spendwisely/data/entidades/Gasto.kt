package com.example.spendwisely.data.entidades

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "gastos")
data class Gasto(
    @PrimaryKey (autoGenerate = true)
    val id : Long,
    val cantidad : Double,
    val fecha : Date,
    val nota : String
) : Parcelable