package com.example.spendwisely.otros

import android.graphics.Color
import com.example.spendwisely.R

enum class Categorias(val valor: Int) {
    COMIDA(0),
    RESTAURANTES(1),
    TRANSPORTE(2),
    ROPA(3),
    SALUD(4),
    ENTRETENIMIENTO(5),
    VIAJES(6),
    FACTURAS(7),
    MASCOTAS(8),
    AHORROS(9),
    DEPOSITO(10),
    SALARIO(11),
    OTROS(12);

    companion object {
        fun obtenerCategoria(valor: Int): String? {
            return values().find{it.valor == valor}?.name
        }

        fun obtenerValor(categoria: String): Int? {
            return values().find{it.name == categoria}?.valor
        }

        fun obtenerImagenCategoria(valor: Int): Int {
            var codCat = -1
            when (valor) {
                0 -> codCat = R.drawable.baseline_local_grocery_store_24
                1 -> codCat = R.drawable.baseline_restaurant_24
                2 -> codCat = R.drawable.baseline_directions_bus_24
                3 -> codCat = R.drawable.baseline_shopping_bag_24
                4 -> codCat = R.drawable.baseline_health_and_safety_24
                5 -> codCat = R.drawable.baseline_local_movies_24
                6 -> codCat = R.drawable.baseline_airplanemode_active_24
                7 -> codCat = R.drawable.baseline_energy_savings_leaf_24
                8 -> codCat = R.drawable.baseline_pets_24
                9 -> codCat = R.drawable.baseline_euro_24
                10 -> codCat = R.drawable.baseline_account_balance_24
                11 -> codCat = R.drawable.baseline_business_center_24
                12 -> codCat = R.drawable.baseline_attach_money_24
            }
            return codCat
        }

        fun obtenerColorCategoria(valor: Int): Int {
            var codColor = -1
            when (valor) {
                0 -> codColor = Color.parseColor("#FB6D9D")
                1 -> codColor = Color.parseColor("#B1E870")
                2 -> codColor = Color.parseColor("#353535")
                3 -> codColor = Color.parseColor("#660FFF")
                4 -> codColor = Color.parseColor("#006804")
                5 -> codColor = Color.parseColor("#FB7C53")
                6 -> codColor = Color.parseColor("#2046FF")
                7 -> codColor = Color.parseColor("#FF2525")
                8 -> codColor = Color.parseColor("#19FFBE")
                9 -> codColor = Color.parseColor("#7EF300")
                10 -> codColor = Color.parseColor("#0062DB")
                11 -> codColor = Color.parseColor("#000000")
                12 -> codColor = Color.parseColor("#D5C21F")
            }
            return codColor
        }
    }
}