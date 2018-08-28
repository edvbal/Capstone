package com.telesoftas.edvinas.onboardingedvblk.utils.network.response;

import com.telesoftas.edvinas.onboardingedvblk.news.articles.Article;

import java.util.List;

public class ArticlesResponse {
    private final List<Article> articles;

    public ArticlesResponse(List<Article> articles) {
        this.articles = articles;
    }

    public List<Article> getArticles() {
        return articles;
    }
}
