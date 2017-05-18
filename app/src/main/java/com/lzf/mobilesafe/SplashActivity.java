package com.lzf.mobilesafe;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lzf.mobilesafe.utils.StreamUtil;
import com.lzf.mobilesafe.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashActivity extends AppCompatActivity {

    private static final int UPDATE_VERSION = 100;
    private static final int ENTER_HOME = 101;
    private static final int URL_ERROR = 102;
    private static final int IO_ERROR = 103;
    private static final int JSON_ERROR = 104;
    private TextView tv_version_name;
    private int mLocalVersionCode;
    private String tag;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_VERSION:
                    //弹出对话框提示用户更新
                    showUpdateDialog();
                    break;
                case ENTER_HOME :
                    //进入程序主界面
                    enterhome();
                    break;
                case IO_ERROR:
                    //弹出对话框提示用户更新
                    ToastUtil.show(SplashActivity.this,"读取异常");
                    enterhome();
                    break;
                case URL_ERROR:
                    //弹出对话框提示用户更新
                    ToastUtil.show(SplashActivity.this,"url异常");
                    enterhome();
                    break;
                case JSON_ERROR:
                    //弹出对话框提示用户更新
                    ToastUtil.show(SplashActivity.this,"json解析异常");
                    enterhome();
                    break;
            }
        }

    };
    private String mVersionDes;
    private String mDownloadUrl;

    /**
     * 弹出对话框，提示用户更新
     */

    private void showUpdateDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("版本更新");
        builder.setMessage(mVersionDes);
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //下载apk,apk链接地址
                downloadApk();
            }
        });
        builder.setNegativeButton("稍后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enterhome();

            }
        });
        builder.show();
    }

    private void downloadApk() {
        //1、apk下载链接地址，放置apk的所在路径
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //2、获取sd卡的路径
            String path = Environment.getExternalStorageDirectory().getAbsolutePath()+
                    File.separator+"mobilesafe74.apk";
            //发送请求，获取apk，并且放置到指定路径
            HttpUtils httpUtils = new HttpUtils();
            //发送请求，传递参数
            httpUtils.download(mDownloadUrl, path, new RequestCallBack<File>() {
                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {
                    //下载成功
                    Log.i(tag,"下载成功");
                    File file = responseInfo.result;
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    //下载失败
                    Log.i(tag,"下载失败");

                }
                //刚刚开始下载的方法
                @Override
                public void onStart() {
                    Log.i(tag,"刚刚开始下载");
                    super.onStart();
                }
                //下载过程中的方法
                @Override
                public void onLoading(long total, long current, boolean isUploading) {
                    Log.i(tag,"下载中");
                    super.onLoading(total, current, isUploading);
                }
            });
        }
    }

    private void enterhome() {
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
        //在开启一个页面后，将导航界面关闭（导航界面只可见一次）
        finish();
    }

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
                Message msg = new Message();
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
                        //7、json解析
                        JSONObject jsonObject = new JSONObject(json);

                        String versionName = jsonObject.getString("versionName");
                        mVersionDes = jsonObject.getString("versionDes");
                        String versionCode = jsonObject.getString("versionCode");
                        mDownloadUrl = jsonObject.getString("downloadUrl");

                        //8、比对版本号（服务器版本号>本地版本号，提示用户更新）
                        if(mLocalVersionCode < Integer.parseInt(versionCode)){
                            //提示用户更新，弹出对话框（UI），消息机制
                            msg.what = UPDATE_VERSION;
                        }else{
                            //进入程序主界面
                            msg.what = ENTER_HOME;
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    msg.what = URL_ERROR;
                }catch (IOException e) {
                    e.printStackTrace();
                    msg.what = IO_ERROR;
                }catch (JSONException e) {
                    e.printStackTrace();
                    msg.what = JSON_ERROR;
                }finally {
                    mHandler.sendMessage(msg);
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
