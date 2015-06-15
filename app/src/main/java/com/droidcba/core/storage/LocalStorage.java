package com.droidcba.core.storage;

import java.util.Set;

public interface LocalStorage {

    void put(String key, Set<String> values);

    void put(String key, String value);

    void put(String key, int value);

    void put(String key, Boolean value);

    boolean getBoolean(String key);

    String getString(String key);

    int getInteger(String key);

    Set<String> getSetString(String key);

}
