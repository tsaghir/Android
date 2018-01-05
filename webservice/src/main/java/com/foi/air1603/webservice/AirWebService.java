package com.foi.air1603.webservice;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Karlo on 9.11.2016..
 */

public interface AirWebService {
    @FormUrlEncoded
    @POST("/")
    Call<AirWebServiceResponse> getData(@Field("method") String method, @Field("table_name") String tableName, @Field("search_by") String searchBy, @Field("value") String value, @Field("data") String data);
}
