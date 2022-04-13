package id.ngoding.nonton.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.dynamicfeatures.fragment.DynamicNavHostFragment
import androidx.navigation.ui.NavigationUI
import id.ngoding.nonton.R
import id.ngoding.nonton.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        NavigationUI.setupWithNavController(binding.bottomNav, navController)
        binding.bottomNav.itemIconTintList = null
    }

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as DynamicNavHostFragment).navController
    }


}