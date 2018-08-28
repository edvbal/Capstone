package com.telesoftas.edvinas.onboardingedvblk.landing;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.telesoftas.edvinas.onboardingedvblk.utils.mvvm.SingleLiveEvent;
import com.telesoftas.edvinas.onboardingedvblk.data.preferences.PreferencesConstants;
import com.telesoftas.edvinas.onboardingedvblk.data.preferences.StorageManager;

public class SplashViewModel extends ViewModel {
    private final StorageManager storageManager;

    private final MutableLiveData<Boolean> tutorialSeenState = new SingleLiveEvent<>();

    public SplashViewModel(StorageManager storageManager) {
        this.storageManager = storageManager;
    }

    public MutableLiveData<Boolean> observeTutorialSeenState() {
        return tutorialSeenState;
    }

    public void onLoad() {
        tutorialSeenState.postValue(isTutorialSeen());
    }

    private boolean isTutorialSeen() {
        return storageManager.getBoolean(PreferencesConstants.KEY_IS_TUTORIAL_SEEN, false);
    }
}
