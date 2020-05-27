package com.example.rain.smarttemp.global;

import android.app.Application;
import android.text.TextUtils;

import com.example.rain.smarttemp.geTuiPush.DemoIntentService;
import com.example.rain.smarttemp.geTuiPush.DemoPushService;
import com.example.rain.smarttemp.utils.SharedPreferencesManager;
import com.igexin.sdk.PushManager;

public class MyApp extends Application {

    public static MyApp app;
    public static String userId;

    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
        //启动个推接收推送信息。。
        PushManager.getInstance().initialize(this.getApplicationContext(), DemoPushService.class);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), DemoIntentService.class);
    }

    public static void setUserID(String id){
        userId=id;
        SharedPreferencesManager.getInstance().putData(MyApp.app,
                SharedPreferencesManager.SP_FILE_GWELL,
                SharedPreferencesManager.KEY_USERID,userId);
    }




    public static String getUserID(){
        if(TextUtils.isEmpty(userId)){
            return SharedPreferencesManager.getInstance().getData(MyApp.app,
                    SharedPreferencesManager.SP_FILE_GWELL,
                    SharedPreferencesManager.KEY_USERID);
        }else{
            return userId;
        }
    }
}
