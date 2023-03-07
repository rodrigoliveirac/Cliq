package com.rodcollab.cliq

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rodcollab.cliq.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navController = NavController(this)
        binding = ActivityMainBinding.inflate(layoutInflater)


        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setupNavigation()
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        bottomNavigation = binding.root.findViewById(R.id.navigationView)

        bottomNavigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.bookings -> {
                    findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.action_clientList_to_bookingListFragment)
                    true
                }
                R.id.clients -> {
                    findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.action_bookingList_to_clientListFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun setupNavigation() {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController =
            findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}