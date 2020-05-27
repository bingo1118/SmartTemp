package com.example.rain.smarttemp.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;

import com.example.rain.smarttemp.R;
import com.example.rain.smarttemp.adapter.PassRecordAdapter;
import com.example.rain.smarttemp.adapter.PersonAdapter;
import com.example.rain.smarttemp.api.RequestCenter;
import com.example.rain.smarttemp.global.MyApp;
import com.example.rain.smarttemp.model.PassPersonByConditionResponse;
import com.example.rain.smarttemp.model.PassRecord;
import com.example.rain.smarttemp.model.PassRecordResponse;
import com.example.rain.smarttemp.model.Person;
import com.example.rain.smarttemp.model.PersonListResponse;
import com.example.rain.smarttemp.utils.T;
import com.google.gson.Gson;
import com.imooc.lib_network.okhttp.listener.DisposeDataListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PersonByConditionActivity extends Activity {

    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;
    @Bind(R.id.swipere_fresh_layout)
    SwipeRefreshLayout swipereFreshLayout;

    Context mContext;
    LinearLayoutManager linearLayoutManager;
    PassRecordAdapter mAdapter;
    List<PassRecord> passRecordList;

    private int lastVisibleItem;
    private int firstVisibleItem;
    private String type;
    private int page=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_by_condition);

        ButterKnife.bind(this);
        mContext=this;
        type=getIntent().getStringExtra("type");//0 员工 1访客 2异常
        refreshListView();
        getPassPersonData();
    }

    private void getPassPersonData() {
        switch (type){
            case "0":
                RequestCenter.getPassRecords(MyApp.getUserID(), "",page+"","","","1","1", new DisposeDataListener() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        if(passRecordList==null){
                            passRecordList=new ArrayList<>();
                        }
                        PassRecordResponse passRecordResponse=(PassRecordResponse)responseObj;
                        if(passRecordList.size()!=0){
                            if(passRecordResponse.getList().size()==0){
                                mAdapter.setHasMore(false);
                            }else{
                                passRecordList.addAll(passRecordResponse.getList());
                            }
                            mAdapter.notifyDataSetChanged();
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
                break;
            case "1":
                RequestCenter.getPassRecords(MyApp.getUserID(), "",page+"","","","2","1", new DisposeDataListener() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        if(passRecordList==null){
                            passRecordList=new ArrayList<>();
                        }
                        PassRecordResponse passRecordResponse=(PassRecordResponse)responseObj;
                        if(passRecordList.size()!=0){
                            if(passRecordResponse.getList().size()==0){

                                mAdapter.setHasMore(false);
                            }else{
                                passRecordList.addAll(passRecordResponse.getList());
                            }
                            mAdapter.notifyDataSetChanged();
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
                break;
            case "2":
                RequestCenter.getAlarmList(MyApp.getUserID(), page+"","","","", "1",new DisposeDataListener() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        if(passRecordList==null){
                            passRecordList=new ArrayList<>();
                        }
                        PassRecordResponse passRecordResponse=(PassRecordResponse) responseObj;
                        if(passRecordList.size()!=0){
                            if(passRecordResponse.getList().size()==0){
                                mAdapter.setHasMore(false);
                            }else{
                                passRecordList.addAll(passRecordResponse.getList());
                            }
                            mAdapter.notifyDataSetChanged();
                        }else{
                            passRecordList=passRecordResponse.getList();
                            linearLayoutManager=new LinearLayoutManager(mContext);
                            linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
                            recyclerView.setLayoutManager(linearLayoutManager);
                            mAdapter=new PassRecordAdapter(mContext,passRecordList);
                            recyclerView.setAdapter(mAdapter);
                            if(passRecordList.size()<10){
                                mAdapter.setHasMore(false);
                            }
                            swipereFreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onFailure(Object reasonObj) {
                        T.showShort(mContext,"获取失败");
                        swipereFreshLayout.setRefreshing(false);
                    }
                });
                break;
        }

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
                getPassPersonData();
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
                        getPassPersonData();
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
}
