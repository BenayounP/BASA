package eu.pbenayoun.repository.referencesrepository


sealed class References(query : String){
    class Success(val referencesSuccessModel: ReferencesSuccessModel) : References(referencesSuccessModel.query)
    class Error(val errorType: ReferencesErrorType) : References(errorType.query)
}

data class ReferencesSuccessModel(val query: String="", val references: Int=0)

sealed class ReferencesErrorType(val query: String=""){
    class NoNetwork(query: String): ReferencesErrorType(query)
}

interface ReferencesRepository {
    suspend  fun getReferences(query : String) : References
}