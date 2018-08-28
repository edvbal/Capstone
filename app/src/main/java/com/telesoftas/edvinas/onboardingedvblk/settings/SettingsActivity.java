package com.telesoftas.edvinas.onboardingedvblk.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.telesoftas.edvinas.onboardingedvblk.R;
import com.telesoftas.edvinas.onboardingedvblk.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SettingsActivity extends BaseActivity {
    @BindView(R.id.settingsToolbar) Toolbar settingsToolbar;

    public static Intent createIntent(Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpSettingsFragment(savedInstanceState);
        setSupportActionBar(settingsToolbar);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }

    private void setUpSettingsFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.settingsFragmentContainer, SettingsFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return true;
    }
}
