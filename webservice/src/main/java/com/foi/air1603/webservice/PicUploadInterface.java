package com.foi.air1603.webservice;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


/**
 * Created by Karlo on 24.12.2016..
 */

public interface PicUploadInterface {
    @Multipart
    @POST("picture-upload")
    Call<AirWebServiceResponse> postImage(@Part("method") String method, @Part("id") Integer id, @Part MultipartBody.Part image);
}
