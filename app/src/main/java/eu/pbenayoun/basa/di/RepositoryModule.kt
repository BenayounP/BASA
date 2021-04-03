package eu.pbenayoun.basa.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import eu.pbenayoun.repository.referencesrepository.fake.FakeDefaultReferencesRepository
import eu.pbenayoun.repository.referencesrepository.ReferencesRepository

@InstallIn(ViewModelComponent::class)
@Module
abstract
class RepositoryModule {
    @Binds
    abstract fun bindReferencesRepository(impl: FakeDefaultReferencesRepository): ReferencesRepository
}

