package com.example.rain.smarttemp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.rain.smarttemp.R;
import com.example.rain.smarttemp.api.RequestCenter;
import com.example.rain.smarttemp.model.PassRecord;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PassRecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context mContext;
    List<PassRecord> passRecordList;

    public PassRecordAdapter(Context mContext, List<PassRecord> passRecordList) {
        this.mContext = mContext;
        this.passRecordList = passRecordList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.pass_record_item,parent,false);
        MyViewHolder viewHolder=new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PassRecord passRecord=passRecordList.get(position);
        ((MyViewHolder)holder).name_tv.setText(passRecord.getPname());
        ((MyViewHolder)holder).time_tv.setText(passRecord.getPassTime());
        ((MyViewHolder)holder).location_tv.setText("位置:"+passRecord.getAddress());
        ((MyViewHolder)holder).device_name_tv.setText("设备:"+passRecord.getDeviceName());
        ((MyViewHolder)holder).state_tv.setText("状态:"+passRecord.getTempratureState());
        ((MyViewHolder)holder).temp_tv.setText(passRecord.getTemperature()+"℃");
        if(passRecord.getTempState()==2){
            ((MyViewHolder)holder).temp_tv.setTextColor(Color.RED);
            ((MyViewHolder)holder).state_tv.setTextColor(Color.RED);
        }else{
            ((MyViewHolder)holder).temp_tv.setTextColor(Color.GRAY);
            ((MyViewHolder)holder).state_tv.setTextColor(Color.GRAY);
        }
        Glide.with(mContext)
                .load(RequestCenter.WEB_URL+passRecord.getImg())
                .apply(RequestOptions.placeholderOf(R.drawable.login_bg).error(R.drawable.login_bg).bitmapTransform(new CircleCrop()))
                .into(((MyViewHolder)holder).photo_iv);
    }

    @Override
    public int getItemCount() {
        if(passRecordList!=null){
            return passRecordList.size();
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

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

