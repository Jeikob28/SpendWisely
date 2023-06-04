package com.example.spendwisely.fragmentos.transacciones

import android.app.AlertDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spendwisely.R
import com.example.spendwisely.data.entidades.Transaccion
import com.example.spendwisely.data.view_models.CuentaViewModel
import com.example.spendwisely.data.view_models.TransaccionViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FragmentoTransacciones : Fragment(), FragTransaccionesAux, OnTransaccionClickListener {

    private var botomNavBar : BottomNavigationView? = null
    private lateinit var fabGastos : FloatingActionButton
    private lateinit var btnFiltros : MaterialButton
    private lateinit var ttlTipoTransaccion : TextView
    private lateinit var mTransaccionViewModel : TransaccionViewModel
    private lateinit var mCuentaViewModel: CuentaViewModel
    private lateinit var adapter: TransaccionListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_fragmento_transacciones, container, false)

        fabGastos = view.findViewById(R.id.fab_transacciones)
        btnFiltros = view.findViewById(R.id.boton_filtros)
        ttlTipoTransaccion = view.findViewById(R.id.tipo_transacciones)
        botomNavBar = activity?.findViewById(R.id.bottomNavigationBar)

        //Recycler view
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_transacciones)
        adapter = TransaccionListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mTransaccionViewModel = ViewModelProvider(this)[TransaccionViewModel::class.java]
        mCuentaViewModel = ViewModelProvider(this)[CuentaViewModel::class.java]

        mTransaccionViewModel.allTransacciones.observe(viewLifecycleOwner, Observer { listTransacciones ->
            if (arguments != null) {
                val filtroId = requireArguments().get("Filtro") as Int
                var nuevaLista = emptyList<Transaccion>()
                when (filtroId) {
                    1 -> {nuevaLista = listTransacciones
                        ttlTipoTransaccion.text = "Todos"}
                    2 -> {nuevaLista = listTransacciones.filter { it.esGasto }
                        ttlTipoTransaccion.text = "Gastos"}
                    3 -> {nuevaLista = listTransacciones.filter { it.esIngreso }
                        ttlTipoTransaccion.text = "Ingresos"}
                }
                adapter.setData(nuevaLista)
            } else {
                adapter.setData(listTransacciones)
            }
        })

        btnFiltros.setOnClickListener {
            val fragmentoFiltros = FragmentoFiltros()
            fragmentoFiltros.show(parentFragmentManager, "fragmentDialog")
        }

        //Color del icono del FAB
        fabGastos.imageTintList = ColorStateList.valueOf(Color.WHITE)

        //Lanzar nuevo gasto (FAB onClick)
        fabGastos.setOnClickListener{
            val fragmentoNuevaTransaccion = FragmentoNuevaTransaccion()
            launchFragNuevoGasto(fragmentoNuevaTransaccion)
        }

        return view
    }

    private fun launchFragNuevoGasto(fragmentoNuevoGasto : Fragment) {
        parentFragmentManager.beginTransaction().replace(R.id.frag_gastos, fragmentoNuevoGasto).addToBackStack("yokse").commit()
        hideFAB(false)
        hideBotomNavBar(false)
        hideBtnFiltros(false)
    }

    private fun deleteGasto(transaccion: Transaccion) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Si") { _, _ ->
            mCuentaViewModel.sumarSaldo(transaccion.cantidad)
            mTransaccionViewModel.deleteGasto(transaccion)
            Toast.makeText(requireContext(),"Eliminado satisfactoriamente!",Toast.LENGTH_LONG).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Eliminar")
        builder.setMessage("¿Está seguro?")
        builder.create().show()
    }

    fun setRvConFiltro() {
        if (arguments != null) {
            val filtroId = requireArguments().get("Filtro") as Int
            var nuevaLista = emptyList<Transaccion>()
            when (filtroId) {
                1 -> {nuevaLista = mTransaccionViewModel.allTransacciones.value!!
                    ttlTipoTransaccion.text = "Todos"}
                2 -> {nuevaLista = mTransaccionViewModel.allTransacciones.value!!.filter { it.esGasto }
                    ttlTipoTransaccion.text = "Gastos"}
                3 -> {nuevaLista = mTransaccionViewModel.allTransacciones.value!!.filter { it.esIngreso }
                    ttlTipoTransaccion.text = "Ingresos"}
            }
            adapter.setData(nuevaLista)
        }
    }

    override fun onItemClick(transaccion: Transaccion) {
        val fragmentoNuevaTransaccion = FragmentoNuevaTransaccion()
        val bundle = Bundle()
        bundle.putParcelable("Gasto",transaccion)
        fragmentoNuevaTransaccion.arguments = bundle

        launchFragNuevoGasto(fragmentoNuevaTransaccion)
    }

    override fun onLongItemClick(transaccion: Transaccion): Boolean {
        deleteGasto(transaccion)
        return true
    }

    override fun hideFAB(isVisible: Boolean) {
        if (isVisible)
            fabGastos.show()
        else
            fabGastos.hide()
    }

    override fun hideBotomNavBar(isVisible: Boolean) {
        if (isVisible)
            botomNavBar?.visibility = View.VISIBLE
        else
            botomNavBar?.visibility = View.GONE
    }

    override fun hideBtnFiltros(isVisible: Boolean) {
        if (isVisible)
            btnFiltros.visibility = View.VISIBLE
        else
            btnFiltros.visibility = View.GONE
    }

}