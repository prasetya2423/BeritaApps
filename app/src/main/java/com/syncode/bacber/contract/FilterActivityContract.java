package com.syncode.bacber.contract;

import com.syncode.bacber.model.Berita;

import java.util.List;

public interface FilterActivityContract {


    interface View {
        void setData(List<Berita> beritaList);

        void errorData(String message);

        void onSwipeRefresh();
    }

    interface Presenter {
        void loadData(String country, String category, String apikey);

        void onRefresh();
    }
}
