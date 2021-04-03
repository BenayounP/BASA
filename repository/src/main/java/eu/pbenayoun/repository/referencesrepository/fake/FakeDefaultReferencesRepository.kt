package eu.pbenayoun.repository.referencesrepository.fake

import eu.pbenayoun.repository.referencesrepository.ReferencesRepository
import eu.pbenayoun.repository.referencesrepository.ReferencesResponse
import kotlinx.coroutines.*
import javax.inject.Inject

class FakeDefaultReferencesRepository @Inject constructor(): ReferencesRepository {
    val requestDelay = 1000L
    var launchError = false

    val fakeErrorReferencesRepository = FakeErrorReferencesRepository(requestDelay)
    val fakeSuccessReferencesRepository = FakeSuccessReferencesRepository(requestDelay)

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

    suspend private fun getSuccessReferences(query: String) : ReferencesResponse {
        fakeSuccessReferencesRepository.nextReferencesAmount = (1..1000).shuffled().first()
        return fakeSuccessReferencesRepository.getReferences(query)
    }

    suspend private fun getErrorReference(query: String) : ReferencesResponse {
        return fakeErrorReferencesRepository.getReferences(query)
    }
}