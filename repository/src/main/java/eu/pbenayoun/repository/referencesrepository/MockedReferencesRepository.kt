package eu.pbenayoun.repository.referencesrepository

import javax.inject.Inject

class MockedReferencesRepository @Inject constructor(): ReferencesRepository {
    override fun getReferences(query: String, referencesCallBack: (references:Int) -> Unit) {
        val references = (1..1000).shuffled().first()
        referencesCallBack(references)
    }
}