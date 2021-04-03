package eu.pbenayoun.repository

import kotlinx.coroutines.test.runBlockingTest
import com.google.common.truth.Truth.assertThat
import eu.pbenayoun.repository.referencesrepository.*
import eu.pbenayoun.repository.referencesrepository.domain.WikiReferencesModel
import eu.pbenayoun.repository.referencesrepository.fake.FakeDefaultReferencesRepository
import eu.pbenayoun.repository.referencesrepository.fake.FakeErrorReferencesRepository
import eu.pbenayoun.repository.referencesrepository.fake.FakeSuccessReferencesRepository
import org.junit.Test



class FakeReferencesRepositoriesTest {


    @Test
    fun testFakeSuccessReferencesRepository() = runBlockingTest {
        // Arrange
        val arrangeDelay = 1L
        val arrangeResponseAmount = 1976
        val arrangeQuery="Liverpool"

        val fakeSuccessReferencesRepository = FakeSuccessReferencesRepository(arrangeDelay)
        fakeSuccessReferencesRepository.nextReferencesAmount=arrangeResponseAmount

        // Act
        val testedResponse=fakeSuccessReferencesRepository.getReferences(arrangeQuery)

        // Assert
        val expectedResponse = ReferencesResponse.Success(WikiReferencesModel(arrangeQuery,arrangeResponseAmount))
        assertThat(testedResponse is ReferencesResponse.Success).isTrue()
        assertThat((testedResponse as ReferencesResponse.Success).wikiReferencesSuccessSuccessModel).isEqualTo(expectedResponse.wikiReferencesSuccessSuccessModel)
    }

    @Test
    fun testFakeErrorReferencesRepository() = runBlockingTest {
        // Arrange
        val arrangeDelay = 1L
        val arrangeQuery="Liverpool"
        val arrangeErrorType = ReferencesErrorType.NoNetwork


        val fakeErrorReferencesRepository = FakeErrorReferencesRepository(arrangeDelay)
        fakeErrorReferencesRepository.nextErrorType = arrangeErrorType

        // Act
        val testedResponse=fakeErrorReferencesRepository.getReferences(arrangeQuery)

        // Assert
        val expectedResponseModel = ReferencesErrorResponseModel(arrangeQuery,ReferencesErrorType.NoNetwork)
        assertThat(testedResponse is ReferencesResponse.Error).isTrue()

        val testedModel = (testedResponse as ReferencesResponse.Error).referencesErrorResponseModel
        assertThat(testedModel.query).isEqualTo(expectedResponseModel.query)
        assertThat(testedModel.referencesErrorType is ReferencesErrorType.NoNetwork).isTrue()
    }

    @Test
    fun testDefaultErrorReferencesRepository() = runBlockingTest {
        // Arrange
        val defaultErrorReferencesRepository = FakeDefaultReferencesRepository()

        // Step 1 : Test success
        // Arrange
        val arrangeQuery1="Liverpool"

        // Act
        val testedSuccessResponse=defaultErrorReferencesRepository.getReferences(arrangeQuery1)

        // Assert
        assertThat(testedSuccessResponse is ReferencesResponse.Success).isTrue()
        val testedSuccessModel= (testedSuccessResponse as ReferencesResponse.Success).wikiReferencesSuccessSuccessModel
        assertThat(testedSuccessModel.query).isEqualTo(arrangeQuery1)
        assertThat(testedSuccessModel.references).isIn(1..1000)

        //Step 2 : Test Error
        // Arrange
        val arrangeQuery2="Marseille"

        // Act
        val testedErrorResponse=defaultErrorReferencesRepository.getReferences(arrangeQuery2)

        // Assert
        val expectedResponseModel = ReferencesErrorResponseModel(arrangeQuery2,ReferencesErrorType.NoNetwork)
        assertThat(testedErrorResponse is ReferencesResponse.Error).isTrue()

        val testedModel = (testedErrorResponse as ReferencesResponse.Error).referencesErrorResponseModel
        assertThat(testedModel.query).isEqualTo(expectedResponseModel.query)
        assertThat(testedModel.referencesErrorType is ReferencesErrorType.NoNetwork).isTrue()
    }
}