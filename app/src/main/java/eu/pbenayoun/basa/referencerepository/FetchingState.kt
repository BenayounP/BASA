package eu.pbenayoun.basa.referencerepository

import eu.pbenayoun.repository.referencesrepository.ReferencesErrorResponseModel

sealed class FetchingState {
    object Idle : FetchingState()
    object Fetching : FetchingState()
    class Error(val referencesErrorResponseModel : ReferencesErrorResponseModel): FetchingState()
}