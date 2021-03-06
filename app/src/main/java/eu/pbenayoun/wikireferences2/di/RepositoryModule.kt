package eu.pbenayoun.wikireferences2.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import eu.pbenayoun.repository.referencesrepository.MockedReferencesRepository
import eu.pbenayoun.repository.referencesrepository.ReferencesRepository

@InstallIn(ActivityComponent::class)
@Module
abstract
class RepositoryModule {
    @Binds
    abstract fun bindReferencesRepository(impl: MockedReferencesRepository): ReferencesRepository
}

