package com.telesoftas.edvinas.onboardingedvblk.news.articles;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.telesoftas.edvinas.onboardingedvblk.R;
import com.telesoftas.edvinas.onboardingedvblk.base.BaseActivity;
import com.telesoftas.edvinas.onboardingedvblk.settings.SettingsActivity;

import butterknife.BindView;

public class ArticlesActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.mainToolbar)
    Toolbar mainToolbar;
    @BindView(R.id.mainNavigationDrawer)
    DrawerLayout mainNavigationDrawer;
    @BindView(R.id.mainNavigationView)
    NavigationView mainNavigationView;
    @BindView(R.id.adView)
    AdView adView;

    public static Intent createIntent(Context context) {
        return new Intent(context, ArticlesActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAd();
        setNewsFragment(savedInstanceState);
        setUpToolbar();
        initNavigationDrawer();
    }

    private void setAd() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        adView.loadAd(adRequest);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_articles;
    }

    public void setNewsFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.mainFragmentContainer, ArticlesFragment.newInstance())
                    .commit();
        }
    }

    private void setUpToolbar() {
        setSupportActionBar(mainToolbar);
    }

    private void initNavigationDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mainNavigationDrawer,
                mainToolbar, R.string.main_toggle_open, R.string.main_toggle_close);
        toggle.syncState();
        mainNavigationView.setNavigationItemSelectedListener(this);
        mainNavigationView.setCheckedItem(R.id.menuDrawerNews);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuDrawerSettings) {
            startActivity(SettingsActivity.createIntent(this));
        }
        mainNavigationDrawer.closeDrawers();

        return false;
    }

    public void closeNavigation() {
        mainNavigationDrawer.closeDrawers();
    }

    public boolean isDrawerOpen() {
        return mainNavigationDrawer.isDrawerOpen(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if (isDrawerOpen()) {
            closeNavigation();
        } else {
            super.onBackPressed();
        }
    }
}
