package eu.pbenayoun.basa

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import eu.pbenayoun.basa.databinding.ActivityMainBinding
import eu.pbenayoun.basa.referencerepository.FetchingState
import eu.pbenayoun.basa.referencerepository.ReferencesRepositoryViewModel
import eu.pbenayoun.repository.referencesrepository.domain.WikiReferencesModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment

        navController = navHostFragment.navController
        setupBottomNavMenu(navController)

        setContentView(binding.root)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("TMP_DEBUG", "onOptionItemSelected")
        return item.onNavDestinationSelected(
            findNavController(R.id.nav_host_fragment)
        ) || super.onOptionsItemSelected(item)
    }

    private fun setupBottomNavMenu(navController: NavController){
        binding.bottomNavView.setupWithNavController(navController)
    }

}