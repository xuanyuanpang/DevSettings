package com.test.devsettings;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lvOption;
    private String[] optionArray;
    private Context mContext;
    private AlertDialog mAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        initObject();
        initView();
    }

    private void initObject() {
        mAlertDialog = new AlertDialog.Builder(MainActivity.this)
                .setMessage(MainActivity.this.getString(R.string.recovery_confirm_content))
                .setTitle(MainActivity.this.getString(R.string.recovery_confirm_title))
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // TODO: 2018/4/20 恢复出厂设置
                        Toast.makeText(MainActivity.this, "正在恢复，啊哈哈，骗你的", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAlertDialog.dismiss();
                    }
                })
                .create();
    }

    private void initView() {
        lvOption = findViewById(R.id.lv_list_option);
        optionArray = getResources().getStringArray(R.array.list_option);
        lvOption.setAdapter(new MAdapter());
        lvOption.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent;
        switch (position) {
            //open close option
            case 0:
                intent = new Intent(this, OpenCloseOptionActivity.class);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(MainActivity.this, WhiteBlackAppsActivity.class);
                startActivity(intent);
                break;
                // back run
            case 4:
                intent = new Intent(MainActivity.this, BackRunAppsRunActivity.class);
                startActivity(intent);
                break;
            //power off
            case 7:

                break;
            //restart
            case 8:

                break;
            case 9:
                mAlertDialog.show();
                break;
            default:
                break;
        }
    }

    class MAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return optionArray.length;
        }

        @Override
        public Object getItem(int position) {
            return optionArray[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_listviewitem, null);
                holder.tv = convertView.findViewById(R.id.textView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv.setText(optionArray[position]);
            return convertView;
        }

        class ViewHolder {
            TextView tv;
        }
    }
}
