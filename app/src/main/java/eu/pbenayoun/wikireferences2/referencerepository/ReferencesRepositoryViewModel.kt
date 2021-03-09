package eu.pbenayoun.wikireferences2.referencerepository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.pbenayoun.repository.referencesrepository.ReferencesCallback
import eu.pbenayoun.repository.referencesrepository.ReferencesErrorType
import eu.pbenayoun.repository.referencesrepository.ReferencesRepository
import javax.inject.Inject

@HiltViewModel
class ReferencesRepositoryViewModel @Inject constructor
    (private val referencesRepository: ReferencesRepository) : ViewModel() {

    private var currentQuery  = ""

    private val _fetchingState = MutableLiveData<FetchingState>()
    val fetchingState: LiveData<FetchingState> = _fetchingState

    init{
        _fetchingState.value = FetchingState.Idle()
    }

    fun setCurrentQuery(newQuery : String){
        currentQuery = newQuery
    }

    fun getReferences(){
        referencesRepository.getReferences(currentQuery,referencesCallBackHandler = this::onRepositoryCallback)
    }

    // Internal Cooking
    private fun onRepositoryCallback(callback: ReferencesCallback){
        when(callback){
            is ReferencesCallback.fetching -> onReferencesFetching(callback.query)
            is ReferencesCallback.Success -> onReferencesSuccess(callback.query, callback.references)
            is ReferencesCallback.Error -> onReferencesError(callback.query,callback.errorType)
        }
    }

    private fun onReferencesFetching(query:String){
        Log.d("TMP_DEBUG", "Repository fetching ${query}")
        _fetchingState.value = FetchingState.Fetching()
    }

    private fun onReferencesSuccess(query:String,references : Int){
        _fetchingState.value = FetchingState.Idle()
        Log.d("TMP_DEBUG", "Repository success for ${query}: ${references}")
    }

    private fun onReferencesError(query : String, errorType: ReferencesErrorType){
        _fetchingState.value = FetchingState.Idle()
        Log.d("TMP_DEBUG", "Repository error for ${query}: ${errorType}")
    }

}