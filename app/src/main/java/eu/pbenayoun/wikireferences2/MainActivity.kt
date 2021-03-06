package eu.pbenayoun.wikireferences2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import eu.pbenayoun.repository.referencesrepository.ReferencesRepository
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var referencesRepository: ReferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        referencesRepository.getReferences("",referencesCallBack = this::onRepositoryCallback)
    }

    private fun onRepositoryCallback(references:Int){
        Log.d("TMP_DEBUG", "onRepositoryCallback: ${references}")
    }
}