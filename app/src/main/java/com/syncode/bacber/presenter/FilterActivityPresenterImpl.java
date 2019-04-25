package com.syncode.bacber.presenter;

import com.syncode.bacber.contract.FilterActivityContract;
import com.syncode.bacber.contract.MainActivityContract;
import com.syncode.bacber.interactor.BeritaInteractorImpl;

import javax.security.auth.callback.Callback;

import androidx.annotation.Nullable;

public class FilterActivityPresenterImpl implements FilterActivityContract.Presenter {


    private FilterActivityContract.View view;
    private MainActivityContract.Interactor interactor;

    public FilterActivityPresenterImpl(FilterActivityContract.View view) {
        this.view = view;
        interactor = new BeritaInteractorImpl();
    }

    @Override
    public void loadData(String country, String category, String apikey) {
        interactor.loadData(38, country, apikey, category, new Callback() {
            @Override
            public boolean equals(@Nullable Object obj) {
                if (interactor.checkExist() > 0) {
                    view.setData(interactor.getData());
                } else {
                    view.errorData("Data Not Found");
                }
                return true;
            }
        });
    }

    @Override
    public void onRefresh() {
        if (interactor.checkExist() > 0) {
            interactor.getData().clear();
            view.onSwipeRefresh();
        }
    }
}
