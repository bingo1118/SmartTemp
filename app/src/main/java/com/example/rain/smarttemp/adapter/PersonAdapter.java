package com.example.rain.smarttemp.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.rain.smarttemp.activity.PassRecordByPersonIdActivity;
import com.example.rain.smarttemp.api.RequestCenter;
import com.example.rain.smarttemp.model.PassRecord;
import com.example.rain.smarttemp.model.Person;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PersonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context mContext;
    List<Person> mList;

    private final int NORMAL_ITEM=1;
    private final int FOOT_ITEM=2;
    boolean hasMore=true;

    public PersonAdapter(Context mContext, List<Person> passRecordList) {
        this.mContext = mContext;
        this.mList = passRecordList;
    }

    @Override
    public int getItemViewType(int position) {
        if(mList!=null&&position==mList.size()){
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
                view = LayoutInflater.from(mContext).inflate(R.layout.person_item,parent,false);
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
            final Person passRecord=mList.get(position);
            ((MyViewHolder)holder).name_tv.setText(passRecord.getNamed());
            ((MyViewHolder)holder).time_tv.setText(passRecord.getLastpasstime());
            ((MyViewHolder)holder).temp_tv.setText(passRecord.getTemperature()==null?"":passRecord.getTemperature()+"℃");
            ((MyViewHolder)holder).sex_tv.setText("性别:"+passRecord.getSex());
            ((MyViewHolder)holder).area_tv.setText("区域:"+passRecord.getArea());
            ((MyViewHolder)holder).apatement_tv.setText("部门:"+passRecord.getDepartment());
            Glide.with(mContext)
                    .load(RequestCenter.WEB_URL+"/personRegImg/"+passRecord.getPersonReg())
                    .apply(RequestOptions.placeholderOf(R.drawable.login_bg).error(R.drawable.login_bg).bitmapTransform(new CircleCrop()))
                    .into(((MyViewHolder)holder).photo_iv);
            ((MyViewHolder)holder).relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext, PassRecordByPersonIdActivity.class);
                    intent.putExtra("personId",passRecord.getPersonid());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(mList!=null){
            return mList.size()+1;
        }else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.rela)
        RelativeLayout relativeLayout;
        @Bind(R.id.temp_tv)
        TextView temp_tv;
        @Bind(R.id.name_tv)
        TextView name_tv;
        @Bind(R.id.time_tv)
        TextView time_tv;
        @Bind(R.id.sex_tv)
        TextView sex_tv;
        @Bind(R.id.area_tv)
        TextView area_tv;
        @Bind(R.id.apatement_tv)
        TextView apatement_tv;
        @Bind(R.id.photo_iv)
        ImageView photo_iv;

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
}
