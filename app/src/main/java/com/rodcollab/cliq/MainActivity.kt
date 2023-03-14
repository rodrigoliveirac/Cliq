package com.rodcollab.cliq

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rodcollab.cliq.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setupNavigation()
        setupBottomNavigationView()
    }

    private fun setupBottomNavigationView() {
        bottomNavigationView = binding.root.findViewById(R.id.navigationView)
        addOnDestinationChangedListener()
        setOnItemSelectedListener()
    }

    private fun setOnItemSelectedListener() {
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.clients -> {
                    if (navController.currentDestination?.id == R.id.bookingListFragment)
                        navController.navigate(R.id.action_bookingList_to_clientList)
                }
                else -> {
                    if (navController.currentDestination?.id == R.id.clientListFragment)
                        navController.navigate(R.id.action_clientList_to_bookingList)
                }
            }
            true
        }
    }

    private fun addOnDestinationChangedListener() {
        navController.addOnDestinationChangedListener { controller, _, _ ->
            if (controller.currentDestination?.id == R.id.bookingListFragment || controller.currentDestination?.id == R.id.clientListFragment) {
                bottomNavigationView.visibility = View.VISIBLE
            } else {
                bottomNavigationView.visibility = View.GONE
            }
        }
    }

    private fun setupNavigation() {
        navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration =
            AppBarConfiguration(
                setOf(
                    R.id.clientListFragment, R.id.bookingListFragment
                )
            )

        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        navController =
            findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}