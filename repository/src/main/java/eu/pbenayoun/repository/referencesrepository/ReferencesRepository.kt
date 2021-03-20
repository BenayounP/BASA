package eu.pbenayoun.repository.referencesrepository


sealed class ReferencesResponse(query : String){
    class Success(val referencesSuccessModel: ReferencesSuccessModel) : ReferencesResponse(referencesSuccessModel.query)
    class Error(val referencesErrorModel: ReferencesErrorModel) : ReferencesResponse(referencesErrorModel.query)
}

data class ReferencesSuccessModel(val query: String="", val references: Int=0)

data class ReferencesErrorModel(val query: String = "", val referencesErrorType: ReferencesErrorType=ReferencesErrorType.NoNetwork())

sealed class ReferencesErrorType(){
    class NoNetwork(): ReferencesErrorType()
}

interface ReferencesRepository {
    suspend  fun getReferences(query : String) : ReferencesResponse
}