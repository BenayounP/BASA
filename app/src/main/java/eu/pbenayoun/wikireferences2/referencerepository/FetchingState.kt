package eu.pbenayoun.wikireferences2.referencerepository

sealed class FetchingState() {
    class Idle() : FetchingState()
    class Fetching() : FetchingState()
}