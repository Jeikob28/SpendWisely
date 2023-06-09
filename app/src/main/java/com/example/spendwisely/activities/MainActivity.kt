package com.example.spendwisely.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.example.spendwisely.R
import com.example.spendwisely.data.view_models.AjustesUsuarioModel
import com.example.spendwisely.fragmentos.cuentas.FragmentoCuentas
import com.example.spendwisely.fragmentos.transacciones.FragmentoTransacciones
import com.example.spendwisely.fragmentos.resumen.FragmentoResumen
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavVi : BottomNavigationView

    private lateinit var mAjustesUsuario : AjustesUsuarioModel
    private var checkPass = true

    private val passwordActivityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {result ->
        if (result.resultCode == Activity.RESULT_OK) {
            Toast.makeText(this,"Se ha accedido correctamente.",Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this,"No se ha accedido a la aplicación.",Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private val fragmentoTransacciones = FragmentoTransacciones()
    private val fragmentoCuentas = FragmentoCuentas()
    private val fragmentoResumen = FragmentoResumen()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAjustesUsuario = ViewModelProvider(this)[AjustesUsuarioModel::class.java]

        mAjustesUsuario.unicoAjustesUsuario.observe(this) {ajustesUsuario ->
            if (checkPass) {
                if (ajustesUsuario.activadoContrasenya) {
                    lanzarPassActivity()
                }
                checkPass = false
            }
        }

        title = getString(R.string.title_transacciones)
        setCurrentFragment(fragmentoTransacciones)

        bottomNavVi = findViewById(R.id.bottomNavigationBar)

        bottomNavVi.setOnItemSelectedListener {
            when(it.itemId){
                R.id.transacciones -> {
                    supportActionBar?.title = getString(R.string.title_transacciones)
                    setCurrentFragment(fragmentoTransacciones)
                }
                R.id.cuentas -> {
                    supportActionBar?.title = getString(R.string.title_cuentas)
                    setCurrentFragment(fragmentoCuentas)
                }
                R.id.resumen -> {
                    supportActionBar?.title = getString(R.string.title_resumen)
                    setCurrentFragment(fragmentoResumen)
                }
            }
            true
        }

    }

    private fun lanzarPassActivity() {
        val intent = Intent(this,PasswordActivity::class.java)
        passwordActivityResult.launch(intent)
    }

    private fun setCurrentFragment(fragment : Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragmentos,fragment)
            commit()
        }
    }

}