package eu.pbenayoun.repository.referencesrepository

interface ReferencesRepository {
    fun getReferences(query : String, referencesCallBack: (references:Int) -> Unit)
}