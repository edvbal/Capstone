package com.telesoftas.edvinas.onboardingedvblk.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(version = 3, entities = {ArticleEntity.class})
//@TypeConverters(Converters::class)
public abstract class ArticlesDatabase extends RoomDatabase {
    public abstract ArticleDao articleDao();
}

