package ru.sweetmilk.movieapp

import android.content.pm.ActivityInfo
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.sweetmilk.movieapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val navView = binding.bottomNavigationView
        navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_movie_list,
                R.id.navigation_user_page,
                R.id.navigation_auth_choose,
                R.id.navigation_user_data,
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        navView.apply {
            setupWithNavController(navController)
            setOnItemSelectedListener { item ->
                if (item.itemId != navView.selectedItemId) {
                    navController.popBackStack()
                    navController.navigate(item.itemId)
                }
                true
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController
            .navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    fun showSnackBar(snackBarText: String?) {
        Snackbar.make(binding.root, snackBarText ?: "", Snackbar.LENGTH_SHORT)
            .setAnchorView(binding.bottomNavigationView)
            .show()
    }
}