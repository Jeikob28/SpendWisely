package com.example.spendwisely.fragmentos.gastos

import com.example.spendwisely.data.entidades.Gasto

interface OnItemClickListener {

    fun onItemClick (gasto: Gasto)

    fun onLongItemClick (gasto: Gasto) : Boolean

}