package com.example.spendwisely.fragmentos.resumen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.spendwisely.activities.MainActivity
import com.example.spendwisely.R
import com.example.spendwisely.data.entidades.AjustesUsuario
import com.example.spendwisely.data.view_models.AjustesUsuarioModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText

class FragmentoContrasenya : Fragment() {

    private var mActivity : MainActivity? = null
    private lateinit var mAjustesUsuario : AjustesUsuarioModel
    private lateinit var switchPass : SwitchMaterial
    private lateinit var pass : TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_fragmento_contrasenya, container, false)

        mAjustesUsuario = ViewModelProvider(this)[AjustesUsuarioModel::class.java]

        switchPass = view.findViewById(R.id.switch_contrasenya)
        pass = view.findViewById(R.id.tiet_contrasenya)
        val estadoPass = view.findViewById<TextView>(R.id.tv_estado_contrasenya)

        val btnGuardarPass = view.findViewById<MaterialButton>(R.id.btn_guardar_ajustes_usuario)

        mAjustesUsuario.unicoAjustesUsuario.observe(viewLifecycleOwner) {ajustesUsuario ->
            if (ajustesUsuario.activadoContrasenya) {
                switchPass.isChecked = true
                estadoPass.text = "Activado"
                pass.setText(ajustesUsuario.contrasenya)
            }
        }

        switchPass.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                estadoPass.text = "Activado"
            } else {
                estadoPass.text = "Desactivado"
                pass.setText("")
            }
        }

        btnGuardarPass.setOnClickListener {
            actualizarAjustesUsuario()
        }

        return view
    }

    private fun actualizarAjustesUsuario() {
        val passw = pass.text.toString()


        if (switchPass.isChecked && passw=="") {
            Toast.makeText(requireContext(),"Tienes que introducir una contraseña antes.",Toast.LENGTH_LONG).show()
        } else if (!switchPass.isChecked && passw!="") {
            Toast.makeText(requireContext(),"No puedes guardar una contraseña si está desactivado.",Toast.LENGTH_LONG).show()
        }
        else {
            val ajustesUsuario = AjustesUsuario(1,switchPass.isChecked,passw)
            mAjustesUsuario.updateAjustesUsuario(ajustesUsuario)
            Toast.makeText(requireContext(),"Actualizado satisfactoriamente!", Toast.LENGTH_LONG).show()
            parentFragmentManager.popBackStackImmediate()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mActivity = activity as? MainActivity
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mActivity?.supportActionBar?.title = "Contraseña"

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
        mActivity?.supportActionBar?.title = getString(R.string.title_resumen)

        val frag = parentFragmentManager.fragments[0] as? FragmentoResumen
        frag?.hideBotomNavBar(true)
    }

}