package com.lzf.mobilesafe.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by AZe on 2017/5/24.
 */

public class SpUtils {
    private static SharedPreferences sp;
    //写
    public static void putBoolean(Context ctx,String key,boolean value){
        //存储节点文件名称，读写方式
        if(sp == null){
            sp = ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, value).commit();
    }
    //读
    public static boolean getBoolean(Context ctx,String key,boolean defValue){
        //存储节点文件名称，读写方式
        if(sp == null){
            sp = ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, defValue);
    }

    //写
    public static void putString(Context ctx,String key,String value){
        //存储节点文件名称，读写方式
        if(sp == null){
            sp = ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).commit();
    }
    //读
    public static String getString(Context ctx,String key,String defValue){
        //存储节点文件名称，读写方式
        if(sp == null){
            sp = ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        return sp.getString(key, defValue);
    }
}
