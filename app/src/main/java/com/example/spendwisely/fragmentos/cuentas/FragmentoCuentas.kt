package com.example.spendwisely.fragmentos.cuentas

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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spendwisely.R
import com.example.spendwisely.data.entidades.Cuenta
import com.example.spendwisely.data.view_models.CuentaViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Locale

class FragmentoCuentas : Fragment(), FragCuentasAux, OnCuentaClickListener {

    private lateinit var fabCuentas : FloatingActionButton
    private var botomNavView : BottomNavigationView? = null
    private lateinit var mCuentaViewModel : CuentaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_fragmento_cuentas, container, false)

        val saldoTotal = view.findViewById<TextView>(R.id.saldo_total)

        fabCuentas = view.findViewById(R.id.fab_cuentas)
        botomNavView = activity?.findViewById(R.id.bottomNavigationBar)

        //Recycler view
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_cuentas)
        val adapter = CuentaListAdapter(this)
        recyclerView.adapter = adapter

        val gridLayoutManager = GridLayoutManager(requireContext(),2,LinearLayoutManager.VERTICAL,false)
        recyclerView.layoutManager = gridLayoutManager

        mCuentaViewModel = ViewModelProvider(this)[CuentaViewModel::class.java]

        mCuentaViewModel.allCuentas.observe(viewLifecycleOwner) { listCuentas ->
            adapter.setData(listCuentas)
        }

        mCuentaViewModel.sumCuentas.observe(viewLifecycleOwner) { saldo ->
            saldoTotal.text = String.format(Locale.getDefault(),"%.2f",saldo)
        }

        //Color del icono del FAB
        fabCuentas.imageTintList = ColorStateList.valueOf(Color.WHITE)

        //Lanzar nueva billetera (FAB onClick)
        fabCuentas.setOnClickListener{
            val fragmentoNuevaCuenta = FragmentoNuevaCuenta()
            launchFragNuevaBilletera(fragmentoNuevaCuenta)
        }

        return view
    }

    private fun launchFragNuevaBilletera(fragmentoNuevaBilletera : Fragment) {
        parentFragmentManager.beginTransaction().replace(R.id.frag_cuentas, fragmentoNuevaBilletera).addToBackStack(null).commit()
        hideFAB(false)
        hideBotomNavBar(false)
    }

    private fun deleteCuenta(cuenta: Cuenta) {
        val builder = AlertDialog.Builder(requireContext())
        if (cuenta.id.toInt()!=1 && cuenta.nombre!="Efectivo") {
            builder.setPositiveButton("Si") { _, _ ->
                mCuentaViewModel.deleteCuenta(cuenta)
                Toast.makeText(requireContext(),"Eliminado satisfactoriamente!", Toast.LENGTH_LONG).show()
            }
            builder.setNegativeButton("No") { _, _ -> }
            builder.setTitle("Eliminar")
            builder.setMessage("¿Está seguro?")
            builder.create().show()
        } else {
            builder.setMessage("No puedes eliminar la cartera de efectivo.")
            builder.create().show()
        }
    }

    override fun onItemClick(cuenta: Cuenta) {
        val fragmentoNuevaCuenta = FragmentoNuevaCuenta()
        val bundle = Bundle()
        bundle.putParcelable("Cuenta",cuenta)
        fragmentoNuevaCuenta.arguments = bundle

        launchFragNuevaBilletera(fragmentoNuevaCuenta)
    }

    override fun onLongItemClick(cuenta: Cuenta): Boolean {
        deleteCuenta(cuenta)
        return true
    }

    override fun hideFAB(isVisible: Boolean) {
        if (isVisible)
            fabCuentas.show()
        else
            fabCuentas.hide()
    }

    override fun hideBotomNavBar(isVisible: Boolean) {
        if (isVisible)
            botomNavView?.visibility = View.VISIBLE
        else
            botomNavView?.visibility = View.GONE
    }

}