package com.telesoftas.edvinas.onboardingedvblk.data.preferences;

public interface StorageManager {
    void putBoolean(String key, boolean value);

    boolean getBoolean(String key, boolean defaultValue);

    void putString(String key, String value);

    String getString(String key, String defaultValue);

    void putInt(String key, int value);

    int getInt(String key, int defaultValue);
}
