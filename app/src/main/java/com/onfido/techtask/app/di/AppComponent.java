package com.onfido.techtask.app.di;

import com.onfido.techtask.PerApplication;
import com.onfido.techtask.RoomScope;
import com.onfido.techtask.app.App;
import com.onfido.techtask.network.NetworkModule;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(
        modules = {
                ActivityBindingsModule.class,
                AndroidSupportInjectionModule.class,
                AppModule.class,
                RoomModule.class,
                NetworkModule.class,
                NetworkConfigModule.class,
                FragmentBindingsModule.class
        }
)
@PerApplication
@RoomScope
public interface AppComponent extends AndroidInjector<App> {

    @Component.Factory
    interface Factory extends AndroidInjector.Factory<App> {
    }
}
