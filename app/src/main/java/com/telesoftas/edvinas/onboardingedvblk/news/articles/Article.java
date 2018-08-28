package com.telesoftas.edvinas.onboardingedvblk.news.articles;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Article implements Parcelable {
    private final String urlToImage;
    private final String title;
    private final String description;
    @SerializedName("publishedAt")
    private final String date;
    private final String url;
    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel parcel) {
            return new Article(parcel);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public Article(String urlToImage,
                   String title, String description, String date, String url
    ) {
        this.urlToImage = urlToImage;
        this.title = title;
        this.description = description;
        this.date = date;
        this.url = url;
    }

    public Article(Article article, String date) {
        urlToImage = article.getUrlToImage();
        title = article.getTitle();
        description = article.getDescription();
        url = article.getUrl();
        this.date = date;
    }

    protected Article(Parcel parcel) {
        urlToImage = parcel.readString();
        title = parcel.readString();
        description = parcel.readString();
        date = parcel.readString();
        url = parcel.readString();
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(urlToImage);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(date);
        dest.writeString(url);
    }
}
