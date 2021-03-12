package eu.pbenayoun.basa.referencerepository

import eu.pbenayoun.repository.referencesrepository.ReferencesErrorType

sealed class FetchingState() {
    class Idle() : FetchingState()
    class Fetching() : FetchingState()
    class Error(val referencesErrorType: ReferencesErrorType): FetchingState()
}