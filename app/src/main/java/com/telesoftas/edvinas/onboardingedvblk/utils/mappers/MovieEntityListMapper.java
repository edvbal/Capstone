package com.telesoftas.edvinas.onboardingedvblk.utils.mappers;

import com.telesoftas.edvinas.onboardingedvblk.data.database.ArticleEntity;
import com.telesoftas.edvinas.onboardingedvblk.news.articles.Article;
import com.telesoftas.edvinas.onboardingedvblk.utils.Lists;

import java.util.List;
import java.util.Random;

public class MovieEntityListMapper {
    public List<Article> mapDomainToBussiness(List<ArticleEntity> articleEntities) {
        return Lists.map(articleEntities, articleEntity -> new Article(
                articleEntity.getUrlToImage(),
                articleEntity.getTitle(),
                articleEntity.getDescription(),
                articleEntity.getDate(),
                articleEntity.getUrl()
        ));
    }

    public List<ArticleEntity> mapBussinessToDomain(List<Article> articles) {
        Random random = new Random(10000);
        return Lists.map(articles, article -> new ArticleEntity(
                random.nextInt(),
                article.getUrlToImage(),
                article.getTitle(),
                article.getDescription(),
                article.getDate(),
                article.getUrl()
        ));
    }
}
