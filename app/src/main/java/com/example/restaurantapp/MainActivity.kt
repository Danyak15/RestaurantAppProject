package com.example.restaurantapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.restaurantapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.view.size
import androidx.core.view.get

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isProgrammaticSelection = false
    private var currentTabId = R.id.restaurantsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.restaurantsFragment, R.id.newsFragment, R.id.profileFragment)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            if (isProgrammaticSelection) return@setOnItemSelectedListener true
            NavigationUI.onNavDestinationSelected(item, navController)
        }

        val tabIds = (0 until binding.bottomNavigationView.menu.size)
            .map { binding.bottomNavigationView.menu[it].itemId }
            .toSet()

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            if (isProgrammaticSelection) return@setOnItemSelectedListener true
            currentTabId = item.itemId
            NavigationUI.onNavDestinationSelected(item, navController)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id in tabIds) {
                currentTabId = destination.id
            }

            isProgrammaticSelection = true
            binding.bottomNavigationView.selectedItemId = currentTabId
            isProgrammaticSelection = false

            binding.bottomNavigationView.isVisible = destination.id !in setOf(
                R.id.loginFragment,
                R.id.registerFragment
            )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_container_view) as NavHostFragment
        return navHostFragment.navController.navigateUp() || super.onSupportNavigateUp()
    }
}