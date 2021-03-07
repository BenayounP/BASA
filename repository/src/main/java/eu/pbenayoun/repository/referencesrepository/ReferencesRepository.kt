package eu.pbenayoun.repository.referencesrepository

sealed class ReferencesCallback(val query : String){
    class fetching(query : String) : ReferencesCallback(query)
    class Success(query: String, val references: Int) : ReferencesCallback(query)
    class Error(query : String, val errorType: ReferencesErrorType) : ReferencesCallback(query)
}

sealed class ReferencesErrorType{
    class NoNetwork(): ReferencesErrorType()
}

interface ReferencesRepository {
    fun getReferences(query : String, referencesCallBackHandler: (callback:ReferencesCallback) -> Unit)
}