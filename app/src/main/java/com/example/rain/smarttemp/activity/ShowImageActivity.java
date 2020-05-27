package com.example.rain.smarttemp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rain.smarttemp.R;
import com.example.rain.smarttemp.api.RequestCenter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShowImageActivity extends Activity {

    @Bind(R.id.image_view)
    ImageView imageView;

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        ButterKnife.bind(this);

        url=getIntent().getStringExtra("url");
        Glide.with(this)
                .load(url)
                .apply(RequestOptions.placeholderOf(R.drawable.login_bg).error(R.drawable.login_bg))
                .into(imageView);
    }
}
