package com.onfido.techtask.app.di;

import com.onfido.techtask.app.config.ProdNetworkConfig;
import com.onfido.techtask.network.NetworkConfig;

import javax.inject.Provider;

import dagger.Module;
import dagger.Provides;

@Module
interface NetworkConfigModule {

    @Provides
    static NetworkConfig networkConfig(Provider<ProdNetworkConfig> prodProvider) {
        return prodProvider.get();
    }
}
