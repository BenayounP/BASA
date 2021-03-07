package eu.pbenayoun.repository.referencesrepository

sealed class ReferencesCallback{
    class Working() : ReferencesCallback()
    class Success(val references: Int) : ReferencesCallback()
    class Error(val errorType: ReferencesErrorType) : ReferencesCallback()
}

sealed class ReferencesErrorType{
    class NoNetwork(): ReferencesErrorType()
}

interface ReferencesRepository {
    fun getReferences(query : String, referencesCallBackHandler: (callback:ReferencesCallback) -> Unit)
}