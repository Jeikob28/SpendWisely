package com.example.spendwisely.fragmentos.gastos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.spendwisely.MainActivity
import com.example.spendwisely.R
import com.example.spendwisely.data.entidades.Gasto
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
    private lateinit var fechaGasto : Date

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_fragmento_nuevo_gasto, container, false)

        val datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("ELIGE LA FECHA").build()

        val boton_guardar_gasto = view.findViewById<MaterialButton>(R.id.btn_guardar_gasto)

        val boton_calendario = view.findViewById<MaterialButton>(R.id.btn_fecha)

        val tv_fecha_elegida = view.findViewById<MaterialTextView>(R.id.tv_fecha_elegida)

        mGastoViewModel = ViewModelProvider(this)[GastoViewModel::class.java]

        if (arguments != null) {
            val gasto : Gasto = requireArguments().get("Gasto") as Gasto
            val cantidad = view.findViewById<TextInputEditText>(R.id.tiet_cantidad)
            val nota = view.findViewById<TextInputEditText>(R.id.tiet_nota)

            cantidad.setText(gasto.cantidad.toString())

            fechaGasto = gasto.fecha
            val fecha = SimpleDateFormat("dd-MMM-yy",Locale.getDefault()).format(fechaGasto)
            tv_fecha_elegida.text = fecha

            nota.setText(gasto.nota)
        }

        boton_calendario.setOnClickListener {
            datePicker.show(parentFragmentManager,"DATE_PICKER")
        }

        datePicker.addOnPositiveButtonClickListener {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.timeInMillis = it
            fechaGasto = calendar.time
            val fecha = SimpleDateFormat("dd-MMM-yy",Locale.getDefault()).format(fechaGasto)
            tv_fecha_elegida.text = fecha
        }

        boton_guardar_gasto.setOnClickListener {
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

    private fun guardarGastoDB() {

        val cantidad = view?.findViewById<TextInputEditText>(R.id.tiet_cantidad)?.text.toString().toDoubleOrNull()
        val nota = view?.findViewById<TextInputEditText>(R.id.tiet_nota)?.text.toString()

        if (inputCheck(cantidad,nota)) {
            //Crear gasto
            val gasto = Gasto(0, cantidad!!, fechaGasto, nota)
            //Añadir gasto
            mGastoViewModel.addGasto(gasto)
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

        if (inputCheck(cantidad,nota)) {
            //Crear gasto
            val gastoUpdated = Gasto(idGasto, cantidad!!, fechaGasto, nota)
            //Actualizar gasto
            mGastoViewModel.updateGasto(gastoUpdated)
            Toast.makeText(requireContext(),"Actualizado satisfactoriamente!",Toast.LENGTH_LONG).show()
            //Volver al fragmento anterior
            parentFragmentManager.popBackStackImmediate()
        } else {
            Toast.makeText(requireContext(),"Rellena todos los campos",Toast.LENGTH_LONG).show()
        }

    }

    private fun inputCheck(cantidad : Double?, nota : String): Boolean {
        return cantidad!=null && nota!=""
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
        mActivity?.supportActionBar?.title=getString(R.string.app_name)

        val frag = parentFragmentManager.fragments[0] as? FragmentoGastos
        frag?.hideFAB(true)
        frag?.hideBotomNavBar(true)
    }

}