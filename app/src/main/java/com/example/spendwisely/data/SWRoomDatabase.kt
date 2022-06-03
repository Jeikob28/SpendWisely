package com.example.spendwisely.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.spendwisely.data.daos.GastoDao
import com.example.spendwisely.data.entidades.Gasto

@Database(entities = [Gasto::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class SWRoomDatabase : RoomDatabase() {

    abstract fun gastoDao() : GastoDao

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
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }

}