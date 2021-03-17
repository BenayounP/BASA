package eu.pbenayoun.repository.referencesrepository

import kotlinx.coroutines.*
import javax.inject.Inject

class MockedReferencesRepository @Inject constructor(): ReferencesRepository {
    val requestDelay = 500L
    var launchError = false

    override suspend fun getReferences(query : String) : References {
            delay(requestDelay)
            val references = when (launchError) {
                true -> getErrorReference(query)
                false -> getSuccessReferences(query)
            }
            launchError=!launchError
            return references
        }

    // INTERNAL COOKING

    private fun getSuccessReferences(query: String) : References{
        val references = (1..1000).shuffled().first()
        return References.Success(ReferencesSuccessModel(query, references))
    }

    private fun getErrorReference(query: String) : References{
        return References.Error(ReferencesErrorType.NoNetwork(query))
    }

}