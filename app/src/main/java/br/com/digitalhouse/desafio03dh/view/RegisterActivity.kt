package br.com.digitalhouse.desafio03dh.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.digitalhouse.desafio03dh.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setUpOnClickListeners()
    }

    fun setUpOnClickListeners(){
        registerBTNSave.setOnClickListener { onBackPressed() }
    }
}