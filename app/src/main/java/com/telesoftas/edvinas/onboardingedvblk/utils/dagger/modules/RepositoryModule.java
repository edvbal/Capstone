package com.telesoftas.edvinas.onboardingedvblk.utils.dagger.modules;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.telesoftas.edvinas.onboardingedvblk.data.database.ArticlesDatabase;
import com.telesoftas.edvinas.onboardingedvblk.data.repositories.OfflineRepository;
import com.telesoftas.edvinas.onboardingedvblk.data.repositories.OnlineRepository;
import com.telesoftas.edvinas.onboardingedvblk.utils.dagger.qualifiers.Database;
import com.telesoftas.edvinas.onboardingedvblk.utils.dagger.scopes.AppScope;
import com.telesoftas.edvinas.onboardingedvblk.utils.network.ArticlesService;
import com.telesoftas.edvinas.onboardingedvblk.data.preferences.StorageManager;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import retrofit2.Retrofit;

@Module
public final class RepositoryModule {
    private RepositoryModule() {
    }

    @AppScope
    @Provides
    public static ArticlesDatabase provideArticlesDatabase(Context context) {
        return Room.databaseBuilder(context, ArticlesDatabase.class, "database")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    public static OnlineRepository provideOnlineRepository(
            StorageManager storageManager,
            Retrofit retrofit
    ) {
        ArticlesService service = retrofit.create(ArticlesService.class);
        return new OnlineRepository(service, storageManager);
    }

    @Provides
    public static OfflineRepository provideOfflineRepository(
            ArticlesDatabase database,
            @Database Scheduler scheduler
    ) {
        return new OfflineRepository(database.articleDao(), scheduler);
    }
}
