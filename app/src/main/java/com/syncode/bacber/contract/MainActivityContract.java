package com.syncode.bacber.contract;

import com.syncode.bacber.model.Berita;

import java.util.List;

import javax.security.auth.callback.Callback;

public interface MainActivityContract {

    interface view {
        void setData(List<Berita> beritaList);

        void errorData(String message);

        void setDataslider(List<Berita> beritaList);

        void onSwipeRefresh();

        void onShowDialogFilter();


    }

    interface Presenter {
        void loadData(String country, String category, String apikey);

        void loadDataSlider(String apikey, String category, String country);

        void onRefresh();

        void dialogShow();

        void createDialog();
    }

    interface Interactor {
        void loadData(Integer page, String country, String apikey, String category, Callback callback);

        void loadDataSlider(String country, String apikey, String category, Callback callback);

        int checkExist();

        List<Berita> getData();

        int checkExistSlider();

        List<Berita> getDataSlider();

        boolean getResponcode();


    }
}
