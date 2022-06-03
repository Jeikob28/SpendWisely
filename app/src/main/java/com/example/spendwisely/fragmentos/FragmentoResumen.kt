package com.example.spendwisely.fragmentos

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.spendwisely.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate

class FragmentoResumen : Fragment() {

    private lateinit var pieChart: PieChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_fragmento_resumen, container, false)

        pieChart = view.findViewById(R.id.frag_resumen_piechart)
        loadPieChart()

        return view
    }

    private fun loadPieChart() {
        var lista = mutableListOf<PieEntry>()
        lista.add(PieEntry(0.1f,"Shopping"))
        lista.add(PieEntry(0.2f,"Recibos"))

        var colores = mutableListOf<Int>()
        for (color in ColorTemplate.MATERIAL_COLORS) {
            colores.add(color)
        }

        for (color in ColorTemplate.VORDIPLOM_COLORS) {
            colores.add(color)
        }

        var dataSet = PieDataSet(lista, "Prueba")
        dataSet.colors = colores

        var data = PieData(dataSet)
        data.setDrawValues(true)
        data.setValueFormatter(PercentFormatter(pieChart))
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.BLACK)

        pieChart.data = data
        pieChart.invalidate()
    }

}