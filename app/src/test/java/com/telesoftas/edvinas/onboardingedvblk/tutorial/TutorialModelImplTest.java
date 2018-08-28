package com.telesoftas.edvinas.onboardingedvblk.tutorial;

import com.telesoftas.edvinas.onboardingedvblk.data.preferences.PreferencesConstants;
import com.telesoftas.edvinas.onboardingedvblk.data.preferences.StorageManager;

import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

public class TutorialModelImplTest {
    @Test
    public void setTutorialSeen_putsTrueInStorageManagerOnKeyIsTutorialSeen() {
        StorageManager storageManager = Mockito.mock(StorageManager.class);
        TutorialModelImpl model = new TutorialModelImpl(storageManager);

        model.setTutorialSeen();

        verify(storageManager).putBoolean(PreferencesConstants.KEY_IS_TUTORIAL_SEEN, true);
    }
}