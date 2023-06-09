package com.example.spendwisely.data

import android.content.ContentValues
import androidx.room.OnConflictStrategy
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

class SWRoomDatabaseCallback : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        val contentValuesCuenta = ContentValues()
        val x : Long = 1
        contentValuesCuenta.put("id",x)
        contentValuesCuenta.put("nombre","Efectivo")
        contentValuesCuenta.put("saldo","0")

        db.insert("Cuentas",OnConflictStrategy.IGNORE,contentValuesCuenta)

        val contentValuesAjustesUsuario = ContentValues()
        contentValuesAjustesUsuario.put("id",x)
        contentValuesAjustesUsuario.put("activadoContrasenya",0)
        contentValuesAjustesUsuario.put("contrasenya","")

        db.insert("Ajustes_Usuario",OnConflictStrategy.IGNORE,contentValuesAjustesUsuario)
    }

}