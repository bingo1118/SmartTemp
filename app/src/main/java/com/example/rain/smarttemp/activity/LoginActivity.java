package com.example.rain.smarttemp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.rain.smarttemp.R;
import com.example.rain.smarttemp.api.RequestCenter;
import com.example.rain.smarttemp.global.MyApp;
import com.example.rain.smarttemp.model.HttpResponse;
import com.example.rain.smarttemp.utils.SharedPreferencesManager;
import com.example.rain.smarttemp.utils.T;
import com.imooc.lib_network.okhttp.listener.DisposeDataListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends Activity {

    @Bind(R.id.userid_edit)
    EditText userid_edit;
    @Bind(R.id.psw_edit)
    EditText psw_edit;
    @Bind(R.id.btn_login)
    Button btn_login;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mContext=this;
    }

    @OnClick({R.id.btn_login})
    public void onCommit(View view){
        final String userid=userid_edit.getText().toString();
        final String pwd=psw_edit.getText().toString();
        if(!TextUtils.isEmpty(userid)&&!TextUtils.isEmpty(pwd)){
            RequestCenter.login(userid, pwd, new DisposeDataListener() {
                @Override
                public void onSuccess(Object responseObj) {
                    HttpResponse httpResponse=(HttpResponse)responseObj;
                    int errorCode=httpResponse.getErrorCode();
                    if(errorCode==0){
                        MyApp.setUserID(userid);
                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        SharedPreferencesManager.getInstance().putData(MyApp.app,
                                SharedPreferencesManager.SP_FILE_GWELL,
                                SharedPreferencesManager.KEY_USERID,userid);
                        SharedPreferencesManager.getInstance().putData(MyApp.app,
                                SharedPreferencesManager.SP_FILE_GWELL,
                                SharedPreferencesManager.KEY_PWD,pwd);
                        finish();
                    }
                }

                @Override
                public void onFailure(Object reasonObj) {

                }
            });
        }else{
            T.showShort(mContext,"输入不能为空");
        }
    }
}
