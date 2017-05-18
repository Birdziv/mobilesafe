package com.lzf.mobilesafe.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by AZe on 2017/5/18.
 */

public class ToastUtil {
    public static void show(Context ctx, String msg){
        Toast.makeText(ctx,msg,0).show();
    }
}
