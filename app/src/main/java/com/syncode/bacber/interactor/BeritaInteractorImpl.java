package com.syncode.bacber.interactor;

import android.util.Log;

import com.syncode.bacber.Network.Api.ApiClient;
import com.syncode.bacber.Network.Api.Api_Interface;
import com.syncode.bacber.contract.MainActivityContract;
import com.syncode.bacber.model.Berita;
import com.syncode.bacber.model.BeritaResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeritaInteractorImpl implements MainActivityContract.Interactor {


    private List<Berita> list = new ArrayList<>();
    private List<Berita> listslider = new ArrayList<>();
    private Api_Interface apiInterface;


    private static final String TAG = "beritainteractor";

    int responcode;

    public BeritaInteractorImpl() {
        apiInterface = ApiClient.getClient().create(Api_Interface.class);
    }

    @Override
    public void loadData(Integer page, String country, String apikey, String category, javax.security.auth.callback.Callback callback) {
        Call<BeritaResponse> BeritaResponseCall = apiInterface.listberita(page, country, category, apikey);
        BeritaResponseCall.enqueue(new Callback<BeritaResponse>() {
            @Override
            public void onResponse(Call<BeritaResponse> call, Response<BeritaResponse> response) {
                assert response.body() != null;
                responcode = response.code();
                if (response.code() == 200) {
                    List<Berita> beritas = response.body().getBeritaList();
                    list.addAll(beritas);
                    callback.equals(list);
                    callback.equals(responcode);
                }

            }

            @Override
            public void onFailure(Call<BeritaResponse> call, Throwable t) {
                Log.i(TAG, t.getMessage());
            }
        });

    }

    @Override
    public void loadDataSlider(String country, String apikey, String category, javax.security.auth.callback.Callback callback) {
        Call<BeritaResponse> BeritaResponseCall = apiInterface.listberita(null, country, category, apikey);
        BeritaResponseCall.enqueue(new Callback<BeritaResponse>() {
            @Override
            public void onResponse(Call<BeritaResponse> call, Response<BeritaResponse> response) {
                assert response.body() != null;
                if (response.code() == 200) {
                    List<Berita> beritas = response.body().getBeritaList();
                    listslider.addAll(beritas);
                    callback.equals(listslider);
                }
            }

            @Override
            public void onFailure(Call<BeritaResponse> call, Throwable t) {
                Log.i(TAG, t.getMessage());
            }
        });

    }


    @Override
    public int checkExist() {
        return list.size();
    }

    @Override
    public List<Berita> getData() {
        return list;
    }

    @Override
    public int checkExistSlider() {
        return listslider.size();
    }

    @Override
    public List<Berita> getDataSlider() {
        return listslider;
    }

    @Override
    public boolean getResponcode() {
        return responcode == 200;
    }
}
