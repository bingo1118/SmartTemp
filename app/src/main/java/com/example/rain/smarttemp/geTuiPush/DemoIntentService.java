package com.example.rain.smarttemp.geTuiPush;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.example.rain.smarttemp.activity.AlarmActivity;
import com.example.rain.smarttemp.model.PassRecord;
import com.example.rain.smarttemp.utils.SharedPreferencesManager;
import com.google.gson.Gson;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Random;


/**
 * Created by Administrator on 2016/12/2.
 */
public class DemoIntentService extends GTIntentService {
    @Override
    public void onReceiveServicePid(Context context, int i) {

    }


    @Override
    public void onReceiveClientId(Context context, String cid) {
        String userID = SharedPreferencesManager.getInstance().getData(context,
                SharedPreferencesManager.SP_FILE_GWELL,
                SharedPreferencesManager.KEY_USERID);
        SharedPreferencesManager.getInstance().putData(context,SharedPreferencesManager.SP_FILE_GWELL,
                "CID",cid);//@@5.27存储个推cid
        PushManager.getInstance().bindAlias(this.getApplicationContext(),userID);
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage gtTransmitMessage) {
        String msg = new String(gtTransmitMessage.getPayload());
        boolean showDateChange=false;
        try {
            PassRecord passRecord = new Gson().fromJson(msg, PassRecord.class);

            Intent intent2 = new Intent(context, AlarmActivity.class);
            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent2.putExtra("info",passRecord);
            context.startActivity(intent2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onReceiveOnlineState(Context context, boolean b) {

    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage gtCmdMessage) {

    }

    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage gtNotificationMessage) {

    }

    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage gtNotificationMessage) {

    }
}
