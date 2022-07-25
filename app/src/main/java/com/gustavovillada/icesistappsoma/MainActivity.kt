package com.gustavovillada.icesistappsoma

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.gustavovillada.icesistappsoma.databinding.ActivityMainBinding
import com.gustavovillada.icesistappsoma.fragment.EmprendimientosFragment
import com.gustavovillada.icesistappsoma.fragment.ProductsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var productsFragment: ProductsFragment
    private lateinit var emprendimientosFragment: EmprendimientosFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productsFragment = ProductsFragment.newInstance()
        emprendimientosFragment = EmprendimientosFragment.newInstance()

        showFragment(productsFragment)
        binding.navigator.setOnItemSelectedListener {
                menuItem->
            if(menuItem.itemId==R.id.revisionsProductsItem){
                showFragment(productsFragment)
            }else if(menuItem.itemId==R.id.emprendimientosItem){
                showFragment(emprendimientosFragment)
            }

            true
        }
    }

    private fun showFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.fragmentContainer.id,fragment)
        transaction.commit()
    }


}