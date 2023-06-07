package com.example.spendwisely.fragmentos.transacciones

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.spendwisely.MainActivity
import com.example.spendwisely.R
import com.example.spendwisely.data.entidades.Transaccion
import com.example.spendwisely.data.view_models.CuentaViewModel
import com.example.spendwisely.data.view_models.TransaccionViewModel
import com.example.spendwisely.otros.Categorias
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class FragmentoNuevaTransaccion : Fragment() {

    private var mActivity : MainActivity? = null
    private lateinit var mTransaccionViewModel : TransaccionViewModel
    private lateinit var mCuentaViewModel : CuentaViewModel
    private var antiguaCantidad : BigDecimal = BigDecimal(0)
    private var fechaGasto : Date? = null
    private lateinit var btnGasto : MaterialButton
    private lateinit var btnIngreso : MaterialButton
    private var esGasto = false
    private var esIngreso = false
    private lateinit var spinnerCategorias : Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_fragmento_nueva_transaccion, container, false)

        mTransaccionViewModel = ViewModelProvider(this)[TransaccionViewModel::class.java]
        mCuentaViewModel = ViewModelProvider(this)[CuentaViewModel::class.java]

        val botonGuardarGasto = view.findViewById<MaterialButton>(R.id.btn_guardar_transaccion)
        val botonCalendario = view.findViewById<MaterialButton>(R.id.btn_fecha)
        val tvFechaElegida = view.findViewById<MaterialTextView>(R.id.tv_fecha_elegida)

        val datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("ELIGE LA FECHA").build()

        val cuentaSpinner = view.findViewById<Spinner>(R.id.spinner_cuentas)

        mCuentaViewModel.allCuentas.observe(viewLifecycleOwner) { listCuentas ->
            val listaCuentas = listCuentas.map { it.nombre }
            val cuAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, listaCuentas)
            cuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            cuentaSpinner.adapter = cuAdapter
            if (arguments != null) {
                val transaccion: Transaccion = requireArguments().get("Gasto") as Transaccion
                cuentaSpinner.setSelection(transaccion.cuenta - 1)
            }
        }

        btnGasto = view.findViewById(R.id.tipo_gasto)
        btnIngreso = view.findViewById(R.id.tipo_ingreso)
        spinnerCategorias = view.findViewById(R.id.spinner_categorias)
        eligeTipoTransaccion(1)

        if (arguments != null) {
            val transaccion : Transaccion = requireArguments().get("Gasto") as Transaccion
            val cantidad = view.findViewById<TextInputEditText>(R.id.tiet_cantidad)
            val nota = view.findViewById<TextInputEditText>(R.id.tiet_nota)

            if (transaccion.esIngreso) {
                eligeTipoTransaccion(2)
            }

            antiguaCantidad = transaccion.cantidad

            cantidad.setText(transaccion.cantidad.toString())
            nota.setText(transaccion.nota)

            val aAdapter = spinnerCategorias.adapter as ArrayAdapter<*>
            var i = 0
            for (x in 0 until aAdapter.count) {
                if (aAdapter.getItem(x) == Categorias.obtenerCategoria(transaccion.categoria)) {
                    i = x
                    break
                }
            }
            spinnerCategorias.setSelection(i)

            fechaGasto = transaccion.fecha
            val fecha = SimpleDateFormat("dd-MMM-yy",Locale.getDefault()).format(fechaGasto!!)
            tvFechaElegida.text = fecha
        }

        btnGasto.setOnClickListener {
            eligeTipoTransaccion(1)
        }

        btnIngreso.setOnClickListener {
            eligeTipoTransaccion(2)
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
                val transaccion : Transaccion = requireArguments().get("Gasto") as Transaccion
                actualizarGastoDB(transaccion.id)
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
            mActivity?.supportActionBar?.title=getString(R.string.titulo_editar_transaccion)
        } else
            mActivity?.supportActionBar?.title=getString(R.string.titulo_nueva_transaccion)

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
        mActivity?.supportActionBar?.title = getString(R.string.title_transacciones)

        val frag = parentFragmentManager.fragments[0] as? FragmentoTransacciones
        frag?.hideFAB(true)
        frag?.hideBotomNavBar(true)
        frag?.hideBtnFiltros(true)
    }

    private fun guardarGastoDB() {
        val cantidad = view?.findViewById<TextInputEditText>(R.id.tiet_cantidad)?.text.toString().toBigDecimalOrNull()
        val nota = view?.findViewById<TextInputEditText>(R.id.tiet_nota)?.text.toString()
        val categoria = view?.findViewById<Spinner>(R.id.spinner_categorias)?.selectedItem as String
        val cuenta = view?.findViewById<Spinner>(R.id.spinner_cuentas)?.selectedItemPosition

        if (inputCheck(cantidad,fechaGasto,nota)) {
            //Crear gasto
            val transaccion = Transaccion(0,cantidad!!,fechaGasto!!,nota,Categorias.obtenerValor(categoria)!!,esGasto,esIngreso,cuenta!!+1)
            //Añadir gasto
            mTransaccionViewModel.addGasto(transaccion)
            //Actualizamos la cartera
            mCuentaViewModel.restarSaldo(transaccion.cantidad)
            Toast.makeText(requireContext(),"Añadido satisfactoriamente!",Toast.LENGTH_LONG).show()
            //Volver al fragmento anterior
            parentFragmentManager.popBackStackImmediate()
        } else {
            Toast.makeText(requireContext(),"Rellena todos los campos",Toast.LENGTH_LONG).show()
        }
    }

    private fun actualizarGastoDB(idGasto : Long) {
        val cantidad = view?.findViewById<TextInputEditText>(R.id.tiet_cantidad)?.text.toString().toBigDecimalOrNull()
        val nota = view?.findViewById<TextInputEditText>(R.id.tiet_nota)?.text.toString()
        val categoria = view?.findViewById<Spinner>(R.id.spinner_categorias)?.selectedItem as String
        val cuenta = view?.findViewById<Spinner>(R.id.spinner_cuentas)?.selectedItemPosition

        if (inputCheck(cantidad,fechaGasto,nota)) {
            //Crear gasto
            val transaccionUpdated = Transaccion(idGasto,cantidad!!,fechaGasto!!,nota,Categorias.obtenerValor(categoria)!!,esGasto,esIngreso,cuenta!!+1)
            //Actualizar gasto
            mTransaccionViewModel.updateGasto(transaccionUpdated)
            //Actualizamos la cartera
            mCuentaViewModel.restarSaldo(transaccionUpdated.cantidad-antiguaCantidad)
            Toast.makeText(requireContext(),"Actualizado satisfactoriamente!",Toast.LENGTH_LONG).show()
            //Volver al fragmento anterior
            parentFragmentManager.popBackStackImmediate()
        } else {
            Toast.makeText(requireContext(),"Rellena todos los campos",Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(cantidad : BigDecimal?, fecha : Date?, nota : String): Boolean {
        return cantidad!=null && fecha!=null && nota!=""
    }

    private fun eligeTipoTransaccion(btonTipo : Int) {
        if (btonTipo == 1) {
            btnGasto.strokeColor = ColorStateList.valueOf(Color.RED)
            btnGasto.strokeWidth = 7
            btnIngreso.strokeColor = null
            btnIngreso.strokeWidth = 0
            val categoriasGastos = Categorias.values().map{it.name}.toMutableList()
            for (x in 9..11) {
                categoriasGastos.remove(Categorias.obtenerCategoria(x))
            }
            val cAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categoriasGastos)
            cAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCategorias.adapter = cAdapter
            esGasto = true
            esIngreso = false
        } else {
            btnIngreso.strokeColor = ColorStateList.valueOf(Color.GREEN)
            btnIngreso.strokeWidth = 7
            btnGasto.strokeColor = null
            btnGasto.strokeWidth = 0
            val categoriasIngresos = Categorias.values().map{it.name}.toMutableList()
            for (x in 0..8) {
                categoriasIngresos.remove(Categorias.obtenerCategoria(x))
            }
            val cAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categoriasIngresos)
            cAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCategorias.adapter = cAdapter
            esGasto = false
            esIngreso = true
        }
    }

}