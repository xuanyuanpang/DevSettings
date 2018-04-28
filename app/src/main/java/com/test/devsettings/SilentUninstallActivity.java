package com.test.devsettings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.rfid.DevSettings;

/**
 * Created by Administrator on 2018/4/28.
 */

public class SilentUninstallActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText editInstallPath ;
    private Button btnInstall ;
    private DevSettings dev ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_silent_install);
        editInstallPath = (EditText) findViewById(R.id.edit_install_path);
        editInstallPath.setText("com.tencent.mobileqq");
        btnInstall = (Button) findViewById(R.id.btn_silent_install) ;
        btnInstall.setText("静默卸载");
        btnInstall.setOnClickListener(this);
        dev = new DevSettings(this);
    }

    @Override
    public void onClick(View v) {
        String packageName = editInstallPath.getText().toString() ;
        if (packageName != null && packageName.length() > 0) {
            dev.UnInstallApk(packageName);
        }
    }
}
