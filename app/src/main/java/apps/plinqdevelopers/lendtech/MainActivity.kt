package apps.plinqdevelopers.lendtech

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import apps.plinqdevelopers.lendtech.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        addNavigationControllers()
        setContentView(view)
    }


    private fun addNavigationControllers() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavBar.setupWithNavController(navController)
        binding.apply {
            navController.addOnDestinationChangedListener { _, destination, _ ->
                val navDestination = destination.id
                if (navDestination == R.id.loansFragment || navDestination == R.id.paymentsFragment || navDestination == R.id.homeFragment ) {
                    bottomNavBar.visibility = View.VISIBLE
                } else {
                    bottomNavBar.visibility = View.GONE
                }
            }
        }
    }
}