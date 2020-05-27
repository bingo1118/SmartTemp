package com.example.rain.smarttemp.utils;

/**
 * Created by Administrator on 2016/7/29.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.rain.smarttemp.global.NpcCommon;


public class SharedPreferencesManager {
    public static final String SP_FILE_GWELL = "gwell";
    public static String KEY_USERID="KEY_USERID";
    public static String KEY_PWD="KEY_PWD";

    public static final String KEY_C_BELL_TYPE = "c_bell_type";
    public static final String KEY_C_SD_BELL = "c_sd_bell";
    public static final String KEY_C_SYS_BELL = "c_system_bell";
    public static final int TYPE_BELL_SYS = 0;
    public static final int TYPE_BELL_SD = 1;

    private String FILE_NAME="smart_temp";
    private static SharedPreferencesManager manager = null;
    private SharedPreferencesManager(){}
    public static final String LAST_AUTO_CHECK_UPDATE_TIME = "last_auto_check_update_time";
    public static final String IS_SHOW_NOTIFY = "is_show_notify";

    public synchronized static SharedPreferencesManager getInstance(){
        if(null==manager){
            synchronized(SharedPreferencesManager.class){
                if(null==manager){
                    manager = new SharedPreferencesManager();
                }
            }
        }
        return manager;
    }

    /**
     * 获取名为fileName的sharedPreferences中key为key的值
     * @param context
     * @param fileName
     * @param key
     * @return
     */
    public String getData(Context context, String fileName, String key) {
        SharedPreferences sf = context.getSharedPreferences(fileName,
                context.MODE_PRIVATE);
        return sf.getString(key, "");
    }

    public long getLongData(Context context, String fileName, String key) {
        SharedPreferences sf = context.getSharedPreferences(fileName,
                context.MODE_PRIVATE);
        return sf.getLong(key,0);
    }

    //@@5.5获取int类型
    public int getIntData(Context context, String fileName, String key) {
        SharedPreferences sf = context.getSharedPreferences(fileName,
                context.MODE_PRIVATE);
        return sf.getInt(key,0);
    }



    public void putData(Context context, String fileName, String key, String value){
        SharedPreferences sf = context.getSharedPreferences(fileName, context.MODE_PRIVATE);
        Editor editor = sf.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void putLongData(Context context, String fileName, String key, long value){
        SharedPreferences sf = context.getSharedPreferences(fileName, context.MODE_PRIVATE);
        Editor editor = sf.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public void putData(Context context, String fileName, String key, long value){
        SharedPreferences sf = context.getSharedPreferences(fileName, context.MODE_PRIVATE);
        Editor editor = sf.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    //@@5.5存储int类型
    public void putIntData(Context context, String fileName, String key, int value){
        SharedPreferences sf = context.getSharedPreferences(fileName, context.MODE_PRIVATE);
        Editor editor = sf.edit();
        editor.putInt(key, value);
        editor.commit();
    }


    public String getData(Context context, String key){
        SharedPreferences sf = context.getSharedPreferences(FILE_NAME, context.MODE_PRIVATE);
        return sf.getString(key, "");
    }
    //@@
    public int getIntData(Context context, String key){
        SharedPreferences sf = context.getSharedPreferences(FILE_NAME, context.MODE_PRIVATE);
        return sf.getInt(key,0);
    }

    public long getLongData(Context context, String key){
        SharedPreferences sf = context.getSharedPreferences(FILE_NAME, context.MODE_PRIVATE);
        return sf.getLong(key,0);
    }

    public void putData(Context context, String key, String value){
        SharedPreferences sf = context.getSharedPreferences(FILE_NAME, context.MODE_PRIVATE);
        Editor editor = sf.edit();
        editor.putString(key, value);
        editor.commit();
    }
    //@@
    public void putData(Context context, String key, int value){
        SharedPreferences sf = context.getSharedPreferences(FILE_NAME, context.MODE_PRIVATE);
        Editor editor = sf.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    //@@11.14清空文件
    public void removeData(Context context, String fileName){
        SharedPreferences sf = context.getSharedPreferences(fileName, context.MODE_PRIVATE);
        Editor editor = sf.edit();
        editor.clear().commit();
    }

    public long getLastAutoCheckUpdateTime(Context context){
        SharedPreferences sf = context.getSharedPreferences(FILE_NAME, context.MODE_PRIVATE);
        return sf.getLong(LAST_AUTO_CHECK_UPDATE_TIME, 0);
    }

    public void putLastAutoCheckUpdateTime(long time,Context context){
        SharedPreferences sf = context.getSharedPreferences(FILE_NAME, context.MODE_PRIVATE);
        Editor editor = sf.edit();
        editor.putLong(LAST_AUTO_CHECK_UPDATE_TIME, time);
        editor.commit();
    }

    public boolean getIsShowNotify(Context context){
        SharedPreferences sf = context.getSharedPreferences(FILE_NAME, context.MODE_PRIVATE);
        return sf.getBoolean(IS_SHOW_NOTIFY,true);
    }

    public int getCBellType(Context context) {
        SharedPreferences sf = context.getSharedPreferences(SP_FILE_GWELL,
                context.MODE_PRIVATE);
        return sf.getInt(NpcCommon.mThreeNum + KEY_C_BELL_TYPE, TYPE_BELL_SYS);
    }

    public int getCSdBellId(Context context) {
        SharedPreferences sf = context.getSharedPreferences(SP_FILE_GWELL,
                context.MODE_PRIVATE);
        return sf.getInt(NpcCommon.mThreeNum + KEY_C_SD_BELL, -1);
    }

    public int getCSystemBellId(Context context) {
        SharedPreferences sf = context.getSharedPreferences(SP_FILE_GWELL,
                context.MODE_PRIVATE);
        return sf.getInt(NpcCommon.mThreeNum + KEY_C_SYS_BELL, -1);
    }
}

