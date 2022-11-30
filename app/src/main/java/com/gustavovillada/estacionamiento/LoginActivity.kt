package com.gustavovillada.estacionamiento

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.gustavovillada.estacionamiento.databinding.ActivityLoginBinding
import com.gustavovillada.estacionamiento.viewModel.LoginActivityViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val loginActivityViewModel: LoginActivityViewModel by viewModels()
    private lateinit var loadingDialog :Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadingDialog = Dialog(this)
        observerLogIn()
        binding.btnLoginLogIn.setOnClickListener {
            login()
        }
    }

    /**
     * Método encargado de observar el comportamiento del inicio de sesión de firebase desde el viewModel
     */
    private fun observerLogIn(){
        loginActivityViewModel.isSigned.observe(this, Observer {
            isLogIn->
            if (isLogIn){
                loadingDialog.dismiss()

                finish()
                val openAplication= Intent(this, MainActivity::class.java)
                startActivity(openAplication)
            }else{

                loadingDialog.dismiss()
                Toast.makeText(this,"Ha ocurrido un error al iniciar sesión", Toast.LENGTH_SHORT).show()
            }
        })
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
        /*
        if(isOkInputs()){
            val email=binding.inputEmailLogin.text.toString()
            val password=binding.inputPasswordLogin.text.toString()
            loginActivityViewModel.sigInWithEmail(email,password)
        }else{
            loadingDialog.dismiss()
        }*/

    }

    /**
     * Método encargado de verificar que la contraseña y el correo electrónico sean válidos.
     */
    private fun isOkInputs(): Boolean {
        if (binding.inputEmailLogin.text.toString().length<3){
            Toast.makeText(this,"Escribe un email válido", Toast.LENGTH_SHORT).show()

            return false
        }else if(binding.inputPasswordLogin.text.toString().length<3){
            Toast.makeText(this,"Escribe una contraseña válida", Toast.LENGTH_SHORT).show()

            return false;
        }
        return true
    }
}