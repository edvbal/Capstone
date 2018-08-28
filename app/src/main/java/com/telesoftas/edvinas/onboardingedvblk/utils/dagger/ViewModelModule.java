package com.telesoftas.edvinas.onboardingedvblk.utils.dagger;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.telesoftas.edvinas.onboardingedvblk.data.repositories.OfflineRepository;
import com.telesoftas.edvinas.onboardingedvblk.data.repositories.OnlineRepository;
import com.telesoftas.edvinas.onboardingedvblk.landing.SplashViewModel;
import com.telesoftas.edvinas.onboardingedvblk.news.articles.ArticlesViewModel;
import com.telesoftas.edvinas.onboardingedvblk.settings.SettingsViewModel;
import com.telesoftas.edvinas.onboardingedvblk.utils.dagger.qualifiers.Ui;
import com.telesoftas.edvinas.onboardingedvblk.utils.firebase.NotificationSubscriber;
import com.telesoftas.edvinas.onboardingedvblk.utils.mappers.MovieEntityListMapper;
import com.telesoftas.edvinas.onboardingedvblk.utils.mvvm.ViewModelFactory;
import com.telesoftas.edvinas.onboardingedvblk.utils.network.SourcesService;
import com.telesoftas.edvinas.onboardingedvblk.data.preferences.StorageManager;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import retrofit2.Retrofit;

@SuppressWarnings("WeakerAccess")
@Module
public abstract class ViewModelModule {
    @Provides
    public static SplashViewModel provideSplashViewModel(StorageManager storageManager) {
        return new SplashViewModel(storageManager);
    }

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel.class)
    abstract ViewModel bindSplashViewModel(SplashViewModel splashViewModel);

    @Provides
    public static ArticlesViewModel provideArticlesViewModel(
            @Ui Scheduler scheduler,
            OnlineRepository onlineRepository,
            OfflineRepository offlineRepository
    ) {
        return new ArticlesViewModel(
                AndroidSchedulers.mainThread(),
                onlineRepository,
                offlineRepository,
                new MovieEntityListMapper()
        );
    }

    @Binds
    @IntoMap
    @ViewModelKey(ArticlesViewModel.class)
    abstract ViewModel bindsArticlesViewModel(ArticlesViewModel mainViewModel);

    @Provides
    public static SettingsViewModel provideSettingsViewModel(
            Retrofit retrofit,
            StorageManager storageManager,
            @Ui Scheduler scheduler
    ) {
        SourcesService service = retrofit.create(SourcesService.class);
        NotificationSubscriber notificationSubscriber = new NotificationSubscriber();
        return new SettingsViewModel(service, storageManager, notificationSubscriber, scheduler);
    }

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel.class)
    abstract ViewModel bindsSettingsViewModel(SettingsViewModel sourceViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindsViewModelFactory(ViewModelFactory viewModelFactory);
}
