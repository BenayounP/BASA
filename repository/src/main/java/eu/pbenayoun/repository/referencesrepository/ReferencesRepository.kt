package eu.pbenayoun.repository.referencesrepository

import eu.pbenayoun.repository.referencesrepository.domain.WikiReferencesModel


sealed class ReferencesResponse(query : String){
    class Success(val wikiReferencesSuccessSuccessModel: WikiReferencesModel) : ReferencesResponse(wikiReferencesSuccessSuccessModel.query)
    class Error(val referencesErrorResponseModel: ReferencesErrorResponseModel) : ReferencesResponse(referencesErrorResponseModel.query)
}

data class ReferencesErrorResponseModel(val query: String = "", val referencesErrorType: ReferencesErrorType=ReferencesErrorType.NoNetwork)

sealed class ReferencesErrorType {
    object NoNetwork: ReferencesErrorType()
}

interface ReferencesRepository {
    suspend  fun getReferences(query : String) : ReferencesResponse
}