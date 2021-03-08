package eu.pbenayoun.wikireferences2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import eu.pbenayoun.wikireferences2.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var referencesRepositoryViewModel: ReferencesRepositoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        referencesRepositoryViewModel = ViewModelProvider(this).get(ReferencesRepositoryViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()

    }


}