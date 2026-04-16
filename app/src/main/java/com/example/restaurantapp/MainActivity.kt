package com.example.restaurantapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.restaurantapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.fragmentContainerView.post {
            val navController = binding.fragmentContainerView.findNavController()
            val appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.restaurantsFragment,
                    R.id.newsFragment,
                    R.id.profileFragment
                )
            )

            binding.bottomNavigationView.setupWithNavController(navController)
            setupActionBarWithNavController(navController, appBarConfiguration)

            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.loginFragment,
                    R.id.registerFragment -> {
                        binding.bottomNavigationView.visibility = View.GONE
                    }
                    else -> {
                        binding.bottomNavigationView.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = binding.fragmentContainerView.findNavController()
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}