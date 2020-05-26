package com.example.rain.smarttemp.global;

import android.app.Application;
import android.text.TextUtils;

import com.example.rain.smarttemp.utils.SharedPreferencesManager;

public class MyApp extends Application {

    public static MyApp app;
    public static String userId;

    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
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
