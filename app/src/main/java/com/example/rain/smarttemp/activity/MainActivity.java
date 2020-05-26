package com.example.rain.smarttemp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rain.smarttemp.R;
import com.example.rain.smarttemp.adapter.PassRecordAdapter;
import com.example.rain.smarttemp.api.RequestCenter;
import com.example.rain.smarttemp.global.MyApp;
import com.example.rain.smarttemp.model.HttpResponse;
import com.example.rain.smarttemp.model.PassRecord;
import com.example.rain.smarttemp.model.PassRecordResponse;
import com.example.rain.smarttemp.model.PassSum;
import com.example.rain.smarttemp.utils.SharedPreferencesManager;
import com.example.rain.smarttemp.utils.T;
import com.imooc.lib_network.okhttp.listener.DisposeDataListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;
    @Bind(R.id.swipere_fresh_layout)
    SwipeRefreshLayout swipereFreshLayout;
    @Bind(R.id.naviView)
    NavigationView navigationView;

    @Bind(R.id.peopleCount_tv)
    TextView peopleCount_tv;
    @Bind(R.id.personCount_tv)
    TextView personCount_tv;
    @Bind(R.id.vistorCount_tv)
    TextView vistorCount_tv;
    @Bind(R.id.unNormalCount_tv)
    TextView unNormalCount_tv;

    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @Bind(R.id.drawer_show_btn)
    ImageButton drawer_show_btn;
    Context mContext;
    LinearLayoutManager linearLayoutManager;
    PassRecordAdapter mAdapter;
    List<PassRecord> passRecordList;

    boolean research;
    private int lastVisibleItem;

    private int firstVisibleItem;
    private int page=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext=this;
        refreshListView();
        getPassRecordData();
        getPassSum();
        initView();
    }

    @OnClick({R.id.personCount_tv,R.id.vistorCount_tv,R.id.unNormalCount_tv,R.id.drawer_show_btn})
    public void onClick(View v){
        int type=0;
        Intent i=new Intent(MainActivity.this,PersonByConditionActivity.class);
        switch (v.getId()){
            case R.id.drawer_show_btn:
                if(!drawerLayout.isDrawerOpen(navigationView)){
                    drawerLayout.openDrawer(Gravity.START);
                }else{
                    drawerLayout.closeDrawer(Gravity.START);
                }
                break;
            case R.id.personCount_tv:
                type=0;
                i.putExtra("type",type+"");
                startActivity(i);
                break;
            case R.id.vistorCount_tv:
                type=1;
                i.putExtra("type",type+"");
                startActivity(i);
                break;
            case R.id.unNormalCount_tv:
                type=2;
                i.putExtra("type",type+"");
                startActivity(i);
                break;
        }

    }

    private void getPassSum() {
        RequestCenter.getPassSum(MyApp.getUserID(), new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                PassSum mResponse=(PassSum)responseObj;
                peopleCount_tv.setText(mResponse.getPeopleCount()+"");
                personCount_tv.setText(mResponse.getPersonCount()+"");
                vistorCount_tv.setText(mResponse.getVistorCount()+"");
                unNormalCount_tv.setText(mResponse.getUnNormalCount()+"");
            }

            @Override
            public void onFailure(Object reasonObj) {
                T.showShort(mContext,"获取失败");
            }
        });
    }

    private Intent intent;
    private void initView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.alarm_item:
                        intent=new Intent(MainActivity.this,AlarmHistoryActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.person_item:
                        intent=new Intent(MainActivity.this,PersonListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.exit:
                        AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                        builder.setMessage("确定退出当前账号？");
                        builder.setTitle("提示:");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                exitAcount();
                            }
                        });
                        builder.show();
                        break;
                }
                return true;
            }
        });
    }

    private void exitAcount() {
        SharedPreferencesManager.getInstance().removeData(mContext,
                SharedPreferencesManager.SP_FILE_GWELL);
        Intent in = new Intent(mContext, LoginActivity.class);
        startActivity(in);
        finish();
    }

    private void refreshListView() {
        //设置刷新时动画的颜色，可以设置4个
        swipereFreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        swipereFreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        swipereFreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        //下拉刷新。。
        swipereFreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                passRecordList.clear();
                getPassRecordData();
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                    if(mAdapter==null){
                        return;
                    }
                    int count = mAdapter.getItemCount();
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem+1 == count&&firstVisibleItem!=0) {
                        page+=1;
                        getPassRecordData();
                    }
                    return;

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                firstVisibleItem=linearLayoutManager.findFirstVisibleItemPosition();
            }
        });
    }

    private void getPassRecordData() {
        RequestCenter.getPassRecords(MyApp.getUserID(), "",page+"","","", new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                if(passRecordList==null){
                    passRecordList=new ArrayList<>();
                }
                PassRecordResponse passRecordResponse=(PassRecordResponse) responseObj;
                if(passRecordList.size()!=0){
                    if(passRecordResponse.getList().size()==0){
                        T.showShort(mContext,"已经没有更多数据");
                    }else{
                        passRecordList.addAll(passRecordResponse.getList());
                        mAdapter.notifyDataSetChanged();
                    }
                }else{
                    passRecordList=passRecordResponse.getList();
                    linearLayoutManager=new LinearLayoutManager(mContext);
                    linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    mAdapter=new PassRecordAdapter(mContext,passRecordList);
                    recyclerView.setAdapter(mAdapter);
                    swipereFreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Object reasonObj) {
                T.showShort(mContext,"获取失败");
                swipereFreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private long mExitTime;
    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}
