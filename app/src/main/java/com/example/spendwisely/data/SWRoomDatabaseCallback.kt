package com.example.spendwisely.data

import android.content.ContentValues
import androidx.room.OnConflictStrategy
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

class SWRoomDatabaseCallback : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        val contentValues = ContentValues()
        val x : Long = 1
        contentValues.put("id",x)
        contentValues.put("nombre","Efectivo")
        contentValues.put("saldo","0")

        db.insert("Cuentas",OnConflictStrategy.IGNORE,contentValues)
    }

}