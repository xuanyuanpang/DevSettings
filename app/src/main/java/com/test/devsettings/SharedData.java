package com.test.devsettings;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2018/4/28.
 */

public class SharedData {

    private Context mContext ;
    public SharedData(Context context) {
        mContext = context ;
    }

    public void setEnableNetworkWhite(boolean enable) {
        SharedPreferences shared = mContext.getSharedPreferences("white_black_network",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit() ;
        editor.putBoolean("white", enable);
        editor.commit() ;
    }


    public boolean getEnableNetworkWhite() {
        SharedPreferences shared = mContext.getSharedPreferences("white_black_network",
                Context.MODE_PRIVATE);
        return shared.getBoolean("white", false);
    }


    public void setEnableNetworkBlack(boolean enable) {
        SharedPreferences shared = mContext.getSharedPreferences("white_black_network",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit() ;
        editor.putBoolean("black", enable);
        editor.commit() ;
    }


    public boolean getEnableNetworkBlack() {
        SharedPreferences shared = mContext.getSharedPreferences("white_black_network",
                Context.MODE_PRIVATE);
        return shared.getBoolean("black", false);
    }
}
