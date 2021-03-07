package eu.pbenayoun.wikireferences2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import eu.pbenayoun.repository.referencesrepository.ReferencesCallback
import eu.pbenayoun.repository.referencesrepository.ReferencesRepository
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var referencesRepositoryViewModel: ReferencesRepositoryViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        referencesRepositoryViewModel = ViewModelProvider(this).get(ReferencesRepositoryViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        referencesRepositoryViewModel.getReferences("test")
    }


}