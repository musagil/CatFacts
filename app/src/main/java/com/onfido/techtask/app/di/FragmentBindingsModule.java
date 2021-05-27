package com.onfido.techtask.app.di;

import com.onfido.techtask.factdetail.FactDetailFragment;
import com.onfido.techtask.factdetail.FactDetailFragmentModule;
import com.onfido.techtask.factlist.FactListFragment;
import com.onfido.techtask.factlist.FactListFragmentModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
interface FragmentBindingsModule {

    @ContributesAndroidInjector(modules = FactListFragmentModule.class)
    FactListFragment productListFragment();

    @ContributesAndroidInjector(modules = FactDetailFragmentModule.class)
    FactDetailFragment productDetailFragment();

}
