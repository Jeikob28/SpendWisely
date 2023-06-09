package com.example.spendwisely.data.entidades

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Ajustes_Usuario")
data class AjustesUsuario(
    @PrimaryKey
    val id : Long,
    val activadoContrasenya : Boolean,
    val contrasenya : String
) : Parcelable