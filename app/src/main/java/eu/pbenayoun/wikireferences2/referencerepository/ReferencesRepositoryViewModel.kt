package eu.pbenayoun.wikireferences2.referencerepository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.pbenayoun.repository.referencesrepository.ReferencesCallback
import eu.pbenayoun.repository.referencesrepository.ReferencesErrorType
import eu.pbenayoun.repository.referencesrepository.ReferencesRepository
import eu.pbenayoun.repository.referencesrepository.ReferencesSuccessModel
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
        referencesRepository.getReferences(currentQuery,referencesCallBackHandler = this::onRepositoryCallback)
    }

    fun onErrorProcessed(){
        _fetchingState.value=FetchingState.Idle()
    }

    // Internal Cooking
    private fun onRepositoryCallback(callback: ReferencesCallback){
        when(callback){
            is ReferencesCallback.fetching -> onReferencesFetching(callback.query)
            is ReferencesCallback.Success -> onReferencesSuccess(callback.referencesSuccessModel)
            is ReferencesCallback.Error -> onReferencesError(callback.errorType)
        }
    }

    private fun onReferencesFetching(query:String){
        Log.d("TMP_DEBUG", "Repository fetching ${query}")
        _fetchingState.value = FetchingState.Fetching()
    }

    private fun onReferencesSuccess(referencesSuccessModel: ReferencesSuccessModel){
        _fetchingState.value = FetchingState.Idle()
        _lastSuccessReferencesModel.value = referencesSuccessModel
        Log.d("TMP_DEBUG", "Repository success for ${referencesSuccessModel.query}: ${referencesSuccessModel.references}")
    }

    private fun onReferencesError(errorType: ReferencesErrorType){
        _fetchingState.value = FetchingState.Error(errorType)
        Log.d("TMP_DEBUG", "Repository error for ${errorType.query}: ${errorType}")
    }

}