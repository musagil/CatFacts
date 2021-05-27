package com.onfido.techtask.factdetail

import androidx.fragment.app.Fragment
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Binds
import dagger.Module

@AssistedModule
@Module(includes = [AssistedInject_FactDetailFragmentModule::class])
abstract class FactDetailFragmentModule {

    @Binds
    abstract fun bindFragment(fragment: FactDetailFragment): Fragment
}
