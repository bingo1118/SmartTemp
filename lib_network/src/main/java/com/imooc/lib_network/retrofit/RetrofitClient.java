package com.imooc.lib_network.retrofit;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RetrofitClient {

    public void uploadFile(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://139.159.220.138:51091/fireSystem/")
                .build();


        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/devimage.jpg");//@@9.30
//        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        List<MultipartBody.Part> parts = new ArrayList<>();
        parts.add(toRequestBodyOfText("mac", "11110202"));//文字
        parts.add(toRequestBodyOfText("location", "bingo"));//文字
        parts.add(toRequestBodyOfImage("image",file));//图片

        api request = retrofit.create(api.class);
        //对 发送请求 进行封装
        Call<ResponseBody> call = request.uploadAvatar(parts);
        call.enqueue(new Callback<ResponseBody>() {
            //请求成功时回调
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //请求处理,输出结果
                response.body();
            }
            //请求失败时候的回调
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                System.out.println("连接失败");
            }
        });

    }

    //文字
    private MultipartBody.Part toRequestBodyOfText (String keyStr, String value) {
        MultipartBody.Part body = MultipartBody.Part.createFormData(keyStr, value);
        return body;
    }
    //keyStr决定了你要上传到服务器的名称
    //pFile.getName() 表示文件的名称
    //图片
    private MultipartBody.Part toRequestBodyOfImage(String keyStr, File pFile){
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), pFile);
        MultipartBody.Part filedata = MultipartBody.Part.createFormData(keyStr, pFile.getName(), requestBody);
        return filedata;
    }
}
