package com.test.devsettings;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.android.rfid.DevSettings;

import java.util.HashSet;
import java.util.List;

public class BackRunAppsAppsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {
    private static String TAG = BackRunAppsAppsFragment.class.getSimpleName();
    private DevSettings mDevSettings;
    private String addAlwaysAppKey = "add_always_app";
    private String deleteAlwaysAppKey = "delete_always_app";
    private String clearAlwaysAppKey = "clear_always_app";
    private String queryAlwaysAppKey = "query_always_app";
    private MultiSelectListPreference mDeleteAlwaysAppPf;
    private String[] mAlwaysApps;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.always_apps_list);

        findPreference(addAlwaysAppKey).setOnPreferenceChangeListener(this);
        mDeleteAlwaysAppPf = ((MultiSelectListPreference) findPreference(deleteAlwaysAppKey));
        mDeleteAlwaysAppPf.setOnPreferenceChangeListener(this);
        findPreference(queryAlwaysAppKey).setOnPreferenceClickListener(this);
        findPreference(clearAlwaysAppKey).setOnPreferenceClickListener(this);
        mDevSettings = new DevSettings(getActivity());
        mAlwaysApps = getAlwaysApps();
        mDeleteAlwaysAppPf.setEntries(mAlwaysApps);
        mDeleteAlwaysAppPf.setEntryValues(mAlwaysApps);
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
            String alwaysAppPkgName = editTextPreference.getText();
            Log.e(TAG, "onSharedPreferenceChanged, alwaysAppPkgName = " + alwaysAppPkgName);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object objValue) {
        Log.e(TAG, "onPreferenceChange, objValue = " + String.valueOf(objValue));
        String key = preference.getKey();
        if (addAlwaysAppKey.equals(key)) {
            String appPkgName = String.valueOf(objValue);
            Log.e(TAG, "onPreferenceChange, addAlwaysAppKey = " + appPkgName);
            mDevSettings.addAlwaysApps(appPkgName);
        } else if (deleteAlwaysAppKey.equals(key)) {
            HashSet<String> strings = (HashSet<String>) objValue;
            for (String appPkgName:strings){
                Log.e(TAG, "onPreferenceChange, deleteAlwaysAppKey = " + appPkgName);
                mDevSettings.deleteAlwaysApps(appPkgName);
            }
        }
        String[] alwaysApps = getAlwaysApps();
        mDeleteAlwaysAppPf.setEntries(alwaysApps);
        mDeleteAlwaysAppPf.setEntryValues(alwaysApps);
        return false;
    }

    /**
     * 获取始终运行的应用名单
     */
    private String[] getAlwaysApps() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<String> alwaysAppsList = mDevSettings.queryAlwaysApps();
        String[] collection = new String[alwaysAppsList.size()];
        for (int i = 0; i < alwaysAppsList.size(); i++) {
            String pkgName = alwaysAppsList.get(i);
            collection[i] = pkgName;
        }
        return collection;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();
        Log.e(TAG, "onPreferenceClick, key = " + key);
        if (queryAlwaysAppKey.equals(key)) {
            List<String> alwaysAppsList = mDevSettings.queryAlwaysApps();
            String message = "";
            for (int i = 0; i < alwaysAppsList.size(); i++) {
                if (i != alwaysAppsList.size() - 1) {
                    String pkgName = alwaysAppsList.get(i);
                    message = message.concat(pkgName + "\n");
                } else {
                    String pkgName = alwaysAppsList.get(i);
                    message = message.concat(pkgName);
                }
            }
            new AlertDialog.Builder(getActivity())
                    .setTitle("始终运行名单内应用：")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    })
                    .setMessage(message)
                    .create()
                    .show();
        } else if (clearAlwaysAppKey.equals(key)) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("确定清除始终运行应用名单？")
                    .setMessage("此项操作将清除所有始终运行名单内的应用")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mDevSettings.clearAlwaysApps();
                            mDeleteAlwaysAppPf.setEntries(getAlwaysApps());
                            mDeleteAlwaysAppPf.setEntryValues(getAlwaysApps());
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
