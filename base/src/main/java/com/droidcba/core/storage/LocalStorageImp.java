package com.droidcba.core.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.droidcba.core.R;

import java.util.HashSet;
import java.util.Set;

/**
 * Default abstraction of SharedPreference to be easily mocked.
 */
public class LocalStorageImp implements LocalStorage {

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    public LocalStorageImp(Context context) {
        sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        editor = sharedPref.edit();
    }

    public void put(String key, Set<String> values) {
        editor.putStringSet(key, values);
        editor.commit();
    }

    public void put(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public void put(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public void put(String key, Boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key) {
        return sharedPref.getBoolean(key, false);
    }

    public String getString(String key) {
        return sharedPref.getString(key, "");
    }

    public int getInteger(String key) {
        return sharedPref.getInt(key, 0);
    }

    public Set<String> getSetString(String key) {
        return sharedPref.getStringSet(key, new HashSet<String>());
    }
}
