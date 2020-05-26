package com.example.rain.smarttemp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.rain.smarttemp.R;
import com.example.rain.smarttemp.api.RequestCenter;
import com.example.rain.smarttemp.global.MyApp;
import com.example.rain.smarttemp.model.DePartmentResonse;
import com.example.rain.smarttemp.model.Department;
import com.example.rain.smarttemp.model.PassSum;
import com.example.rain.smarttemp.utils.T;
import com.imooc.lib_network.okhttp.listener.DisposeDataListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class PartmentChooseListView extends LinearLayout {

    private String hint_Text="";
    private TextView editText;
    private ImageView imageView;
    private LinearLayout search_line;
    private PopupWindow popupWindow = null;
    private List<Object> dataList = new ArrayList<>();
    private View mView;
    private Context mContext;
    ProgressBar loading_prg_monitor;
    private RelativeLayout clear_choice;

    List<Department> mData;

    public PartmentChooseListView(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub
    }

    public PartmentChooseListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        // TODO Auto-generated constructor stub
    }

    public PartmentChooseListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PartmentChooseListView);
        if (typedArray != null) {
            hint_Text= typedArray.getString(R.styleable.PartmentChooseListView_hint_Text);
            typedArray.recycle();
        }
        initView();
    }

    public void initView() {
        mContext = getContext();



        String infServie = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater layoutInflater;
        layoutInflater = (LayoutInflater) getContext().getSystemService(infServie);
        View view = layoutInflater.inflate(R.layout.dropdownlist_view, this, true);
        loading_prg_monitor = (ProgressBar) findViewById(R.id.loading_prg_monitor);
        editText = (TextView) findViewById(R.id.text);
        editText.setHint(hint_Text);
        imageView = (ImageView) findViewById(R.id.btn);
        clear_choice = (RelativeLayout) findViewById(R.id.clear_choice);
        clear_choice.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onChildChooceClickListener!=null){
                    onChildChooceClickListener.OnChildClick(null);
                    clearView();
                }
            }
        });
        getAreaListData();
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopWindow();
            }
        });
    }

    public void clearView() {
        editText.setText("");
        clear_choice.setVisibility(GONE);
        imageView.setVisibility(VISIBLE);
    }

    private void getAreaListData() {
        showLoading();
        RequestCenter.getDepartment( new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                DePartmentResonse mResponse=(DePartmentResonse)responseObj;
                List<Department> mList=mResponse.getList();
                mData = mList;
                closeLoading();
                setClickable(true);
            }

            @Override
            public void onFailure(Object reasonObj) {
                closeLoading();
                T.showShort(mContext,"获取失败");
            }
        });
    }

    public void showLoading() {
        imageView.setVisibility(View.GONE);
        loading_prg_monitor.setVisibility(View.VISIBLE);
    }

    public void closeLoading() {
        loading_prg_monitor.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
    }

    /**
     * 打开下拉列表弹窗
     */
    public void showPopWindow() {
        // 加载popupWindow的布局文件
        String infServie = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater layoutInflater;
        layoutInflater = (LayoutInflater) getContext().getSystemService(infServie);
        View contentView = layoutInflater.inflate(R.layout.dropdownlist_popupwindow, null, false);
        search_line = contentView.findViewById(R.id.search_line);
        TextView info_tv = contentView.findViewById(R.id.info_tv);
        search_line.setVisibility(GONE);
        PartmentChooseListView.XCDropDownListAdapter xcDropDownListAdapter = new PartmentChooseListView.XCDropDownListAdapter(getContext());
        ListView listView = (ListView) contentView.findViewById(R.id.listView);

        listView.setAdapter(xcDropDownListAdapter);
        popupWindow = new PopupWindow(contentView, LayoutParams.WRAP_CONTENT, 800, true);

        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.list_item_color_bg));
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popupWindow = null;
            }
        });//@@12.20
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(this);
    }

    /**
     * 关闭下拉列表弹窗
     */
    public void closePopWindow() {
        popupWindow.dismiss();
        popupWindow = null;
    }

    public boolean ifShow() {
        if (popupWindow == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 设置数据
     *
     * @param list
     */
    public void setItemsData(List<Object> list) {
        dataList = list;
        editText.setText("");
    }

    public void setEditTextData(String editTextData) {
        editText.setText(editTextData);
    }

    /**
     * 数据适配器
     *
     * @author caizhiming
     */
    class XCDropDownListAdapter extends BaseAdapter {

        Context mContext;
        LayoutInflater inflater;

        public XCDropDownListAdapter(Context ctx) {
            mContext = ctx;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            // 自定义视图
            PartmentChooseListView.ListItemView listItemView = null;
            if (convertView == null) {
                // 获取list_item布局文件的视图
                convertView = LayoutInflater.from(mContext).inflate(R.layout.dropdownlist_item, null);

                listItemView = new PartmentChooseListView.ListItemView();
                // 获取控件对象
                listItemView.tv = (TextView) convertView
                        .findViewById(R.id.tv);
                listItemView.layout = (LinearLayout) convertView.findViewById(R.id.layout_container);
                // 设置控件集到convertView
                convertView.setTag(listItemView);
            } else {
                listItemView = (PartmentChooseListView.ListItemView) convertView.getTag();
            }
            final Department mAlarmType = mData.get(position);;
            // 设置数据
            listItemView.tv.setText(mAlarmType.getName());
            final String text = mAlarmType.getName();
            listItemView.layout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    editText.setText(text);
                    imageView.setVisibility(View.GONE);
                    clear_choice.setVisibility(View.VISIBLE);
                    if(onChildChooceClickListener!=null){
                        onChildChooceClickListener.OnChildClick(mAlarmType);
                    }
                    closePopWindow();
                }
            });

            return convertView;
        }

    }

    private static class ListItemView {
        TextView tv;
        LinearLayout layout;
    }

    public void addFinish() {
        imageView.setVisibility(View.VISIBLE);
        clear_choice.setVisibility(View.GONE);
    }

    public void setEditTextHint(String textStr) {
        editText.setHint(textStr);
    }

    PartmentChooseListView.OnChildChooceClickListener onChildChooceClickListener = null;//@@9.12

    public interface OnChildChooceClickListener {
        void OnChildClick(Department info);
    }

    public void setOnChildChooceClickListener(PartmentChooseListView.OnChildChooceClickListener o) {
        this.onChildChooceClickListener = o;
    }
}

