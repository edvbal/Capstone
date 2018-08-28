package com.telesoftas.edvinas.onboardingedvblk.landing;

import com.telesoftas.edvinas.onboardingedvblk.data.preferences.StorageManager;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import static com.telesoftas.edvinas.onboardingedvblk.data.preferences.PreferencesConstants.KEY_IS_TUTORIAL_SEEN;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SplashModelImplTest {
    private StorageManager storageManager;
    private SplashModel model;

    @Before
    public void setUp() throws Exception {
        storageManager = mock(StorageManager.class);
        model = new SplashModelImpl(storageManager);
    }

    @Test
    public void isTutorialSeen_returnsTrueFromStorageManager() {
        when(storageManager.getBoolean(KEY_IS_TUTORIAL_SEEN, false))
                .thenReturn(true);

        boolean actual = model.isTutorialSeen();

        Assert.assertTrue(actual);
    }

    @Test
    public void isTutorialSeen_returnsFalseFromStorageManager() {
        when(storageManager.getBoolean(KEY_IS_TUTORIAL_SEEN, false))
                .thenReturn(false);

        boolean actual = model.isTutorialSeen();

        Assert.assertFalse(actual);
    }
}