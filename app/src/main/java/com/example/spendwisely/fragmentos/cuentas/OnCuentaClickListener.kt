package com.example.spendwisely.fragmentos.cuentas

import com.example.spendwisely.data.entidades.Cuenta

interface OnCuentaClickListener {

    fun onItemClick (cuenta: Cuenta)

    fun onLongItemClick (cuenta: Cuenta) : Boolean

}