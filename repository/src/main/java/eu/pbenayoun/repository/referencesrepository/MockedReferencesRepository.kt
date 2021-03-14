package eu.pbenayoun.repository.referencesrepository

import android.util.Log
import kotlinx.coroutines.*
import javax.inject.Inject

class MockedReferencesRepository @Inject constructor(): ReferencesRepository {
    val requestDelay = 500L
    var launchError = false

    init{
        Log.d("TMP_DEBUG","MockedReferencesRepository init")
    }

    override fun getReferences(query : String, referencesCallBackHandler: (callback:ReferencesCallback) -> Unit) {
        GlobalScope.launch(Dispatchers.Default) {
            Log.d("TMP_DEBUG","get references launch")
            launch(Dispatchers.Main) {
                Log.d("TMP_DEBUG","get references announce fetching")
                referencesCallBackHandler(ReferencesCallback.fetching(query))
            }
            delay(requestDelay)
            Log.d("TMP_DEBUG","get references after delay")
            when (launchError){
                true -> launchError(this,query,referencesCallBackHandler)
                false -> launchSuccess(this,query,referencesCallBackHandler)
            }
            launchError=!launchError
        }
    }

    fun launchSuccess(coroutineScope: CoroutineScope,
                    query : String,
                    referencesCallBackHandler: (callback:ReferencesCallback) -> Unit) : Job{
        val references = (1..1000).shuffled().first()
        return coroutineScope.launch(Dispatchers.Main) {
            referencesCallBackHandler(ReferencesCallback.Success(ReferencesSuccessModel(query, references)))
        }
    }

    fun launchError(coroutineScope: CoroutineScope,
                    query : String,
                    referencesCallBackHandler: (callback:ReferencesCallback) -> Unit) : Job{
        return  coroutineScope.launch(Dispatchers.Main) {
            referencesCallBackHandler(ReferencesCallback.Error(ReferencesErrorType.NoNetwork(query)))
        }
    }

}