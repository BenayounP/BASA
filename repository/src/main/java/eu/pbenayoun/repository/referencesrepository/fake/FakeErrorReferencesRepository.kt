package eu.pbenayoun.repository.referencesrepository

import kotlinx.coroutines.*
import javax.inject.Inject

class FakeErrorReferencesRepository @Inject constructor(val requestDelay : Long): ReferencesRepository {
    var nextErrorType = ReferencesErrorType.NoNetwork()

    override suspend fun getReferences(query : String) : ReferencesResponse {
            delay(requestDelay)
            return ReferencesResponse.Error(ReferencesErrorModel(query, nextErrorType))
        }
}