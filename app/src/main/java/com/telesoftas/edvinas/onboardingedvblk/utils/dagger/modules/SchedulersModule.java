package com.telesoftas.edvinas.onboardingedvblk.utils.dagger.modules;

import com.telesoftas.edvinas.onboardingedvblk.utils.dagger.qualifiers.Database;
import com.telesoftas.edvinas.onboardingedvblk.utils.dagger.qualifiers.Ui;
import com.telesoftas.edvinas.onboardingedvblk.utils.dagger.qualifiers.Network;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public final class SchedulersModule {
    private SchedulersModule() {
    }

    @Ui
    @Provides
    public static Scheduler provideUiScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Network
    @Provides
    public static Scheduler provideNetworkScheduler() {
        return Schedulers.io();
    }

    @Database
    @Provides
    public static Scheduler provideDatabaseScheduler() {
        return Schedulers.single();
    }
}
