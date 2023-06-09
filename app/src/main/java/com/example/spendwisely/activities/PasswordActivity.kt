package com.example.spendwisely.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.spendwisely.R
import com.example.spendwisely.data.entidades.AjustesUsuario
import com.example.spendwisely.data.view_models.AjustesUsuarioModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class PasswordActivity : AppCompatActivity() {

    private lateinit var mAjustesUsuario : AjustesUsuarioModel
    private lateinit var ajustesUsuario: AjustesUsuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)

        mAjustesUsuario = ViewModelProvider(this)[AjustesUsuarioModel::class.java]

        mAjustesUsuario.unicoAjustesUsuario.observe(this) {vmAjustesUsuario ->
            ajustesUsuario = vmAjustesUsuario
        }

        val tietPassword = findViewById<TextInputEditText>(R.id.tiet_password)
        val btnAcceder = findViewById<MaterialButton>(R.id.btn_acceder)

        btnAcceder.setOnClickListener {
            val passwordIntroducida = tietPassword.text.toString()
            if (passwordIntroducida == ajustesUsuario.contrasenya) {
                val resultIntent = Intent()
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this,"Contraseña incorrecta.",Toast.LENGTH_SHORT).show()
            }
        }

    }

}