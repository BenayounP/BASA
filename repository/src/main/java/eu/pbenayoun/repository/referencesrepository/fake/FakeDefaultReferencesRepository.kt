package eu.pbenayoun.repository.referencesrepository

import kotlinx.coroutines.*
import javax.inject.Inject

class FakeDefaultReferencesRepository @Inject constructor(): ReferencesRepository {
    val requestDelay = 500L
    var launchError = false

    override suspend fun getReferences(query : String) : ReferencesResponse {
            delay(requestDelay)
            val references = when (launchError) {
                true -> getErrorReference(query)
                false -> getSuccessReferences(query)
            }
            launchError=!launchError
            return references
        }

    // INTERNAL COOKING

    private fun getSuccessReferences(query: String) : ReferencesResponse{
        val references = (1..1000).shuffled().first()
        return ReferencesResponse.Success(ReferencesSuccessModel(query, references))
    }

    private fun getErrorReference(query: String) : ReferencesResponse{
        return ReferencesResponse.Error(ReferencesErrorType.NoNetwork(query))
    }
}