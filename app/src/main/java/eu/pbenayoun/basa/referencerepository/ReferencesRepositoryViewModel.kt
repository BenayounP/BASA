@file:Suppress("RemoveCurlyBracesFromTemplate")

package eu.pbenayoun.basa.referencerepository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.pbenayoun.repository.referencesrepository.*
import eu.pbenayoun.repository.referencesrepository.domain.WikiReferencesModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ReferencesRepositoryViewModel @Inject constructor
    (private val referencesRepository: ReferencesRepository) : ViewModel() {

    private var currentQuery  = ""

    private val _fetchingState = MutableLiveData<FetchingState>().apply { value=FetchingState.Idle }
    val fetchingState: LiveData<FetchingState> = _fetchingState


    private val _lastSuccessReferencesModel = MutableLiveData<WikiReferencesModel>().apply { value =
        WikiReferencesModel() }
    val lastReferencesSuccessReferencesSuccessModel : LiveData<WikiReferencesModel> = _lastSuccessReferencesModel

    fun getCurrentQuery() : String {
        return currentQuery
    }

    fun setCurrentQuery(newQuery : String){
        currentQuery = newQuery
    }

    fun getReferences(){
        _fetchingState.value = FetchingState.Fetching
        viewModelScope.launch {
            val references= referencesRepository.getReferences(currentQuery)
            handleResponse(references)
            _fetchingState.value=FetchingState.Idle
        }


    }

    fun onErrorProcessed(){
        _fetchingState.value=FetchingState.Idle
    }

    // Internal Cooking
    private fun handleResponse(referencesResponse: ReferencesResponse){
        when(referencesResponse){
            is ReferencesResponse.Success -> onReferencesSuccess(referencesResponse.wikiReferencesSuccessSuccessModel)
            is ReferencesResponse.Error -> onReferencesError(referencesResponse.referencesErrorResponseModel)
        }
    }

    private fun onReferencesSuccess(referencesSuccessSuccessModel: WikiReferencesModel){
        _fetchingState.value = FetchingState.Idle
        _lastSuccessReferencesModel.value = referencesSuccessSuccessModel
        Log.d("TMP_DEBUG", "Repository success for ${referencesSuccessSuccessModel.query}: ${referencesSuccessSuccessModel.references}")
    }

    private fun onReferencesError(errorResponseModel: ReferencesErrorResponseModel){
        _fetchingState.value = FetchingState.Error(errorResponseModel)
        Log.d("TMP_DEBUG", "Repository error for ${errorResponseModel.query}: $errorResponseModel")
    }

}