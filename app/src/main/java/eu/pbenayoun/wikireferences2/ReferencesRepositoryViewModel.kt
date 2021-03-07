package eu.pbenayoun.wikireferences2

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.pbenayoun.repository.referencesrepository.ReferencesCallback
import eu.pbenayoun.repository.referencesrepository.ReferencesRepository
import javax.inject.Inject

@HiltViewModel
class ReferencesRepositoryViewModel @Inject constructor
    (private val referencesRepository: ReferencesRepository) : ViewModel() {


    fun getReferences(query : String){
        referencesRepository.getReferences(query,referencesCallBackHandler = this::onRepositoryCallback)
    }

    // Internal Cooking
    private fun onRepositoryCallback(callback: ReferencesCallback){
        when(callback){
            is ReferencesCallback.fetching -> Log.d("TMP_DEBUG", "Repository fetching ${callback.query}")
            is ReferencesCallback.Success -> Log.d("TMP_DEBUG", "Repository success for ${callback.query}: ${callback.references}")
            is ReferencesCallback.Error -> Log.d("TMP_DEBUG", "Repository error for ${callback.query}: ${callback.errorType}")
        }
    }

}