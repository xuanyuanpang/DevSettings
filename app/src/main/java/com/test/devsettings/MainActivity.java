package com.test.devsettings;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ListView lvOption ;
    private String[] optionArray ;
    private Context mContext ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this ;
        initView() ;
    }

    private void initView() {
        lvOption = (ListView) findViewById(R.id.lv_list_option);
        optionArray = getResources().getStringArray(R.array.list_option);
        lvOption.setAdapter(new MAdapter());
    }

    class MAdapter extends BaseAdapter{
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
            ViewHolder holder ;
            if (convertView == null) {
                holder = new ViewHolder() ;
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_listviewitem, null);
                holder.tv = (TextView) convertView.findViewById(R.id.textView);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv.setText(optionArray[position]);
            return convertView;
        }

        class ViewHolder{
            TextView tv ;
        }
    }
}
