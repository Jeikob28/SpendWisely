package com.example.spendwisely.fragmentos.gastos

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.spendwisely.MainActivity
import com.example.spendwisely.R
import com.example.spendwisely.data.entidades.Cuenta
import com.example.spendwisely.data.entidades.Gasto
import com.example.spendwisely.data.view_models.CuentaViewModel
import com.example.spendwisely.data.view_models.GastoViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import java.text.SimpleDateFormat
import java.util.*

class FragmentoNuevoGasto : Fragment() {

    private var mActivity : MainActivity? = null
    private lateinit var mGastoViewModel : GastoViewModel
    private lateinit var mCuentaViewModel: CuentaViewModel
    private var antiguaCantidad : Double = 0.0
    private var fechaGasto : Date? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_fragmento_nuevo_gasto, container, false)

        val botonGuardarGasto = view.findViewById<MaterialButton>(R.id.btn_guardar_gasto)
        val botonCalendario = view.findViewById<MaterialButton>(R.id.btn_fecha)
        val tvFechaElegida = view.findViewById<MaterialTextView>(R.id.tv_fecha_elegida)

        val datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("ELIGE LA FECHA").build()

        mGastoViewModel = ViewModelProvider(this)[GastoViewModel::class.java]
        mCuentaViewModel = ViewModelProvider(this)[CuentaViewModel::class.java]

        if (arguments != null) {
            val gasto : Gasto = requireArguments().get("Gasto") as Gasto
            val cantidad = view.findViewById<TextInputEditText>(R.id.tiet_cantidad)
            val nota = view.findViewById<TextInputEditText>(R.id.tiet_nota)

            antiguaCantidad = gasto.cantidad

            cantidad.setText(gasto.cantidad.toString())
            nota.setText(gasto.nota)

            fechaGasto = gasto.fecha
            val fecha = SimpleDateFormat("dd-MMM-yy",Locale.getDefault()).format(fechaGasto!!)
            tvFechaElegida.text = fecha
        }

        botonCalendario.setOnClickListener {
            datePicker.show(parentFragmentManager,"DATE_PICKER")
        }

        datePicker.addOnPositiveButtonClickListener {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.timeInMillis = it
            fechaGasto = calendar.time

            val fecha = SimpleDateFormat("dd-MMM-yy",Locale.getDefault()).format(fechaGasto!!)
            tvFechaElegida.text = fecha
        }

        botonGuardarGasto.setOnClickListener {
            if (arguments != null) {
                val gasto : Gasto = requireArguments().get("Gasto") as Gasto
                actualizarGastoDB(gasto.id)
            } else
                guardarGastoDB()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mActivity = activity as? MainActivity
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (arguments != null) {
            mActivity?.supportActionBar?.title=getString(R.string.titulo_editar_gasto)
        } else
            mActivity?.supportActionBar?.title=getString(R.string.titulo_nuevo_gasto)

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
        mActivity?.supportActionBar?.title = getString(R.string.title_gastos)

        val frag = parentFragmentManager.fragments[0] as? FragmentoGastos
        frag?.hideFAB(true)
        frag?.hideBotomNavBar(true)
    }

    private fun guardarGastoDB() {
        val cantidad = view?.findViewById<TextInputEditText>(R.id.tiet_cantidad)?.text.toString().toDoubleOrNull()
        val nota = view?.findViewById<TextInputEditText>(R.id.tiet_nota)?.text.toString()

        if (inputCheck(cantidad,fechaGasto,nota)) {
            //Crear gasto
            val gasto = Gasto(0,cantidad!!,fechaGasto!!,nota)
            //Añadir gasto
            mGastoViewModel.addGasto(gasto)
            //Actualizamos la cartera
            mCuentaViewModel.restarSaldo(gasto.cantidad)
            Toast.makeText(requireContext(),"Añadido satisfactoriamente!",Toast.LENGTH_LONG).show()
            //Volver al fragmento anterior
            parentFragmentManager.popBackStackImmediate()
        } else {
            Toast.makeText(requireContext(),"Rellena todos los campos",Toast.LENGTH_LONG).show()
        }
    }

    private fun actualizarGastoDB(idGasto : Long) {
        val cantidad = view?.findViewById<TextInputEditText>(R.id.tiet_cantidad)?.text.toString().toDoubleOrNull()
        val nota = view?.findViewById<TextInputEditText>(R.id.tiet_nota)?.text.toString()

        if (inputCheck(cantidad,fechaGasto,nota)) {
            //Crear gasto
            val gastoUpdated = Gasto(idGasto,cantidad!!,fechaGasto!!,nota)
            //Actualizar gasto
            mGastoViewModel.updateGasto(gastoUpdated)
            //Actualizamos la cartera
            mCuentaViewModel.restarSaldo(gastoUpdated.cantidad-antiguaCantidad)
            Toast.makeText(requireContext(),"Actualizado satisfactoriamente!",Toast.LENGTH_LONG).show()
            //Volver al fragmento anterior
            parentFragmentManager.popBackStackImmediate()
        } else {
            Toast.makeText(requireContext(),"Rellena todos los campos",Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(cantidad : Double?, fecha : Date?, nota : String): Boolean {
        return cantidad!=null && fecha!=null && nota!=""
    }

}