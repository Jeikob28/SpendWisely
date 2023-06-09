package com.example.spendwisely.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.spendwisely.data.entidades.AjustesUsuario

@Dao
interface AjustesUsuarioDao {

    @Query("SELECT * FROM Ajustes_Usuario LIMIT 1")
    fun getAjustesUsuario() : LiveData<AjustesUsuario>

    @Update
    suspend fun updateAjustesUsuario(ajustesUsuario: AjustesUsuario)

}