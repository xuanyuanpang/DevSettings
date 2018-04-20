package com.test.devsettings;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.SwitchPreference;

import com.android.rfid.DevSettings;

/**
 *
 * @author Administrator
 * @date 2018/3/24
 */

public class OpenCloseOptionActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {

    private static final String WLAN_FORBIDDEN = "wlan_forbidden";
    private static final String BLUETOOTH_FORBIDDEN = "bluetooth_forbidden";
    private static final String SIM_FORBIDDEN = "sim_forbidden";
    private static final String USB_DEBUG_FORBIDDEN = "usbdebug_forbidden";
    private static final String CHARGE_ONLY_FORBIDDEN = "charge_only_forbidden";
    private static final String GPS_FORBIDDEN = "gps_forbidden";
    private static final String NFC_FORBIDDEN = "nfc_forbidden";
    private static final String HOME_FORBIDDEN = "home_forbidden";
    private static final String BACK_FORBIDDEN = "back_forbidden";
    private static final String RECENT_FORBIDDEN = "recent_forbidden";
    private static final String PHONE_FORBIDDEN = "phone_forbidden";
    private static final String NOTIFICATION_BAR_FORBIDDEN = "notification_bar_forbidden";

    private SwitchPreference swithWlan;
    private SwitchPreference swithBt;
    private SwitchPreference swithSIM;
    private SwitchPreference swithUsbDebug;
    private SwitchPreference swithChargeOnly;
    private SwitchPreference swithGPS;
    private SwitchPreference swithNFC;
    private SwitchPreference swithPhone;
    private SwitchPreference swithHome;
    private SwitchPreference swithRecent;
    private SwitchPreference swithBack;
    private SwitchPreference swithNotificationBar;

    private DevSettings devSettings ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.open_close_option);
        devSettings = new DevSettings(this);
        initView();
    }

    private void initView() {
        swithWlan = (SwitchPreference) findPreference(WLAN_FORBIDDEN) ;
        swithBt = (SwitchPreference) findPreference(BLUETOOTH_FORBIDDEN) ;
        swithSIM = (SwitchPreference) findPreference(SIM_FORBIDDEN) ;
        swithUsbDebug = (SwitchPreference) findPreference(USB_DEBUG_FORBIDDEN) ;
        swithChargeOnly = (SwitchPreference) findPreference(CHARGE_ONLY_FORBIDDEN) ;
        swithGPS = (SwitchPreference) findPreference(GPS_FORBIDDEN) ;
        swithNFC = (SwitchPreference) findPreference(NFC_FORBIDDEN) ;
        swithPhone = (SwitchPreference) findPreference(PHONE_FORBIDDEN) ;
        swithHome = (SwitchPreference) findPreference(HOME_FORBIDDEN) ;
        swithRecent = (SwitchPreference) findPreference(RECENT_FORBIDDEN) ;
        swithBack = (SwitchPreference) findPreference(BACK_FORBIDDEN) ;
        swithNotificationBar = (SwitchPreference) findPreference(NOTIFICATION_BAR_FORBIDDEN) ;

        swithWlan.setOnPreferenceChangeListener(this);
        swithBt.setOnPreferenceChangeListener(this);
        swithSIM.setOnPreferenceChangeListener(this);
        swithUsbDebug.setOnPreferenceChangeListener(this);
        swithChargeOnly.setOnPreferenceChangeListener(this);
        swithGPS.setOnPreferenceChangeListener(this);
        swithPhone.setOnPreferenceChangeListener(this);
        swithHome.setOnPreferenceChangeListener(this);
        swithRecent.setOnPreferenceChangeListener(this);
        swithBack.setOnPreferenceChangeListener(this);
        swithNotificationBar.setOnPreferenceChangeListener(this);
        swithNFC.setOnPreferenceChangeListener(this);

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        final String key = preference.getKey() ;
        boolean checked = (Boolean)newValue ;
        switch (key) {
            case WLAN_FORBIDDEN:
                devSettings.setWifiEnabled(!checked);
                break;
            case BLUETOOTH_FORBIDDEN:
                devSettings.setBtEnabled(!checked);
                break;
            case SIM_FORBIDDEN:
                devSettings.setSimEnabled(!checked);
                break;
            case USB_DEBUG_FORBIDDEN:
                devSettings.setAdbEnabled(!checked);
                break;
            case CHARGE_ONLY_FORBIDDEN:
                devSettings.setAdbEnabled(!checked);
                break;
            case GPS_FORBIDDEN:
                devSettings.setGpsEnabled(!checked);
                break;
            case NFC_FORBIDDEN:
                devSettings.setNfcEnabled(!checked);
                break;
            //设置是否禁用通话功能
            case PHONE_FORBIDDEN:
                devSettings.setPhoneCall(checked);
                break;
            //禁用HOME键
            case HOME_FORBIDDEN:
                devSettings.lockHome(checked);
                break;
            //禁用多任务按键
            case RECENT_FORBIDDEN:
                devSettings.setMenuKey(checked);
                break;
            //禁用返回键
            case BACK_FORBIDDEN:
                devSettings.lockBackKey(checked);
                break;
            //禁用下拉通知栏
            case NOTIFICATION_BAR_FORBIDDEN:
                devSettings.lockStatusBar(checked);
                break;
                default:
                    break;
        }
        return true;
    }
}
