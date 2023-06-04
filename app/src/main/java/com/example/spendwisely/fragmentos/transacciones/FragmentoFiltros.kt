package com.example.spendwisely.fragmentos.transacciones

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.spendwisely.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.radiobutton.MaterialRadioButton

class FragmentoFiltros : DialogFragment() {

    private var oSeleccionada : Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_fragmento_filtros, container, false)

        val rGroup = view.findViewById<RadioGroup>(R.id.radiog_filtros)
        rGroup.check(R.id.rb_opcion_todos)
        rGroup.setOnCheckedChangeListener { _, checkedId ->
            oSeleccionada = when (checkedId) {
                R.id.rb_opcion_todos -> 1
                R.id.rb_opcion_gastos -> 2
                R.id.rb_opcion_ingresos -> 3
                else -> -1
            }
        }

        val btnCancelar = view.findViewById<MaterialButton>(R.id.btn_cancelar)
        val btnAceptar = view.findViewById<MaterialButton>(R.id.btn_aceptar)

        btnCancelar.setOnClickListener {
            dismiss()
        }

        btnAceptar.setOnClickListener {
            val fragmentoTransaccion = parentFragmentManager.fragments[0] as? FragmentoTransacciones
            val bundle = Bundle()
            bundle.putInt("Filtro",oSeleccionada)
            fragmentoTransaccion?.arguments = bundle
            fragmentoTransaccion?.setRvConFiltro()
            dismiss()
        }

        return view
    }

}