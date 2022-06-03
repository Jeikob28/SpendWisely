package com.example.spendwisely.fragmentos

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.spendwisely.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FragmentoCuentas : Fragment() {

    private lateinit var fabCuentas : FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_fragmento_cuentas, container, false)

        fabCuentas = view.findViewById(R.id.fab_cuentas)

        //Color del icono del FAB
        fabCuentas.imageTintList = ColorStateList.valueOf(Color.WHITE)

        return view
    }

}