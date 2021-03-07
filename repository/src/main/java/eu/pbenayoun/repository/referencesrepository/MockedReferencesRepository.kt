package eu.pbenayoun.repository.referencesrepository

import kotlinx.coroutines.*
import javax.inject.Inject

class MockedReferencesRepository @Inject constructor(): ReferencesRepository {
    override fun getReferences(query : String, referencesCallBackHandler: (callback:ReferencesCallback) -> Unit) {
        GlobalScope.launch(Dispatchers.Default) {
            launch(Dispatchers.Main) {
                referencesCallBackHandler(ReferencesCallback.Working())
            }
            delay(1000L)
            val references = (1..1000).shuffled().first()
            launch(Dispatchers.Main) {
                referencesCallBackHandler(ReferencesCallback.Success(references))
            }
        }
    }

}