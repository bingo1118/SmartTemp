package com.example.rain.smarttemp.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.rain.smarttemp.R;
import com.example.rain.smarttemp.activity.ShowImageActivity;
import com.example.rain.smarttemp.api.RequestCenter;
import com.example.rain.smarttemp.model.PassRecord;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PassRecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context mContext;
    List<PassRecord> passRecordList;
    boolean hasMore=true;
    private final int NORMAL_ITEM=1;
    private final int FOOT_ITEM=2;

    public PassRecordAdapter(Context mContext, List<PassRecord> passRecordList) {
        this.mContext = mContext;
        this.passRecordList = passRecordList;
    }

    @Override
    public int getItemViewType(int position) {
        if(passRecordList!=null&&position==passRecordList.size()){
            return FOOT_ITEM;
        }else{
            return NORMAL_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case NORMAL_ITEM:
                view = LayoutInflater.from(mContext).inflate(R.layout.pass_record_item,parent,false);
                holder = new MyViewHolder(view);
                break;
            case FOOT_ITEM:
                view = LayoutInflater.from(mContext).inflate(R.layout.foot_item,parent,false);
                holder = new FootViewHolder(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FootViewHolder) {
            if(hasMore){
                ((FootViewHolder) holder).progressbar.setVisibility(View.VISIBLE);
                ((FootViewHolder) holder).tip_tv.setText("正在加载更多数据");
            }else{
                ((FootViewHolder) holder).progressbar.setVisibility(View.GONE);
                ((FootViewHolder) holder).tip_tv.setText("我也是有底线的");
            }
        }else{
            final PassRecord passRecord=passRecordList.get(position);
            ((MyViewHolder)holder).name_tv.setText(passRecord.getPname());
            ((MyViewHolder)holder).time_tv.setText(passRecord.getPassTime());
            ((MyViewHolder)holder).location_tv.setText("位置:"+passRecord.getAddress());
            ((MyViewHolder)holder).device_name_tv.setText("设备:"+passRecord.getDeviceName());
            ((MyViewHolder)holder).state_tv.setText("状态:"+passRecord.getTempratureState());
            ((MyViewHolder)holder).temp_tv.setText(passRecord.getTemperature()+"℃");
            ((MyViewHolder)holder).rela.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPassRecordInfo(passRecord);
                }
            });
            if(passRecord.getTempState()==2){
                ((MyViewHolder)holder).temp_tv.setTextColor(Color.RED);
                ((MyViewHolder)holder).state_tv.setTextColor(Color.RED);
            }else{
                ((MyViewHolder)holder).temp_tv.setTextColor(Color.GRAY);
                ((MyViewHolder)holder).state_tv.setTextColor(Color.GRAY);
            }
            Glide.with(mContext)
                    .load(RequestCenter.WEB_URL+"/passRecordImg/"+passRecord.getImg())
                    .apply(RequestOptions.placeholderOf(R.drawable.login_bg).error(R.drawable.login_bg).bitmapTransform(new CircleCrop()))
                    .into(((MyViewHolder)holder).photo_iv);
        }
    }


    @Override
    public int getItemCount() {
        if(passRecordList!=null){
            return passRecordList.size()+1;
        }else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.temp_tv)
        TextView temp_tv;
        @Bind(R.id.name_tv)
        TextView name_tv;
        @Bind(R.id.time_tv)
        TextView time_tv;
        @Bind(R.id.location_tv)
        TextView location_tv;
        @Bind(R.id.device_name_tv)
        TextView device_name_tv;
        @Bind(R.id.state_tv)
        TextView state_tv;
        @Bind(R.id.photo_iv)
        ImageView photo_iv;
        @Bind(R.id.rela)
        RelativeLayout rela;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class FootViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tip_tv)
        TextView tip_tv;
        @Bind(R.id.progressbar)
        ProgressBar progressbar;

        public FootViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.name_tv)
    TextView name_tv;
    @Bind(R.id.partment_tv)
    TextView partment_tv;
    @Bind(R.id.decvice_tv)
    TextView decvice_tv;
    @Bind(R.id.temp_tv)
    TextView temp_tv;
    @Bind(R.id.state_tv)
    TextView state_tv;
    @Bind(R.id.time_tv)
    TextView time_tv;
    @Bind(R.id.location_tv)
    TextView location_tv;

    private void showPassRecordInfo(final PassRecord passRecord) {
        AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
        View view=LayoutInflater.from(mContext).inflate(R.layout.pass_record_info,null,false);
        ButterKnife.bind(this,view);
        name_tv.setText("姓名:"+(passRecord.getDname()==null?"无":passRecord.getPname()));
        partment_tv.setText("部门:"+(passRecord.getDname()==null?"无":passRecord.getDname()));
        decvice_tv.setText("设备:"+passRecord.getDeviceName());
        temp_tv.setText(passRecord.getTemperature()+"℃");
        state_tv.setText("状态:"+passRecord.getTempratureState());
        time_tv.setText("时间:"+passRecord.getPassTime());
        location_tv.setText("位置"+passRecord.getAddress());
        Glide.with(mContext)
                .load(RequestCenter.WEB_URL+"/passRecordImg/"+passRecord.getImg())
                .apply(RequestOptions.placeholderOf(R.drawable.login_bg).error(R.drawable.login_bg))
                .into(image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, ShowImageActivity.class);
                intent.putExtra("url",RequestCenter.WEB_URL+"/passRecordImg/"+passRecord.getImg());
                mContext.startActivity(intent);
            }
        });
        builder.setView(view);
        builder.show();
    }
}

