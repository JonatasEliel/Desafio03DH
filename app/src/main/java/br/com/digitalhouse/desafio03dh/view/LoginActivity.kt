package br.com.digitalhouse.desafio03dh.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.digitalhouse.desafio03dh.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setUpOnClickListeners()
    }

    fun setUpOnClickListeners(){
        loginBTNSave.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        loginTVRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}