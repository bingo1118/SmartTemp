package com.example.rain.smarttemp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.rain.smarttemp.R;
import com.example.rain.smarttemp.adapter.PassRecordAdapter;
import com.example.rain.smarttemp.api.RequestCenter;
import com.example.rain.smarttemp.model.PassRecord;
import com.example.rain.smarttemp.utils.MusicManger;
import com.example.rain.smarttemp.utils.SharedPreferencesManager;
import com.example.rain.smarttemp.view.MyImageView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlarmActivity extends Activity {
    private Context mContext;

    @Bind(R.id.alarm_fk_img)
    MyImageView alarmFkImg;
    @Bind(R.id.alarm_music_image)
    ImageView alarmMusicImage;
    @Bind(R.id.temp_tv)
    TextView temp_tv;
    @Bind(R.id.alarm_name)
    TextView alarm_name;
    @Bind(R.id.alarm_time)
    TextView alarm_time;
    @Bind(R.id.alarm_partment)
    TextView alarm_partment;
    @Bind(R.id.image)
    ImageView image;

    private boolean musicOpenOrClose = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide title
        setContentView(R.layout.activity_alarm);


        //在锁屏状态下弹出。。
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        winParams.flags |= (WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.activity_alarm);
        ButterKnife.bind(this);
        mContext = this;

        alarmInit();//imageview动画设置。。
        MusicManger.getInstance().playAlarmMusic(mContext);

        initView();
    }

    private void initView() {
        PassRecord passRecord=(PassRecord) getIntent().getSerializableExtra("info");
        temp_tv.setText(passRecord.getTemperature()+"℃");
        alarm_name.setText(passRecord.getPname());
        alarm_time.setText(passRecord.getPassTime());
        alarm_partment.setText(passRecord.getDname());
        Glide.with(mContext)
                .load(RequestCenter.WEB_URL+"/passRecordImg/"+passRecord.getImg())
                .apply(RequestOptions.placeholderOf(R.drawable.login_bg).error(R.drawable.login_bg).bitmapTransform(new CircleCrop()))
                .into(image);
    }

    private void alarmInit() {
        //imageview动画设置。。
        final AnimationDrawable anim = (AnimationDrawable) alarmFkImg
                .getBackground();
        ViewTreeObserver.OnPreDrawListener opdl = new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                anim.start();
                return true;
            }
        };
        alarmFkImg.getViewTreeObserver().addOnPreDrawListener(opdl);
    }

    @OnClick({R.id.alarm_music_image})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.alarm_music_image:
                if (musicOpenOrClose) {
                    SharedPreferencesManager.getInstance().putData(mContext,
                            "AlarmVoice",
                            "closealarmvoice",
                            System.currentTimeMillis());//@@
                    MusicManger.getInstance().stop();
                    alarmMusicImage.setImageResource(R.drawable.bj_yl_jy);
                    musicOpenOrClose = false;
                } else {
                    MusicManger.getInstance().playAlarmMusic(mContext);
                    alarmMusicImage.setImageResource(R.drawable.bj_yl);
                    musicOpenOrClose = true;
                }
                break;

            default:
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MusicManger.getInstance().stop();
    }
}
