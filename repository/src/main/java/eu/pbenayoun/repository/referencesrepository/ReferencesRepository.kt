package eu.pbenayoun.repository.referencesrepository


sealed class ReferencesResponse(query : String){
    class Success(val referencesSuccessModel: ReferencesSuccessModel) : ReferencesResponse(referencesSuccessModel.query)
    class Error(val errorType: ReferencesErrorType) : ReferencesResponse(errorType.query)
}

data class ReferencesSuccessModel(val query: String="", val references: Int=0)

sealed class ReferencesErrorType(val query: String=""){
    class NoNetwork(query: String): ReferencesErrorType(query)
}

interface ReferencesRepository {
    suspend  fun getReferences(query : String) : ReferencesResponse
}