package com.test.devsettings;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.android.rfid.DevSettings;

import java.util.List;

public class WhiteBlackAppsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {
    private static String TAG = WhiteBlackAppsFragment.class.getSimpleName();
    private DevSettings mDevSettings;
    private String addWhiteAppKey = "add_white_app";
    private String deleteWhiteAppKey = "delete_white_app";
    private String clearWhiteAppKey = "clear_white_app";
    private String queryWhiteAppKey = "query_white_app";
    private String addBlackAppKey = "add_black_app";
    private String deleteBlackAppKey = "delete_black_app";
    private String clearBlackAppKey = "clear_black_app";
    private String queryBlackAppKey = "query_black_app";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.white_black_apps_list);

        findPreference(addWhiteAppKey).setOnPreferenceChangeListener(this);
        findPreference(deleteWhiteAppKey).setOnPreferenceChangeListener(this);
        findPreference(addBlackAppKey).setOnPreferenceChangeListener(this);
        findPreference(deleteBlackAppKey).setOnPreferenceChangeListener(this);
        findPreference(queryWhiteAppKey).setOnPreferenceClickListener(this);
        findPreference(clearWhiteAppKey).setOnPreferenceClickListener(this);
        findPreference(queryBlackAppKey).setOnPreferenceClickListener(this);
        findPreference(clearBlackAppKey).setOnPreferenceClickListener(this);
        mDevSettings = new DevSettings(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.e(TAG, "onSharedPreferenceChanged, key = " + key);
        Preference pref = findPreference(key);
        if (pref instanceof EditTextPreference) {
            EditTextPreference editTextPreference = (EditTextPreference) pref;
            String whiteAppPkgName = editTextPreference.getText();
            Log.e(TAG, "onSharedPreferenceChanged, whiteAppPkgName = " + whiteAppPkgName);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object objValue) {
        Log.e(TAG, "onPreferenceChange, objValue = " + String.valueOf(objValue));
        String key = preference.getKey();
        String appPkgName = String.valueOf(objValue);
        if (addWhiteAppKey.equals(key)) {
            Log.e(TAG, "onPreferenceChange, addWhiteAppKey = " + appPkgName);
            mDevSettings.addWhiteAppsList(appPkgName);
        } else if (deleteWhiteAppKey.equals(key)) {
            Log.e(TAG, "onPreferenceChange, deleteWhiteAppKey = " + appPkgName);
            mDevSettings.deleteWhiteAppsList(appPkgName);
        } else if (addBlackAppKey.equals(key)) {
            Log.e(TAG, "onPreferenceChange, addBlackAppKey = " + appPkgName);
            mDevSettings.addBlackAppsList(appPkgName);
        } else if (deleteBlackAppKey.equals(key)) {
            Log.e(TAG, "onPreferenceChange, deleteBlackAppKey = " + appPkgName);
            mDevSettings.deleteBlackAppsList(appPkgName);
        }
        return false;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();
        Log.e(TAG, "onPreferenceClick, key = " + key);
        if (queryWhiteAppKey.equals(key)) {
            List<String> whiteAppsList = mDevSettings.queryWhiteAppsList();
            String message = "";
            for (int i = 0; i < whiteAppsList.size(); i++) {
                if (i != whiteAppsList.size() - 1) {
                    String pkgName = whiteAppsList.get(i);
                    message = message.concat(pkgName + "\n");
                }else{
                    String pkgName = whiteAppsList.get(i);
                    message = message.concat(pkgName);
                }
            }
            new AlertDialog.Builder(getActivity())
                    .setTitle("白名单内应用：")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    })
                    .setMessage(message)
                    .create()
                    .show();
        } else if (clearWhiteAppKey.equals(key)) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("确定清除应用白名单？")
                    .setMessage("此项操作将清除所有应用白名单内的应用并取消应用白名单")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mDevSettings.clearWhiteAppsList();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .create()
                    .show();
        } else if (queryBlackAppKey.equals(key)) {
            List<String> blackAppsList = mDevSettings.queryBlackAppsList();
            String message = "";
            for (int i = 0; i < blackAppsList.size(); i++) {
                if (i != blackAppsList.size()-1){
                    message = message.concat(blackAppsList.get(i) + "\n");
                }else {
                    message = message.concat(blackAppsList.get(i));
                }
            }
            new AlertDialog.Builder(getActivity())
                    .setTitle("黑名单内应用：")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    })
                    .setMessage(message)
                    .create()
                    .show();
        } else if (clearBlackAppKey.equals(key)) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("确定清除应用黑名单？")
                    .setMessage("此项操作将清除所有应用黑名单内的应用并取消应用黑名单")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mDevSettings.clearBlackAppsList();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .create()
                    .show();
        }
        return false;
    }
}
