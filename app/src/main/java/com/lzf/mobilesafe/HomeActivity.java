package com.lzf.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

/**
 * Created by AZe on 2017/5/18.
 */
public class HomeActivity extends Activity {

    private GridView gv_home;
    private String[] mTitleStr;
    private int[] mDrawableIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initUI();
        initData();

    }

    private void initData() {
        mTitleStr = new String[]{
          "手机防盗","通信卫士","软件管理","进程管理","流量统计","手机杀毒",
                "缓存清理","高级工具","设置中心"
        };
        mDrawableIds = new int[]{
          R.drawable.home_safe,R.drawable.home_callmsgsafe,R.drawable.home_apps,
                R.drawable.home_taskmanager, R.drawable.home_netmanager,R.drawable.home_trojan,
                R.drawable.home_sysoptimize,R.drawable.home_tools,
                R.drawable.home_settings
        };
        //九宫格控件设置数据适配器
        gv_home.setAdapter(new MyAdapter());
        //为item条目设置点击事件
        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 8:
                        Intent intent = new Intent(getApplicationContext(),SettingActivity.class);
                        startActivity(intent);
                        break;
                }

            }
        });
    }

    private void initUI() {
        gv_home = (GridView) findViewById(R.id.gv_home);
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mTitleStr.length;
        }

        @Override
        public Object getItem(int position) {
            return mTitleStr[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //把布局文件找出来inflate方法，因为此处的gridview_item不是activity_home里的
            View view = View.inflate(getApplicationContext(),R.layout.gridview_item,null);
            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_title.setText(mTitleStr[position]);
            ImageView iv_icon =(ImageView) view.findViewById(R.id.iv_icon);
            iv_icon.setImageResource(mDrawableIds[position]);
            return view;
        }
    }
}
