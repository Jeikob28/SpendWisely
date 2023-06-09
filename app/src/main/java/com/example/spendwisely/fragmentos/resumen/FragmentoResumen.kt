package com.example.spendwisely.fragmentos.resumen

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.spendwisely.R
import com.example.spendwisely.data.entidades.Transaccion
import com.example.spendwisely.data.view_models.TransaccionViewModel
import com.example.spendwisely.otros.Categorias
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.google.android.material.bottomnavigation.BottomNavigationView

class FragmentoResumen : Fragment(), FragResumenAux {

    private var botomNavBar : BottomNavigationView? = null

    private lateinit var pieChart : PieChart
    private lateinit var mTransaccionViewModel : TransaccionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_fragmento_resumen, container, false)

        pieChart = view.findViewById(R.id.frag_resumen_piechart)

        botomNavBar = activity?.findViewById(R.id.bottomNavigationBar)

        val btnContrasenya = view.findViewById<ImageView>(R.id.iv_candado)

        mTransaccionViewModel = ViewModelProvider(this)[TransaccionViewModel::class.java]

        mTransaccionViewModel.allTransacciones.observe(viewLifecycleOwner) {listTransacciones ->
            datosPieChart(listTransacciones)
        }

        btnContrasenya.setOnClickListener {
            val fragmentoContrasenya = FragmentoContrasenya()
            launchFragContrasenya(fragmentoContrasenya)
        }

        return view
    }

    private fun datosPieChart(listaTransacciones : List<Transaccion>) {

        pieChart.setUsePercentValues(true)
        pieChart.description.isEnabled = false
        pieChart.animateY(1400, Easing.EaseInOutQuad)
        pieChart.transparentCircleRadius = 0.0f

        val sumaGastos = listaTransacciones.filter { it.esGasto }.sumOf { it.cantidad }
        val sumaIngresos = listaTransacciones.filter { it.esIngreso }.sumOf { it.cantidad }

        val listaPorCategorias = listaTransacciones.groupBy { it.categoria }

        val listaDatos = arrayListOf<PieEntry>()
        val colores = arrayListOf<Int>()

        for (x in listaPorCategorias) {
            listaDatos.add(PieEntry(x.value.size.toFloat(),Categorias.obtenerCategoria(x.key)))
            colores.add(Categorias.obtenerColorCategoria(x.key))
        }

        pieChart.setCenterTextSize(15f)
        pieChart.centerText = "Total Gastos: $sumaGastos€ \n Total Ingresos: $sumaIngresos€"

        val dataSet = PieDataSet(listaDatos,"Transacciones")
        dataSet.colors = colores
        dataSet.sliceSpace = 5f
        dataSet.selectionShift = 10f

        val data = PieData(dataSet)
        data.setDrawValues(true)
        data.setValueFormatter(PercentFormatter(pieChart))
        data.setValueTextSize(16f)
        data.setValueTextColor(Color.WHITE)

        pieChart.data = data
        pieChart.invalidate()

    }

    private fun launchFragContrasenya(fragmentoContrasenya : Fragment) {
        parentFragmentManager.beginTransaction().replace(R.id.frag_resumen, fragmentoContrasenya).addToBackStack(null).commit()
        hideBotomNavBar(false)
    }

    override fun hideBotomNavBar(isVisible: Boolean) {
        if (isVisible)
            botomNavBar?.visibility = View.VISIBLE
        else
            botomNavBar?.visibility = View.GONE
    }

}