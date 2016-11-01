package com.adsoft.girls_tatoos_gallery;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferenceConnector {
    public static final String PREF_NAME = "GALLERY";
    public static final int MODE = Context.MODE_PRIVATE;
    public static final String PRODUCT_ARRAY = "PRODUCT_ARRAY";
    public static final String STATUS_PRODUCT = "STATUS_PRODUCT";
    public static final String FREE = "FREE";
    public static final String PAID = "PAID";
    public static final String FEEDBACK_POPUP = "FEEDBACK_POPUP";
    public static final String SHARING = "SHARING";
    public static final String SHOWADD = "SHOWADD";
   

    public static void writeInteger(Context context, String key, int value) {
        getEditor(context).putInt(key, value).commit();

    }

    public static int readInteger(Context context, String key, int defValue) {
        return getPreferences(context).getInt(key, defValue);
    }

    public static void writeString(Context context, String key, String value) {
        getEditor(context).putString(key, value).commit();

    }

    public static String readString(Context context, String key, String defValue) {
        return getPreferences(context).getString(key, defValue);
    }

    public static void writeBoolean(Context context, String key, Boolean value) {
        getEditor(context).putBoolean(key, value).commit();

    }

    public static Boolean readBoolean(Context context, String key,
                                      Boolean defValue) {
        return getPreferences(context).getBoolean(key, defValue);
    }

    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, MODE);
    }

    public static Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }

    public static void clear(Context context) {
        getEditor(context).clear().commit();
    }


}
