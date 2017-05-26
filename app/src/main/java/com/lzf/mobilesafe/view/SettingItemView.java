package com.lzf.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzf.mobilesafe.R;

/**
 * Created by AZe on 2017/5/19.
 */

public class SettingItemView extends RelativeLayout {

    private static final String NAMESPACE = "http://schemas.android.com/apk/res/com.lzf.mobilesafe";
    private CheckBox cb_box;
    private TextView tv_des;
    private String tag;
    private String mDestitle;
    private String mDesoff;
    private String mDeson;

    public SettingItemView(Context context) {
        this(context,null);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //View.inflate(context,R.layout.setting_item_view,this);将设置界面的一个条目转换成
        //view对象，之后显示在手机中
        View view = View.inflate(context, R.layout.setting_item_view,null);
        this.addView(view);
        //现在SettingItemView中已经加载了setting_item_view，可以找到里面条目
        TextView tv_title =(TextView)this.findViewById(R.id.tv_title);
        tv_des = (TextView)this.findViewById(R.id.tv_des);
        cb_box = (CheckBox)this.findViewById(R.id.cb_box);
        //获取自定义以及原生属性的操作，写在此构造方法处，AttributeSet attrs对象中获取
        initAttrs(attrs);
        //tv_des是控件，mDesoff是属性
        tv_title.setText(mDestitle);
    }

    /**
     * 构造方法中维护好的属性集合
     * 返回属性集合中自定义的属性集
     */
    private void initAttrs(AttributeSet attrs) {
        //获取属性总个数
        //Log.i(tag,"attrs.getAttributeCout() = "+ attrs.getAttributeCount());
        //获取属性名称以及属性值
        //for(int i=0;i<attrs.getAttributeCount();i++){
           // Log.i(tag,"name ="+attrs.getAttributeName(i));
           // Log.i(tag,"value ="+attrs.getAttributeValue(i));
       // }
        mDestitle = attrs.getAttributeValue(NAMESPACE,"destitle");
        mDesoff = attrs.getAttributeValue(NAMESPACE,"desoff");
        mDeson = attrs.getAttributeValue(NAMESPACE,"deson");
        Log.i(tag,mDestitle);
    }

    /**
     *
     * @return 返回当前SettingItemView是否是选中状态
     */
    public boolean isCheck(){
        return cb_box.isChecked();
    }
    public void setCheck(boolean isCheck){
        //当前条目在选择过程中，cb_box的选中状态也在跟随isCheck变化
        cb_box.setChecked(isCheck);
        //文字需要变化
        if(isCheck){
            //开启
            tv_des.setText(mDeson);
        }else {
            tv_des.setText(mDesoff);
        }
    }
}
