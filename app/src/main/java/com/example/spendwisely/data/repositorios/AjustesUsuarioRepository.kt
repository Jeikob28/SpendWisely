package com.example.spendwisely.data.repositorios

import androidx.lifecycle.LiveData
import com.example.spendwisely.data.daos.AjustesUsuarioDao
import com.example.spendwisely.data.entidades.AjustesUsuario

class AjustesUsuarioRepository(private val ajustesUsuarioDao: AjustesUsuarioDao) {

    val unicoAjustesUsuario: LiveData<AjustesUsuario> = ajustesUsuarioDao.getAjustesUsuario()

    suspend fun update(ajustesUsuario: AjustesUsuario) {
        ajustesUsuarioDao.updateAjustesUsuario(ajustesUsuario)
    }

}