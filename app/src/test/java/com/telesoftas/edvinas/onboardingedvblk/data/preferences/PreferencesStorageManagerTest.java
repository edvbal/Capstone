package com.telesoftas.edvinas.onboardingedvblk.data.preferences;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PreferencesStorageManagerTest {
    private static final String DEFAULT_KEY = "defaultKey";
    private static final boolean DEFAULT_BOOLEAN_VALUE = false;
    public static final String DEFAULT_STRING = "defaultString";
    public static final int DEFAULT_INT = 0;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    private StorageManager storageManager;

    @Before
    @SuppressLint("CommitPrefEdits")
    public void setUp() throws Exception {
        editor = mock(SharedPreferences.Editor.class);
        sharedPreferences = mock(SharedPreferences.class);
        storageManager = new PreferencesStorageManager(sharedPreferences);
        when(sharedPreferences.edit()).thenReturn(editor);
        when(editor.putBoolean(anyString(), anyBoolean())).thenReturn(editor);
        when(editor.putString(anyString(), anyString())).thenReturn(editor);
        when(editor.putInt(anyString(), anyInt())).thenReturn(editor);
    }

    @Test
    public void putBoolean_savesBooleanInKey() {
        storageManager.putBoolean(DEFAULT_KEY, DEFAULT_BOOLEAN_VALUE);

        verify(editor).putBoolean(DEFAULT_KEY, DEFAULT_BOOLEAN_VALUE);
        verify(editor).apply();
    }

    @Test
    public void getBoolean_getsBooleanFromKey() {
        storageManager.getBoolean(DEFAULT_KEY, DEFAULT_BOOLEAN_VALUE);

        verify(sharedPreferences).getBoolean(DEFAULT_KEY, DEFAULT_BOOLEAN_VALUE);
    }

    @Test
    public void getString_getsStringFromKey() {
        storageManager.getString(DEFAULT_KEY, DEFAULT_STRING);

        verify(sharedPreferences).getString(DEFAULT_KEY, DEFAULT_STRING);
    }

    @Test
    public void putString_putsStringinKey() {
        storageManager.putString(DEFAULT_KEY, DEFAULT_STRING);

        verify(editor).putString(DEFAULT_KEY, DEFAULT_STRING);
        verify(editor).apply();
    }

    @Test
    public void getInt_getsIntFromKey() {
        storageManager.getInt(DEFAULT_KEY, DEFAULT_INT);

        verify(sharedPreferences).getInt(DEFAULT_KEY, DEFAULT_INT);
    }

    @Test
    public void putInt_putsIntInKey() {
        storageManager.putInt(DEFAULT_KEY, DEFAULT_INT);

        verify(editor).putInt(DEFAULT_KEY, DEFAULT_INT);
        verify(editor).apply();
    }
}