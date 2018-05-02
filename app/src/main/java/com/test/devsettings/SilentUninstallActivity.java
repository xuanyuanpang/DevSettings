package com.test.devsettings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.rfid.DevSettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/4/28.
 */

public class SilentUninstallActivity extends AppCompatActivity implements View.OnClickListener ,
        AdapterView.OnItemClickListener{
    private EditText editInstallPath ;
    private Button btnInstall ;
    private DevSettings dev ;

    private ListView lvApp ;
    private Button btnFlush ;

//    ArrayList<PackageInfo> appList = new ArrayList<PackageInfo>(); //用来存储获取的应用信息数据
    ArrayList<String> appPackageNameList = new ArrayList<>() ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_silent_uninstall);
        dev = new DevSettings(this);
        initView() ;
    }

    private void initView() {
        lvApp = (ListView) findViewById(R.id.lv_uninstall_app);
        lvApp.setOnItemClickListener(this);
        btnFlush = (Button) findViewById(R.id.btn_flush) ;
        btnFlush.setOnClickListener(this);
        appPackageNameList = new ArrayList<>() ;
        getInstalledApp();
        lvApp.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                appPackageNameList));
    }

    @Override
    public void onClick(View v) {
        appPackageNameList = new ArrayList<>() ;
        getInstalledApp();
        lvApp.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                appPackageNameList));
    }

    private void getInstalledApp(){
        //获取所有已安装的应用信息
        List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
        for(int i=0;i<packages.size();i++) {
            PackageInfo packageInfo = packages.get(i);
            //非系统级应用
            if((packageInfo.applicationInfo.flags& ApplicationInfo.FLAG_SYSTEM)==0){
//                appList.add(packageInfo);
                appPackageNameList.add(packageInfo.packageName) ;
            }
        }
        }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        createDialog(position);
    }


    //卸载对话框
    private void createDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("是否卸载\n" + appPackageNameList.get(position)) ;
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dev.UnInstallApk(appPackageNameList.get(position));
            }
        });
        builder.setNegativeButton("取消", null) ;
        builder.create().show();
    }
}
