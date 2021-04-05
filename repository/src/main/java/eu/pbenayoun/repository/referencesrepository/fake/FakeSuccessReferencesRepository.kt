package eu.pbenayoun.repository.referencesrepository.fake

import eu.pbenayoun.repository.referencesrepository.ReferencesRepository
import eu.pbenayoun.repository.referencesrepository.ReferencesResponse
import eu.pbenayoun.repository.referencesrepository.domain.WikiReferencesModel
import kotlinx.coroutines.*
import javax.inject.Inject

class FakeSuccessReferencesRepository @Inject constructor(private val requestDelay : Long):
    ReferencesRepository {
    var nextReferencesAmount : Int = 42

    override suspend fun getReferences(query : String) : ReferencesResponse {
            delay(requestDelay)
            return ReferencesResponse.Success(WikiReferencesModel(query, nextReferencesAmount))
        }
}