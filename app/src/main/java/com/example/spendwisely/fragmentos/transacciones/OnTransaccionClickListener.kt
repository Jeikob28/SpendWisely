package com.example.spendwisely.fragmentos.transacciones

import com.example.spendwisely.data.entidades.Transaccion

interface OnTransaccionClickListener {

    fun onItemClick (transaccion: Transaccion)

    fun onLongItemClick (transaccion: Transaccion) : Boolean

}