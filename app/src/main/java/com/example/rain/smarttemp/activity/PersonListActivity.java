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
import com.example.rain.smarttemp.adapter.PersonAdapter;
import com.example.rain.smarttemp.api.RequestCenter;
import com.example.rain.smarttemp.global.MyApp;
import com.example.rain.smarttemp.model.Department;
import com.example.rain.smarttemp.model.Person;
import com.example.rain.smarttemp.model.PersonListResponse;
import com.example.rain.smarttemp.utils.T;
import com.example.rain.smarttemp.view.PartmentChooseListView;
import com.google.gson.Gson;
import com.imooc.lib_network.okhttp.listener.DisposeDataListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PersonListActivity extends Activity {

    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;
    @Bind(R.id.swipere_fresh_layout)
    SwipeRefreshLayout swipereFreshLayout;
    @Bind(R.id.partment_choose)
    PartmentChooseListView partment_choose;

    Context mContext;
    LinearLayoutManager linearLayoutManager;
    PersonAdapter mAdapter;
    List<Person> passRecordList;

    private int lastVisibleItem;
    private int firstVisibleItem;
    private String mPartmentId;
    private int page=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_list);
        ButterKnife.bind(this);
        mContext=this;
        refreshListView();
        getData();
        initView();
    }

    private void initView() {
        partment_choose.setOnChildChooceClickListener(new PartmentChooseListView.OnChildChooceClickListener() {
            @Override
            public void OnChildClick(Department info) {
                page=1;
                if(info!=null){
                    mPartmentId=info.getDepartmentId()+"";
                    passRecordList.clear();
                    getData();
                }else{
                    page=1;
                    mPartmentId="";
                    passRecordList.clear();
                    getData();
                }
            }
        });
    }

    private void getData() {
        RequestCenter.getListPerson(MyApp.getUserID(),mPartmentId,page+"", new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                if(passRecordList==null){
                    passRecordList=new ArrayList<>();
                }
                PersonListResponse passRecordResponse=(PersonListResponse)responseObj;
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
                    mAdapter=new PersonAdapter(mContext,passRecordList);
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
                mPartmentId="";
                passRecordList.clear();
                partment_choose.clearView();
                getData();
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
                    getData();
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
