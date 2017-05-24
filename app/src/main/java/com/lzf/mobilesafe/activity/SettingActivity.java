package com.lzf.mobilesafe.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.lzf.mobilesafe.R;
import com.lzf.mobilesafe.utils.ConstantValue;
import com.lzf.mobilesafe.utils.SpUtils;
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

    /**
     * 版本更新开关
     */
    private void initUpdate() {
        final SettingItemView siv_update =(SettingItemView) findViewById(R.id.siv_update);
        //获取已有的开关状态，用作显示
        boolean open_update = SpUtils.getBoolean(this, ConstantValue.OPEN_UPDATE,false);
        //是否选中，根据上一次存储的结果决定
        siv_update.setCheck(open_update);
        siv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果之前是选中的，点击之后变成未选中，反之变成选中
                //所以先获取点击之前的选中状态
                boolean isCheck = siv_update.isCheck();
                siv_update.setCheck(!isCheck);
                SpUtils.putBoolean(getApplicationContext(),ConstantValue.OPEN_UPDATE,!isCheck);
            }
        });
    }
}
