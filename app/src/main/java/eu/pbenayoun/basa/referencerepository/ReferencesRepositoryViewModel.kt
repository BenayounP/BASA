package eu.pbenayoun.basa.referencerepository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.pbenayoun.repository.referencesrepository.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ReferencesRepositoryViewModel @Inject constructor
    (private val referencesRepository: ReferencesRepository) : ViewModel() {

    private var currentQuery  = ""

    private val _fetchingState = MutableLiveData<FetchingState>().apply { value=FetchingState.Idle() }
    val fetchingState: LiveData<FetchingState> = _fetchingState


    private val _lastSuccessReferencesModel = MutableLiveData<ReferencesSuccessModel>().apply { value =
        ReferencesSuccessModel() }
    val lastReferencesSuccessReferencesModel : LiveData<ReferencesSuccessModel> = _lastSuccessReferencesModel

    fun getCurrentQuery() : String {
        return currentQuery
    }

    fun setCurrentQuery(newQuery : String){
        currentQuery = newQuery
    }

    fun getReferences(){
        _fetchingState.value = FetchingState.Fetching()
        viewModelScope.launch {
            val references= referencesRepository.getReferences(currentQuery)
            handleResponse(references)
            _fetchingState.value=FetchingState.Idle()
        }


    }

    fun onErrorProcessed(){
        _fetchingState.value=FetchingState.Idle()
    }

    // Internal Cooking
    private fun handleResponse(referencesResponse: ReferencesResponse){
        when(referencesResponse){
            is ReferencesResponse.Success -> onReferencesSuccess(referencesResponse.referencesSuccessModel)
            is ReferencesResponse.Error -> onReferencesError(referencesResponse.referencesErrorModel)
        }
    }

    private fun onReferencesSuccess(referencesSuccessModel: ReferencesSuccessModel){
        _fetchingState.value = FetchingState.Idle()
        _lastSuccessReferencesModel.value = referencesSuccessModel
        Log.d("TMP_DEBUG", "Repository success for ${referencesSuccessModel.query}: ${referencesSuccessModel.references}")
    }

    private fun onReferencesError(errorModel: ReferencesErrorModel){
        _fetchingState.value = FetchingState.Error(errorModel)
        Log.d("TMP_DEBUG", "Repository error for ${errorModel.query}: ${errorModel}")
    }

}