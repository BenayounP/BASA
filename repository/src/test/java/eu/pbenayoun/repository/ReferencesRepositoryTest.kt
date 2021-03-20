package eu.pbenayoun.repository

import eu.pbenayoun.repository.referencesrepository.FakeSuccessReferencesRepository
import kotlinx.coroutines.test.runBlockingTest
import com.google.common.truth.Truth.assertThat
import eu.pbenayoun.repository.referencesrepository.ReferencesResponse
import eu.pbenayoun.repository.referencesrepository.ReferencesSuccessModel
import org.junit.Test

import org.junit.Assert.*


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ReferencesRepositoryTest {


    @Test
    fun testFakeSuccessReferencesRepository() = runBlockingTest {
        // Arrange
        val delay = 500L
        val responseAmount = 1976
        val query="Liverpool"

        val fakeSuccessReferencesRepository = FakeSuccessReferencesRepository(delay)
        fakeSuccessReferencesRepository.nextReferencesAmount=responseAmount

        // Act
        val testResponse=fakeSuccessReferencesRepository.getReferences(query)

        // Assert
        val expectedResponse = ReferencesResponse.Success(ReferencesSuccessModel(query,responseAmount))
        assertThat(testResponse is ReferencesResponse.Success).isTrue()
        assertThat((testResponse as ReferencesResponse.Success).referencesSuccessModel).isEqualTo(expectedResponse.referencesSuccessModel)

    }
}