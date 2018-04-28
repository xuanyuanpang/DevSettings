package com.test.devsettings;

import android.content.DialogInterface;
import android.os.VibrationEffect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.rfid.AutoRunAppInfo;
import com.android.rfid.DevSettings;

import java.util.ArrayList;
import java.util.List;

public class AutoRunAppActivity extends AppCompatActivity {

    private ListView lvAuto ;
    private DevSettings dev ;
    private List<AutoRunAppInfo> listAll = new ArrayList<>();
    private List<AutoRunAppInfo> listAutoEnabe = new ArrayList<>();
    private List<AutoRunAppInfo> listAutoDisenable = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_run_app);

        dev = new DevSettings(this);


        initView() ;
    }

    private void initView(){
        lvAuto = (ListView) findViewById(R.id.list_auto);
//        listAutoEnabe = dev.queryAutoApp(true);
        listAll = dev.queryAutoApp();
        lvAuto.setAdapter(new Madapter());
        lvAuto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                createDialog(position) ;
            }
        });
    }


    private void createDialog(final int position) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        final AutoRunAppInfo info = listAll.get(position);
        if(info.isEnable()){
            builder.setTitle("是否将自启动关闭?");
        }else{
            builder.setTitle("是否将自启动打开?");
        }

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(info.isEnable()){
                    dev.setAutoApp(info, false);
                    AutoRunAppInfo newInfo = new AutoRunAppInfo();
                    newInfo.setComponentName(info.getComponentName());
                    newInfo.setPackageName(info.getPackageName());
                    newInfo.setEnable(false);
                    listAll.set(position,newInfo) ;
                }else{
                    dev.setAutoApp(info, true);
                    AutoRunAppInfo newInfo = new AutoRunAppInfo();
                    newInfo.setComponentName(info.getComponentName());
                    newInfo.setPackageName(info.getPackageName());
                    newInfo.setEnable(true);
                    listAll.set(position,newInfo) ;
                }
                lvAuto.setAdapter(new Madapter());
            }
        });
        builder.setNegativeButton("取消", null) ;
        builder.create().show();
    }

    private class Madapter extends BaseAdapter{

        @Override
        public int getCount() {
            return listAll.size();
        }

        @Override
        public Object getItem(int position) {
            return listAll.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder ;
            if (convertView == null) {
                convertView = (View) LayoutInflater.from(AutoRunAppActivity.this).
                        inflate(R.layout.item_auto, null);
                holder = new ViewHolder() ;
               holder.tvPackageName = (TextView) convertView.findViewById(R.id.tv_packageName);
                holder.tvStaus = (TextView) convertView.findViewById(R.id.tv_status);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag() ;
            }
            if(listAll != null && !listAll.isEmpty()){
                AutoRunAppInfo info = listAll.get(position) ;
                holder.tvPackageName.setText(info.getPackageName());
                if (info.isEnable()) {
                    holder.tvStaus.setText("已开");
                }else{
                    holder.tvStaus.setText("已关");
                }

            }
            return convertView;
        }


        class ViewHolder {
            TextView tvPackageName ;
            TextView tvStaus ;
        }
    }
}
