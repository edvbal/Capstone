package com.telesoftas.edvinas.onboardingedvblk.utils.dagger.modules;

import com.telesoftas.edvinas.onboardingedvblk.landing.SplashActivity;
import com.telesoftas.edvinas.onboardingedvblk.news.articles.ArticlesFragment;
import com.telesoftas.edvinas.onboardingedvblk.news.details.ArticleDetailsActivity;
import com.telesoftas.edvinas.onboardingedvblk.news.details.fragment.ArticleDetailsFragment;
import com.telesoftas.edvinas.onboardingedvblk.settings.SettingsFragment;
import com.telesoftas.edvinas.onboardingedvblk.utils.dagger.scopes.ActivityScope;
import com.telesoftas.edvinas.onboardingedvblk.utils.dagger.scopes.FragmentScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module()
public abstract class ContributorsModule {
    @ActivityScope
    @ContributesAndroidInjector()
    public abstract ArticleDetailsActivity bindArticleDetailsActivity();

    @FragmentScope
    @ContributesAndroidInjector
    public abstract ArticleDetailsFragment bindArticleDetailsFragment();

    @FragmentScope
    @ContributesAndroidInjector
    public abstract SettingsFragment bindSettingsFragment();

    @FragmentScope
    @ContributesAndroidInjector
    public abstract ArticlesFragment bindArticlesFragment();
}