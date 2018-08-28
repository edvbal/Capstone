package com.telesoftas.edvinas.onboardingedvblk.utils.dagger;

import com.telesoftas.edvinas.onboardingedvblk.base.BaseApplication;
import com.telesoftas.edvinas.onboardingedvblk.utils.dagger.modules.AppModule;
import com.telesoftas.edvinas.onboardingedvblk.utils.dagger.modules.ContributorsModule;
import com.telesoftas.edvinas.onboardingedvblk.utils.dagger.modules.NetworkModule;
import com.telesoftas.edvinas.onboardingedvblk.utils.dagger.modules.RepositoryModule;
import com.telesoftas.edvinas.onboardingedvblk.utils.dagger.modules.SchedulersModule;
import com.telesoftas.edvinas.onboardingedvblk.utils.dagger.scopes.AppScope;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@AppScope
@Component(modules = {AndroidSupportInjectionModule.class, AppModule.class,
        ContributorsModule.class, NetworkModule.class, SchedulersModule.class,
        ViewModelModule.class, RepositoryModule.class}
)
public interface AppComponent extends AndroidInjector<BaseApplication> {
    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<BaseApplication> {
    }
}
