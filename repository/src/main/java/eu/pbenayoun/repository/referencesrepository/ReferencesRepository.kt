package eu.pbenayoun.repository.referencesrepository




sealed class ReferencesCallback(query : String){
    class fetching(val query : String) : ReferencesCallback(query)
    class Success(val referencesSuccessModel: ReferencesSuccessModel) : ReferencesCallback(referencesSuccessModel.query)
    class Error(val errorType: ReferencesErrorType) : ReferencesCallback(errorType.query)
}

data class ReferencesSuccessModel(val query: String="", val references: Int=0)

sealed class ReferencesErrorType(val query: String=""){
    class NoNetwork(query: String): ReferencesErrorType(query)
}

interface ReferencesRepository {
    fun getReferences(query : String, referencesCallBackHandler: (callback:ReferencesCallback) -> Unit)
}