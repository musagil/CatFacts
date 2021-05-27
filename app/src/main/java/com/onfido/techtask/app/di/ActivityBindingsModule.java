package com.onfido.techtask.app.di;

import com.onfido.techtask.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
interface ActivityBindingsModule {
    @ContributesAndroidInjector
    MainActivity mainActivityInjector();
}
