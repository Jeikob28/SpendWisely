package com.example.spendwisely

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import android.os.Bundle
import com.example.spendwisely.fragmentos.cuentas.FragmentoCuentas
import com.example.spendwisely.fragmentos.gastos.FragmentoGastos
import com.example.spendwisely.fragmentos.resumen.FragmentoResumen
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavVi : BottomNavigationView

    private val fragmentoGastos = FragmentoGastos()
    private val fragmentoCuentas = FragmentoCuentas()
    private val fragmentoResumen = FragmentoResumen()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = getString(R.string.title_gastos)
        setCurrentFragment(fragmentoGastos)

        bottomNavVi = findViewById(R.id.bottomNavigationBar)

        bottomNavVi.setOnItemSelectedListener {
            when(it.itemId){
                R.id.gastos-> {
                    supportActionBar?.title = getString(R.string.title_gastos)
                    setCurrentFragment(fragmentoGastos)
                }
                R.id.cuentas-> {
                    supportActionBar?.title = getString(R.string.title_cuentas)
                    setCurrentFragment(fragmentoCuentas)
                }
                R.id.resumen-> {
                    supportActionBar?.title = getString(R.string.title_resumen)
                    setCurrentFragment(fragmentoResumen)
                }
            }
            true
        }

    }

    private fun setCurrentFragment(fragment : Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragmentos,fragment)
            commit()
        }
    }

}