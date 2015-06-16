package com.droidcba.core;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * Base session object.
 */
public class SessionParcel implements Parcelable {

    private Map<String, String> map;

    public void put(String key, String value) {
        this.map.put(key, value);
    }

    public String get(String key) {
        return map.get(key);
    }

    public SessionParcel() {
        map = new HashMap<String, String>();
    }

    // Parcel section

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(map.size());
        for (Map.Entry<String, String> entry : map.entrySet()) {
            out.writeString(entry.getKey());
            out.writeString(entry.getValue());
        }
    }

    private SessionParcel(Parcel in) {
        //initialize your map before
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            String key = in.readString();
            String value = in.readString();
            map.put(key, value);
        }
    }

    public static final Creator CREATOR = new Creator() {
        public SessionParcel createFromParcel(Parcel in) {
            return new SessionParcel(in);
        }

        public SessionParcel[] newArray(int size) {
            return new SessionParcel[size];
        }
    };
}