package com.gustavovillada.estacionamiento

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.gustavovillada.estacionamiento.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var loadingDialog :Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadingDialog = Dialog(this)
        binding.btnLoginLogIn.setOnClickListener {
            login()
        }
    }


    /**
     * Método encargado de mostrar el loading y de llamar al viewModel para iniciar sesión.
     */
    private fun login() {
        loadingDialog.setCancelable(false)
        loadingDialog.setContentView(R.layout.dialog_loading)
        loadingDialog.show()

        loadingDialog.dismiss()

        finish()
        val openAplication= Intent(this, MainActivity::class.java)
        startActivity(openAplication)


    }

}