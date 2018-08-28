package com.telesoftas.edvinas.onboardingedvblk.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "articles")
public class ArticleEntity {
    @PrimaryKey
    private final int id;
    private final String urlToImage;
    private final String title;
    private final String description;
    @SerializedName("publishedAt")
    private final String date;
    private final String url;

    public ArticleEntity(
            int id,
            String urlToImage,
            String title,
            String description,
            String date,
            String url
    ) {
        this.id = id;
        this.urlToImage = urlToImage;
        this.title = title;
        this.description = description;
        this.date = date;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }
}
