package com.example.spendwisely.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.spendwisely.data.conversores.BigDecimalConverter
import com.example.spendwisely.data.conversores.DateConverter
import com.example.spendwisely.data.daos.AjustesUsuarioDao
import com.example.spendwisely.data.daos.CuentaDao
import com.example.spendwisely.data.daos.TransaccionDao
import com.example.spendwisely.data.entidades.AjustesUsuario
import com.example.spendwisely.data.entidades.Cuenta
import com.example.spendwisely.data.entidades.Transaccion

@Database(entities = [Transaccion::class, Cuenta::class, AjustesUsuario::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class, BigDecimalConverter::class)
abstract class SWRoomDatabase : RoomDatabase() {

    abstract fun transaccionDao() : TransaccionDao
    abstract fun cuentaDao() : CuentaDao
    abstract fun ajustesUsuarioDao() : AjustesUsuarioDao

    companion object {

        //Singleton
        @Volatile
        private var INSTANCE : SWRoomDatabase? = null

        fun getSWDatabase(context : Context) : SWRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance!=null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SWRoomDatabase::class.java,
                    "sw_room_database"
                ).addCallback(SWRoomDatabaseCallback()).build()
                INSTANCE = instance
                return instance
            }
        }

    }

}