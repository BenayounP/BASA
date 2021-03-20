package eu.pbenayoun.repository.referencesrepository

import kotlinx.coroutines.*
import javax.inject.Inject

class FakeSuccessReferencesRepository @Inject constructor(val requestDelay : Long): ReferencesRepository {
    var nextReferencesAmount : Int = 42

    override suspend fun getReferences(query : String) : ReferencesResponse {
            delay(requestDelay)
            return ReferencesResponse.Success(ReferencesSuccessModel(query, nextReferencesAmount))
        }
}