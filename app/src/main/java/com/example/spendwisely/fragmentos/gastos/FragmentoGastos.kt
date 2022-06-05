package com.example.spendwisely.fragmentos.gastos

import android.app.AlertDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spendwisely.R
import com.example.spendwisely.data.entidades.Gasto
import com.example.spendwisely.data.view_models.CuentaViewModel
import com.example.spendwisely.data.view_models.GastoViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FragmentoGastos : Fragment(), FragGastosAux, OnGastoClickListener {

    private lateinit var fabGastos : FloatingActionButton
    private var botomNavBar : BottomNavigationView? = null
    private lateinit var mGastoViewModel : GastoViewModel
    private lateinit var mCuentaViewModel: CuentaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_fragmento_gastos, container, false)

        fabGastos = view.findViewById(R.id.fab_gastos)
        botomNavBar = activity?.findViewById(R.id.bottomNavigationBar)

        //Recycler view
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_gastos)
        val adapter = GastoListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mGastoViewModel = ViewModelProvider(this)[GastoViewModel::class.java]
        mCuentaViewModel = ViewModelProvider(this)[CuentaViewModel::class.java]

        mGastoViewModel.allGastos.observe(viewLifecycleOwner, Observer { listGastos ->
            adapter.setData(listGastos)
        })

        //Color del icono del FAB
        fabGastos.imageTintList = ColorStateList.valueOf(Color.WHITE)

        //Lanzar nuevo gasto (FAB onClick)
        fabGastos.setOnClickListener{
            val fragmentoNuevoGasto = FragmentoNuevoGasto()
            launchFragNuevoGasto(fragmentoNuevoGasto)
        }

        return view
    }

    private fun launchFragNuevoGasto(fragmentoNuevoGasto : Fragment) {
        parentFragmentManager.beginTransaction().replace(R.id.frag_gastos, fragmentoNuevoGasto).addToBackStack(null).commit()
        hideFAB(false)
        hideBotomNavBar(false)
    }

    private fun deleteGasto(gasto: Gasto) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Si") { _, _ ->
            mCuentaViewModel.sumarSaldo(gasto.cantidad)
            mGastoViewModel.deleteGasto(gasto)
            Toast.makeText(requireContext(),"Eliminado satisfactoriamente!",Toast.LENGTH_LONG).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Eliminar")
        builder.setMessage("¿Está seguro?")
        builder.create().show()
    }

    override fun onItemClick(gasto: Gasto) {
        val fragmentoNuevoGasto = FragmentoNuevoGasto()
        val bundle = Bundle()
        bundle.putParcelable("Gasto",gasto)
        fragmentoNuevoGasto.arguments = bundle

        launchFragNuevoGasto(fragmentoNuevoGasto)
    }

    override fun onLongItemClick(gasto: Gasto): Boolean {
        deleteGasto(gasto)
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

}