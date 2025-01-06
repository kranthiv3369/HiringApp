package com.hiring.network;

import com.hiring.model.CandidateModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface NetworkServiceApi {
    @GET("hiring.json")
    Call<List<CandidateModel>> getFetchItems();

    static NetworkServiceApi getInstance() {
        return new Retrofit.Builder()
                .baseUrl("https://fetch-hiring.s3.amazonaws.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NetworkServiceApi.class);
    }
}