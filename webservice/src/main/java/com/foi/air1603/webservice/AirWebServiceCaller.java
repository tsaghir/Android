package com.foi.air1603.webservice;

import com.google.gson.Gson;

import java.io.File;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Karlo on 9.11.2016..
 */

public class AirWebServiceCaller {

    private final String baseUrl = "http://sportmanager.fitforev.lin25.host25.com/";
    AirWebServiceHandler mAirWebServiceHandler;
    Retrofit retrofit;
    //private final String baseUrl = "http://192.168.178.20/";

    /**
     * @param airWebServiceHandler Handler kojeg se poziva nakon što dođu podaci s web servisa
     */
    public AirWebServiceCaller(AirWebServiceHandler airWebServiceHandler) {
        this.mAirWebServiceHandler = airWebServiceHandler;

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.addInterceptor(loggingInterceptor);


        this.retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
    }

    /**
     * Metoda koja poziva web servis preko retrofita s definiranim prametrima
     *
     * @param method     Naziv metode koju servis podržava
     * @param entityType Klasa prema kojoj će GSON parsirati json
     * @param data       Podaci koji se šalju web servisu kroz get parametar npr. objekt tipa User
     */
    public void getData(String method, String tableName, String searchBy, String value, final Type entityType, Object data) {

        AirWebService serviceCaller = retrofit.create(AirWebService.class);
        Call<AirWebServiceResponse> call;
        Gson gson = new Gson();

        if (data != null) {
            call = serviceCaller.getData(method, tableName, searchBy, value, gson.toJson(data, entityType));
        } else {
            call = serviceCaller.getData(method, tableName, searchBy, value, null);
        }

        if (call != null) {
            call.enqueue(new Callback<AirWebServiceResponse>() {
                @Override
                public void onResponse(Call<AirWebServiceResponse> call, Response<AirWebServiceResponse> response) {
                    try {
                        if (response.isSuccessful()) {
                            handleResponse(response);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<AirWebServiceResponse> call, Throwable t) {
                    t.printStackTrace();
                }


            });
        }
    }

    public void uploadPicture(String fileUri, Integer user_id, String tableName) {
        PicUploadInterface serviceCaller = retrofit.create(PicUploadInterface.class);
        Call<AirWebServiceResponse> call = null;

        File file = new File(fileUri);

        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("upload", file.getName(), reqFile);

        if (fileUri != null) {
            if (tableName == "Users") {
                call = serviceCaller.postImage("changeUserPicture", user_id, body);
            } else if (tableName == "Places") {
                call = serviceCaller.postImage("uploadPlacePicture", user_id, body);
            }
        }

        if (call != null) {
            call.enqueue(new Callback<AirWebServiceResponse>() {
                @Override
                public void onResponse(Call<AirWebServiceResponse> call, Response<AirWebServiceResponse> response) {
                    handleResponse(response);
                }

                @Override
                public void onFailure(Call<AirWebServiceResponse> call, Throwable t) {
                    t.printStackTrace();
                }

            });
        }
    }

    /**
     * Metoda koju se poziva nakon što Retrofit vrati inicijalni odgovor
     *
     * @param response tipa AirWebServiceResponse kojeg vraća Retrofit
     */
    private void handleResponse(Response<AirWebServiceResponse> response) {
        System.out.println("----------------->5. AirWebServiceCaller:handleResponse");

        if (mAirWebServiceHandler != null) {
            mAirWebServiceHandler.onDataArrived(response.body(), true);
        }
    }
}
