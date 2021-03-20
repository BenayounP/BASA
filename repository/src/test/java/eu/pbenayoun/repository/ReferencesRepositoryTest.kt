package eu.pbenayoun.repository

import kotlinx.coroutines.test.runBlockingTest
import com.google.common.truth.Truth.assertThat
import eu.pbenayoun.repository.referencesrepository.*
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ReferencesRepositoryTest {


    @Test
    fun testFakeSuccessReferencesRepository() = runBlockingTest {
        // Arrange
        val arrangeDdelay = 1L
        val arrangeResponseAmount = 1976
        val arrenageQuery="Liverpool"

        val fakeSuccessReferencesRepository = FakeSuccessReferencesRepository(arrangeDdelay)
        fakeSuccessReferencesRepository.nextReferencesAmount=arrangeResponseAmount

        // Act
        val testedResponse=fakeSuccessReferencesRepository.getReferences(arrenageQuery)

        // Assert
        val expectedResponse = ReferencesResponse.Success(ReferencesSuccessModel(arrenageQuery,arrangeResponseAmount))
        assertThat(testedResponse is ReferencesResponse.Success).isTrue()
        assertThat((testedResponse as ReferencesResponse.Success).referencesSuccessModel).isEqualTo(expectedResponse.referencesSuccessModel)
    }

    @Test
    fun testFakeErrorReferencesRepository() = runBlockingTest {
        // Arrange
        val arrangeDelay = 1L
        val arrangeQuery="Liverpool"
        val arrangeErrorType = ReferencesErrorType.NoNetwork()


        val fakeErrorReferencesRepository = FakeErrorReferencesRepository(arrangeDelay)
        fakeErrorReferencesRepository.nextErrorType = arrangeErrorType

        // Act
        val testedResponse=fakeErrorReferencesRepository.getReferences(arrangeQuery)

        // Assert
        val expectedResponseModel = ReferencesErrorModel(arrangeQuery,ReferencesErrorType.NoNetwork())
        val expectedResponse = ReferencesResponse.Error(expectedResponseModel)
        assertThat(testedResponse is ReferencesResponse.Error).isTrue()

        val testedModel = (testedResponse as ReferencesResponse.Error).referencesErrorModel
        assertThat(testedModel.query).isEqualTo(expectedResponseModel.query)
        assertThat(testedModel.referencesErrorType is ReferencesErrorType.NoNetwork).isTrue()

    }
}