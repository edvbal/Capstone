package com.telesoftas.edvinas.onboardingedvblk.tutorial;


import com.telesoftas.edvinas.onboardingedvblk.data.preferences.PreferencesConstants;
import com.telesoftas.edvinas.onboardingedvblk.data.preferences.StorageManager;

class TutorialModelImpl implements TutorialModel {
    private final StorageManager storageManager;

    public TutorialModelImpl(StorageManager storageManager) {
        this.storageManager = storageManager;
    }

    @Override
    public void setTutorialSeen() {
        storageManager.putBoolean(PreferencesConstants.KEY_IS_TUTORIAL_SEEN, true);
    }
}
