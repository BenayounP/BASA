package eu.pbenayoun.wikireferences2.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import eu.pbenayoun.repository.referencesrepository.MockedReferencesRepository
import eu.pbenayoun.repository.referencesrepository.ReferencesRepository

@InstallIn(ViewModelComponent::class)
@Module
abstract
class RepositoryModule {
    @Binds
    abstract fun bindReferencesRepository(impl: MockedReferencesRepository): ReferencesRepository
}

