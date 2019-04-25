package com.syncode.bacber.Network.Api;

import com.syncode.bacber.model.BeritaResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api_Interface {

    @GET("/v2/top-headlines")
    Call<BeritaResponse> listberita(@Query("pageSize") Integer page, @Query("country") String country, @Query("category") String category, @Query("apiKey") String apikey);


}
