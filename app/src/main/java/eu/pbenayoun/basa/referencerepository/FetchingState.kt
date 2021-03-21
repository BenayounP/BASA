package eu.pbenayoun.basa.referencerepository

import eu.pbenayoun.repository.referencesrepository.ReferencesErrorModel

sealed class FetchingState() {
    class Idle() : FetchingState()
    class Fetching() : FetchingState()
    class Error(val referencesErrorModel : ReferencesErrorModel): FetchingState()
}