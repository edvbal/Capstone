package com.telesoftas.edvinas.onboardingedvblk.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ProgressBar;

import com.telesoftas.edvinas.onboardingedvblk.R;
import com.telesoftas.edvinas.onboardingedvblk.base.BaseMvvmFragment;
import com.telesoftas.edvinas.onboardingedvblk.tutorial.TutorialActivity;
import com.telesoftas.edvinas.onboardingedvblk.utils.popup.SnackbarProvider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingsFragment extends BaseMvvmFragment<SettingsViewModel> {
    @BindView(R.id.settingsSpinner)
    AppCompatSpinner settingsSpinner;
    @BindView(R.id.settingsProgressBar)
    ProgressBar settingsProgressBar;
    @BindView(R.id.settingsCheckBox)
    CheckBox settingsCheckBox;
    private SourcesSpinnerAdapter adapter;
    private SnackbarProvider snackbarProvider;
    private SettingsViewModel settingsViewModel;

    public static Fragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    protected Class<SettingsViewModel> getViewModel() {
        return SettingsViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, SettingsViewModel settingsViewModel) {
        this.settingsViewModel = settingsViewModel;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        settingsViewModel.onLoad();
        snackbarProvider = new SnackbarProvider(view);
        settingsViewModel.observeEmptyState().observe(this, this::showEmptyDropDown);
        settingsViewModel.observeProgressState().observe(this, this::hideProgress);
        settingsViewModel.observeNotificationState().observe(this, this::checkNotification);
        settingsViewModel.observeSourcesState().observe(this, this::populateDropDown);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_settings;
    }

    @OnClick(R.id.buttonSettingsShowTutorial)
    void onShowTutorialClick() {
        showTutorialScreen();
    }

    @OnClick(R.id.settingsCheckBox)
    void onClickCheckBox() {
        boolean isChecked = settingsCheckBox.isChecked();
        settingsViewModel.onClickCheckbox(isChecked);
        if (isChecked) {
            showCheckBoxSelectedPopUp();
        } else {
            showCheckBoxUnselectedPopUp();
        }
    }

    public void populateDropDown(Pair<List<Source>, Integer> spinnerPair) {
        adapter = new SourcesSpinnerAdapter(
                getContext(),
                R.layout.item_spinner_sources,
                spinnerPair.first
        );
        settingsSpinner.setAdapter(adapter);
        settingsSpinner.setSelection(spinnerPair.second);
        setOnItemSelectedListener();
    }

    public void setOnItemSelectedListener() {
        settingsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Source source = adapter.getItem(position);
                if (source.getId() != null) {
                    settingsViewModel.onSpinnerItemClicked(source.getId(), position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // empty
            }
        });
    }

    public void showTutorialScreen() {
        startActivity(TutorialActivity.createIntent(getContext()));
        getActivity().finish();
    }

    public void hideProgress(boolean isProgress) {
        if (isProgress) {
            settingsProgressBar.setVisibility(View.VISIBLE);
        } else {
            settingsProgressBar.setVisibility(View.GONE);
        }
    }

    public void checkNotification(boolean isChecked) {
        settingsCheckBox.setChecked(isChecked);
    }

    public void showCheckBoxSelectedPopUp() {
        snackbarProvider.showSnackbar(getString(R.string.settings_notification_enabled));
    }

    public void showCheckBoxUnselectedPopUp() {
        snackbarProvider.showErrorSnackbar(getString(R.string.settings_notification_disabled));
    }

    public void showEmptyDropDown(boolean isEmpty) {
        if (!isEmpty) {
            return;
        }
        List<Source> sourceList = new ArrayList<>();
        sourceList.add(new Source(getString(R.string.spinner_no_items)));
        adapter = new SourcesSpinnerAdapter(
                getContext(),
                R.layout.item_spinner_sources,
                sourceList
        );
        settingsSpinner.setAdapter(adapter);
        settingsSpinner.setEnabled(false);
    }
}
