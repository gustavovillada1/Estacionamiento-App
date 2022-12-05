package com.gustavovillada.estacionamiento

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gustavovillada.estacionamiento.databinding.ActivityMainBinding
import com.gustavovillada.estacionamiento.databinding.ActivityParkingBinding
import com.gustavovillada.estacionamiento.model.Zona

class ParkingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityParkingBinding
    private lateinit var zona:Zona

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityParkingBinding.inflate(layoutInflater)
        setContentView(binding.root)



        zona= intent.getSerializableExtra("zona") as Zona

        zona?.let {
            adapteInformationUI(it)
        }

        binding.btnBackParking.setOnClickListener{
            finish()
        }

    }

    private fun adapteInformationUI(zona:Zona){
        binding.tvNombreZona.text=zona.name
        val disponibles=zona.capacity-zona.ocupation
        binding.tvCantidadDisponibles.text="${disponibles} disponibles"
        binding.tvInstruccionesZona.text="Descripción: ${zona.instructions}"
    }
}