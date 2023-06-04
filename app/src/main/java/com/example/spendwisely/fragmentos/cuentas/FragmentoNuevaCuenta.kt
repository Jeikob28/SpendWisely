package com.example.spendwisely.fragmentos.cuentas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.spendwisely.MainActivity
import com.example.spendwisely.R
import com.example.spendwisely.data.entidades.Cuenta
import com.example.spendwisely.data.view_models.CuentaViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.math.BigDecimal

class FragmentoNuevaCuenta : Fragment() {

    private var mActivity : MainActivity? = null
    private lateinit var mCuentaViewModel : CuentaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_fragmento_nueva_cuenta, container, false)

        val botonGuardarBill = view.findViewById<MaterialButton>(R.id.btn_guardar_cuenta)

        mCuentaViewModel = ViewModelProvider(this)[CuentaViewModel::class.java]

        if (arguments != null) {
            val cuenta : Cuenta = requireArguments().get("Cuenta") as Cuenta
            val nombre = view.findViewById<TextInputEditText>(R.id.tiet_nombre)
            val saldo = view.findViewById<TextInputEditText>(R.id.tiet_saldo)

            nombre.setText(cuenta.nombre)
            saldo.setText(cuenta.saldo.toString())
        }

        botonGuardarBill.setOnClickListener {
            if (arguments != null) {
                val cuenta : Cuenta = requireArguments().get("Cuenta") as Cuenta
                actualizarBilleteraDB(cuenta.id)
            } else {
                guardarBilleteraDB()
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mActivity = activity as? MainActivity
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (arguments != null) {
            mActivity?.supportActionBar?.title=getString(R.string.titulo_editar_billetera)
        } else
            mActivity?.supportActionBar?.title=getString(R.string.titulo_nueva_billetera)

        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                parentFragmentManager.popBackStackImmediate()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mActivity?.supportActionBar?.title = getString(R.string.title_cuentas)

        val frag = parentFragmentManager.fragments[0] as? FragmentoCuentas
        frag?.hideFAB(true)
        frag?.hideBotomNavBar(true)
    }

    private fun guardarBilleteraDB() {
        val nombre = view?.findViewById<TextInputEditText>(R.id.tiet_nombre)?.text.toString()
        val saldo = view?.findViewById<TextInputEditText>(R.id.tiet_saldo)?.text.toString().toBigDecimalOrNull()

        if (inputCheck(nombre,saldo)) {
            //Crear cuenta
            val cuenta = Cuenta(0,nombre,saldo!!)
            //Añadir cuenta
            mCuentaViewModel.addCuenta(cuenta)
            Toast.makeText(requireContext(),"Añadido satisfactoriamente!", Toast.LENGTH_LONG).show()
            //Volver al fragmento anterior
            parentFragmentManager.popBackStackImmediate()
        } else {
            Toast.makeText(requireContext(),"Rellena todos los campos",Toast.LENGTH_LONG).show()
        }
    }

    private fun actualizarBilleteraDB(idCuenta : Long) {
        val nombre = view?.findViewById<TextInputEditText>(R.id.tiet_nombre)?.text.toString()
        val saldo = view?.findViewById<TextInputEditText>(R.id.tiet_saldo)?.text.toString().toBigDecimalOrNull()

        if (inputCheck(nombre,saldo)) {
            //Crear cuenta
            val cuentaUpdated = Cuenta(idCuenta,nombre,saldo!!)
            //Actualizar gasto
            mCuentaViewModel.updateCuenta(cuentaUpdated)
            Toast.makeText(requireContext(),"Actualizado satisfactoriamente!",Toast.LENGTH_LONG).show()
            //Volver al fragmento anterior
            parentFragmentManager.popBackStackImmediate()
        } else {
            Toast.makeText(requireContext(),"Rellena todos los campos",Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(nombre : String, saldo : BigDecimal?): Boolean {
        return nombre!="" && saldo!=null
    }

}