package eu.pbenayoun.repository.referencesrepository.fake

import eu.pbenayoun.repository.referencesrepository.ReferencesErrorResponseModel
import eu.pbenayoun.repository.referencesrepository.ReferencesErrorType
import eu.pbenayoun.repository.referencesrepository.ReferencesRepository
import eu.pbenayoun.repository.referencesrepository.ReferencesResponse
import kotlinx.coroutines.*
import javax.inject.Inject

class FakeErrorReferencesRepository @Inject constructor(private val requestDelay : Long):
    ReferencesRepository {
    var nextErrorType = ReferencesErrorType.NoNetwork

    override suspend fun getReferences(query : String) : ReferencesResponse {
            delay(requestDelay)
            return ReferencesResponse.Error(ReferencesErrorResponseModel(query, nextErrorType))
        }
}