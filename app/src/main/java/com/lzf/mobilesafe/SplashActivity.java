package com.lzf.mobilesafe;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.lzf.mobilesafe.utils.StreamUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashActivity extends AppCompatActivity {

    private TextView tv_version_name;
    private int mLocalVersionCode;
    private String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //初始化UI
        initUI();
        //初始化数据
        initData();
    }

    /**
     * 获取数据方法
     */
    private void initData() {
        //1、应用版本名称
        tv_version_name.setText("版本名称"+getVersionName());
        // 检测（本地版本号与服务器版本号是否一致）是否有更新，如果有更新，提示用户下载
        //2、获取本地版本号
        mLocalVersionCode = getVersionCode();
        //3、获取服务器的版本号（客户端发请求，服务端给响应，json用的比较多，xml用的少）
        //json中内容包括：更新版本的版本名称、新版本的描述信息、服务器的版本号、新版本的下载地址
        checkVersion();
    }

    private void checkVersion() {
        new Thread(){
            @Override
            public void run() {
                //发送请求获取数据  new URL("http://10.0.2.2:8080/update74.json");
                try {
                    //1、封装URL地址
                    //换成电脑ip地址之后会显示需要下载，这可能是导致无法解析的源头，就是所谓的读取异常
                    URL url = new URL("http://10.108.248.154:8080/update74.json");
                    //2、开启一个链接
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //3、设置常见请求参数（请求头）
                    connection.setRequestMethod("GET");
                    //请求超时
                    connection.setConnectTimeout(2000);
                    //读取超时
                    connection.setReadTimeout(2000);

                    //4、获取响应码，响应码为200就成功
                    int code = connection.getResponseCode();
                    Log.i(tag,String.valueOf(code));
                    if(code == 200){
                        //5、以流的形式，将数据获取下来
                        InputStream is = connection.getInputStream();
                        //6、将流转换成字符串（字符串封装）
                        String json = StreamUtil.streamToString(is);
                        Log.i(tag,json);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    /**
     * 获取UI控件
     */
    private void initUI() {

        tv_version_name = (TextView) findViewById(R.id.tv_version_name);
    }

    /**
     * 获取版本名称
     * @return
     */
    private String getVersionName() {
        //1、包管理对象packageManager
        PackageManager pm = getPackageManager();
        //2、从包的管理者对象中获取指定包名基本信息（版本名称，号）
        try {
            PackageInfo packageinfo = pm.getPackageInfo(getPackageName(), 0);
            //3、获取版本信息
            return packageinfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回版本号
     * @return
     */
    private int getVersionCode() {
        //1、包管理对象packageManager
        PackageManager pm = getPackageManager();
        //2、从包的管理者对象中获取指定包名基本信息（版本名称，号）
        try {
            PackageInfo packageinfo = pm.getPackageInfo(getPackageName(), 0);
            //3、获取版本信息
            return packageinfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
