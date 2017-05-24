package com.lzf.mobilesafe.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.lzf.mobilesafe.R;
import com.lzf.mobilesafe.view.SettingItemView;

/**
 * Created by AZe on 2017/5/19.
 */
public class SettingActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        
        initUpdate();
    }

    private void initUpdate() {
        final SettingItemView siv_update =(SettingItemView) findViewById(R.id.siv_update);
        siv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果之前是选中的，点击之后变成未选中，反之变成选中
                //所以先获取点击之前的选中状态
                boolean isCheck = siv_update.isCheck();
                siv_update.setCheck(!isCheck);
            }
        });
    }
}
