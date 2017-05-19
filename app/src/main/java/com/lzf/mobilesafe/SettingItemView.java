package com.lzf.mobilesafe;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by AZe on 2017/5/19.
 */

public class SettingItemView extends RelativeLayout {

    public SettingItemView(Context context) {
        this(context,null);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //View.inflate(context,R.layout.setting_item_view,this);
        View view = View.inflate(context,R.layout.setting_item_view,null);
        this.addView(view);
        //现在SettingItemView中已经加载了setting_item_view，可以找到里面条目
        TextView tv_title =(TextView)this.findViewById(R.id.tv_title);
        TextView tv_des =(TextView)this.findViewById(R.id.tv_des);
        CheckBox cb_box =(CheckBox)this.findViewById(R.id.cb_box);
    }
}
