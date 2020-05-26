package com.example.rain.smarttemp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;

import com.example.rain.smarttemp.R;
import com.example.rain.smarttemp.api.RequestCenter;
import com.example.rain.smarttemp.global.MyApp;
import com.example.rain.smarttemp.model.HttpResponse;
import com.example.rain.smarttemp.utils.SharedPreferencesManager;
import com.imooc.lib_network.okhttp.listener.DisposeDataListener;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //透明状态栏          
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 透明导航栏          
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置屏幕显示方向为竖屏
        mContext = this;
        init();
    }

    private void init()
    {
        /*此段代码作用是让程序在欢迎界面停留3秒之后跳转到MainActivity界面*/
        Timer timer=new Timer();//Timer类是JDK中提供的一个定时器功能，使用时会在主线程之外开启一个单独的线程执行指定任务，任务可以执行一次或者多次
        TimerTask task=new TimerTask() {//TimerTask类是一个实现了Runnable接口的抽象类，同时代表一个可以被Timer执行的任务
            @Override
            public void run() {//跳转主界面的任务代码写在TimerTask的run()方法中
                autoLogin();
            }
        };
        timer.schedule(task,3000);//timer.schedule用于开启TimerTask类 传递两个参数，第一个参数为TimerTask的对象，第二个参数为TimerTask和run()之间的时间差为3秒。
    }


    public void autoLogin() {
        String userId = SharedPreferencesManager.getInstance().getData(mContext, SharedPreferencesManager.SP_FILE_GWELL, SharedPreferencesManager.KEY_USERID);
        String userPwd = SharedPreferencesManager.getInstance().getData(mContext, SharedPreferencesManager.SP_FILE_GWELL, SharedPreferencesManager.KEY_PWD);
        if (!TextUtils.isEmpty(userId)&& !TextUtils.isEmpty(userPwd)){
            RequestCenter.login(userId, userPwd, new DisposeDataListener() {
                @Override
                public void onSuccess(Object responseObj) {
                    HttpResponse httpResponse=(HttpResponse)responseObj;
                    int errorCode=httpResponse.getErrorCode();
                    if(errorCode==0){
                        Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Object reasonObj) {
                    Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        } else {
            Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
