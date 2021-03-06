package com.imooc.lib_network.retrofit;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface api {
    @Multipart
    @POST("UploadFileAction")
    Call<ResponseBody> uploadAvatar(@Part List<MultipartBody.Part> requestBodyMap);
}
