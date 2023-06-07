package com.example.spendwisely.data.conversores

import androidx.room.TypeConverter
import java.math.BigDecimal

class BigDecimalConverter {

    @TypeConverter
    fun fromBigDecimal(bigDecimal: BigDecimal?) : String? {
        return bigDecimal?.toString()
    }

    @TypeConverter
    fun toBigDecimal(value: String?) : BigDecimal? {
        return value?.let { BigDecimal(it) }
    }

}