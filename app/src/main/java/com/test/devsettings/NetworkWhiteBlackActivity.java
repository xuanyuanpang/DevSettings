package com.test.devsettings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.android.rfid.DevSettings;

import java.util.List;

/**
 * 网络黑白名单设置
 */
public class NetworkWhiteBlackActivity extends AppCompatActivity implements View.OnClickListener
        , CompoundButton.OnCheckedChangeListener {

    private Switch switchWhilte;
    private Switch switchBlack;
    private ListView lvWhite;
    private ListView lvBlack;
    private Button btnAddWhite;
    private Button btnClearWhite;
    private Button btnAddBlack;
    private Button btnClearBlack;
    private LinearLayout layoutWhite;
    private LinearLayout layoutBlack;

    private SharedData shared;
    private boolean isEnableWhite;
    private boolean isEnableBlack;

    private DevSettings dev;

    private List<String> listWhite ;
    private List<String> listBlack ;
    private ArrayAdapter<String> adapterWhite ;
    private ArrayAdapter<String> adapterBlack ;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_white_black);

        dev = new DevSettings(this);
        shared = new SharedData(this);
        initView();
    }

    private void initView() {
        switchWhilte = (Switch) findViewById(R.id.switch_network_white);
        switchBlack = (Switch) findViewById(R.id.switch_network_black);
        lvWhite = (ListView) findViewById(R.id.list_network_white);
        lvBlack = (ListView) findViewById(R.id.list_network_black);
        btnAddBlack = (Button) findViewById(R.id.btn_add_black);
        btnAddWhite = (Button) findViewById(R.id.btn_add_white);
        btnClearWhite = (Button) findViewById(R.id.btn_clear_white_all);
        btnClearBlack = (Button) findViewById(R.id.btn_clear_black_all);
        layoutWhite = (LinearLayout) findViewById(R.id.layout_white);
        layoutBlack = (LinearLayout) findViewById(R.id.layout_black);

        isEnableWhite = shared.getEnableNetworkWhite();
        isEnableBlack = shared.getEnableNetworkBlack();
        listWhite = dev.queryNetworkWhiteList() ;
        listBlack = dev.queryBlackAppsList() ;
        if (listBlack != null && !listBlack.isEmpty()) {
            adapterBlack = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listBlack) ;
            lvBlack.setAdapter(adapterBlack);
        }

        if (listWhite != null && !listWhite.isEmpty()) {
            adapterWhite = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listWhite) ;
            lvWhite.setAdapter(adapterWhite);
        }
        if (!isEnableWhite) {
            switchWhilte.setChecked(false);
            layoutWhite.setVisibility(View.GONE);
        } else {
            switchWhilte.setChecked(true);
            layoutWhite.setVisibility(View.VISIBLE);
        }

        if (!isEnableBlack) {
            switchBlack.setChecked(false);
            layoutBlack.setVisibility(View.GONE);
        } else {
            switchBlack.setChecked(true);
            layoutBlack.setVisibility(View.VISIBLE);
        }


        btnAddBlack.setOnClickListener(this);
        btnAddWhite.setOnClickListener(this);
        btnClearWhite.setOnClickListener(this);
        btnClearBlack.setOnClickListener(this);

        switchWhilte.setOnCheckedChangeListener(this);
        switchBlack.setOnCheckedChangeListener(this);

        lvBlack.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                createDialogDelte(false, position);
            }
        });
        lvWhite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                createDialogDelte(true, position);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_black:
                createAddDialog(false) ;
                break;
            case R.id.btn_add_white:
                createAddDialog(true) ;
                break;
            case R.id.btn_clear_white_all:
                listWhite.removeAll(listWhite);
                dev.clearNetWorkWhiteList();
                adapterWhite.notifyDataSetChanged();
                break;
            case R.id.btn_clear_black_all:
                listBlack.removeAll(listWhite);
                dev.clearNetWorkBlackList();
                adapterBlack.notifyDataSetChanged();
                break;

        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.switch_network_white:
                if (isChecked) {
                    layoutWhite.setVisibility(View.VISIBLE);
                    shared.setEnableNetworkWhite(true);
                    //启动白名单 时关闭黑名单
                }else{
                    layoutWhite.setVisibility(View.GONE);
                    shared.setEnableNetworkWhite(false);
                }
                break;
            case R.id.switch_network_black:
                if (isChecked) {
                    layoutBlack.setVisibility(View.VISIBLE);
                    shared.setEnableNetworkBlack(true);
                    //启动黑名单 时，关闭白名单
                }else{
                    layoutBlack.setVisibility(View.GONE);
                    shared.setEnableNetworkBlack(false);
                }
                break;
        }
    }


    EditText editAdd ;
    //弹出添加窗口
    private void createAddDialog(final boolean isWhite) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        editAdd = new EditText(this);
        if (isWhite) {
            builder.setTitle("添加网络白名单");
        }else{
            builder.setTitle("添加网络黑名单");
        }
        editAdd.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        builder.setView(editAdd);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //实现添加白名单逻辑
                String web = editAdd.getText().toString() ;
                if (web != null && web.length() > 0) {
                    if (isWhite) {
                        listWhite.add(web);
                        dev.addNetWorkWhiteList(web);
                        if (adapterWhite != null) {
                            adapterWhite.notifyDataSetChanged();
                        }else{
                            adapterWhite = new ArrayAdapter<String>(NetworkWhiteBlackActivity.this, android.R.layout.simple_list_item_1, listWhite) ;
                            lvWhite.setAdapter(adapterWhite);
                        }

                    }else{
                        listBlack.add(web);
                        dev.addNetWorkBlackList(web);
                        if (adapterBlack != null) {
                            adapterBlack.notifyDataSetChanged();
                        }else{
                            adapterBlack = new ArrayAdapter<String>(NetworkWhiteBlackActivity.this, android.R.layout.simple_list_item_1, listBlack) ;
                            lvBlack.setAdapter(adapterBlack);
                        }
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "输入为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("取消", null);
        builder.create().show();

    }

    private void createDialogDelte(final boolean isWhite, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        editAdd = new EditText(this);
        editAdd.setFocusable(false);
        if (isWhite) {
            builder.setTitle("删除网络白名单");
            editAdd.setText(listWhite.get(position));
        }else{
            builder.setTitle("删除网络黑名单");
            editAdd.setText(listBlack.get(position));
        }
        editAdd.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

        builder.setView(editAdd);
        builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //实现添加白名单逻辑
                String web = editAdd.getText().toString() ;
                if (web != null && web.length() > 0) {
                    if (isWhite) {

                        dev.deleteNetWorkWhiteList(web);
                        listWhite.remove(position);
                        adapterWhite.notifyDataSetChanged();
                    }else{
                        listBlack.remove(position);
                        dev.deleteNetWorkBlackList(web);
                        adapterBlack.notifyDataSetChanged();
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "输入为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("取消", null);
        builder.create().show();

    }



//    private class MAdapter extends BaseAdapter{
//
//        private List<String> listData ;
//
//        public MAdapter(listData){
//
//        }
//
//        @Override
//        public int getCount() {
//            return 0;
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            return null;
//        }
//    }
}
